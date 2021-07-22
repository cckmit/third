package cn.redflagsoft.base.service;

import java.util.List;
import java.util.Map;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Phrase;

public interface PhraseService {
	
	/**
	 * ǰ̨��ʾ�����б�
	 * 
	 * @param taskType
	 * @param workType
	 * @param processType
	 * @param clerkID
	 * @return List<Phrase>
	 */
	List<Phrase> findPhraseByTypes(int taskType, int workType, int processType, Long clerkID);
	
	List<Phrase> findPhraseByType( int processType);
	
	Phrase getPhraseBySn(Long sn);
	
	List<Map<String,Object>> findTitleByTypes(ResultFilter filter);
}
