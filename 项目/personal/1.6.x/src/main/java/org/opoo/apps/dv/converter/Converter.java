package org.opoo.apps.dv.converter;

import java.util.List;

import org.opoo.apps.dv.ConversionMetaData;


/**
 * 文件转换器。例如Office转换（PDF2SWF）或者视频转换（rmvb to flv）转换器。
 * 
 * @author lcj
 */
public interface Converter {

	/**
	 * 执行文件转换。
	 * 如果是转换Office文件，一般要指定页码（分段）集合。
	 * 
	 * @param cm 要转换的对象的元数据
	 * @param inputFile 输入文件
	 * @param partNumbers 页码（分段）集合
	 */
	 void convert(ConversionMetaData cm, InputArtifact inputFile, List<Integer> partNumbers);
	 
}
