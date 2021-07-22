package org.opoo.apps;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * 标识应用框架的版本。
 * 
 * GNU 风格的版本号命名格式:主版本号 . 子版本号 [. 修正版本号 [. 编译版本号 ]]
 * 
 * 英文对照 : Major_Version_Number.Minor_Version_Number[.Revision_Number[.Build_Number]]
　* 示例 : 1.2.1,2.0,5.0.0 build-13124
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class Version implements Comparable<Version>, Serializable{
	private static final long serialVersionUID = -7383390469560549043L;

	//static Log log  = LogFactory.getLog(Version.class);
	/**
	 * 获取实现版本。
	 * @return
	 */
	public static final String getImplementationVersion() {
		return Version.class.getPackage().getImplementationVersion();
	}

	/**
	 * 获取实现标题。
	 * @return
	 */
	public static final String getImplementationTitle() {
		return Version.class.getPackage().getImplementationTitle();
	}

	/**
	 * 
	 * @return
	 */
	public static final String getImplementationVendor() {
		return Version.class.getPackage().getImplementationVendor();
	}
	

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		//AppsHome.initialize();
//		System.setProperty(LogFactory.DIAGNOSTICS_DEST_PROPERTY, "STDERR");
//		
//		LogLog.setInternalDebugging(true);
//		LogLog.setQuietMode(false);
//		
//		//AppsHome.initialize();
//		
//		
//		System.setProperty("apps.logs", AppsHome.getAppsLogsPath());
//		
//		System.out.println(Version.class.getPackage());
//		
//		Log log  = LogFactory.getLog(Version.class);
//		AppsHome.initialize();
//		log.debug("");
//	}
	
	private int major;
    private int minor;
    private int revision;
    private String extraVersionInfo;
    
    private Version() {
    }

    /**
     * @since 1.6.6
     * @param major
     * @param minor
     * @param release
     */
    public Version(int major, int minor, int release) {
        this.major = major;
        this.minor = minor;
        this.revision = release;
    }

    /**
     * @since 1.6.6
     * @param major
     * @param minor
     * @param release
     * @param versionString
     */
    public Version(int major, int minor, int release, String versionString) {
        this.major = major;
        this.minor = minor;
        this.revision = release;
        this.extraVersionInfo = versionString;
    }

	
    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getRevision() {
        return revision;
    }

    public String getExtraVersionInfo() {
        return extraVersionInfo;
    }

    public String getVersionString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(major);
        buffer.append(".");
        buffer.append(minor);
        buffer.append(".");
        buffer.append(revision);
        if (extraVersionInfo != null) {
            buffer.append(" ");
            buffer.append(extraVersionInfo);
        }
        return buffer.toString();
    }

    /**
     * Peforms a comparison to see if a version is greater, lesser, or equal to
     * the current version. A comparison will not be performed on the version, since there is
     * no good way to compare it. This means there is the caveat that a version 1.0.1_beta3
     * will have the same comparison as 1.0.1.
     * 
     * 注意：只比较前3位。
     *
     * @param version Version to compare against
     * @return 1 if the current is greater, -1 if is less, 0 if is the same
     */
    public int compareTo(Version version) {

        if (major > version.major) {
            return 1;
        }
        else if (major < version.major) {
            return -1;
        }

        if (minor > version.minor) {
            return 1;
        }
        else if (minor < version.minor) {
            return -1;
        }
        
        if(revision > version.revision){
        	return 1;
        }else if(revision < version.revision){
        	return -1;
        }

        return 0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Version version = (Version) o;

        if (major != version.major) {
            return false;
        }
        if (minor != version.minor) {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if (revision != version.revision) {
            return false;
        }

        return !(extraVersionInfo != null ? !extraVersionInfo.equals(version.extraVersionInfo) :
                version.extraVersionInfo != null);
    }

    public int hashCode() {
        int result;
        result = major;
        result = 29 * result + minor;
        result = 29 * result + revision;
        result = 29 * result + (extraVersionInfo != null ? extraVersionInfo.hashCode() : 0);
        return result;
    }


    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Version");
        sb.append("{major=").append(major);
        sb.append(", minor=").append(minor);
        sb.append(", revision=").append(revision);
        sb.append(", extraVersionInfo='").append(extraVersionInfo).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Parse of a version string of the format 1.0.1 foo, the extraVersionInfo on
     * the end is optional
     *
     * @param s version string to parse.
     * @return a new version object maching the string
     */
    public static Version parseVersion(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Version string can not be null");
        }

        StringTokenizer tokenizer = new StringTokenizer(s.trim(), "- ");
        System.out.println(tokenizer.countTokens());
        String versionString = tokenizer.nextToken();
        
        Version version = new Version();
        String[] items = versionString.trim().split("\\.");

        try {
            version.major = Integer.parseInt(items[0]);
            version.minor = Integer.parseInt(items[1]);
            if(items.length > 2){
            	version.revision = Integer.parseInt(items[2]);
            }
            if(items.length > 3){
            	StringBuffer sb = new StringBuffer();
            	for(int i = 3 ; i < items.length ; i++){
            		if(i > 3){
            			sb.append("-");
            		}
            		sb.append(items[i]);
            	}
            	version.extraVersionInfo = sb.toString();
            }
            
//            if (items.length > 2) {
//                if (items[2].indexOf(" ") > -1) {
//                    version.revision
//                            = Integer.parseInt(items[2].substring(0, items[2].indexOf(" ")));
//                    version.extraVersionInfo
//                            = items[2].substring(items[2].indexOf(" "), items[2].length()).trim();
//                }
//                else {
//                    version.revision = Integer.parseInt(items[2]);
//                }
//            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Illegal version " + s);
        }
        
        while(tokenizer.hasMoreTokens()){
        	if(version.extraVersionInfo != null){
        		version.extraVersionInfo += "-" + tokenizer.nextToken();
        	}
        }
        return version;
    }
}
