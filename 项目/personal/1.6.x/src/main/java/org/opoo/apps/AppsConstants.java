package org.opoo.apps;



/**
 * ϵͳ������
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public abstract class AppsConstants {
	
	/**
	 * ģ������Ŀ¼����
	 */
	public static final String MODULE_INF = "MODULE-INF";
	/**
	 * ģ���Servlet Path��
	 */
	public static final String MODULE_SERVLET_PATH = "/modules/";
	/**
	 * ��Դ·����
	 */
	public static final String RESOURCE_SERVLET_PATH = "/resources/";
	/**
	 * ģ��Ŀ¼����
	 */
	public static final String MODULE_DIR_NAME = "modules";
	
	
	
	/**
	 * ÿ����Ϣ�е���󸽼�����
	 */
	public static final String ATTACHMENTS_MAX_ATTACHMENTS_PER_MESSAGE = "attachments.maxAttachmentsPerMessage";
	/**
	 * ��������С��
	 */
	public static final String ATTACHMENTS_MAX_ATTACHMENT_SIZE = "attachments.maxAttachmentSize";
	/**
	 * ���ػ����롣
	 */
	public static final String LOCALE_CHARACTER_ENCODING = "locale.characterEncoding";
	/**
	 * ���ػ����ԡ�
	 */
	public static final String LOCALE_LANGUAGE = "locale.language";
	/**
	 * ���ػ����ҡ�
	 */
	public static final String LOCALE_COUNTRY = "locale.country";
	/**
	 * ���ػ�ʱ����
	 */
	public static final String LOCALE_TIMEZONE = "locale.timeZone";
	
	
	/**
	 * ϵͳʵ��ID.
	 */
	public static final String INSTANCE_ID = "appsInstanceId";
	/**
	 * ϵͳ�ڼ�Ⱥ�еĽڵ�ID��
	 */
    public static final String NODE_ID = "appsNodeId";
    
    
    
    /**
     * 
     * ���ַ�����Ӧ���롣
     *
     */
    public static interface HttpServletResponse{
    	
    	/**
    	 * ϵͳά����
    	 * Maintenance
    	 */
    	int SC_MAINTENANCE = 270;
    	
    	/**
    	 * ��Ҫ����
    	 */
    	int SC_RESTART_REQUIRED = 271;
    	
    	
    	/**
    	 * δ��ȷ��װ
    	 */
    	int SC_NOT_SETUP = 272;
    }
}
