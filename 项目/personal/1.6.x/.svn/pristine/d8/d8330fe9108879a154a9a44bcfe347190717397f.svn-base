package org.opoo.apps.json;

/**
 * 使用前缀生成序列号。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class PrefixSerialNumber implements SerialNumber {
	private int i = 0;
	private String prefix = "x";

	public PrefixSerialNumber() {
	}

	public PrefixSerialNumber(String prefix) {
		this.prefix = prefix;
	}

	public synchronized String next() {
		return prefix + (i++);
	}

	public static void main(String[] args) {
		PrefixSerialNumber sn = new PrefixSerialNumber();
		System.out.println(sn.next());
		System.out.println(sn.next());
	}
}
