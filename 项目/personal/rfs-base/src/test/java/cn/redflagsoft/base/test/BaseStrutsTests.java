package cn.redflagsoft.base.test;

import org.opoo.apps.test.StrutsTests;


/**
 * Struts2 ���Ի����ࡣ
 * 
 * ������������Ӧ�ó��򣨰������� Spring �������Ĳ�����
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public abstract class BaseStrutsTests extends StrutsTests {

	/* (non-Javadoc)
	 * @see org.opoo.apps.test.StrutsTests#getContextLoaderClassName()
	 */
	@Override
	protected String getContextLoaderClassName() {
		return TestParams.contextLoaderClassName;
	}

	/* (non-Javadoc)
	 * @see org.opoo.apps.test.StrutsTests#getInstanceLocation()
	 */
	@Override
	protected String getInstanceLocation() {
		return TestParams.instanceLocation;
	}
	
}
