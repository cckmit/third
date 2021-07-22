package org.opoo.apps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.opoo.apps.util.FileUtils;

/**
 * Provides the the ability to use simple XML property files. Each property is in the form X.Y.Z, which would map to an
 * XML snippet of:
 * <pre>
 * &lt;X&gt;
 *     &lt;Y&gt;
 *         &lt;Z&gt;someValue&lt;/Z&gt;
 *     &lt;/Y&gt;
 * &lt;/X&gt;
 * </pre>
 * <p/>
 * The XML file is passed in to the constructor and must be readable and writable. Setting property values will
 * automatically persist those value to disk. The file encoding used is UTF-8.
 */
public class XMLAppsProperties extends AbstractAppsProperties implements AppsProperties<String, String> {
	private static final Log log = LogFactory.getLog(XMLAppsProperties.class);

	private File file;
    private Document doc;

    /**
     * Parsing the XML file every time we need a property is slow. Therefore, we use a Map to cache property values that
     * are accessed more than once.
     */
    private Map<String, String> propertyCache = new HashMap<String, String>();
    /**
     * A locking object for synchronization.
     */
    private Object propLock = new Object();

    /**
     * Creates a properties object given an input stream of valid XML.
     *
     * @param in an input stream of XML.
     * @throws Exception if an exception occurs while parsing.
     */
    public XMLAppsProperties(InputStream in) throws Exception {
        Reader reader = new BufferedReader(new InputStreamReader(in));
        buildDoc(reader);
    }

