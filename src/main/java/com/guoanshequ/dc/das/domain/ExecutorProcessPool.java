package com.guoanshequ.dc.das.domain;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ProjectName: ds
 * @Package: com.guoanshequ.dc.das.domain
 * @Description: 线程池
 * @Author: gbl
 * @CreateDate: 2018/12/7 15:57
 */
public class ExecutorProcessPool {

    private static final Logger logger = LogManager.getLogger(ExecutorProcessPool.class);
    private static ExecutorService executor;

    private final int threadMax =15;

    private static  class ExecutorProcessPoolHolder{
        private static ExecutorProcessPool pool = new ExecutorProcessPool();
    }

    private ExecutorProcessPool() {
        System.out.println("threadMax>>>>>>>" + threadMax);
        executor = ExecutorServiceFactory.getInstance().createFixedThreadPool(threadMax);
    }

    public static ExecutorProcessPool getInstance() {
        ExecutorProcessPool epp = ExecutorProcessPoolHolder.pool;
        ThreadPoolExecutor tpe = ((ThreadPoolExecutor) executor);

        int queueSize = tpe.getQueue().size();
        System.out.println("当前排队线程数：" + queueSize);
        logger.info("当前排队线程数：" + queueSize);

        int activeCount = tpe.getActiveCount();
        System.out.println("当前活动线程数：" + activeCount);
        logger.info("当前活动线程数：" + activeCount);

        long completedTaskCount = tpe.getCompletedTaskCount();
        System.out.println("线程池执行完成线程数：" + completedTaskCount);
        logger.info("线程池执行完成线程数：" + completedTaskCount);

        long taskCount = tpe.getTaskCount();
        System.out.println("线程池执行的总线程数：" + taskCount);
        logger.info("线程池执行的总线程数：" + taskCount);

        Integer poolSize = tpe.getPoolSize();
        System.out.println("线程池当前总线程数"+poolSize);
        logger.info("线程池当前总线程数"+poolSize);

        Integer corePoolSize = tpe.getCorePoolSize();
        System.out.println("线程池当前核心线程数"+corePoolSize);
        logger.info("线程池当前核心线程数"+corePoolSize);

        return epp;
    }


    /**
     * @Description 关闭线程池，说明：调用关闭线程池方法后，线程池会执行完队列中的所有任务才退出
     * @author gbl
     * @date 2018/12/7 16:02
     **/

    public void shutdown(){
        executor.shutdown();
    }


    /**
     * @Description 提交任务到线程池，可以接收线程返回值
     * @author gbl
     * @date 2018/12/7 16:02
     **/

    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }


    /**
     * @Description 提交任务到线程池，可以接收线程返回值
     * @author gbl
     * @date 2018/12/7 16:03
     **/

    public Future<?> submit(Callable<?> task) {
        return executor.submit(task);
    }


    /**
     * @Description 直接提交任务到线程池，无返回值
     * @author gbl
     * @date 2018/12/7 16:03
     **/

    public void execute(Runnable task){
        executor.execute(task);
    }

}
