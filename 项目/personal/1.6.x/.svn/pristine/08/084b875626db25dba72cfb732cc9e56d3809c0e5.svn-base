package org.opoo.apps.database.spring;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.cache.AbstractClusterTask;
import org.opoo.apps.cache.CacheFactory;
import org.opoo.apps.database.DataSourceManager;
import org.opoo.apps.event.v2.EventDispatcher;
import org.opoo.apps.event.v2.EventDispatcherAware;
import org.opoo.apps.event.v2.core.PropertyEvent;
import org.opoo.util.AbstractOpooProperties;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.annotation.Transactional;



/**
 * 使用数据库表存储的系统属性访问类。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
//@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class DatabaseProperties extends AbstractOpooProperties implements EventDispatcherAware{
	static final Log log = LogFactory.getLog(DatabaseProperties.class);
	private static final String LOAD_PROPERTIES = "SELECT NAME, PROPVALUE FROM SYS_PROPS";
	private static final String INSERT_PROPERTY = "INSERT INTO SYS_PROPS(NAME, PROPVALUE) VALUES (?, ?)";
	private static final String UPDATE_PROPERTY = "UPDATE SYS_PROPS SET PROPVALUE=? WHERE NAME=?";
	private static final String DELETE_PROPERTY = "DELETE FROM SYS_PROPS WHERE NAME LIKE ?";
	private static final String LOAD_LOCALIZED_PROPERTIES = "SELECT NAME, PROPVALUE, LOCALE FROM SYS_PROPS_LOCALE";
	private static final String INSERT_LOCALIZED_PROPERTY = "INSERT INTO SYS_PROPS_LOCALE(NAME, PROPVALUE, LOCALE) VALUES (?, ?, ?)";
	private static final String UPDATE_LOCALIZED_PROPERTY = "UPDATE SYS_PROPS_LOCALE SET PROPVALUE=? WHERE NAME=? AND LOCALE=?";
	private static final String DELETE_LOCALIZED_PROPERTY = "DELETE FROM SYS_PROPS_LOCALE WHERE NAME LIKE ? AND LOCALE LIKE ?";
	private static final String PROPERTY_LOCALES = "SELECT LOCALE FROM SYS_PROPS_LOCALE WHERE NAME LIKE ?";
	
	private boolean localized;
	private final AtomicBoolean initialized = new AtomicBoolean();
	private ConcurrentMap<String, String> properties;
	private static final AtomicReference<DatabaseProperties> instance = new AtomicReference<DatabaseProperties>();
	private static final AtomicReference<DatabaseProperties> localizedInstance = new AtomicReference<DatabaseProperties>();
	private EventDispatcher dispatcher;
	
	private SimpleJdbcTemplate jdbcTemplate;
	
	 private static final List<PropertyEvent> tempEventQueue = new ArrayList<PropertyEvent>();
	
	static{
		CacheFactory.addClusteringListener(new PropertyClusteringListener());
	}

	public static DatabaseProperties getInstance() {
		return getInstanceInternal(instance, false);
	}

	public static DatabaseProperties getNewInstance() {
		DatabaseProperties appsProps = new DatabaseProperties(false);
		appsProps.init();
		return appsProps;
	}

//	public static DatabaseProperties getInstance(DataSource datasource) {
//		return getInstanceInternal(instance, false, datasource);
//	}

	public static DatabaseProperties getLocalizedInstance() {
		return getInstanceInternal(localizedInstance, true);
	}

	private static DatabaseProperties getInstanceInternal(AtomicReference<DatabaseProperties> reference, boolean localized) {
		while (reference.get() == null) {
			if (reference.compareAndSet(null, new DatabaseProperties(localized))) {
				reference.get().init();
			}
		}
		// do
		// {
		// if(reference.get() != null)
		// break;
		// if(reference.compareAndSet(null, new
		// DatabaseOpooProperties(localized)))
		// ((DatabaseOpooProperties)reference.get()).init(datasource);
		// } while(true);
		DatabaseProperties props;
		for (props = (DatabaseProperties) reference.get(); !props.initialized.get(); Thread.yield())
			;
		return props;
	}

	private DatabaseProperties(boolean localized) {
		this.localized = localized;
	}
	
	@Transactional(readOnly=true)
	public void init() {
		// TODO 事务处理
		init(null);
	}

	private void init(DataSource ds) {
		try {
			buildSimpleJdbcTemplate(ds);
			ConcurrentMap<String, String> map = new ConcurrentHashMap<String, String>();
			properties = map;
			loadProperties(map);
//			properties = map;
		} catch (CannotGetJdbcConnectionException e){
			if(AppsGlobals.isSetup()){
				throw e;
			}
		}finally {
			initialized.set(true);
		}
	}
	
	private SimpleJdbcTemplate buildSimpleJdbcTemplate(DataSource ds){
		if(ds == null){
			try {
				ds = DataSourceManager.getDataSource();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
		jdbcTemplate = new SimpleJdbcTemplate(ds);
//		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(ds);
//		tt = new TransactionTemplate(transactionManager);
		return jdbcTemplate;
	}
	protected SimpleJdbcTemplate getSimpleJdbcTemplate(){
		return jdbcTemplate;
	}
	

	public static void destroy() {
		instance.set(null);
		localizedInstance.set(null);
	}

	public int size() {
		return properties.size();

	}

	public void clear() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmpty() {
		return properties.isEmpty();
	}

	public boolean containsKey(Object key) {
		return properties.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return properties.containsValue(value);
	}

	public Collection<String> values() {
		return Collections.unmodifiableCollection(properties.values());
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		for (Map.Entry<? extends String, ? extends String> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return Collections.unmodifiableSet(properties.entrySet());
	}

	public Set<String> keySet() {
		return Collections.unmodifiableSet(properties.keySet());
	}

	public String get(Object key) {
		return properties.get(key);
	}

	public Collection<String> getChildrenNames(String parentKey) {
		Object[] keys = properties.keySet().toArray();
        Collection<String> results = new HashSet<String>();
        String parentKeyDot = parentKey + ".";
        for (int i = 0, n = keys.length; i < n; i++) {
            String key = (String) keys[i];
            if (key.startsWith(parentKeyDot)) {
                if (key.equals(parentKey)) {
                    continue;
                }
                int dotIndex = key.indexOf(".", parentKey.length() + 1);
                if (dotIndex < 1) {
                    if (!results.contains(key)) {
                        results.add(key);
                    }
                }
                else {
                    String name = parentKey + key.substring(parentKey.length(), dotIndex);
                    results.add(name);
                }
            }
        }
        return results;
	}

	public Collection<String> getPropertyNames() {
		return new ArrayList<String>(properties.keySet());
	}

	//@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String remove(Object key) {
		String value = properties.remove(key);
		if (value != null) {
			/*
			List<String> removedValues = new ArrayList<String>();
			Collection<String> propNames = getPropertyNames();
			String keyDot = key + ".";
			for (String name : propNames) {
				if (name.startsWith(keyDot)) {
					removedValues.add(properties.remove(name));
				}
			}
			*/
			//被删除的子属性
			Map<String,String> removedChildProperties = removeChildProperties((String)key);
			List<String> removedValues = new ArrayList<String>(removedChildProperties.values());

			deleteProperty((String) key);
			
			//对于子节点的处理 
			//FIXME 是否有此必要
			for(Map.Entry<String, String> en: removedChildProperties.entrySet()){
				CacheFactory.doClusterTask(new PropertyClusterRemoveTask(en.getKey(), localized));
				fireEvent(new PropertyEvent(PropertyEvent.Type.REMOVED, en.getKey(), en.getValue(), null));
			}

			// Populate parameters for the event
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("PARAM_REMOVED_VALUES", removedValues);
			
			// Send update to other cluster members.
			CacheFactory.doClusterTask(new PropertyClusterRemoveTask((String) key, localized));
			//Fire event last - if listeners fire cluster tasks relying on property state, that needs to get across first
			fireEvent(new PropertyEvent(PropertyEvent.Type.REMOVED, (String) key, value, params));
		}
		return value;
	}
	
	private Map<String,String> removeChildProperties(String key) {
        Map<String,String> removedProperties = new LinkedHashMap<String,String>();
        Collection<String> propNames = getPropertyNames();
        String keyDot = key + ".";
        for (String name : propNames) {
            if (name.startsWith(keyDot)) {
                removedProperties.put(name, properties.remove(name));
            }
        }

        return removedProperties;
    }

	//@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public String put(String key, String value) {
		if (key == null || value == null) {
            throw new NullPointerException("Key or value cannot be null. Key=" + key + ", value=" + value);
        }
        if ("".equals(key)) {
            log.warn("Can not save a blank key: '" + key + "'.");
            return null;
        }
        // Oracle hack. The value field in the db schema is marked as "not null",
        // but Oracle treats empty strings and null as the same thing. Therefore
        // we convert an empty String to a single space for Oracle.
        if (value.equals("") && DataSourceManager.getMetaData().getDatabaseType().isOracle()) {
            value = " ";
        }
        if (key.endsWith(".")) {
            key = key.substring(0, key.length() - 1);
        }
        key = key.trim();
        
        String oldValue = properties.put(key, value);
        PropertyEvent event = null;
        
        if (oldValue != null) {
            if (!oldValue.equals(value)) {
                updateProperty(key, value);

                Map<String,Object> params = new HashMap<String,Object>();
				params.put("PARAM_MODIFIED_OLD_VALUE", oldValue);
				
                event = new PropertyEvent(PropertyEvent.Type.MODIFIED, key, value, params);
            }
        }
        else {
            insertProperty(key, value);

            event = new PropertyEvent(PropertyEvent.Type.ADDED, key, value, null);
        }

        // Send update to other cluster members.
        CacheFactory.doClusterTask(new PropertyClusterPutTask(key, value, localized));
        if (event != null) {
            //fire event after prop value has propagated across cluster
            fireEvent(event);
        }

        return oldValue;
	}
	

