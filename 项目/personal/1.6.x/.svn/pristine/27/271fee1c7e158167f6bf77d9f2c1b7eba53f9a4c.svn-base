package org.opoo.apps.file.handler;


import org.opoo.apps.Model;

public class SampleHandler implements FileHandler {

	private boolean interupt;
	public SampleHandler() {
		super();
	}
	
	public SampleHandler(boolean interupt) {
		super();
		this.interupt = interupt;
	}

	public Model handle(Model lastResult, FileInfo fileInfo, FileHandlerChain chain)throws Exception {
		lastResult.setMessage(this.toString());
		if(this.interupt){
			return lastResult;
		}
		//new FilterChain();
		return chain.handle(lastResult, fileInfo);
	}
}
