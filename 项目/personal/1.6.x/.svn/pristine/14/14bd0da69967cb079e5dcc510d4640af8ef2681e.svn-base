package org.opoo.apps.file.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.opoo.apps.Model;

/**
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public class SimpleFileHandlerChain implements FileHandlerChain {
	private static final Log log = LogFactory.getLog(SimpleFileHandlerChain.class);
	private Iterator<FileHandler> iterator;
	private List<FileHandler> handlers;
	
	public Model handle(Model model, FileInfo inputFile) throws Exception {
		if(iterator.hasNext()){
			FileHandler fh = iterator.next();
			model = fh.handle(model, inputFile, this);
		}
		return model;
	}
	
	/**
	 * 
	 * @return
	 */
	public Model perform(FileInfo fileInfo){
		if(handlers == null){
			return new Model(false, "没有定义处理器", null);
		}
		if(fileInfo.getFile() == null){
			return new Model(false, "被处理文件为空", null);
		}
		try {
			iterator = handlers.iterator();
			return handle(new Model(), fileInfo);
		} catch (Exception e) {
			log.error("perform chain error", e);
			return new Model(false, e.getMessage(), null);
		}
	}
	
	
	public void release(){
		iterator = null;
	}

	public void pause(){
		iterator = handlers.iterator();
	}
	
	
	


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
	
	
	
	public static void main(String[] args){
		List<FileHandler> handlers = new ArrayList<FileHandler>();
		FileHandler fh = new SampleHandler();
		handlers.add(fh);
		fh = new SampleHandler(true);
		handlers.add(fh);
		fh = new SampleHandler();
		handlers.add(fh);
		//handlers.iterator().next();
		
		System.out.println(handlers);
		
		SimpleFileHandlerChain chain = new SimpleFileHandlerChain();
		chain.setHandlers(handlers);
		Model m = chain.perform(null);
		System.out.println(m.getMessage());
	}
}
