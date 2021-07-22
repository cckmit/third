package cn.redflagsoft.base.service.impl;

import java.util.Date;

import org.opoo.apps.util.DateUtils;
import org.opoo.ndao.criterion.Logic;
import org.opoo.ndao.criterion.Restrictions;

import cn.redflagsoft.base.bean.Log;
import cn.redflagsoft.base.dao.LogDao;
import cn.redflagsoft.base.service.LogService;

public class LogServiceImpl implements LogService{
	private LogDao logDao;

	public LogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	public int deleteLogsByModule(String module, Byte status) {
		Logic logic = Restrictions.logic(Restrictions.eq("module", module));
		if (status != null) {
			logic.and(Restrictions.eq("status", status));
		}
		return logDao.remove(logic);
	}

	public int deleteLogsByTime(Date start, Date end) {
		Logic logic = null;
		if (end == null) {
			logic = Restrictions.logic(Restrictions.ge("creationTime", DateUtils.toStartOfDay(start)))
					.and(Restrictions.le("creationTime", DateUtils.toEndOfDay(start)));
		} else {
			logic = Restrictions.logic(Restrictions.ge("creationTime", start))
					.and(Restrictions.le("creationTime", end));
		}
		return logDao.remove(logic);
	}

	public int deleteLogsByType(String type, Byte status) {
		Logic logic = Restrictions.logic(Restrictions.eq("type", type));
		if (status != null) {
			logic.and(Restrictions.eq("status", status));
		}
		return logDao.remove(logic);
	}

	public int deleteLog(Long id) {
		return logDao.remove(id);
	}

	public Log saveLog(Log log) {
		if (log != null) {
			log.setCreationTime(new Date());
			if (log.getStatus() == null)
				log.setStatus(Log.STATUS_ONUSE);
			return logDao.save(log);
		}
		return null;
	}
}
