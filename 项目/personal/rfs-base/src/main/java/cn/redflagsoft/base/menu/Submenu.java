package cn.redflagsoft.base.menu;


/**
 * �Ӳ˵���
 * 
 * �Ӳ˵�����Ҳ�ǲ˵������ü̳з�ʽ��
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public interface Submenu extends Menu {
	
	/**
	 * �ϼ��˵���
	 * 
	 * @return
	 */
	Menu getSuperior();
	
	/**
	 * ָ����ǰ�Ӳ˵��Ƿ��Ǵ� superior �� parent ���Ӳ˵��̳ж����ġ�
	 * 
	 * @return
	 */
	boolean isInherited();
}
