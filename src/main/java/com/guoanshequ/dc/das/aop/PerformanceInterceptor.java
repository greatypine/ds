package com.guoanshequ.dc.das.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <p>
 * PerformanceInterceptor 性能统计接口：接口耗时统计
 * <p>*/
@Aspect
@Component
public class PerformanceInterceptor {

    private Logger logger = Logger.getLogger(PerformanceInterceptor.class);

    //我需要监控所有task的耗时（task放在包com.guoanshequ.dc.das.task下面）
    @Around("execution(* com.guoanshequ.dc.das.task.*.*(..))")
    public Object logTome(ProceedingJoinPoint pjp) throws Throwable {
        long begin = System.currentTimeMillis();
        String method = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().getName();

        Object ret = pjp.proceed();
        logger.info("func<doAround> method<" + className + "." + method + "> 消耗时间： <"
                + (System.currentTimeMillis() - begin) + ">ms");
        return ret;
    }
}
