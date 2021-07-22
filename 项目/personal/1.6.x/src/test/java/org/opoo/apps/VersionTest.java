package org.opoo.apps;

import static org.junit.Assert.*;

import org.junit.Test;

public class VersionTest {

	@Test
	public void test() {
		//fail("Not yet implemented");

		Version version1 = Version.parseVersion("1.6.5.4");
		Version version2 = Version.parseVersion("1.6.5.4a");
		Version version3 = Version.parseVersion("1.6.5.4-SNAPSHOT");
		Version version4 = Version.parseVersion("1.6.5.4 DEV sdasd");
		
		System.out.println(version1);
		System.out.println(version2);
		System.out.println(version3);
		System.out.println(version4);
		
		System.out.println(isResolveRequired());
	}
	
	protected static boolean isResolveRequired(){
		try {
			//��1.6.6�汾��ʼ�������������������
			//���Ҵ���汾��ʼ�������еĹ��ܣ��Ѿ����ɵ���opoo-apps�У����
			//�����������ﴦ��
			Version.class.getConstructor(int.class, int.class, int.class);
		} catch (NoSuchMethodException e) {
			System.out.println(e);
			return true;
		}
		return false;
	}

}