    /**
     * Creates a new XMLJiveProperties object from the given file of XML.
     *
     * @param fileName the full path the file that properties should be read from and written to.
     * @throws IOException if an exception occurs reading or parsing the file.
     */
    public XMLAppsProperties(String fileName) throws IOException {
        final Logger log = LogManager.getLogger(XMLAppsProperties.class);
        this.file = new File(fileName);
        if (!file.exists()) {
            // Attempt to recover from this error case by seeing if the tmp file exists. It's
            // possible that the rename of the tmp file failed the last time Jive was running,
            // but that it exists now.
            File tempFile;
            tempFile = new File(file.getParentFile(), file.getName() + ".tmp");
            if (tempFile.exists()) {
                log.error("WARNING: " + fileName + " was not found, but temp file from "
                        + "previous write operation was. Attempting automatic recovery. Please "
                        + "check file for data consistency.");
                tempFile.renameTo(file);
            }
            // There isn't a possible way to recover from the file not being there, so throw an
            // error.
            else {
                throw new FileNotFoundException("XML properties file does not exist: " + fileName);
            }
        }
        else {
            // Make sure there is no temp file. If there is one, then the last file
            // write process might not have finished correctly. In that case, we'll
            // attempt an automatic recovery based on the following algorithm:
            //  1) If the temp file is newer than the main file, rename the main file
            //     to a bak file (using a timestamp for naming), then rename the temp
            //     file to the main file. However, we make sure that the temp file
            //     is valid xml (in case writing out the temp file failed). If it's
            //     not, we rename it to a bak file.
            //  2) If the main file is newer than the temp file, ensure that it's valid
            //     XML. If it is, rename the temp file to a bak file (using a timestamp
            //     for naming) then delete the temp file. If the main file is not
            //     valid XML, rename the main file to a bak file, then rename the temp
            //     file to the main file.
            File tempFile;
            tempFile = new File(file.getParentFile(), file.getName() + ".tmp");
            if (tempFile.exists()) {
                log.error("WARNING: found a temp file: " + tempFile.getName() + ". This may "
                        + "indicate that a previous write operation failed. Attempting automatic "
                        + "recovery. Please check file " + fileName + " for data consistency.");
                // If the temp file is newer, get rid of the main file and use the temp file.
                if (tempFile.lastModified() > file.lastModified()) {
                    // Make sure the temp file is valid XML.
                    boolean error = false;
                    Reader reader = null;
                    try {
                        reader = new InputStreamReader(FileUtils.newFileInputStream(tempFile), "UTF-8");
                        SAXReader xmlReader = new SAXReader();
                        xmlReader.read(reader);
                    }
                    catch (Exception e) {
                        error = true;
                    }
                    finally {
                        try {
                            reader.close();
                        }
                        catch (Exception e) {
                        }
                    }
                    // If there was an error in the temp file XML, rename the temp file to a bak.
                    if (error) {
                        String bakFile = tempFile.getName() + "-" + System.currentTimeMillis() + ".bak";
                        tempFile.renameTo(new File(tempFile.getParentFile(), bakFile));
                    }
                    // Otherwise rename the main file to a bak, and the temp file to the main file.
                    else {
                        String bakFile = file.getName() + "-" + System.currentTimeMillis() + ".bak";
                        file.renameTo(new File(file.getParentFile(), bakFile));
                        // Rename operations don't seem to release all file resources
                        // on some OS's. Therefore, wait 100 ms before continuing with
                        // the next rename operation.
                        try {
                            Thread.sleep(100);
                        }
                        catch (Exception e) {
                        }
                        // Now rename the temp file to the main file.
                        tempFile.renameTo(file);
                    }
                }
                else {
                    // First, see if the main file is valid XML.
                    boolean error = false;
                    Reader reader = null;
                    try {
                        reader = new InputStreamReader(FileUtils.newFileInputStream(file), "UTF-8");
                        SAXReader xmlReader = new SAXReader();
                        xmlReader.read(reader);
                    }
                    catch (Exception e) {
                        error = true;
                    }
                    finally {
                        try {
                            reader.close();
                        }
                        catch (Exception e) {
                        }
                    }
                    // If there was an error, the main file is corrupt. Rename it to a bak
                    // file and then rename the temp file to the main file.
                    if (error) {
                        String bakFileName = file.getName() + "-" + System.currentTimeMillis() + ".bak";
                        File bakFile = new File(file.getParentFile(), bakFileName);
                        file.renameTo(bakFile);
                        // Rename operations don't seem to release all file resources
                        // on some OS's. Therefore, wait 100 ms before continuing with
                        // the next rename operation.
                        try {
                            Thread.sleep(100);
                        }
                        catch (Exception e) {
                        }
                        // Now rename the temp file to the main file.
                        tempFile.renameTo(file);
                    }
                    // If not an error, the temp file should be renamed to a bak file.
                    else {
                        String bakFile = tempFile.getName() + "-" + System.currentTimeMillis() + ".bak";
                        tempFile.renameTo(new File(tempFile.getParentFile(), bakFile));
                    }
                }
            }
        }
        // Check read and write privs.
        if (!file.canRead()) {
            throw new IOException("XML properties file must be readable: " + fileName);
        }
        if (!file.canWrite()) {
            throw new IOException("XML properties file must be writable: " + fileName);
        }
        Reader reader = null;
        try {
            reader = new InputStreamReader(FileUtils.newFileInputStream(file), "UTF-8");
            buildDoc(reader);
        }
        catch (Exception e) {
            log.error("Error creating XML properties file " + fileName + ": " + e.getMessage());
            throw new IOException(e.getMessage());
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception e) {
                log.debug(e.getMessage(), e);
            }
        }
    }

    /**
     * Returns the value of the specified property.
     *
     * @param o the name of the property to get.
     * @return the value of the specified property.
     */
    public String get(Object o) {
        String name = (String) o;
        String value = (String) propertyCache.get(name);
        if (value != null) {
            return value;
        }

        String[] propName = parsePropertyName(name);
        // Search for this property by traversing down the XML heirarchy.
        Element element = doc.getRootElement();
        for (int i = 0; i < propName.length; i++) {
            element = element.element(propName[i]);
            if (element == null) {
                // This node doesn't match this part of the property name which
                // indicates this property doesn't exist so return null.
                return null;
            }
        }

        synchronized (propLock) {
            // At this point, we found a matching property, so return its value.
            // Empty strings are returned as null.
            value = element.getText();
            if ("".equals(value)) {
                return null;
            }
            else {
                // Add to cache so that getting property next time is fast.
                value = value.trim();
                propertyCache.put(name, value);
                return value;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getChildrenNames(String parent) {
        String[] propName = parsePropertyName(parent);
        // Search for this property by traversing down the XML heirarchy.
        Element element = doc.getRootElement();
        for (int i = 0; i < propName.length; i++) {
            element = element.element(propName[i]);
            if (element == null) {
                // This node doesn't match this part of the property name which
                // indicates this property doesn't exist so return empty array.
                return Collections.emptyList();
            }
        }
        // We found matching property, return names of children.
        List<Element> children = element.elements();
        int childCount = children.size();
        List<String> childrenNames = new ArrayList<String>(childCount);
        for (Element e : children) {
            childrenNames.add((e).getName());
        }
        return childrenNames;
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getPropertyNames() {
        List<String> propNames = new java.util.LinkedList<String>();
        List<Element> elements = doc.getRootElement().elements();
        if (elements.size() == 0) {
            return Collections.emptyList();
        }
        for (Element element : elements) {
            getElementNames(propNames, element, element.getName());
        }
        return propNames;
    }

    /**
     * Returns the value of the attribute of the given property name or <tt>null</tt> if it doesn't exist. Note, this
     *
     * @param name the property name to lookup - ie, "foo.bar"
     * @param attribute the name of the attribute, ie "id"
     * @return the value of the attribute of the given property or <tt>null</tt> if it doesn't exist.
     */
    public String getAttribute(String name, String attribute) {
        if (name == null || attribute == null) {
            return null;
        }
        String[] propName = parsePropertyName(name);
        // Search for this property by traversing down the XML heirarchy.
        Element element = doc.getRootElement();
        for (int i = 0; i < propName.length; i++) {
            String child = propName[i];
            element = element.element(child);
            if (element == null) {
                // This node doesn't match this part of the property name which
                // indicates this property doesn't exist so return empty array.
                break;
            }
        }
        if (element != null) {
            // Get its attribute value
            return element.attributeValue(attribute);
        }
        return null;
    }

    /**
     * Method to recursively get the element names in the properties object.
     *
     * @param list the List to add final property names to.
     * @param e the node we're currently on.
     * @param name the property name so far.
     */
    @SuppressWarnings("unchecked")
    private void getElementNames(List<String> list, Element e, String name) {
        if (e.elements().isEmpty()) {
            list.add(name);
        }
        else {
            List<Element> children = e.elements();
            for (int i = 0; i < children.size(); i++) {
                Element child = (Element) children.get(i);
                getElementNames(list, child, name + '.' + child.getName());
            }
        }
    }

    /**
     * Sets the value of the specified property. If the property doesn't currently exist, it will be automatically
     * created.
     *
     * @param k the name of the property to set.
     * @param v the new value for the property.
     */
    public synchronized String put(String k, String v) {
        String name = (String) k;
        String value = (String) v;
        
        if(value == null){
        	remove(k);
        }
        //新旧值不相等时才保存
        String oldValue = propertyCache.get(name);
        if(!value.equals(oldValue)){
        	
        	if(log.isDebugEnabled()){
        		String s = String.format("属性 '%s'由 '%s' 变更为 '%s'.", name, oldValue, value);
        		log.debug(s);
        	}

        	// Set cache correctly with prop name and value.
	        propertyCache.put(name, value);
	
	        String[] propName = parsePropertyName(name);
	        // Search for this property by traversing down the XML heirarchy.
	        Element element = doc.getRootElement();
	        for (int i = 0; i < propName.length; i++) {
	            // If we don't find this part of the property in the XML heirarchy
	            // we add it as a new node
	            if (element.element(propName[i]) == null) {
	                element.addElement(propName[i]);
	            }
	            element = element.element(propName[i]);
	        }
	        // Set the value of the property in this node.
	        element.setText(value);
	        
	        // write the XML properties to disk
	        saveProperties();
        }
        
        // This version always returns null
        return null;
    }

    /**
     * Sets multiple properties at once. If any of the property doesn't currently exist they will be automatically
     * created.
     *
     * @param propertyMap a map of properties, keyed on property name.
     */
    public synchronized void putAll(Map<? extends String, ? extends String> propertyMap) {
        for (Map.Entry<? extends String, ? extends String> entry : propertyMap.entrySet()) {
            String propertyName = entry.getKey();
            String propertyValue = entry.getValue();

            String[] propName = parsePropertyName(propertyName);
            // Search for this property by traversing down the XML heirarchy.
            Element element = doc.getRootElement();
            for (int i = 0; i < propName.length; i++) {
                // If we don't find this part of the property in the XML heirarchy
                // we add it as a new node
                if (element.element(propName[i]) == null) {
                    element.addElement(propName[i]);
                }
                element = element.element(propName[i]);
            }
            // Set the value of the property in this node.
            if (propertyValue != null) {
                element.setText(propertyValue);
            }

            // Set cache correctly with prop name and value.
            propertyCache.put(propertyName, propertyValue);
        }

        // write the XML properties to disk
        saveProperties();
    }

    /**
     * Deletes the specified property.
     *
     * @param n the property to delete.
     */
    public synchronized String remove(Object n) {
        String name = (String) n;
        if(propertyCache.containsKey(name)){

        	// Remove property from cache.
	        propertyCache.remove(name);
	
	        String[] propName = parsePropertyName(name);
	        // Search for this property by traversing down the XML heirarchy.
	        Element element = doc.getRootElement();
	        for (int i = 0; i < propName.length - 1; i++) {
	            element = element.element(propName[i]);
	            // Can't find the property so return.
	            if (element == null) {
	                return null;
	            }
	        }
	        String value = element.getText();
	        // Found the correct element to remove, so remove it...
	        element.remove(element.element(propName[propName.length - 1]));
	        // .. then write to disk.
	        saveProperties();
	        return value;
        }
        return null;
    }

    public String getProperty(String name) {
        return (String) get(name);
    }

    public boolean containsKey(Object object) {
        return get(object) != null;
    }

    public boolean containsValue(Object object) {
        throw new UnsupportedOperationException("Not implemented in xml version");
    }

    public Collection<String> values() {
        throw new UnsupportedOperationException("Not implemented in xml version");
    }

    public boolean isEmpty() {
        return false; // I think this is safe to say
    }

    public int size() {
        throw new UnsupportedOperationException("Not implemented in xml version");
    }

    public Set<Map.Entry<String, String>> entrySet() {
        throw new UnsupportedOperationException("Not implemented in xml version");
    }

    public void clear() {
        throw new UnsupportedOperationException("Not implemented in xml version");
    }

    public Set<String> keySet() {
        throw new UnsupportedOperationException("Not implemented in xml version");
    }

    public File getFile() {
        return file;
    }

    /**
     * Builds the document XML model up based the given reader of XML data.
     */
    private void buildDoc(Reader in) throws Exception {
        SAXReader xmlReader = new SAXReader();
        doc = xmlReader.read(in);
    }

    /**
     * Saves the properties to disk as an XML document. A temporary file is used during the writing process for maximum
     * safety.
     */
    private synchronized void saveProperties() {
        final Logger log = LogManager.getLogger(XMLAppsProperties.class);
        Writer writer = null;
        boolean error = false;
        // Write data out to a temporary file first.
        File tempFile = null;
        try {
            tempFile = new File(file.getParentFile(), file.getName() + ".tmp");
            writer = new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8");
            XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
            xmlWriter.write(doc);
        }
        catch (Exception e) {
            log.error("Unable to write to file " + file.getName() + ".tmp" + ": " + e.getMessage());

            // There were errors so abort replacing the old property file.
            error = true;
        }
        finally {
            try {
                writer.flush();
                writer.close();
            }
            catch (Exception e) {
                log.error(e);
                error = true;
            }
        }
        // No errors occurred, so delete the main file.
        if (!error) {
            // Reset error flag.
            error = false;
            // Write out the new contents to the file.
            try {
                writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                XMLWriter xmlWriter = new XMLWriter(writer, OutputFormat.createPrettyPrint());
                xmlWriter.write(doc);
            }
            catch (Exception e) {
                log.error("Unable to write to file '" + file.getName() + "': " + e.getMessage());
                // There were errors so abort replacing the old property file.
                error = true;
            }
            finally {
                try {
                    writer.flush();
                    writer.close();
                }
                catch (Exception e) {
                    log.error(e);
                    error = true;
                }
            }
            // If no errors, delete the temp file.
            if (!error) {
                tempFile.delete();
            }
        }
    }

    /**
     * Returns an array representation of the given Jive property. Jive properties are always in the format
     * "prop.name.is.this" which would be represented as an array of four Strings.
     *
     * @param name the name of the Jive property.
     * @return an array representation of the given Jive property.
     */
    private String[] parsePropertyName(String name) {
        // this gets called a fair amount - don't want to use StringTokenizer...
        return StringUtils.split(name, '.');
    }
}
