package org.opoo.apps.dv.converter;

import org.opoo.apps.dv.ConversionArtifactType;

/**
 * 转换器工厂。
 * @author lcj
 *
 */
public interface ConverterFactory {

	/**
	 * 查找指定类型的转换器。
	 * 
	 * @param type 类型
	 * @return 转换器
	 */
	Converter getConverter(ConversionArtifactType type);
}
