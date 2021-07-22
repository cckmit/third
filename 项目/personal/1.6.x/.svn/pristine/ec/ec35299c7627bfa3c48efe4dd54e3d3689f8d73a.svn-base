package org.opoo.apps.file.converter;

import java.util.Arrays;
import java.util.List;

public class FileTypesPair implements java.io.Serializable{
	private static final long serialVersionUID = -3347406316441852708L;
	
	public static final String SEPARATOR = ",";
	private List<String> inputTypes;
	private List<String> outputTypes;
		
	public FileTypesPair(List<String> inputTypes, List<String> outputTypes) {
		super();
		this.inputTypes = lowerCase(inputTypes);
		this.outputTypes = lowerCase(outputTypes);

	}
	
	private static List<String> lowerCase(List<String> list){
		for(int i = 0, n = list.size() ; i < n ; i++){
			String s = list.get(i).toLowerCase().trim();
			list.set(i, s);
		}
		return list;
	}
	
	public FileTypesPair(String[] inputTypes, String[] outputTypes) {
		this(Arrays.asList(inputTypes), Arrays.asList(outputTypes));
	}
	
	public FileTypesPair(String inputTypes, String outputTypes) {
		this(inputTypes.split(SEPARATOR), outputTypes.split(SEPARATOR));
	}
	
	public boolean supports(String fromFormat, String toFormat){
		return inputTypes.contains(fromFormat) && outputTypes.contains(toFormat);
	}
}
