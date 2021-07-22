package org.opoo.apps.id.sequence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.apps.AppsGlobals;
import org.opoo.apps.database.DataSourceManager;
import org.opoo.apps.database.DatabaseType;


/**
 * 默认的 Sequence 提供者。
 * 根据配置（Properties）决定具体采用何种 Sequence，相关的参数也定义在配置中。
 * 
 * @author Alex Lin(alex@opoo.org)
 */
public class DefaultSequenceProvider extends AbstractSequenceProvider implements SequenceProvider {
	
	private static final Log log = LogFactory.getLog(DefaultSequenceProvider.class);
	public static final String DEFAULT_SEQUENCE_TYPE = "SequenceProvider.default.sequenceType";
	public static final String DEFAULT_SEQUENCE_BLOCK_SIZE = "SequenceProvider.default.blockSize";
	public static final String DEFAULT_SEQUENCE_MAX_LO = "SequenceProvider.default.maxLo";
	public static final String DEFAULT_SEQUENCE_FLEXIBLE_BLOCK_SIZE_ENABLED = "SequenceProvider.default.flexibleBlockSizeEnabled";
	
	
	private Map<String, Sequence> sequences = new ConcurrentHashMap<String, Sequence>();
	private Map<SequenceType, SequenceBuilder> builders = new HashMap<SequenceType, SequenceBuilder>();
	
	private DatabaseType databaseType;
	private DataSource dataSource;
	//private TransactionTemplate transactionTemplate;
	private SequenceType sequenceType;
	private int maxLo = Byte.MAX_VALUE;
	private int blockSize;
	private boolean flexibleBlockSizeEnabled = true;
	
	public DefaultSequenceProvider(){
		initializeParams();
		initializeBuilders();
	}
	
	private void initializeParams(){
		dataSource = DataSourceManager.getRuntimeDataSource();
		databaseType = DataSourceManager.getMetaData().getDatabaseType();
//		if(Application.isContextInitialized()){
//			transactionTemplate = Application.getContext().get("transactionTemplate", TransactionTemplate.class);
//		}
		
		String st = AppsGlobals.getProperty(DEFAULT_SEQUENCE_TYPE, "block");
		try {
			sequenceType = SequenceType.valueOf(st);
		} catch (Exception e) {
			log.warn("Wrong sequence type '" + st + "', using 'block' default.");
			sequenceType = SequenceType.block;
		}
		
		blockSize = AppsGlobals.getProperty(DEFAULT_SEQUENCE_BLOCK_SIZE, 5);
		maxLo = AppsGlobals.getProperty(DEFAULT_SEQUENCE_MAX_LO, Byte.MAX_VALUE);
		flexibleBlockSizeEnabled = AppsGlobals.getProperty(DEFAULT_SEQUENCE_FLEXIBLE_BLOCK_SIZE_ENABLED, true);
	}
	
	private void initializeBuilders(){
		builders.clear();
		registerBuilder(SequenceType.table, new TableSequenceBuilder());
		registerBuilder(SequenceType.sequence, new DatabaseSequenceBuilder());
		registerBuilder(SequenceType.hilo, new HiLoSequenceBuilder());
		registerBuilder(SequenceType.seqilo, new SeqHiLoSequenceBuilder());
		registerBuilder(SequenceType.block, new BlockableSequenceBuilder());
		registerBuilder(SequenceType.oracleBlock, new OracleBlockableSequenceBuilder());
	}
	
	
	
	public synchronized Sequence getSequence(String key) {
		Sequence seq = sequences.get(key);
		if(seq == null){
			seq = createNewSequence(sequenceType, key);
			sequences.put(key, seq);
		}
		return seq;
	}
	

	protected Sequence createNewSequence(SequenceType sequenceType, String key) {
		return builders.get(sequenceType).buildSequence(key);
	}
	
	public void registerBuilder(SequenceType type, SequenceBuilder builder){
		builders.put(type, builder);
	}
	
	
	public static interface SequenceBuilder{
		Sequence buildSequence(String name);
	}
	
	class TableSequenceBuilder implements SequenceBuilder{
		public Sequence buildSequence(String name) {
			boolean isOracleUpdateReturningEnabled = AppsGlobals.getProperty("datasource.oracle.updateReturningEnabled", true);
			if(databaseType.isOracle() && isOracleUpdateReturningEnabled){
				return new OracleTableSequence2(dataSource, name);
			}else{
				return new TableSequence2(dataSource, name);
			}
		}
	}
	class DatabaseSequenceBuilder implements SequenceBuilder{
		public Sequence buildSequence(String name) {
			if(databaseType.isOracle()){
				return new OracleSequence(dataSource, "req_" + name);
			}
			if(databaseType.isPostgres()){
				return new PostgresSequence(dataSource, "req_" + name);
			}
			throw new IllegalArgumentException("不支持数据库Sequence, 请修改配置。");
		}
	}
	
	class HiLoSequenceBuilder extends TableSequenceBuilder{
		public Sequence buildSequence(String name) {
			Sequence s = super.buildSequence(name);
			return new HiLoSequence(s, maxLo);
		}
	}
	
	class SeqHiLoSequenceBuilder extends DatabaseSequenceBuilder{
		@Override
		public Sequence buildSequence(String name) {
			Sequence s = super.buildSequence(name);
			return new HiLoSequence(s, maxLo);
		}
	}
	
	class BlockableSequenceBuilder extends TableSequenceBuilder{
		@Override
		public Sequence buildSequence(String name) {
			Sequence s = super.buildSequence(name);
			//if(s instanceof Blockable){
			return new BlockableSequenceWrapper((Blockable)s, name, blockSize, flexibleBlockSizeEnabled);
			//}
			//return null;
		}
	}
	
	class OracleBlockableSequenceBuilder extends BlockableSequenceBuilder implements SequenceBuilder{
		public Sequence buildSequence(String name) {
			if(databaseType.isOracle()){
				return new BlockableSequenceWrapper(new OracleTableBlockable(dataSource, name), name, blockSize, flexibleBlockSizeEnabled);
			}else{
				//throw new IllegalStateException("当前不是Oracle数据库，无法启用OracleBlockSequence。");
				log.warn("当前不是Oracle数据库，无法启用OracleBlockSequence。使用普通版block sequence替代");
				return super.buildSequence(name);
			}
		}
	}
	
//	class HibernateSequenceBuilder implements SequenceBuilder{
//		public Sequence buildSequence(String name) {
//			HibernateTemplate template = Application.getContext();
//			return new HibernateSequence(template, name, blockSize);
//		}
//	}
}
