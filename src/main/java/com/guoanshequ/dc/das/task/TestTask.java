package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.utils.DateUtils;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by daishuhua on 2017/4/11.
 */
@Component
public class TestTask {

//    @Scheduled(cron = "0/5 * *  * * ? ")
    public void schTest1() {
        Date date = new Date();
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sim.format(date);
        System.out.println("这是Spring定时器，每五秒执行一次,当前时间：" + dateStr);
        Map<String, String> datemap = DateUtils.getFirstday_Lastday_Month(new Date());
    	String begindate = datemap.get("first");
    	String enddate = datemap.get("last");
    	System.out.println(begindate+"===="+enddate);
    }
}
