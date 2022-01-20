package springold.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.zealer.cps.base.annotation.Log;
import com.zealer.cps.base.constant.AppConstant;
import com.zealer.cps.base.controller.BaseController;
import com.zealer.cps.base.message.SuccessActionResult;
import com.zealer.cps.base.model.vo.PaginationBean;
import com.zealer.cps.base.util.HttpUtils;
import com.zealer.cps.task.value.ScheduleJobItem;
import com.zealer.cps.task.value.ScheduleJobReq;
import springold.JobMethod;
import springold.bean.ScheduleJob;
import springold.bean.ScheduleJobReq;
import springold.constant.AppConstant;
import springold.service.QuartzJobService;

@Controller
@RequestMapping( "/taskController" )
public class TaskController
{
    private static Logger log = LoggerFactory.getLogger( TaskController.class );

    @Resource( name = "quartzJobService" )
    private QuartzJobService quartzJobService;

    @Resource( name = "JobMethod" )
    private JobMethod jobMethod;

    @RequestMapping( "/list" )
    public String listJob(@ModelAttribute("job") ScheduleJobReq jobReq, Model model, HttpServletRequest request )
    {
        List<ScheduleJob> pb = quartzJobService.getJobsByPage( jobReq );
        try {
            pb.setUrl( HttpUtils.getRequestInfo( request, true ) );
        } catch ( Exception e ) {
            log.error( "get request url error", e );
        }
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
    public ResponseEntity<Map<String, Object> > executeJob( ScheduleJob job, Model model )
    {
        jobMethod.runJobNow( job );
        return(new ResponseEntity<Map<String, Object> > ( new HashMap<String, Object>(), HttpStatus.OK ) );
    }


    /**
     * 跳转到添加定时任务的页面
     * @param model
     *            储存结果的实体
     */
    @RequestMapping( value = "/addJob", method = RequestMethod.GET )
    public String addForm( Model model )
    {
        model.addAttribute( "job", new ScheduleJob() );
        return("task/addJob");
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
        ra.addFlashAttribute( "actionResult", new SuccessActionResult() );
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