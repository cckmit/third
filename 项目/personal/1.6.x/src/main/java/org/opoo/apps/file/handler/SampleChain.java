package org.opoo.apps.file.handler;

import java.util.ArrayList;
import java.util.List;

import org.opoo.apps.Model;


public class SampleChain implements FileHandlerChain {

	private int index = 0;
	private List<FileHandler> handlers;



	/**
	 * @return the handlers
	 */
	public List<FileHandler> getHandlers() {
		return handlers;
	}


	/**
	 * @param handlers the handlers to set
	 */
	public void setHandlers(List<FileHandler> handlers) {
		this.handlers = handlers;
	}
	
	
	public static void main(String[] args) throws Exception{
		List<FileHandler> handlers = new ArrayList<FileHandler>();
		FileHandler fh = new SampleHandler();
		handlers.add(fh);
		fh = new SampleHandler(true);
		handlers.add(fh);
		fh = new SampleHandler();
		handlers.add(fh);
		//handlers.iterator().next();
		
		System.out.println(handlers);
		
		SampleChain chain = new SampleChain();
		chain.setHandlers(handlers);
		Model m = chain.handle(new Model(), null);
		System.out.println(m.getMessage());
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(9);
		list.add(10);
		list.add(0, 20);
		System.out.println(list);
	}


	public Model handle(Model model, FileInfo fileInfo) throws Exception {
		if(index >= handlers.size()){
			return model;
		}
		
		FileHandler fh = handlers.get(index);
		System.out.println("handler " + index + ": " + fh);
		index++;
		return fh.handle(model, fileInfo, this);
	}

}
