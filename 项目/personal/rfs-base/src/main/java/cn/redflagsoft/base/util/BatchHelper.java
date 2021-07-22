/*
 * $Id: BatchHelper.java 6174 2013-01-10 05:55:58Z lcj $
 * 
 * Copyright 2007-2009 RedFlagSoft.CN All Rights Reserved.
 * RedFlagSoft PROPRIETARY/CONFIDENTIAL.
 *
 * δ�������к�����Ϣ�������޹�˾��ɣ��κ��˲������ԣ������������ڣ�
 * �ԷǷ��ķ�ʽ���ơ�������չʾ���������ء����ء����ã�ʹ�á�
 */
package cn.redflagsoft.base.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opoo.ndao.support.ResultFilter;
import org.opoo.util.Assert;

import cn.redflagsoft.base.service.ObjectByIDFinder;
import cn.redflagsoft.base.service.ObjectFinder;
import cn.redflagsoft.base.service.ObjectHandler;

/**
 * �������ߡ�
 * 
 * @author Alex Lin(lcql@msn.com)
 *
 */
public abstract class BatchHelper {
	private static final Log log = LogFactory.getLog(BatchHelper.class);
	private static final boolean IS_DEBUG_ENABLED = log.isDebugEnabled();
	
	/**
	 * ���δ������
	 * ÿ���ζ�ֱ�Ӳ�ѯ�����󼯺ϣ�Ȼ��ѭ������ÿ������
	 * 
	 * @param finder ��ѯ��
	 * @param handler ������
	 * @param filter ��ѯ����
	 * @param handlerChangeFinder ����������Ƿ��Ӱ���ѯ���Ĳ�ѯ����
	 * @return ��������
	 */
	public static <T,R> int batchHandleObject(ObjectFinder<T> finder, ObjectHandler<T,R> handler, 
			ResultFilter filter, boolean handlerChangeFinder){
		Assert.notNull(filter, "����ָ����ѯ����");
		Assert.isTrue(filter.getMaxResults() > 0, "ÿ���δ��������������0����filter.maxResults��ָ��");

		int pageSize = filter.getMaxResults();
		int objectCount = finder.getObjectCount(filter);
		
		if(objectCount == 0){
			log.debug("�����������Ϊ0����������");
			return 0;
		}
		
		if(IS_DEBUG_ENABLED){
			log.debug("���д��������" + objectCount + "������ʼ����...");
		}
		
		int index = 0;
		int resultCount = 0;
		while(true){
			ResultFilter filter2 = ResultFilter.createResultFilter(filter);
			filter2.setFirstResult(handlerChangeFinder ? 0 : index);//����������Ӱ���˲�ѯ����ÿ�ζ��ӵ�0����ʼ��ѯ
			List<T> objects = finder.findObjects(filter2);
			
			if(!objects.isEmpty()){
				if(IS_DEBUG_ENABLED){
					log.debug("��ʼ�����" + (index + 1) + '-' + (index + objects.size()) + "��...");
				}
				for(T object:objects){
					index++;
					
					if(IS_DEBUG_ENABLED){
						log.debug("handle object #" + index + ": " + object);
					}
					R r = handler.handle(object);
					if(r != null){
						resultCount++;
					}
				}
			}
			
			//���û�в�ѯ���κμ�¼���߼�¼��С�ڷ�ҳ����ʾ�Ѿ���������������
			if(objects.size() < pageSize){
				break;
			}
			//��������Ӱ���ѯ���������ôﵽ��������������ҳ��д10���ܹ�100����ĿǰindexΪ100��
			if(!handlerChangeFinder && index >= objectCount){
				break;
			}
		}
		
		/*
		for(int i = 0 ; i < objectCount ; i += filter.getMaxResults()){
			ResultFilter filter2 = ResultFilter.createResultFilter(filter);
			filter2.setFirstResult(i);
			List<T> objects = finder.findObjects(filter2);
			if(IS_DEBUG_ENABLED){
				log.debug("��ʼ�����" + (i + 1) + '-' + (i + objects.size()) + "��...");
			}
			for(T object:objects){
				if(IS_DEBUG_ENABLED){
					index++;
					log.debug("handle object #" + index + ": " + object);
				}
				R r = handler.handle(object);
				if(r != null){
					resultCount++;
				}
			}
		}*/
		
		if(IS_DEBUG_ENABLED){
			log.debug("���д��������" + objectCount + "����ʵ�ʴ������" + resultCount + "����ѭ��" + index + "��");
		}
		return resultCount;
	}
	
