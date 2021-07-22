package cn.redflagsoft.base.service;

import java.util.List;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.ObjectPeople;
import cn.redflagsoft.base.bean.RFSObject;
import cn.redflagsoft.base.vo.BatchUpdateResult;


/**
 * ҵ���������Ա֮���ϵ�ķ���ӿڶ��塣
 * 
 * @author Alex Lin(alex@opoo.org)
 *
 */
public interface ObjectPeopleService {
	/**
	 * ������ϵ��
	 * @param op
	 * @return
	 */
	ObjectPeople createObjectPeople(ObjectPeople op);
	
	
	/**
	 * ����һ����������
	 * @param objectId
	 * @param clerkID
	 * @param type
	 * @param objectType
	 * @return
	 */
	ObjectPeople createObjectPeople(Long objectId, Long clerkID,	int type, int objectType);
	
	/**
	 * �����˲��Ҷ���
	 * @param peopleId
	 * @param type
	 * @return
	 */
	List<RFSObject> findObjectsByPeopleId(Long peopleId, int type);
	
	/**
	 * ���ݶ��������
	 * @param objectId
	 * @param type
	 * @return
	 */
	List<Clerk> findClerksByObjectId(Long objectId, int type);
	
	/**
	 * ����ҵ�������Ա�͹�ϵ���Ͳ�ѯ��ϵ��������
	 * 
	 * @param objectId ҵ�����id
	 * @param peopleId ��ԱID
	 * @param type ��ϵ������
	 * @param objectType ҵ����������
	 * @return
	 */
	int getObjectPeopleCount(Long objectId, Long peopleId, int type, int objectType);
    
	/**
	 * ����ҵ�������Ա�͹�ϵ����ɾ����ϵ��
	 * 
	 * @param objectId ҵ�����id
	 * @param clerkID ��Աid
	 * @param type ��ϵ����
	 * @param objectType ҵ����������
	 */
	void removeObjectPeople(Long objectId, Long clerkID, int type, int objectType);
	
	/**
	 * ���ָ���˶�ָ������Ĺ�ע��
	 * ���� {@link #createObjectPeople(ObjectPeople)}�����ľ���������
	 * @param object ҵ�����
	 * @param clerk ��Ա
	 * @return ������ӳɹ��Ĺ�ϵ�������������ϵ�Ѿ����ڣ�����0
	 * @see ObjectPeople#TYPE_FOCUS
	 * @see #createObjectPeople(ObjectPeople)
	 */
	int addObjectFocus(RFSObject object, Clerk clerk);
	
	/**
	 * ɾ��ָ���˶�ָ������Ĺ�ע��
	 * ���� {@link #removeObjectPeople(Long, Long, short, short)} �����ľ���������
	 * @param object ҵ�����
	 * @param clerk ��Ա
	 * @return ����ɾ���ɹ��Ĺ�ϵ�������������ϵ�Ѿ����ڣ�����0
	 * @see ObjectPeople#TYPE_FOCUS
	 * @see #removeObjectPeople(Long, Long, short, short)
	 */
	 int removeObjectFocus(RFSObject object, Clerk clerk);
	 
	 
	/**
	 * ������ԱID���Ҷ���id���ϡ�
	 * 
	 * @param peopleId ��Աid
	 * @param type ��ϵ����
	 * @return ����id����
	 */
	List<Long> findObjectIdsByPeopleId(Long peopleId, int type);

	/**
	 * ���ݶ���id������Աid����
	 * @param objectId ����id
	 * @param type ��ϵ����
	 * @return ��Աid����
	 */
	List<Long> findPeopleIdsByObjectId(Long objectId, int type);
	
	/**
	 * Ϊָ��ID����Ա�������ö���
	 * �������ID����Ϊ�գ������ָ����ָ�����͵Ĺ�����ϵ��
	 * 
	 * @param peopleId ��ԱID
	 * @param objectIds ����ID����
	 * @param type ��ϵ����
	 * @param objectType ��������
	 * @return �������ý��
	 */
	BatchUpdateResult setObjectsToPeople(Long peopleId, List<Long> objectIds, int type, int objectType);
	
	/**
	 * ������ԱID���Ҷ���ID���ϡ�
	 * 
	 * @param peopleId ��ԱID
	 * @param type ��ϵ����
	 * @param objectType ��������
	 * @return ����ID����
	 */
	List<Long> findObjectIdsByPeopleId(Long peopleId, int type, int objectType);

	/**
	 * �Ƴ�ָ��������ϣ���
	 * 
	 * @param peopleId
	 * @param objectIds
	 * @param type
	 * @param objectType
	 * @return
	 */
	int removeObjectsOfPeople(Long peopleId, List<Long> objectIds, int type, int objectType);
	
	
	/**
	 * Ϊָ��ID����Ա������Ӷ���
	 * 
	 * <pre>
	 * Ҫ�ж�ָ���Ĺ�����ϵ�Ƿ��Ѿ����ڣ������ַ�ʽʵ�������飺
	 * 1. �ж�ÿ����ϵ�Ƿ���ڣ����������������������������½���ϵ����ÿ����ϵ������һ����ѯ��
	 * 	 	���ݿ��������Ϊ n + x�� ����(x <= n)���� n~2n �����ݲ��� ��
	 * 2. ��ɾ��Ҫ��ӵ����й�ϵ������û�У���Ȼ���������½���ϵ�����ݿ����Ϊ n + 1 �Ρ�
	 * 
	 * ͨ������£��ڶ��ָ��Ż���
	 * 
	 * ʹ�õ�һ��ʱ��BatchUpdateResult��updateRows��¼�Ѵ��ڵĹ���������createRows��¼�½��Ĺ���������
	 * ʹ�õڶ���ʱ��BatchUpdateResult��deleteRows��¼ɾ���Ĺ���������createRows��¼�½��Ĺ������� 
	 * ������Ĺ������� - ɾ���ļ�¼������
	 * </pre>
	 *
	 * @param peopleId
	 * @param objectIds
	 * @param type
	 * @param objectType
	 * @return
	 */
	BatchUpdateResult addObjectsToPeople(Long peopleId, List<Long> objectIds, int type, int objectType);
}