//	private void fireEvent(String key, String value, PropertyEvent.Type type) {
//		fireEvent(new PropertyEvent(type, key, value, null));
//	}

	private void fireEvent(PropertyEvent event) {
		if (dispatcher != null){
			dispatcher.dispatchEvent(event);
		}else{
			//log.error("Unable to fire apps property event - missing dispatcher");
			log.error(
             "Unable to fire apps property event - missing dispatcher. Storing event temporarily to dispatch later");
			tempEventQueue.add(event);
		}
	}
		

	
	public List<Locale> getLocalesForProperty(String name) {
		return getSimpleJdbcTemplate().query(PROPERTY_LOCALES, new ParameterizedRowMapper<Locale>(){
			public Locale mapRow(ResultSet rs, int rowNum) throws SQLException {
				String l = rs.getString(1);
				return localeCodeToLocale(l);
			}}, name);
	}
	
    public static Locale localeCodeToLocale(String localeCode) {
		Locale locale = null;
		if (localeCode != null && localeCode.length() > 0) {
			String language = null;
			String country = null;
			String variant = null;
			StringTokenizer tokenizer = new StringTokenizer(localeCode, "_");
			if (tokenizer.hasMoreTokens()) {
				language = tokenizer.nextToken();
				if (tokenizer.hasMoreTokens()) {
					country = tokenizer.nextToken();
					if (tokenizer.hasMoreTokens())
						variant = tokenizer.nextToken();
				}
			}
			locale = new Locale(language, country == null ? "" : country, variant == null ? "" : variant);
		}
		return locale;
	}

    //@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	private void insertProperty(String name, String value) {
		if(localized){
			String sql = INSERT_LOCALIZED_PROPERTY;
			String baseKey = StringUtils.substringBeforeLast(name, ".");
			String localeCode = StringUtils.substringAfterLast(name, ".");
			getSimpleJdbcTemplate().update(sql, baseKey, value, localeCode);
		}else{
			String sql = INSERT_PROPERTY;
			getSimpleJdbcTemplate().update(sql, name, value);
		}
		
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			con = ConnectionManager.getConnection();
//			if (localized)
//				pstmt = createLocalizedInsertStatement(con, value, name);
//			else
//				pstmt = createInsertStatement(con, value, name);
//			pstmt.execute();
//		} catch (SQLException e) {
//			log.error(e.getMessage(), e);
//		} finally {
//			ConnectionManager.close(pstmt, con);
//		}
	}

