package springold.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springold.JobMethod;
import springold.bean.ScheduleJob;
import springold.bean.ScheduleJobReq;
import springold.constant.AppConstant;
import springold.service.QuartzJobService;
import springold.utils.DateFormatUtil;

@Controller
@RequestMapping( "/taskController" )
public class TaskController
{
    private static final Logger log = LoggerFactory.getLogger( TaskController.class );

    @Resource( name = "quartzJobService" )
    private QuartzJobService quartzJobService;

    @Autowired
    private JobMethod jobMethod;

    @RequestMapping( "/list" )
    public String listJob(ScheduleJobReq jobReq, Model model, HttpServletRequest request )
    {
        List<ScheduleJob> pb = quartzJobService.getJobsByPage( jobReq );
        model.addAttribute( "pb", pb );
        if(null == null){
            
        }
        return("task/taskList");
    }


    /**
     * @Description: ����ִ�ж�ʱ����
     * @Author: Weitj
     * @Date: 2022/01/24 10:48
      * @param id
     * @param model
     * @return: java.lang.Strings
     */
    @ResponseBody
    @RequestMapping( value = "/executeJob" )
    public String executeJob( int id, Model model ){
    ScheduleJob scheduleJobById = quartzJobService.getScheduleJobById(id);
        // ����ִ�ж�ʱ����
        jobMethod.runJobNow( scheduleJobById );
        //���¶�ʱ�����״̬
        scheduleJobById.setUpdateTime(DateFormatUtil.formatNow());
        quartzJobService.updateJob(scheduleJobById);
        return "success";
    }


   /**
    * @Description: ��ǰ�������б�����ӹ���
    * @Author: weitj
    * @Date:  
    * @param id  job��id
    * @return: java.lang.String
    */
    @ResponseBody
    @RequestMapping( value = "/addOrUpdate", method = RequestMethod.GET )
    public Map<String,String>  addOrUpdate(int id) throws SchedulerException {
        ScheduleJob scheduleJobById = quartzJobService.getScheduleJobById(id);
        // ��ӻ��߸��¶�ʱ����
        jobMethod.addOrUpdateJob(scheduleJobById);
        scheduleJobById.setUpdateTime(DateFormatUtil.formatNow());
        scheduleJobById.setJobStatus(ScheduleJob.STATUS_ִ����+"");
        // ���¶�ʱ�����״̬
        quartzJobService.updateJob(scheduleJobById);
        List<ScheduleJob> pb = quartzJobService.getJobsByPage( new ScheduleJobReq() );
        Map<String,String> map = new HashMap<>();
        map.put("msg","success");
        return map;
    }


    @ResponseBody
    @RequestMapping( value = "/pauseJob", method = RequestMethod.GET )
    public Map<String,String>  pauseJob(int id) throws SchedulerException {
        ScheduleJob scheduleJobById = quartzJobService.getScheduleJobById(id);
        // ��ӻ��߸��¶�ʱ����
        jobMethod.pauseJob(scheduleJobById);
        scheduleJobById.setUpdateTime(DateFormatUtil.formatNow());
        scheduleJobById.setJobStatus(ScheduleJob.STATUS_��ͣ��+"");
        // ���¶�ʱ�����״̬
        quartzJobService.updateJob(scheduleJobById);
        List<ScheduleJob> pb = quartzJobService.getJobsByPage( new ScheduleJobReq() );
        Map<String,String> map = new HashMap<>();
        map.put("msg","success");
        return map;
    }


    /**
     * ��Ӷ�ʱ�����¼
     * @param job ����ʵ��
     */
    @RequestMapping( value = "/addJob", method = RequestMethod.POST )
    public String addUser( @ModelAttribute("job") ScheduleJob job, RedirectAttributes ra, Model model,
                           HttpServletRequest request )
    {
        SimpleDateFormat format = new SimpleDateFormat( AppConstant.DATE_FORMAT_YYYYMMDDHHMMSS );
        job.setCreateTime( format.format( new Date() ) );
        quartzJobService.inserJob( job );
        ra.addFlashAttribute( "actionResult", "success" );
        return("redirect:/taskController/list.do");
    }


