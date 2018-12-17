package com.guoanshequ.dc.das.domain;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.domain
 * @Description:
 * @Author: gbl
 * @CreateDate: 2018/12/7 16:16
 */
public class SMSSendReplyAbleTask implements Callable {

    private String taskName;

    public SMSSendReplyAbleTask(String taskName){
        this.taskName = taskName;
    }

    @Override
    public Object call() throws Exception {
        try {

            TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 1000));// 1000毫秒以内的随机数，模拟业务逻辑处理
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-------------这里执行业务逻辑，Callable TaskName = " + taskName + "-------------");
        return ">>>>>>>>>>>>>线程返回值，Callable TaskName = " + taskName + "<<<<<<<<<<<<<<";

    }
}
