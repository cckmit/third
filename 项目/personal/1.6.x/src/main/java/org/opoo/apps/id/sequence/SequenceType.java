package org.opoo.apps.id.sequence;


/**
 * Sequence ���͡�
 * �оٵ�ǰʵ�ֵ����п��õ� Sequence �����͡�
 * ��ǰ�����㷨��֧���ڼ�Ⱥ��Ӧ�á�
 * @author Alex Lin(alex@opoo.org)
 *
 */
public enum SequenceType{
//	/**
//	 * �Զ��壬��Ҫ�û��Զ���
//	 */
//	custom,
	/**
	 * ͨ�����ݱ�洢�� id������Ϊ1����Ч��һ����Ϊ�����㷨�Ļ�����
	 */
	table,
	/**
	 * ͨ�����ݿ�����֧�ֵ� Sequence ���� id���������ض����ݿ⡣
	 */
	sequence,
	/**
	 * ͨ���ߵ�λ�㷨���� id����λ���� table����Ч��
	 */
	hilo, 
	/**
	 * ͨ���ߵ�λ�㷨���� id����λ���� sequence����Ч��
	 */
	seqilo,
	/**
	 * ͨ���黺�棬һ���Բ������ id����Ч��
	 */
	block,
//	/**
//	 * ͨ��Hibernate��ѯ���ݿ����id��ʹ�ù̶��Ŀ��С��
//	 */
//	hibernate,
//	/**
//	 * ͨ��Hibernate��ѯ ���ݿ����id�����ȡ���Բ���
//	 */
//	hiblock;
	/**
	 * Oracle block
	 */
	oracleBlock;
}