//	private PreparedStatement createLocalizedInsertStatement(Connection con, String value, String name)
//			throws SQLException {
//		String baseKey = StringUtils.substringBeforeLast(name, ".");
//		String localeCode = StringUtils.substringAfterLast(name, ".");
//		PreparedStatement pstmt = con
//				.prepareStatement("INSERT INTO appsLocalizedProp(name, propValue, locale) VALUES (?, ?, ?)");
//		pstmt.setString(1, baseKey);
//		pstmt.setString(2, value);
//		pstmt.setString(3, localeCode);
//		return pstmt;
//	}
//
//	private PreparedStatement createInsertStatement(Connection connection, String value, String name)
//			throws SQLException {
//		if (connection == null) {
//			throw new SQLException("No connection was available.");
//		} else {
//			PreparedStatement pstmt = connection
//					.prepareStatement("INSERT INTO appsProperties(name, propValue) VALUES (?, ?)");
//			pstmt.setString(1, name);
//			pstmt.setString(2, value);
//			return pstmt;
//		}
//	}

	// @Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	private void updateProperty(String name, String value) {
		if(localized){
			String sql = UPDATE_LOCALIZED_PROPERTY;
			String baseKey = StringUtils.substringBeforeLast(name, ".");
			String localeCode = StringUtils.substringAfterLast(name, ".");
			getSimpleJdbcTemplate().update(sql, value, baseKey, localeCode);
		}else{
			String sql = UPDATE_PROPERTY;
			getSimpleJdbcTemplate().update(sql, value, name);
		}
		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		try {
//			con = ConnectionManager.getConnection();
//			if (localized)
//				pstmt = createLocalizedUpdateStatment(con, value, name);
//			else
//				pstmt = createUpdateStatment(con, value, name);
//			pstmt.execute();
//		} catch (SQLException e) {
//			log.error(e.getMessage(), e);
//		} finally {
//			ConnectionManager.close(pstmt, con);
//		}
	}

