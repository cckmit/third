package org.opoo.apps.file.openoffice.sheet;

import java.io.File;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.opoo.apps.file.FileUpdater;
import org.opoo.apps.file.converter.openoffice.OpenOfficeFileTypeConverter;
import org.opoo.apps.file.openoffice.SocketXConnectionProvider;
import org.opoo.apps.file.openoffice.XComponentRefresherAware;
import org.opoo.apps.file.openoffice.XConnectionProvider;
import org.opoo.apps.file.openoffice.XDocumentConverter;


/**
 * ���Զ����ɵ�Excel�У��Զ����к��Զ��и�ʵЧ����Ҫͨ�����������ˢ��
 * һ�Ρ�
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class XLSRowOptimalHeightRefreshUpdater implements FileUpdater{
	private static final Logger log = Logger.getLogger(XLSRowOptimalHeightRefreshUpdater.class.getName());
	
	
	private XDocumentConverter converter;
	//private XConnectionProvider connectionProvider;
	//private XComponentRefresher refresher = new XLSRowHeightRefresher();
	
	public XLSRowOptimalHeightRefreshUpdater(){
		this(new SocketXConnectionProvider());
	}
	
	public XLSRowOptimalHeightRefreshUpdater(XConnectionProvider connectionProvider){
		super();
		setConnectionProvider(connectionProvider);
	}
	
	/* (non-Javadoc)
	 * @see org.opoo.apps.file.RefreshService#refresh(java.io.File, java.io.File)
	 */
	public void update(File input, File output) {
		String ext = FilenameUtils.getExtension(input.getAbsolutePath());
		if("xls".equalsIgnoreCase(ext) || "xlsx".equalsIgnoreCase(ext) || "ods".equalsIgnoreCase(ext)){
			converter.convert(input, output);
		}else{
			log.warning("��ǰ�и�ˢ����ֻ�ܸ���XLS�ļ���");
		}
	}
	
//	/**
//	 * @return the connectionProvider
//	 */
//	public XConnectionProvider getConnectionProvider() {
//		return connectionProvider;
//	}
	
	/**
	 * @param connectionProvider the connectionProvider to set
	 */
	public void setConnectionProvider(XConnectionProvider connectionProvider) {
		//this.connectionProvider = connectionProvider;
		converter = createXDocumentConverter(connectionProvider);
	}
	
	protected XDocumentConverter createXDocumentConverter(XConnectionProvider connectionProvider){
		XDocumentConverter converter = connectionProvider.getConnection().getDocumentConverter();
		if(converter instanceof XComponentRefresherAware){
			((XComponentRefresherAware) converter).setComponentRefresher(new SpreadsheetRowOptimalHeightRefresher());
		}else{
			throw new IllegalArgumentException("ת����δʵ�ֽӿ�XComponentRefresherAware");
		}
		return converter;
	}
	
	
	public static void main(String[] args){
		//System.out.println(FilenameUtils.getExtension(new File("D:\\3.xls").getAbsolutePath()));
		
		
		//if(true) return;
		
		XConnectionProvider connectionProvider = new SocketXConnectionProvider("192.168.18.15");//
		OpenOfficeFileTypeConverter c = new OpenOfficeFileTypeConverter();
		XLSRowOptimalHeightRefreshUpdater r = new XLSRowOptimalHeightRefreshUpdater();
		
		c.setConnectionProvider(connectionProvider);
		r.setConnectionProvider(connectionProvider);

		
		//r.refresh(new File("E:/work.home/appHome/attachments/2009/04/08/1239185315297.xls"), new File("d:\\excelrefreshed.xls"));
		//r.refresh(new File("d:\\2.xls"), new File("d:\\2_.pdf"), "pdf");
	
		//��һ�֣���refresh��Ȼ����ת��
		//r.update(new File("d:\\3.xls"), new File("d:\\2_1_m.xls"));
		//c.convert(new File("d:\\2_1_m.xls"), new File("d:\\2_1.pdf"));
		
		//�ڶ��֣�refreshͬʱת��
		//r.update(new File("d:\\3.xls"), new File("d:\\2_2.pdf"));
		//r.update(new File("E:\\work.home\\appHome\\temp\\export\\export-6312221180602441746.xls"), 
		//	new File("D:\\ex-by15a.pdf"));
		//r.update(new File("d:\\b.xlsx"), new File("d:\\b_.pdf"));
		
		//c.convert(new File("d:\\2.xls"), new File("d:\\2_2_15a.pdf"));
		r.update(new File("d:\\2.xls"), new File("d:\\2_2_15au.pdf"));
	}
}