    /**
     * ��ʼ���޸ı�
     * @param jobId
     * @return ��ת��ַ
     */
    @RequestMapping( value = "/updateJob", method = RequestMethod.GET )
    public String updateForm( @RequestParam("id") Integer jobId, Model model,
                              HttpServletRequest request )
    {
        ScheduleJob job = quartzJobService.getScheduleJobById( jobId );
        model.addAttribute( "job", job );
        return("task/updateJob");
    }


    /**
     * �޸Ķ�ʱ�����¼��Ϣ
     * @param job ���޸ĵĲ���Աʵ��
     * @param model ��װ��������ʵ��
     * @param request �������
     * @return ��ת��ַ
     */
    @RequestMapping( value = "/updateJob", method = RequestMethod.POST )
    public String updateJob( @ModelAttribute ScheduleJob job, RedirectAttributes ra, Model model,
                             HttpServletRequest request )
    {
        SimpleDateFormat format = new SimpleDateFormat( AppConstant.DATE_FORMAT_YYYYMMDDHHMMSS );
        job.setUpdateTime( format.format( new Date() ) );
        quartzJobService.updateJob( job );
//        ra.addFlashAttribute( "actionResult", new SuccessActionResult() );
        return("redirect:/taskController/list.do");
    }


    /**
     * ɾ��һ����ʱ�����¼��Ϣ
     * @return
     */
    @RequestMapping( value = "/deleteJob" )
    public String deleteJob(@RequestParam("id") int jobId, ModelMap modelMap)
    {
        ScheduleJob scheduleJob = quartzJobService.getScheduleJobById(jobId);
        // �Ƴ���������е�job
        jobMethod.deleteJob(scheduleJob);
        scheduleJob.setUpdateTime(DateFormatUtil.formatNow());
        scheduleJob.setJobStatus(ScheduleJob.STATUS_��ע��+"");
        // ���¶�ʱ�����״̬
        quartzJobService.updateJob(scheduleJob);
        return("redirect:/taskController/list.do");
    }
    /**
     * ɾ��һ����ʱ�����¼��Ϣ
     * @return
     */
    @ResponseBody
    @RequestMapping( value = "/resumeJob" )
    public String resumeJob(@RequestParam("id") int jobId, ModelMap modelMap)
    {
        ScheduleJob scheduleJob = quartzJobService.getScheduleJobById(jobId);
        // ��������
        jobMethod.resumeJob(scheduleJob);
        scheduleJob.setUpdateTime(DateFormatUtil.formatNow());
        scheduleJob.setJobStatus(ScheduleJob.STATUS_ִ����+"");
        // ���¶�ʱ�����״̬
        quartzJobService.updateJob(scheduleJob);
//        return("redirect:/taskController/list.action");
        return "success";
    }


    /**
     * У��ִ������ı��ʽ�Ƿ���ȷ
     * @param expression
     * @return
     */
    @ResponseBody
    @RequestMapping( value = "/checkExp", produces = "application/json;charset=utf-8" )
    public ResponseEntity<Map<String, Object> > checkExpression( String expression )
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put( AppConstant.SYSTEM_JSON_CODE, AppConstant.SYSTEM_JSON_ERROR );
        if ( jobMethod.checkCron( expression ) )
        {
            map.put( AppConstant.SYSTEM_JSON_CODE, AppConstant.SYSTEM_JSON_SUCCESS );
        }
        return(new ResponseEntity<Map<String, Object> > ( map, HttpStatus.OK ) );
    }


    /**
     * ĳ����ʱ�����µ�����ִ�м�¼��Ϣ�б�
     * @param jobReq
     * @return
     */
//    @RequestMapping( "/itemJob" )
//    public String executeJobList( @ModelAttribute("job") ScheduleJobReq jobReq, int jobId,
//                                  Model model, HttpServletRequest request )
//    {
//        PaginationBean<ScheduleJobItem> pb = quartzJobService.getJobItemsByPage( jobId, jobReq );
//        try {
//            pb.setUrl( HttpUtils.getRequestInfo( request, true ) );
//        } catch ( Exception e ) {
//            log.error( "get request url error", e );
//        }
//        model.addAttribute( "pb", pb );
//        model.addAttribute( "jobId", jobId );
//        return("task/taskItemList");
//    }
}