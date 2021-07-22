package cn.redflagsoft.base.service;

import java.math.BigDecimal;
import java.util.Date;

import cn.redflagsoft.base.bean.Caution;
import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Risk;

/**
 * ��ʾ�����ࡣ
 * @author lcj
 *
 */
public interface CautionService extends RFSObjectService<Caution>{
//	/**
//	 * ����Caution��
//	 * @param caution
//	 * @return
//	 */
//	Caution saveCaution(Caution caution);
//	
//		/**
//	 * ����IDɾ����
//	 * @param id
//	 */
//	void removeCaution(Long id);
//	
//	/**
//	 * ����
//	 * @param caution
//	 * @return
//	 */
//	Caution updateCaution(Caution caution);
	
	/**
	 * ����Risk��Ϣ����Caution����
	 * @param risk ��죨���գ�������Ϊ��
	 * @param superviseClerk ��ʾ�����ߣ�����Ϊ�գ�һ��Ϊ��쵥λ�����
	 * @return
	 */
	Caution createCaution(Risk risk, Clerk superviseClerk);
	
	/**
	 * ����Risk��Ϣ����Caution���󣬲���ȷ��ָ���˲���ֵ��
	 * 
	 * @param risk ��죨���գ�������Ϊ��
	 * @param superviseClerk ��ʾ�����ߣ�����Ϊ�գ�һ��Ϊ��쵥λ�����
	 * @param name ��ʾ����
	 * @param code ����
	 * @param grade �����
	 * @param objectAttrValue ��������Ե�ֵ
	 * @param conclusion ����
	 * @param happenTime ����ʱ��
	 * @return ��ʾ����
	 */
	Caution createCaution(Risk risk, Clerk superviseClerk, String name, String code, Byte grade, BigDecimal objectAttrValue, String conclusion, Date happenTime, String cautionSumary, String remark);
	
}
