package cn.redflagsoft.base.dao;

import java.util.List;

import cn.redflagsoft.base.bean.Process;

public interface ProcessDao extends org.opoo.ndao.Dao<Process, Long>{
	List<Process> getProcessByTaskSNAndType(Long taskSN,int type);
}
