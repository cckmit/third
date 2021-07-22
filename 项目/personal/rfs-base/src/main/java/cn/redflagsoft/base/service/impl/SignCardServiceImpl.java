package cn.redflagsoft.base.service.impl;

import java.util.Date;
import java.util.List;

import org.opoo.apps.annotation.Queryable;
import org.opoo.ndao.criterion.Restrictions;
import org.opoo.ndao.criterion.SimpleExpression;
import org.opoo.ndao.support.ResultFilter;

import cn.redflagsoft.base.bean.Clerk;
import cn.redflagsoft.base.bean.Org;
import cn.redflagsoft.base.bean.RiskLog;
import cn.redflagsoft.base.bean.SignCard;
import cn.redflagsoft.base.bean.SignCardAccept;
import cn.redflagsoft.base.bean.SignCardCheck;
import cn.redflagsoft.base.bean.SignCardNotice;
import cn.redflagsoft.base.bean.SignCardPenalty;
import cn.redflagsoft.base.bean.SignCardSurvey;
import cn.redflagsoft.base.bean.SignCardVO;
import cn.redflagsoft.base.dao.ObjectsDao;
import cn.redflagsoft.base.dao.RiskLogDao;
import cn.redflagsoft.base.dao.SignCardAcceptDao;
import cn.redflagsoft.base.dao.SignCardCheckDao;
import cn.redflagsoft.base.dao.SignCardNoticeDao;
import cn.redflagsoft.base.dao.SignCardPenaltyDao;
import cn.redflagsoft.base.dao.SignCardSurveyDao;
import cn.redflagsoft.base.security.UserClerkHolder;
import cn.redflagsoft.base.service.ClerkService;
import cn.redflagsoft.base.service.GlossaryService;
import cn.redflagsoft.base.service.OrgService;
import cn.redflagsoft.base.service.SignCardService;
import cn.redflagsoft.base.service.TaskService;

