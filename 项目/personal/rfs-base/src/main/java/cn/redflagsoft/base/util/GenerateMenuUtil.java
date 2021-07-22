package cn.redflagsoft.base.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GenerateMenuUtil {
	
	private static final String MENU_FILE_PATH = "D:\\touchstone-menus.xml";
	private static int GROUP_INDEX = 1;
	private static int MENU_INDEX = 1;
	private static int GROUP_FILL_DIGIT = 2;
	private static int MENU_FILL_DIGIT = 3;
	private static final boolean isAutoCommit = false;
	private static final String DRIVER_CLASS = "org.hsqldb.jdbcDriver";
	private static final String DRIVER_URL = "jdbc:hsqldb:hsql://192.168.18.5:9008/";
	private static final String USER_NAME = "sa";
	private static final String PASSWORD = "";
	private static final String MENU_GROUP_INSERT_SQL = "INSERT INTO SEC_MENU_GROUP(ID,GROUP_NAME,DISPLAY_ORDER) VALUES(?,?,?)";
	private static final String MENU_INSERT_SQL = "INSERT INTO SEC_MENU(AUTHORITY,NAME,APPLICATION,GROUPID,DISPLAY_ORDER) VALUES(?,?,?,?,?)";	
	private static Connection connect;
	private static PreparedStatement ps1, ps2;
	private static String group;
	private static String menu;
	private static Long groupId;
	
	
	public static void main(String[] args) throws Exception {
		File file = new File(MENU_FILE_PATH);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		//buildXML(doc, file);
		transferXML(doc);
	}
	
	public static void transferXML(Document doc) throws Exception {
		getConnection();
		setAutoCommit();
		preparedStatement();
		execute(doc.getElementsByTagName("nodes"));
	}
	
	public static void buildXML(Document doc, File file) throws Exception {
		processing(doc.getElementsByTagName("nodes"));
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transfer = tf.newTransformer();
		transfer.transform(new DOMSource(doc), new StreamResult(file));		
	}
	
	public static void doMenuGroup(Node node, Integer index) {
		group = fillString(GROUP_FILL_DIGIT, index.toString());
		if(node instanceof Element) {
			Element element = (Element)node;
			group = "M" + group; 
			element.setAttribute("id", group);
			element.setAttribute("displayOrder", String.valueOf(GROUP_INDEX));
			GROUP_INDEX++;
		}
		processing(node.getChildNodes());
	}
	
	public static void doMenu(Node node, Integer index) {
		menu = fillString(MENU_FILL_DIGIT, index.toString());
		if(node instanceof Element) {
			Element element = (Element)node;
			element.setAttribute("authority", group + menu);
			element.setAttribute("sceneSource", menu);
			element.setAttribute("displayOrder", String.valueOf(MENU_INDEX));
			MENU_INDEX++;
		}
	}
	
	public static void processing(NodeList nl) {
		Node node;
		for(int i=0; i<nl.getLength(); i++) {
			node = nl.item(i);
			if(node.hasChildNodes()) {
				doMenuGroup(node, GROUP_INDEX);
				MENU_INDEX = 1;
			} else {
				doMenu(node, MENU_INDEX);
			}
		}
	}
	
	public static void execute(NodeList nl) {
		Node node;
		for(int i=0; i<nl.getLength(); i++) {
			node = nl.item(i);
			if(node.hasChildNodes()) {
				permanenceMenuGroup(node);
				commit();
			} else {
				permanenceMenu(node);
			}
		}
	}
	
	public static void permanenceMenuGroup(Node node) {
		if(node instanceof Element) {
			Element element = (Element)node;
			String displayOrder = element.getAttribute("displayOrder");
			String name = element.getAttribute("label");
			groupId = Long.valueOf(displayOrder);
			execute(groupId, name, Integer.valueOf(displayOrder));
		}
		execute(node.getChildNodes());
	}
	
	public static void permanenceMenu(Node node) {
		if(node instanceof Element) {
			Element element = (Element)node;
			String authority = element.getAttribute("authority");
			String sceneSource = element.getAttribute("sceneSource");
			String displayOrder = element.getAttribute("displayOrder");
			String name = element.getAttribute("label");
			execute(authority, name, sceneSource, groupId, Integer.valueOf(displayOrder));
		}
	}
	
	public static String fillString(int off, String str) {
		off -= str.length();
		for(int i=0; i<off; i++) {
			str = "0" + str;
		}
		return str;
	}
	
	/**
	 * 加载连接对象
	 */
	public static void getConnection() {
		try {
			Class.forName(DRIVER_CLASS);
			connect = DriverManager.getConnection(DRIVER_URL, USER_NAME, PASSWORD);
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置是否自动提交事务
	 */
	public static void setAutoCommit() {
		try {
			if(connect == null) getConnection();
			connect.setAutoCommit(isAutoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 提交事务
	 */
	public static void commit() {
		try {
			if(connect == null) throw new NullPointerException("Connection 空引用异常 ... ");
			connect.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 准备语句对象
	 */
	public static void preparedStatement() {
		try {
			if(connect == null) throw new NullPointerException("Connection 空引用异常 ... ");
			ps1 = connect.prepareStatement(MENU_GROUP_INSERT_SQL);
			ps2 = connect.prepareStatement(MENU_INSERT_SQL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置参数
	 */
	public static void execute(Long id, String name, int displayOrder) {
		try {
			ps1.setLong(1, id);
			ps1.setString(2, name);
			ps1.setInt(3, displayOrder);
			ps1.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(String authority,String name,String application,Long groupid,int displayOrder) {
		try {
			ps2.setString(1, authority);
			ps2.setString(2, name);
			ps2.setString(3, application);
			ps2.setLong(4, groupid);
			ps2.setInt(5, displayOrder);
			ps2.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * 关闭连接
	 */
	public static void closeAll() {
		try {
			if (ps1 != null) {
				ps1.close();
				ps1 = null;
			}
			if (ps2 != null) {
				ps2.close();
				ps2 = null;
			}			
			if (connect != null) {
				connect.close();
				connect = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}