	/**
	 * ���δ������
	 * ÿ���ζ�ֻ��ѯ������ID���ϣ�Ȼ��ѭ����ѯÿ�������ٽ��д����ʺ�ʹ���˻���
	 * �Ĳ�ѯ��������RFSObject��ϵ��ObjectService��
	 * 
	 * @param finder ��ѯ��
	 * @param handler ������
	 * @param filter ��ѯ����
	 * @param handlerChangeFinder ����������Ƿ��Ӱ���ѯ���Ĳ�ѯ����
	 * @return ��������
	 * @deprecated
	 */
	public static <T,K,R> int batchHandleObjectByID(ObjectByIDFinder<T, K> finder, ObjectHandler<T,R> handler, 
			ResultFilter filter, boolean handlerChangeFinder){
		Assert.notNull(filter, "����ָ����ѯ����");
		Assert.isTrue(filter.getMaxResults() > 0, "ÿ���δ��������������0����filter.maxResults��ָ��");
		
		int pageSize = filter.getMaxResults();
		int objectCount = finder.getObjectCount(filter);
		
		if(objectCount == 0){
			log.debug("�����������Ϊ0����������");
			return 0;
		}
		
		
		if(IS_DEBUG_ENABLED){
			log.debug("���д��������" + objectCount + "������ʼ����...");
		}
		
		int index = 0;
		int resultCount = 0;
		while(true){
			ResultFilter filter2 = ResultFilter.createResultFilter(filter);
			filter2.setFirstResult(handlerChangeFinder ? 0 : index);
			List<K> ids = finder.findObjectIds(filter2);
			
			if(!ids.isEmpty()){
				if(IS_DEBUG_ENABLED){
					log.debug("��ʼ�����" + (index + 1) + '-' + (index + ids.size()) + "��...");
				}
				for(K id: ids){
					index++;

					if(IS_DEBUG_ENABLED){
						log.debug("handle object #" + index + ": " + id);
					}
					T object = finder.getObject(id);
					R r = handler.handle(object);
					if(r != null){
						resultCount++;
					}
				}
			}
			
			//���û�в�ѯ���κμ�¼���߼�¼��С�ڷ�ҳ����ʾ�Ѿ���������������
			if(ids.size() < pageSize){
				break;
			}
			//��������Ӱ���ѯ���������ôﵽ��������������ҳ��д10���ܹ�100����ĿǰindexΪ100��
			if(!handlerChangeFinder && index >= objectCount){
				break;
			}
		}
		
		
		/*
		for(int i = 0 ; i < objectCount ; i += filter.getMaxResults()){
			ResultFilter filter2 = ResultFilter.createResultFilter(filter);
			filter2.setFirstResult(i);
			List<K> ids = finder.findObjectIds(filter2);
			
			if(IS_DEBUG_ENABLED){
				log.debug("��ʼ�����" + (i + 1) + '-' + (i + ids.size()) + "��...");
			}
			
			for(K id: ids){
				if(IS_DEBUG_ENABLED){
					index++;
					log.debug("handle object #" + index + ": " + id);
				}
				T object = finder.getObject(id);
				R r = handler.handle(object);
				if(r != null){
					resultCount++;
				}
			}
		}
		*/
		if(IS_DEBUG_ENABLED){
			log.debug("���д��������" + objectCount + "����ʵ�ʴ������" + resultCount + "����ѭ��" + index + "��");
		}
		return resultCount;
	}
}
