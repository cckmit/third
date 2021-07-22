package cn.redflagsoft.base.dao;

import java.util.List;

import cn.redflagsoft.base.bean.Progress;
import cn.redflagsoft.base.test.BaseTests;

public class ProgressDaoTest extends BaseTests {

	protected ProgressDao progressDao;

	public void testSave() {
		super.setComplete();

		Progress visProgress = new Progress();

		visProgress.setId(1000L);
//		visProgress.setRefObjectId(project.getId());
//		visProgress.setRefObjectName(project.getName());
		visProgress.setDescription("2009年12月22日项目进度报告");
		visProgress.setTitle("2009年12月22日项目进度报告");
		visProgress.setFlags(0);
		System.out.println(visProgress);
		Progress temp = progressDao.save(visProgress);
		assertNotNull(temp);
	}

	public void testDelete() {
		Progress vis = getOneVisualProgress();
		int result = progressDao.delete(vis);
		System.out.println(result);
		assertSame(result, 1);
	}

	public Progress getOneVisualProgress() {
		List<Progress> vislist = progressDao.find();
		Progress result = null;
		if (vislist.size() != 0 && !vislist.isEmpty()) {
			result = vislist.get(0);
		}
		return result;
	}

	public void testFind() {
		List<Progress> vislist = progressDao.find();
		assertNotNull(vislist);
	}

	public void testFindByObjectId() {
		Progress progress = progressDao.get(27L);
		assertNotNull(progress);
	}
}
