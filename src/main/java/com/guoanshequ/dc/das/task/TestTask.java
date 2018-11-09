package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.TinyDispatch;
import com.guoanshequ.dc.das.service.TinyDispatchService;
import com.guoanshequ.dc.das.utils.DateUtils;
import com.guoanshequ.dc.das.utils.ImpalaUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by daishuhua on 2017/4/11.
 *
 * 调度配置说明：
 * 每隔5秒执行一次：  * /5 * * * * ?
 * 每隔1分钟执行一次：0 * /1 * * * ?
 * 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发  "0 0/5 14,18 * * ?"
 * 每天的下午2点到6点每1分钟一次触发  "0 0/1 14-18 * * ?"
 */
@Component
public class TestTask {

	@Autowired
	TinyDispatchService tinyDispatchService; 
	
	
	 private static final Logger logger = LogManager.getLogger(TestTask.class);
	
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
    
    public void testMongoConn() {
    	try {
			String tinyCode = tinyDispatchService.queryCodeByOrderId("47753df937764e8e9b93fbcec5e30e42");
			logger.info("mongo连接成功,订单号对应的小区号为："+tinyCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public void testImpala() {
    	try {
    		String sql = "SELECT order_sn FROM gemini.t_order limit 10 ";
    		List<Map<String,Object>> resultList = ImpalaUtil.execute(sql);
    		logger.info("sql:"+sql+"=================="+resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