public class SignCardServiceImpl extends AbstractRFSObjectService<SignCard>
        implements SignCardService {
    private RiskLogDao riskLogDao;
    private OrgService orgService;
    private ClerkService clerkService;
    private GlossaryService glossaryService;
    
    private SignCardAcceptDao signCardAcceptDao;
    private SignCardCheckDao signCardCheckDao;
    private SignCardPenaltyDao signCardPenaltyDao;
    private SignCardNoticeDao signCardNoticeDao;
    private SignCardSurveyDao signCardSurveyDao;

    private ObjectsDao objectsDao;
    private TaskService taskService;
    
    public ObjectsDao getObjectsDao() {
        return objectsDao;
    }

    public void setObjectsDao(ObjectsDao objectsDao) {
        this.objectsDao = objectsDao;
    }
    
    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    
    public GlossaryService getGlossaryService() {
        return glossaryService;
    }

    public void setGlossaryService(GlossaryService glossaryService) {
        this.glossaryService = glossaryService;
    }


    public RiskLogDao getRiskLogDao() {
        return riskLogDao;
    }

    public void setRiskLogDao(RiskLogDao riskLogDao) {
        this.riskLogDao = riskLogDao;
    }

    public OrgService getOrgService() {
        return orgService;
    }

    public void setOrgService(OrgService orgService) {
        this.orgService = orgService;
    }

    public ClerkService getClerkService() {
        return clerkService;
    }

    public void setClerkService(ClerkService clerkService) {
        this.clerkService = clerkService;
    }

    public SignCardAcceptDao getSignCardAcceptDao() {
		return signCardAcceptDao;
	}

	public void setSignCardAcceptDao(SignCardAcceptDao signCardAcceptDao) {
		this.signCardAcceptDao = signCardAcceptDao;
	}

	public SignCardCheckDao getSignCardCheckDao() {
		return signCardCheckDao;
	}

	public void setSignCardCheckDao(SignCardCheckDao signCardCheckDao) {
		this.signCardCheckDao = signCardCheckDao;
	}

	public SignCardPenaltyDao getSignCardPenaltyDao() {
		return signCardPenaltyDao;
	}

	public void setSignCardPenaltyDao(SignCardPenaltyDao signCardPenaltyDao) {
		this.signCardPenaltyDao = signCardPenaltyDao;
	}

	public SignCardNoticeDao getSignCardNoticeDao() {
		return signCardNoticeDao;
	}

	public void setSignCardNoticeDao(SignCardNoticeDao signCardNoticeDao) {
		this.signCardNoticeDao = signCardNoticeDao;
	}

	public SignCardSurveyDao getSignCardSurveyDao() {
		return signCardSurveyDao;
	}

	public void setSignCardSurveyDao(SignCardSurveyDao signCardSurveyDao) {
		this.signCardSurveyDao = signCardSurveyDao;
	}

	@Queryable(argNames = { "id" })
    public SignCardVO getSignCardVOById(Long id) {
        SignCardVO scv = new SignCardVO();
        SignCard sc = getObjectDao().get(id);
        if (sc != null) {
            scv.setId(sc.getId());
            scv.setCode(sc.getCode());
            scv.setLabel(sc.getLabel());
            String typeName = glossaryService.getByCategoryAndCode(
                    SignCard.GLOSSARY_CATEGORY, sc.getType());
            scv.setTypeName(typeName);
            scv.setGrade(sc.getGrade());
            scv.setGradeName(getRiskService().getGradeExplain(sc.getGrade()));
            if (sc.getRvDutyerID() != null) {
                Org o = orgService.getOrgByDutyEntity(sc.getRvDutyerID());
                if (o != null) {
                    scv.setRvDutyerName(o.getAbbr());
                }
            }
            if (sc.getRvDutyerOrgId() != null) {
                Org o = orgService.getOrgByDutyEntity(sc.getRvDutyerOrgId());
                if (o != null) {
                    scv.setRvEntityName(o.getAbbr());
                }
            }
            scv.setCreateType(sc.getCreateType());
            scv.setCreateTypeName(sc.getCreateTypeName());
            scv.setRvOccurTime(sc.getRvOccurTime());
            scv.setRvDesc(sc.getRvDesc());
            scv.setJuralLimit(sc.getJuralLimit());
            RiskLog rl = riskLogDao.get(sc.getRefRiskLogID());
            if (rl != null) {
                scv.setRuleID(rl.getRuleID());
                scv.setRuleName(rl.getName());

            }
        }
        return scv;
    }

    public SignCard createSignCard(SignCard signCard) {
        // 获得当前登录系统用户信息
        Clerk clerk = UserClerkHolder.getClerk();
        signCard.setCreator(clerk.getId());
        signCard.setCreationTime(new Date());
        signCard.setCreateType(SignCard.CREATE_TYPE_HAND);
        SignCard ret=saveObject(signCard);
        
        // 获取 过错信息的ID值
        Long signcard_id = ret.getId();
        // 创建过错受理对象
        SignCardAccept newSignCardAccept = new SignCardAccept();
        newSignCardAccept.setSignCardId(signcard_id);
        signCardAcceptDao.save(newSignCardAccept);
        // 创建 整改核查对象
        SignCardCheck newSignCardCheck = new SignCardCheck();
        newSignCardCheck.setSignCardId(signcard_id);
        signCardCheckDao.save(newSignCardCheck);
        //创建 纠错告知对象
        SignCardNotice newSignCardNotice = new SignCardNotice();
        newSignCardNotice.setSignCardId(signcard_id);
        signCardNoticeDao.save(newSignCardNotice);
        // 创建 责任追究对象
        SignCardPenalty newSignCardPenalty = new SignCardPenalty();
        newSignCardPenalty.setSignCardId(signcard_id);
        signCardPenaltyDao.save(newSignCardPenalty);
        // 创建 事实认定 对象
        SignCardSurvey newSignCardSurvey = new SignCardSurvey();
        newSignCardSurvey.setSignCardId(signcard_id);
        signCardSurveyDao.save(newSignCardSurvey);
        
        return ret;
    }

    public SignCard getSignCard(Long id) {
        ResultFilter filters = ResultFilter.createPageableResultFilter(0, 1);
        SimpleExpression criterions = Restrictions.eq("id", id);
        filters.setCriterion(criterions);

        List<SignCard> list = getObjectDao().find(filters);

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
