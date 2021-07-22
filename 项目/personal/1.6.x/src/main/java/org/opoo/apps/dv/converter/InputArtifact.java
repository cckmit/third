package org.opoo.apps.dv.converter;

import java.io.File;
import java.io.Serializable;

import org.opoo.apps.dv.ConversionArtifactType;

public class InputArtifact implements Serializable{
	private static final long serialVersionUID = -6548907853259749984L;
	private final File file;
	private final ConversionArtifactType type;
	private final int partNumber;
	
	/**
	 * 
	 * @param file 文件
	 * @param type 文件的类型
	 * @param partNumber 文件的编号
	 */
	public InputArtifact(File file, ConversionArtifactType type, int partNumber) {
		super();
		//FIXME 是否需要这样的判断，因为该类一般不是在外部使用
		//所以可能并不需要这样的判断方式，过多的判断会降低效率
		if(file == null || !file.exists() || !file.canRead() || file.length() == 0){
			throw new IllegalArgumentException("输入文件无效");
		}
		this.file = file;
		this.type = type;
		this.partNumber = partNumber;
	}
	
	public InputArtifact(File file, ConversionArtifactType type){
		this(file, type, 0);
	}
	
	public File getFile() {
		return file;
	}
	
	public ConversionArtifactType getType() {
		return type;
	}

	public int getPartNumber() {
		return partNumber;
	}
}
