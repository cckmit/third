package org.opoo.apps.dv.converter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.opoo.apps.dv.ConversionArtifactType;

public abstract class AbstractConverterFactory implements ConverterFactory{
	private boolean useCache;
	private ConcurrentMap<ConversionArtifactType, Converter> converters = new ConcurrentHashMap<ConversionArtifactType, Converter>();

	/**
	 * 是否使用缓存
	 * @return
	 */
	public boolean isUseCache() {
		return useCache;
	}

	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}

	public final Converter getConverter(ConversionArtifactType type) {
		if(!useCache){
			return createAndPopulateCreator(type);
		}
		
		Converter converter = converters.get(type);
		if(converter == null){
			converter = createAndPopulateCreator(type);
			if(converter != null){
				converters.put(type, converter);
			}
		}
		return converter;
	}
	
	private Converter createAndPopulateCreator(ConversionArtifactType type){
		Converter converter = createConverter(type);
		if(converter instanceof ConverterPrototype){
			((ConverterPrototype) converter).setConversionArtifactType(type);
		}
		return converter;
	}

	/**
	 * 根据类型创建转换器。
	 * @param type
	 * @return
	 */
	protected abstract Converter createConverter(ConversionArtifactType type);
}
