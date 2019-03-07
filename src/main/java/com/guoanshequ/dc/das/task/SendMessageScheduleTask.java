package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.utils.HttpInterfaceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 发送短信接口 调用daqWeb
 * @author zhaoxg
 *
 */
@Component
@org.springframework.context.annotation.PropertySource("classpath:/config/common.properties")
public class SendMessageScheduleTask {

	private static final Logger logger = LogManager.getLogger(SendMessageScheduleTask.class);
	
	@Value("${daqweb.interface.url}")
    private String daqweburl;
	
	/**
	 *
	 * @Title: syncOnlineMemberTask 
	 * @Description: 同步线上人员信息。调用daqweb接口，每1分钟跑一次，采用多线程 
	 * @param 
	 * @return void 返回类型 
	 * @throws
	 */
	@Scheduled(cron = "0 */1 * * * ?")
	public void syncOnlineMemberTask() {
		try {
			// 调用daqweb同步线上人员接口。
			String URL = daqweburl;
			String param = String.format(HttpInterfaceUtils.PARAM, "InterManager", "syncOnLineHuman");
			logger.info("请求URL:" + URL);
			logger.info("请求param:" + param);
			if(daqweburl!=null&&daqweburl.length()>10) {
				String ret = HttpInterfaceUtils.sendPost(URL, param);
				logger.info(">>>>>>>>>>>>>>>>>同步线上人员:" + ret);
			}else {
				logger.info(">>>>>>>>>>>>>>>>>测试环境未同步线上人员！");
			}
		} catch (Exception e) {
			logger.info("由于网络或其它原因，同步线上人员失败，请查看！");
			logger.info(e.toString());
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 
	 * @Title: syncOnlineMemberTask 
	 * @Description:发送 预警消息的方法。(发频道负责人 )
	 * @param 
	 * @return void 返回类型
	 * @throws
	 * cron:  0 0 8 * * ? *
	 */
	@Scheduled(cron = "0 0 10 * * ?")
	public void sendCareerMessageTask() {
		try {
				logger.info(">>>>>>>>>>>>>>>>>开始发频道负责人---sendshortmessagestart......... ");
			    //调用daqweb同步线上人员接口。
				String URL=daqweburl;
		    	String param=String.format(HttpInterfaceUtils.PARAM, "InterManager", "sendWarningMessage");
				logger.info("请求URL:"+URL);
				logger.info("请求param:"+param);
				if(daqweburl!=null&&daqweburl.length()>10) {
					String ret = HttpInterfaceUtils.sendPost(URL,param);
			    	logger.info(ret);
					logger.info(">>>>完成发频道负责人--sendshortmessageend..URL:"+URL);
				}else {
					logger.info(">>>>完成发频道负责人(未发送)--sendshortmessageend..URL:"+URL);
				}
			} catch (Exception e) {
				logger.info("由于网络或其它原因，发送失败，请查看！");
				logger.info(e.toString());
				e.printStackTrace();
			}
	}
	
	/**
	 * 
	 * @Title: syncOnlineMemberTask 
	 * @Description:发送 预警消息的方法。 (发店长 )
	 * @param 
	 * @return void 返回类型 暂时不开启 
	 * @throws
	 * cron:  0 0 8 * * ? *
	 */
	/*@Scheduled(cron = "0 0 12 * * ? *")
	public void sendCareerMessageDzTask() {
		try {
				logger.info(">>>>>>>>>>>>>>>>>开始发店长 ");
			    //调用daqweb同步线上人员接口。
				String URL=HttpInterfaceUtils.DAQWEB_URL;
		    	String param=String.format(HttpInterfaceUtils.PARAM, "InterManager", "sendWarningMessageDz");
				String ret = HttpInterfaceUtils.sendPost(URL,param);
		    	logger.info(ret);
			} catch (Exception e) {
				logger.info("由于网络或其它原因，发送失败，请查看！");
				logger.info(e.toString());
				e.printStackTrace();
			}
	}*/
	
}
