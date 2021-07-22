package cn.redflagsoft.base.web.struts2;

import java.util.*;
import cn.redflagsoft.base.test.BaseTests;
public class MaxIDTest extends BaseTests {

	
public void testFindMaxID(){
			final String sql ="select table_name from all_tables where owner='TEST'";
			List<Map> list=jdbcTemplate.queryForList(sql);
			List<Map> result = new ArrayList<Map>();
			Long maxID=Long.valueOf(0);
			System.out.println("size:"+list.size());
			for(int j=0;j<list.size();j++){
				Map m=list.get(j);
				String tablename=(String) m.get("table_name");
				//System.out.println("tablename:"+tablename);
				String sql2 ="select max(id) as maxID from "+tablename+" where id<1136001600390";
				String sql3 ="select max(sn) as maxID from "+tablename+" where sn<1136001600390";	
				Long i=0L;
				try{
					i =jdbcTemplate.queryForLong(sql2);
				}catch(Exception e){
					try{
						i =jdbcTemplate.queryForLong(sql2);
					}catch(Exception e2){
						i=0L;
					}
				}
				System.out.println("==========================");
				System.out.println("talbename:"+tablename);
				System.out.println("max(id/sn):"+i);
				System.out.println("==========================");
						if(i>maxID){
							maxID=i;	
						}	
			}
			System.out.println("maxID:"+maxID);
	}
	
}