//	private PreparedStatement createLocalizedUpdateStatment(Connection connection, String value, String name)
//			throws SQLException {
//		if (connection == null) {
//			throw new SQLException("No connection was available.");
//		} else {
//			String baseKey = StringUtils.substringBeforeLast(name, ".");
//			String localeCode = StringUtils.substringAfterLast(name, ".");
//			PreparedStatement pstmt = connection
//					.prepareStatement("UPDATE appsLocalizedProp SET propValue=? WHERE name=? AND locale=?");
//			pstmt.setString(1, value);
//			pstmt.setString(2, baseKey);
//			pstmt.setString(3, localeCode);
//			return pstmt;
//		}
//	}
//
//	private PreparedStatement createUpdateStatment(Connection connection, String value, String name)
//			throws SQLException {
//		if (connection == null) {
//			throw new SQLException("No connection was available.");
//		} else {
//			PreparedStatement pstmt = connection.prepareStatement("UPDATE appsProperties SET propValue=? WHERE name=?");
//			pstmt.setString(1, value);
//			pstmt.setString(2, name);
//			return pstmt;
//		}
//	}
//
//	private PreparedStatement createLocalizedDeleteStatement(Connection connection, String name) throws SQLException {
//		if (connection == null) {
//			throw new SQLException("No connection was available.");
//		} else {
//			String baseKey = StringUtils.substringBeforeLast(name, ".");
//			String localeCode = StringUtils.substringAfterLast(name, ".");
//			PreparedStatement pstmt = connection
//					.prepareStatement("DELETE FROM appsLocalizedProp WHERE name LIKE ? AND locale LIKE ?");
//			pstmt.setString(1, (new StringBuilder()).append(baseKey).append("%").toString());
//			pstmt.setString(2, (new StringBuilder()).append(localeCode).append("%").toString());
//			return pstmt;
//		}
//	}

	//@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	private void deleteProperty(String name) {
		if (localized) {
			String sql = DELETE_LOCALIZED_PROPERTY;
			String baseKey = StringUtils.substringBeforeLast(name, ".") + "%";
			String localeCode = StringUtils.substringAfterLast(name, ".") + "%";

			getSimpleJdbcTemplate().update(sql, baseKey, localeCode);

		} else {
			String sql = DELETE_PROPERTY;
			getSimpleJdbcTemplate().update(sql, name);//删除本身
			getSimpleJdbcTemplate().update(sql, name + ".%");//删除下级
		}
	}

	private void loadProperties(final Map<String, String> map) {
		String sql = LOAD_PROPERTIES; 
		if (localized){
			sql = LOAD_LOCALIZED_PROPERTIES;//"SELECT name, propValue, locale FROM appsLocalizedProp";
		}
		
		getSimpleJdbcTemplate().getJdbcOperations().execute(sql, new PreparedStatementCallback(){
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				ResultSet rs = null;
				try{
					rs = ps.executeQuery();
					while(rs.next()){
						String name = rs.getString(1);
	                    String value = rs.getString(2);
						if (localized){
							name += "." + rs.getString(3);
						}
						if (value.equals(" ") && DataSourceManager.getMetaData().getDatabaseType().isOracle()){
							value = "";
						}
						map.put(name, value);
					}
					
					return null;
				}finally{
					JdbcUtils.closeResultSet(rs);
				}
			}
		});
	}

	/**
	 * 集群中属性被删除时执行
	 * @param key
	 */
	protected void clusterRemove(String key) {
		String value = properties.remove(key);
		if(log.isDebugEnabled()){
			log.debug("集群删除属性：" + key);
		}
		if(value != null){
			Map<String, String> removedChildProperties = removeChildProperties(key);
			
			//发本地事件
			for(Map.Entry<String, String> en: removedChildProperties.entrySet()){
				fireEvent(new PropertyEvent(PropertyEvent.Type.REMOVED, en.getKey(), en.getValue(), null));
			}
			fireEvent(new PropertyEvent(PropertyEvent.Type.REMOVED, key, value, null));
		}
	}

	protected void clusterPut(String key, String value) {
		/*
		properties.put(key, value);
		if(log.isDebugEnabled()){
			log.debug("集群设置属性: " + key + " = " + value);
		}
		*/
		
		String oldValue = properties.put(key, value);
		if(log.isDebugEnabled()){
			log.debug("集群设置属性: " + key + " = " + value);
		}
		
		//发本地事件
        PropertyEvent event = null;
        if (oldValue != null) {
            if (!oldValue.equals(value)) {
                Map<String,Object> params = new HashMap<String,Object>();
				params.put("PARAM_MODIFIED_OLD_VALUE", oldValue);
                event = new PropertyEvent(PropertyEvent.Type.MODIFIED, key, value, params);
            }
        }
        else {
            event = new PropertyEvent(PropertyEvent.Type.ADDED, key, value, null);
        }
		if(event != null){
			fireEvent(event);
		}
	}

	public Collection<String> getProperties(String parent) {
		throw new UnsupportedOperationException("getProperties(String parent)");
	}
	
	public String toString(){
		return super.toString() + ":" + properties;
	}

	public void setEventDispatcher(EventDispatcher eventDispatcher) {
		this.dispatcher = eventDispatcher;
		while (tempEventQueue.size() > 0) {
            dispatcher.dispatchEvent(tempEventQueue.remove(0));
        }
	}
}

	
	
