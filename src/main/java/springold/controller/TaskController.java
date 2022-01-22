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
        return("task/taskList");
    }


    /**
     * 立即执行定时任务
     * @param job 任务实体
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping( value = "/executeJob", produces = "application/json;charset=utf-8" )
    public String executeJob( int id, Model model ){
    ScheduleJob scheduleJobById = quartzJobService.getScheduleJobById(id);
        jobMethod.runJobNow( scheduleJobById );
        return "sucess";
    }


   /**
    * @Description: 向当前的任务列表中添加工作
    * @Author: weitj
    * @Date:  
    * @param id  job的id
    * @return: java.lang.String
    */
    @ResponseBody
    @RequestMapping( value = "/addJob", method = RequestMethod.GET )
    public Map<String,String>  addJob(int id) throws SchedulerException {
        ScheduleJob scheduleJobById = quartzJobService.getScheduleJobById(id);
        jobMethod.addOrUpdateJob(scheduleJobById);
        List<ScheduleJob> pb = quartzJobService.getJobsByPage( new ScheduleJobReq() );
        Map<String,String> map = new HashMap<>();
        map.put("msg","success");
        return map;
    }


    /**
     * 添加定时任务记录
     * @param job 任务实体
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
     * 初始化修改表单
     * @param jobId
     * @return 跳转地址
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
     * 修改定时任务记录信息
     * @param job 待修改的操作员实体
     * @param model 封装处理结果的实体
     * @param request 请求对象
     * @return 跳转地址
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
     * 删除一条定时任务记录信息
     * @return
     */
    @RequestMapping( value = "/deleteJob" )
    public String deleteJob(@RequestParam("id") int jobId, ModelMap modelMap)
    {
        quartzJobService.deleteJob( jobId );
//        modelMap.addAttribute( "actionResult", new SuccessActionResult() );
        return("redirect:/taskController/list.do");
    }


    /**
     * 校验执行任务的表达式是否正确
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
     * 某个定时任务下的所有执行记录信息列表
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