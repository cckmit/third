package org.opoo.apps.id.sequence;


/**
 * Sequence 类型。
 * 列举当前实现的所有可用的 Sequence 的类型。
 * 当前集中算法都支持在集群中应用。
 * @author Alex Lin(alex@opoo.org)
 *
 */
public enum SequenceType{
//	/**
//	 * 自定义，需要用户自定义
//	 */
//	custom,
	/**
	 * 通过数据表存储的 id，步进为1，低效，一般作为其它算法的基础。
	 */
	table,
	/**
	 * 通过数据库自身支持的 Sequence 产生 id。依赖于特定数据库。
	 */
	sequence,
	/**
	 * 通过高低位算法产生 id，高位采用 table。高效。
	 */
	hilo, 
	/**
	 * 通过高低位算法产生 id，高位采用 sequence。高效。
	 */
	seqilo,
	/**
	 * 通过块缓存，一次性产生多个 id。高效。
	 */
	block,
//	/**
//	 * 通过Hibernate查询数据库产生id，使用固定的块大小。
//	 */
//	hibernate,
//	/**
//	 * 通过Hibernate查询 数据库产生id，块存取策略采用
//	 */
//	hiblock;
	/**
	 * Oracle block
	 */
	oracleBlock;
}
