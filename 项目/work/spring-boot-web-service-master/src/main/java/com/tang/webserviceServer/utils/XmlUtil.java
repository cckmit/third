package com.tang.webserviceServer.utils;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;


public class XmlUtil {

    public static final String DATASET_TAG_START = "<DataSet>";

    public static final String DATASET_TAG_END = "</DataSet>";

    public static final String xmlWrap = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";

    /**
     * JavaBean转换成xml
     * 默认编码UTF-8
     * @param obj
     * @return
     */
    public static String convertToXml(Object obj) {
        return convertToXml(obj, "UTF-8");
    }

    /**
     * JavaBean转换成xml
     * @param obj
     * @param encoding
     * @return
     */
    public static String convertToXml(Object obj, String encoding) {
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            result = writer.toString();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlWrap+result;
    }

    /**
     * xml转换成JavaBean
     * @param xml
     * @param c
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T convertToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    @SuppressWarnings("unchecked")
    /**
     * 将String类型的xml转换成对象
     */
    public static Object convertXmlStrToObject(Class clazz, String xmlStr) {
        Object xmlObject = null;
        try {
            //xmlStr = rmWrap ? xmlStr.replace(DATASET_TAG_START,"").replace(DATASET_TAG_END,"") : xmlStr;
            JAXBContext context = JAXBContext.newInstance(clazz);
            // 进行将Xml转成对象的核心接口
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }

    @SuppressWarnings("unchecked")
    /**
     * 将file类型的xml转换成对象
     */
    public static Object convertXmlFileToObject(Class clazz, String xmlPath) {
        Object xmlObject = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FileReader fr = null;
            try {
                fr = new FileReader(xmlPath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if(fr != null){
                    fr.close();
                }
            }
            xmlObject = unmarshaller.unmarshal(fr);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
        return xmlObject;
    }
}
