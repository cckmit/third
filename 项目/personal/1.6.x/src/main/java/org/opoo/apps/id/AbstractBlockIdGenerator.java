package org.opoo.apps.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.bean.core.SysId;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;


/**
 * 批量产生的ID生成器。
 * 
 * @author Alex Lin(alex@opoo.org)
 * @deprecated 不适合集群，标记为过期
 */
public abstract class AbstractBlockIdGenerator implements LongIdGenerator, InitializingBean {
	protected static Log log = LogFactory.getLog(AbstractBlockIdGenerator.class);
	public static final int DEFAULT_BLOCK_SIZE = 5;
	public static final int DEFAULT_STEP = 1;
	
	
	private int blockSize = DEFAULT_BLOCK_SIZE;
	private int step = DEFAULT_STEP;
	private long current = 0L;
	private String name;
	private boolean initialized = false;
	
	
	public AbstractBlockIdGenerator(String name){
		this.name = name;
		//afterPropertiesSet();
		log.debug("create " + this.getClass() + " for name:" + name);
	}
	public void afterPropertiesSet(){
		Assert.hasLength(name);
	}
	protected void initialize()  {
		if(!initialized){
			//Assert.hasLength(name);
			log.debug("init - blockSize: " + blockSize);
			SysId pid = loadSysId(name);
			if(pid == null){
				SysId id = new SysId(name, current);
				id.setStep(step);
				createSysId(id);
				log.debug("init - create persistent id: " + id);
			}else{
				current = pid.getCurrent();
				step = pid.getStep();
				current = (current / blockSize) * blockSize + blockSize;
			}
			initialized = true;
			log.debug("init - inited current: " + current);
		}
	}
	
	protected abstract SysId loadSysId(String name);
	protected abstract void createSysId(SysId persistentId);
	protected abstract void updateSysId(String name, long current);
	
	
	public final Long getCurrent() {
		initialize();
		return current;
	}
	
	public final Long getNext() {
		initialize();
		
		if(current % blockSize == 0){
			updateSysId(name, current);
		}
		current++;
		return current;
	}

	public final int getBlockSize() {
		return blockSize;
	}

	public final void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public final int getStep() {
		return step;
	}

	public final void setStep(int step) {
		this.step = step;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return String.valueOf(current);
	}
	
	public static class DefaultBlockIdGenerator extends AbstractBlockIdGenerator{		
		public DefaultBlockIdGenerator(String name) {
			super(name);
		}

		@Override
		protected void createSysId(SysId persistentId) {			
		}

		@Override
		protected SysId loadSysId(String name) {
			return null;
		}

		@Override
		protected void updateSysId(String name, long current) {			
		}
	}
	
	
	public static void main(String[] args){
		AbstractBlockIdGenerator g = new DefaultBlockIdGenerator("user");
		IdGenerator<String> gg = new StringIdGeneratorWrapper(g);
		StopWatch s = new StopWatch("test id generator");
		s.start("g");
		for(int i = 0 ; i < 1002 ; i++){
			long l = g.getNext();
			System.out.println(l);
			System.out.println(gg.getNext());
			//gg.getNext();
		}
		s.stop();
		System.out.println(s.prettyPrint());
	}
}
