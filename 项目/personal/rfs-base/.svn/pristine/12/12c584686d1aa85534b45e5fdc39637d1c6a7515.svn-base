package cn.redflagsoft.base.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.redflagsoft.base.bean.BizTrack;
import cn.redflagsoft.base.bean.BizTrackSect;

public class BizTrackVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2191331587156554242L;
	private int objectCount;
	private BizTrack bizTrack;
	private List<BizTrackSect> bizTrackSectList = new ArrayList<BizTrackSect>();
	private List<BizTrackNodeVO> bizTrackNodeList = new ArrayList<BizTrackNodeVO>();
	
	/**
	 * 项目总数
	 * 
	 * @return int
	 */
	public int getObjectCount() {
		return objectCount;
	}
	
	/**
	 * @param objectCount
	 */
	public void setObjectCount(int objectCount) {
		this.objectCount = objectCount;
	}

	/**
	 * 业务轨迹
	 * 
	 * @return BizTrack
	 */
	public BizTrack getBizTrack() {
		return bizTrack;
	}
	
	public void setBizTrack(BizTrack bizTrack) {
		this.bizTrack = bizTrack;
	}

	/**
	 * 流程段号
	 * 
	 * @return List<BizTrackSect>
	 */
	public List<BizTrackSect> getBizTrackSectList() {
		return bizTrackSectList;
	}

	public void setBizTrackSectList(List<BizTrackSect> bizTrackSectList) {
		this.bizTrackSectList = bizTrackSectList;
	}

	/**
	 * 业务节点
	 * 
	 * @return List<BizTrackNode>
	 */
	public List<BizTrackNodeVO> getBizTrackNodeList() {
		return bizTrackNodeList;
	}
	
	public void setBizTrackNodeList(List<BizTrackNodeVO> bizTrackNodeList) {
		this.bizTrackNodeList = bizTrackNodeList;
	}
}
