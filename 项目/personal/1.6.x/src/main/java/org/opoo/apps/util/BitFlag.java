package org.opoo.apps.util;


public class BitFlag implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2914496791092181683L;
	private int mask;
	
	
	public BitFlag() {
	}
	
	public BitFlag(int mask){
		this.mask = mask;
	}
	
	public BitFlag set(int flag){
		this.mask |= flag;
		return this;
	}
	
	public BitFlag clear(int flag){
		this.mask &= ~flag;
		return this;
	}
	
	
	public BitFlag setValue(int position, boolean value){
		if(position > 31 || position < 1){
			throw new IllegalArgumentException("存储标志的位置必须在 1 到 31 之间 。");
		}
		int m = (int) Math.pow(2, position - 1);
		System.out.println(m);
		return value ? set(m) : clear(m);
	}
	
	public boolean getValue(int position){
		int m = (int) Math.pow(2, position - 1);
		return has(m);
	}
	
	public boolean has(int flag){
		return (mask & flag) != 0L;
	}
	
	
	public String toString(){
		return StringUtils.zeroPadString(Integer.toBinaryString(mask), 32);
	}
	
	public int intValue(){
		return mask;
	}
	
	
	
	public static void main(String[] args){
		BitFlag bf = new BitFlag();
		bf.set(1);
		System.out.println(bf);
		bf.clear(8);
		System.out.println(bf);
		bf.set(16);
		System.out.println(bf);
		
		String string = Integer.toBinaryString(Integer.MAX_VALUE);
		System.out.println(string);
		
		bf = new BitFlag();
		bf.setValue(5, true);
		System.out.println(bf.toString());
		bf.setValue(4, false);
		System.out.println(bf.toString());
		
		System.out.println(bf.getValue(4));
		System.out.println(bf.getValue(5));
	}
}
