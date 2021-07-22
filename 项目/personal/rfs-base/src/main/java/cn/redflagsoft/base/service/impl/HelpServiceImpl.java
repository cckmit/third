package cn.redflagsoft.base.service.impl;

import java.util.List;

import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Help;
import cn.redflagsoft.base.dao.HelpDao;
import cn.redflagsoft.base.service.HelpService;

public class HelpServiceImpl implements HelpService {
	public HelpDao helpDao;
	
	
	public HelpDao getHelpDao() {
		return helpDao;
	}

	public void setHelpDao(HelpDao helpDao) {
		this.helpDao = helpDao;
	}
	

	public int deleteHelp(Help help) {
		return helpDao.delete(help);
	}

	public List<Help> findHelps(ResultFilter filter) {
		return helpDao.find(filter);
	}

	public Help getHelp(Long id) {
		return helpDao.get(id);
	}

	public Help saveOrUpdateHelp(Help help) {
		return helpDao.saveOrUpdate(help);
	}

}
