package org.opoo.apps.json;

/**
 * ×ÖÄ¸Ë³ÐòµÄÁ¬ÐøºÅÂë¡£
 * a,b,c,...z,aa,ab,...
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class AlphaSerialNumber implements SerialNumber {
	char[] chars = new char[10];
	int max = 0;

	public AlphaSerialNumber() {
		for (int i = 0; i < 10; i++) {
			chars[i] = 0;
		}
	}

	void j(int i) {
		if (i == 10) {
			return;
		}
		if (i > max) {
			max = i;
		}
		if (chars[i] == 0) {
			chars[i] = 'a';
		} else if (chars[i] == 'z') {
			chars[i] = 'a';
			j(i + 1);
		} else {
			chars[i]++;
		}
		//System.out.println(max + "," + chars[0] + "," +chars[1] + "," + chars[3]);
	}

	public String next() {
		j(0);
		if (max == 0) {
			return String.valueOf(chars[0]);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = max; i >= 0; i--) {
			sb.append(chars[i]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		AlphaSerialNumber sn = new AlphaSerialNumber();
		for (int i = 0; i < 1000; i++) {
			System.out.println(sn.next());
			System.out.println(Long.toString(i, 20) + " - " + i);
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}
