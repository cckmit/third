package cn.redflagsoft.base.menu;


/**
 * 找不到菜单的异常。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class MenuNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8432199575741038019L;

	public MenuNotFoundException() {
	}

	public MenuNotFoundException(String message) {
		super(message);
	}

	public MenuNotFoundException(Throwable cause) {
		super(cause);
	}

	public MenuNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MenuNotFoundException(Long id) {
		this("找不到 id 为 '" + id + "' 菜单。");
	}

}
