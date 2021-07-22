package org.opoo.apps.dv.office.converter;

public class TestGenericConversionConverterFactory extends GenericOfficeConverterFactory {
	@Override
	protected GenericOfficeConverter buildConverterInstance() {
		return new TestGenericConversionConverter();
	}
}
