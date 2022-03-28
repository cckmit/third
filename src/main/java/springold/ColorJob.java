package springold;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//��jobDateMapʵ�ֳ־û�   ���ϴδ������ֵ����jobDateMap
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob
        implements Job
{
    public static final String FAVORITE_COLOR = "favorite color";
    public static final String EXECUTION_COUNT = "count";
    private int _counter = 1;

    public void execute(JobExecutionContext context)
            throws JobExecutionException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString("color");
        int count = data.getInt("count");
        int total = data.getInt("total");
        System.out.println(("ColorJob:  �� " + dateFormat.format(new Date()) + "ִ��  "+  jobKey +"\n"
                + " color : " + favoriteColor + "\n"
                + " ��  " + count + "�� ִ��\n"
                + " ��  " + total + "�� ִ��\n"
                + " ��Ա����_counter�ǵ� " + this._counter+ "�� ִ��"));

        count++;
        data.put("count", count);
        total++;
        data.put("total", total);


        this._counter += 1;
    }
}