class PropertyClusterRemoveTask extends AbstractClusterTask {
	private static final long serialVersionUID = -1903827921499972150L;
	private String key;
	boolean localized;

	public PropertyClusterRemoveTask() {
	}

	protected PropertyClusterRemoveTask(String key, boolean localized) {
		this.key = key;
		this.localized = localized;
	}

	public void execute() {
		DatabaseProperties.log.debug("PropertyClusterPutTask.execute");
		if (localized)
			DatabaseProperties.getLocalizedInstance().clusterRemove(key);
		else
			DatabaseProperties.getInstance().clusterRemove(key);
	}
}

class PropertyClusterPutTask extends AbstractClusterTask {
	private static final long serialVersionUID = -6716241834110683337L;
	private String key;
	private String value;
	private boolean localized;

	public PropertyClusterPutTask() {
	}

	protected PropertyClusterPutTask(String key, String value, boolean localized) {
		this.key = key;
		this.value = value;
		this.localized = localized;
	}

	public void execute() {
		//DatabaseProperties.log.debug("PropertyClusterPutTask.execute");
		if (localized)
			DatabaseProperties.getLocalizedInstance().clusterPut(key, value);
		else
			DatabaseProperties.getInstance().clusterPut(key, value);
	}
}

class PropertyClusteringListener implements CacheFactory.ClusteringListener{
	public void clusteringStarted() {
		DatabaseProperties.log.debug("收到集群事件，重新初始化属性。");
		DatabaseProperties.getInstance().init();
	}

	public void clusteringStopped() {
		
	}
}
