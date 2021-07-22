package cn.redflagsoft.base.bean;

/**
 * 帮助类
 * @author zf
 *
 */
public class Help extends VersionableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String title;			//	标题	TITLE	VARCHAR2(200)
	private String scene;			//	场景	SCENE	VARCHAR2(200)
	private String url;				//	URL	URL	VARCHAR2(200)
	private String content;			//	内容	CONTENT	VARCHAR2(4000)
	private String summary;			//	摘要	SUMMARY	VARCHAR2(500)
	private String path;			//	内容文件路径	PATH	VARCHAR2(200)
	private int width = 0;			//	宽度	W	NUMBER(10)
	private int height = 0;			//	高度	H	NUMBER(10)
	private int x = 0;				//	位置X	X	NUMBER(10)
	private int y = 0;				//	位置Y	Y	NUMBER(10)
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getScene() {
		return scene;
	}
	public void setScene(String scene) {
		this.scene = scene;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
