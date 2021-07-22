package org.opoo.apps.id;

import java.io.Serializable;

import org.opoo.apps.util.StringUtils;

/**
 * String 型的 ID 生成器包装类。
 * 
 * 通过一个其他类型的ID生成器包装成一个String类型的ID 生成器。
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class StringIdGeneratorWrapper implements StringIdGenerator {
	private IdGenerator<? extends Serializable> gen;
	private int length = 20;
	public StringIdGeneratorWrapper(IdGenerator<? extends Serializable> longGen, int length){
		this.length = length;
		this.gen = longGen;
	}
	
	public StringIdGeneratorWrapper(IdGenerator<? extends Serializable> longGen){
		this.gen = longGen;
	}
	
	public String getCurrent() {
		return StringUtils.zeroPadString(String.valueOf(gen.getCurrent()), length);
	}

	public String getNext() {
		return StringUtils.zeroPadString(String.valueOf(gen.getNext()), length);
	}
	
	public String toString(){
		return super.toString() + ":" +  getCurrent();
	}
}
