package cn.redflagsoft.base.dao;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.Dao;


import cn.redflagsoft.base.bean.RiskLog;


public interface RiskLogDao  extends Dao<RiskLog,Long> {

	List<RiskLog> findGradeChanged(Date mStart,Date mEnd,byte grade,List<Long> dutyClerkIDS);
}
