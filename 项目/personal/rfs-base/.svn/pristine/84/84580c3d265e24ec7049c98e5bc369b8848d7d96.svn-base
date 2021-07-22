package cn.redflagsoft.base.scheme.schemes.menu.v2;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StopWatch;

import cn.redflagsoft.base.scheme.AbstractScheme;
import cn.redflagsoft.base.scheme.Scheme;
import cn.redflagsoft.base.scheme.SchemeInvoker;
import cn.redflagsoft.base.scheme.SchemeManager;
import cn.redflagsoft.base.test.BaseTests;




public class MenuSchemeV2Test extends BaseTests{

	protected MenuScheme menuSchemeV2;
	protected SchemeManager schemeManager;
	
	
	public void etestSave() throws FileNotFoundException, DocumentException{
		menuSchemeV2.setFile("d:\\nodes.xml");
		menuSchemeV2.doSave();
		
		super.setComplete();
	}
	
	
	public void etestSaveMethod() throws Exception{
		System.out.println(menuSchemeV2.getClass());
		Scheme scheme = schemeManager.getScheme("menuSchemeV2");
		System.out.println(scheme.getClass());
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("menuData", "asdsalijflajflklksa");
		((AbstractScheme)scheme).setParameters(map);
		SchemeInvoker.invoke(scheme, "save");
	}
	
	
	
	public void etestDoSave() throws Exception{
		MenuScheme v2 = new MenuScheme();
		v2.doSave();
		
		StringReader reader = new StringReader(v2.getMenuData());
		SAXReader saxReader = new SAXReader();
		saxReader.setEncoding("GBK");
		Document document = saxReader.read(reader);
		System.out.println(document);
		
		Element rootElement = document.getRootElement();
		showElement(rootElement);

		boolean bb = true;
		StopWatch stop = new StopWatch();
		stop.start("ssssssssss");
		for(int i = 0 ; i < 1000 ; i++){
			boolean b = rootElement.isRootElement();
			bb = b && bb;
		}
		stop.stop();
		
		System.out.println(stop.prettyPrint());
		System.out.println(bb);
		
//		StringReader reader = new StringReader(v2.getMenuData());
//		StringInputStream inputStream = new StringInputStream(v2.getMenuData());
//		ByteArrayInputStream is = new ByteArrayInputStream(v2.getMenuData().getBytes("UTF-8"));
//		
//		DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
//		//通过静态方法创建DocumentBuilder 实例
//		//准备建立Document 对象
//		DocumentBuilder db = dbf.newDocumentBuilder();
//		Document document = db.parse(is);
//		System.out.println(document);
//		
//		
//		showNode(document);
	}
	
	private void showElement(Element node){
		System.out.println(node.getName() + ", " + node.attributeValue("id") + ", " + node.attributeValue("name"));
		List<Element> list = node.elements();
		for(Element e: list){
			showElement(e);
		}
	}
	
//	private void showNode(Node node){
//		System.out.println(node.getClass());
//		NamedNodeMap map = node.getAttributes();
//		System.out.println(node.getNodeType());
//		
//		if(node.hasChildNodes()){
//			NodeList nodes = node.getChildNodes();
//			for(int i = 0, n = nodes.getLength() ; i < n ; i++){
//				Node item = nodes.item(i);
//				showNode(item);
//			}
//		}
//	}



}
