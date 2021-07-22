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
	 * @param file �ļ�
	 * @param type �ļ�������
	 * @param partNumber �ļ��ı��
	 */
	public InputArtifact(File file, ConversionArtifactType type, int partNumber) {
		super();
		//FIXME �Ƿ���Ҫ�������жϣ���Ϊ����һ�㲻�����ⲿʹ��
		//���Կ��ܲ�����Ҫ�������жϷ�ʽ��������жϻή��Ч��
		if(file == null || !file.exists() || !file.canRead() || file.length() == 0){
			throw new IllegalArgumentException("�����ļ���Ч");
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
