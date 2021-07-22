package cn.redflagsoft.base.service.impl;

import java.util.List;
import java.util.Map;

import cn.redflagsoft.base.bean.Glossary;
import cn.redflagsoft.base.dao.GlossaryDao;
import cn.redflagsoft.base.service.GlossaryService;

public class GlossaryServiceImpl implements GlossaryService {
	private GlossaryDao glossaryDao;

	public void setGlossaryDao(GlossaryDao glossaryDao) {
		this.glossaryDao = glossaryDao;
	}

	public Map<Integer, String> findByCategory(short category) {
		return glossaryDao.findByCategory(category);
	}
	
	public List<Glossary> findByCategory2(short category) {
		return glossaryDao.findByCategory2(category);
	}

	public String getByCategoryAndCode(short category, int code) {
		return glossaryDao.getByCategoryAndCode(category, code);
	}
}
