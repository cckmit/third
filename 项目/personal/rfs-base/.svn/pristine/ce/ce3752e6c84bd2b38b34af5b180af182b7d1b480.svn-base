package cn.redflagsoft.base.dao.hibernate3;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.dao.RiskLogDao;
import cn.redflagsoft.base.test.BaseTests;


public class RiskLogHibernateDaoTest extends  BaseTests {
	protected RiskLogDao riskLogDao;
	
	public void test1() throws Exception{	
		Date now=new Date();
		
		Calendar c=Calendar.getInstance();
		c.set(2009, 9, 1, 17, 50, 0);
		
		List<Long> dutyClerkIDS=new ArrayList<Long>();
		dutyClerkIDS.add(872L);
		dutyClerkIDS.add(892L);
		dutyClerkIDS.add(756L);
		List<RiskLog> lr=riskLogDao.findGradeChanged(c.getTime(), now, (byte)1, dutyClerkIDS);
		HashMap pmap=new HashMap();
		HashMap tmap=new HashMap();
		for(RiskLog r:lr){
			if(pmap.get(r.getProjectName())!=null){
				pmap.put(r.getProjectName(),(Long)pmap.get(r.getProjectName())+1);
			}else
			{
				pmap.put(r.getProjectName(),1L);
			}
			if(tmap.get(r.getTaskName())!=null){
				tmap.put(r.getTaskName(),(Long)tmap.get(r.getTaskName())+1);
			}else
			{
				tmap.put(r.getTaskName(),1L);
			}
			System.out.println(r.getProjectName());
			System.out.println(r.getTaskName());
			System.out.println(r.getGrade());
		}
		
		System.out.println(pmap.toString());
		System.out.println(tmap.toString());
		Object[] os=pmap.keySet().toArray();
		for(Object o:os){
			System.out.println(o);
		}
		Object[] os2=tmap.keySet().toArray();
		for(Object o:os2){
			System.out.println(o);
		}
	}
}
