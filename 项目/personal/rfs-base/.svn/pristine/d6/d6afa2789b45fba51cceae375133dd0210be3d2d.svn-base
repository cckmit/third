package cn.redflagsoft.base.dao;

import java.util.Date;
import java.util.List;

import org.opoo.ndao.Dao;

import cn.redflagsoft.base.bean.Log;

public interface LogDao extends Dao<Log, Long> {
	/**
	 * 查询指定时间段信息
	 * 
	 * @param start, end
	 * @return List<Log>
	 */
	List<Log> queryLogList(Date start, Date end);
}
