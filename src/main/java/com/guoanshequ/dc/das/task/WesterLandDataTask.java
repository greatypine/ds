package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.controller.WesterLandController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.task
 * @Description: 东方大地数据定时任务
 * @Author: gbl
 * @CreateDate: 2019/3/7 9:46
 */
@Component
public class WesterLandDataTask {

    @Autowired
    WesterLandController westerLandController;

    /**
     * @Description 交易
     * @author gbl
     * @date 2019/3/7 9:50
     **/

    @Scheduled(cron = "0 56 23 * * ?")
    public void westerLandTransaction() {
        new Thread() {
            public void run() {
                westerLandController.getWesterLandTransaction();
            }

        }.start();

    }

    /**
     * @Description 产品
     * @author gbl
     * @date 2019/3/7 9:51
     **/

    @Scheduled(cron = "40 56 23 * * ?")
    public void westerLandProduct() {
        new Thread() {
            public void run() {
                westerLandController.getWesterLandProduct();
            }

        }.start();

    }

    /**
     * @Description 用户
     * @author gbl
     * @date 2019/3/7 9:51
     **/


    @Scheduled(cron = "20 57 23 * * ?")
    public void westerLandUser() {
        new Thread() {
            public void run() {
                westerLandController.getWesterLandUser();
            }

        }.start();

    }
}