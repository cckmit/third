package cn.redflagsoft.base.util;

public class ConvertTimeUnit {
	public static String toCharacter(byte timeUnit){
		switch(timeUnit){
		case 1: return "��";
		case 2: return "��";
		case 3: return "��";
		case 4: return "��";
		case 5: return "ʱ";
		case 6: return "��";
		case 7: return "��";
		case 8: return "����";
		case 9: return "��";
		default:
			return "";
		}
	}
}
