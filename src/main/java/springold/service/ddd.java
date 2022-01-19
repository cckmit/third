package springold.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zealer.cps.base.dao.BaseDaoInterface;
import com.zealer.cps.base.model.vo.PaginationBean;
import com.zealer.cps.task.value.ScheduleJob;
import com.zealer.cps.task.value.ScheduleJobItem;
import com.zealer.cps.task.value.ScheduleJobReq;
import springold.bean.ScheduleJob;

@Service( "quartzJobService" )
public class QuartzJobService
{
    public static final String    JOB_LIST    = "quartzJob.jobsList";
    public static final String    JOB_SELECT_BYID = "quartzJob.selectById";
    public static final String    JOB_INSERT    = "quartzJob.addJob";
    public static final String    JOB_UPDATE    = "quartzJob.updateJob";
    public static final String    JOB_DELETE    = "quartzJob.deleteJob";
    public static final String    JOB_LIST_PAGE    = "quartzJob.jobListPage";

    public static final String    JOBITEM_LIST_PAGE    = "jobItem.selectListPageByMap";
    public static final String    JOBITEM_INSERT        = "jobItem.insertJobItem";
    public static final String    JOBITEM_SELETE_BYID    = "jobItem.selectByPrimaryKey";

    @Resource( name = "mybatisBaseDao" )
    private BaseDaoInterface baseDao;


    /**
     * 获取所有的定时任务记录信息
     * @return
     */
    public List<ScheduleJob> getAllJobs()
    {
        return(this.baseDao.queryForList( JOB_LIST, null ) );
    }


    /**
     * 根据id获取任务记录
     * @param id
     * @return
     */
    public ScheduleJob getScheduleJobById( int id )
    {
        return(this.baseDao.query( JOB_SELECT_BYID, id ) );
    }


    /**
     * 插入一条定时任务记录
     * @param job
     */
    public void inserJob( ScheduleJob job )
    {
        this.baseDao.insertData( JOB_INSERT, job );
    }


    /**
     * 更新一条定时任务记录
     * @param job
     */
    public void updateJob( ScheduleJob job )
    {
        this.baseDao.updateData( JOB_UPDATE, job );
    }


    /**
     * 删除一条定时任务记录
     * @param job
     */
    public void deleteJob( int id )
    {
        this.baseDao.deleteData( JOB_DELETE, id );
    }


    /**
     * 分页获取定时任务记录信息
     * @return
     */
    public PaginationBean<ScheduleJob> getJobsByPage( ScheduleJobReq jobReq )
    {
        PaginationBean<ScheduleJob>    pb    = new PaginationBean<ScheduleJob>( jobReq.getCurrent(), 0, jobReq.getPageSize() );
        Map<String, Object>        map    = new HashMap<String, Object>();
        map.put( "page", pb );
        return(this.baseDao.queryForListPageByMap( JOB_LIST_PAGE, map ) );
    }


    /**
     * 分页获取定时任务执行记录信息
     * @return
     */
    public PaginationBean<ScheduleJobItem> getJobItemsByPage( Integer jobId, ScheduleJobReq jobReq )
    {
        PaginationBean<ScheduleJobItem> pb    = new PaginationBean<ScheduleJobItem>( jobReq.getCurrent(), 0, jobReq.getPageSize() );
        Map<String, Object>        map    = new HashMap<String, Object>();
        map.put( "jobId", jobId );
        map.put( "page", pb );
        return(this.baseDao.queryForListPageByMap( JOBITEM_LIST_PAGE, map ) );
    }


    /**
     * 插入一条定时任务执行记录信息
     * @param jobItem
     */
    @Transactional( propagation = Propagation.REQUIRED )
    public void inserJobItem( ScheduleJobItem jobItem )
    {
        this.baseDao.insertData( JOBITEM_INSERT, jobItem );
    }


    /**
     * 根据ID获取一条定时任务执行记录信息
     * @param id
     * @return
     */
    public ScheduleJobItem getScheduleJobItemById( int id )
    {
        return(this.baseDao.query( JOBITEM_SELETE_BYID, id ) );
    }
}