package cn.redflagsoft.base.vo.statistics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class PlanMonitor extends HashMap<Object, Object> implements Map<Object, Object>, Serializable {
	private static final long serialVersionUID = -1459499078379920287L;
	public static final String MANAGER_KEY = "manager";
	public static final String MANAGER_TYPE_KEY = "managerType";
	public static final String STATISTIC_GRADE_GROUP_KEY = "statistic";
	public static final String LIFE_STAGE_PREFIX = "ls";
	private Map<Integer, GradeGroup> gradeGroups = new HashMap<Integer, GradeGroup>();
	
	public PlanMonitor(){
	}
	
	public PlanMonitor(Map<Integer, GradeGroup> gradeGroups){
		addGradeGroups(gradeGroups);
	}
	
	public void addGradeGroup(int lifeStage, GradeGroup gg){
		put(LIFE_STAGE_PREFIX + lifeStage, gg);
		this.gradeGroups.put(lifeStage, gg);
	}
	
	public void addGradeGroups(Map<Integer, GradeGroup> gradeGroups){
		//putAll(gradeGroups);
		//this.gradeGroups.putAll(gradeGroups);
		for(Map.Entry<Integer, GradeGroup> en: gradeGroups.entrySet()){
			addGradeGroup(en.getKey(), en.getValue());
		}
		
	}
	
	public void setGradeGroups(Map<Integer, GradeGroup> gradeGroups){
		if(this.gradeGroups != null && !this.gradeGroups.isEmpty()){
			for(Integer key: this.gradeGroups.keySet()){
				this.remove(LIFE_STAGE_PREFIX + key);
			}
		}
		this.gradeGroups.clear();
		addGradeGroups(gradeGroups);
		//putAll(gradeGroups);
		//this.gradeGroups = gradeGroups;
	}
	
	public GradeGroup getGradeGroup(int lifeStage){
		return gradeGroups.get(lifeStage);
	}
	/**
	 * @return the statistic
	 */
	public GradeGroup calculateStatistic() {
		GradeGroup gg = new GradeGroup();
		if(gradeGroups != null && !gradeGroups.isEmpty()){
			for(GradeGroup tmp: gradeGroups.values()){
				gg.getNormal().setCount(gg.getNormal().getCount() + tmp.getNormal().getCount());
				gg.getBlue().setCount(gg.getBlue().getCount() + tmp.getBlue().getCount());
				gg.getYellow().setCount(gg.getYellow().getCount() + tmp.getYellow().getCount());
				gg.getRed().setCount(gg.getRed().getCount() + tmp.getRed().getCount());
				gg.getWhite().setCount(gg.getWhite().getCount() + tmp.getWhite().getCount());
				gg.getBlack().setCount(gg.getBlack().getCount() + tmp.getBlack().getCount());
				gg.getOrange().setCount(gg.getOrange().getCount() + tmp.getOrange().getCount());				
			}
		}
		put(STATISTIC_GRADE_GROUP_KEY, gg);
		return gg;
	}
	
	public GradeGroup getStatistic(){
		return (GradeGroup) get(STATISTIC_GRADE_GROUP_KEY);
	}
	/**
	 * @param statistic the statistic to set
	 */
	//public void setStatistic(GradeGroup statistic) {
	//	put(STATISTIC_GRADE_GROUP_KEY, statistic);
	//}
	
	


	/**
	 * @return the manager
	 */
	public Object getManager() {
		return get(MANAGER_KEY);
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(Object manager) {
		put(MANAGER_KEY, manager);
	}
	
	
	public byte getManagerType(){
		return (Byte) get(MANAGER_TYPE_KEY);
	}
	
	public void setManagerType(byte managerType){
		put(MANAGER_TYPE_KEY, managerType);
	}
}
