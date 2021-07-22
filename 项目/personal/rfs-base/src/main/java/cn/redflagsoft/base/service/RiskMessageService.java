package cn.redflagsoft.base.service;

import java.util.List;
import java.util.Map;

public interface RiskMessageService {
	
	List<Map<String,Object>> findAcceptSMSPersons(Byte grade);
}
