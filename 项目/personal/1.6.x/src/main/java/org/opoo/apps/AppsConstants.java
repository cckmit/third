package org.opoo.apps;



/**
 * 系统常数。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public abstract class AppsConstants {
	
	/**
	 * 模块配置目录名。
	 */
	public static final String MODULE_INF = "MODULE-INF";
	/**
	 * 模块的Servlet Path。
	 */
	public static final String MODULE_SERVLET_PATH = "/modules/";
	/**
	 * 资源路径。
	 */
	public static final String RESOURCE_SERVLET_PATH = "/resources/";
	/**
	 * 模块目录名。
	 */
	public static final String MODULE_DIR_NAME = "modules";
	
	
	
	/**
	 * 每条消息中的最大附件数。
	 */
	public static final String ATTACHMENTS_MAX_ATTACHMENTS_PER_MESSAGE = "attachments.maxAttachmentsPerMessage";
	/**
	 * 附件最大大小。
	 */
	public static final String ATTACHMENTS_MAX_ATTACHMENT_SIZE = "attachments.maxAttachmentSize";
	/**
	 * 本地化编码。
	 */
	public static final String LOCALE_CHARACTER_ENCODING = "locale.characterEncoding";
	/**
	 * 本地化语言。
	 */
	public static final String LOCALE_LANGUAGE = "locale.language";
	/**
	 * 本地化国家。
	 */
	public static final String LOCALE_COUNTRY = "locale.country";
	/**
	 * 本地化时区。
	 */
	public static final String LOCALE_TIMEZONE = "locale.timeZone";
	
	
	/**
	 * 系统实例ID.
	 */
	public static final String INSTANCE_ID = "appsInstanceId";
	/**
	 * 系统在集群中的节点ID。
	 */
    public static final String NODE_ID = "appsNodeId";
    
    
    
    /**
     * 
     * 各种服务响应代码。
     *
     */
    public static interface HttpServletResponse{
    	
    	/**
    	 * 系统维护中
    	 * Maintenance
    	 */
    	int SC_MAINTENANCE = 270;
    	
    	/**
    	 * 需要重启
    	 */
    	int SC_RESTART_REQUIRED = 271;
    	
    	
    	/**
    	 * 未正确安装
    	 */
    	int SC_NOT_SETUP = 272;
    }
}
