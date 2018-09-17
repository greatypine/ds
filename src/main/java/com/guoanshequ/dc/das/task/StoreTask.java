package com.guoanshequ.dc.das.task;

import com.guoanshequ.dc.das.model.DistCityCode;
import com.guoanshequ.dc.das.model.Store;
import com.guoanshequ.dc.das.service.DistCityCodeService;
import com.guoanshequ.dc.das.service.StoreService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sunning on 2018/9/6.
 * 1.通过开店时间对门店进行排序
 * 2.通过开店时间将未开店的门店进行开店处理
 */
@Component
public class StoreTask {
    @Autowired
    DistCityCodeService distCityCodeService;
    @Autowired
    StoreService storeService;
    private static final Logger logger = LogManager.getLogger(StoreTask.class);
    /**
     * 根据城市获取每个城市下的门店(如果有开店时间按开店时间排序如果没有按创建时间排序)
     */
    @Scheduled(cron ="0 */30 * * * ?")
    public void renewalStoreOrdNumber(){
        new Thread(){
            public void run() {
                try{
                    logger.info("**********门店自动排序任务调度开始**********");
                    // 获取所有有门店的城市
                    List<DistCityCode> allDistCityCode = distCityCodeService.getAllDistCityCode();
                    for (DistCityCode distCityCode:allDistCityCode) {
                        int t = 1, j = 1, n = 1, m = 1;
                        List<Store> stores = storeService.findStoreToCityNameAndOpenShopTime(distCityCode.getCityname());
                        if (stores != null && stores.size() > 0) {
                            for (Store store : stores) {
                                store.setOrdnumber(t);
                                storeService.updateStore(store);
                                t++;
                            }
                        }
                        // 判断当前城市的最大排序如果有在当前基础上相加如果没有从一开始
                        int number = storeService.findMaxStoreOreNumber(distCityCode.getCityname());
                        List<Store> isnullOrdnumber = storeService.findStoreToCityNameAndOpenShopTimeIsNull(distCityCode.getCityname());
                        //根据城市获取开店时间为空的门店
                        if (isnullOrdnumber != null && isnullOrdnumber.size() > 0) {
                            for (Store store : isnullOrdnumber) {
                                store.setOrdnumber(j + number);
                                storeService.updateStore(store);
                                j++;
                            }

                        }
                    }
                    logger.info("**********门店自动排序任务调度结束**********");
                }catch (Exception e){
                    logger.info(e.toString());
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Scheduled(cron ="0 0 23 * * ?")
    public void syncStoreEstate(){
        new Thread(){
            public void run() {
                try{
                    logger.info("**********门店更改运营状态任务调度开始**********");
                    List<Store> storeDateEstate = storeService.findStoreDateEstate();
                    if(storeDateEstate != null && storeDateEstate.size() > 0){
                        for (Store store : storeDateEstate) {
                            store.setEstate("试运营");
                            storeService.updateStore(store);
                        }
                    }
                    logger.info("**********门店更改运营状态任务调度结束**********");
                }catch (Exception e){
                    logger.info(e.toString());
                    e.printStackTrace();
                }
            }
        }.start();
    }




}
