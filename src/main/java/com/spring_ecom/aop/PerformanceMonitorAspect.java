package com.spring_ecom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component 
@Aspect 
public class PerformanceMonitorAspect {
  private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(LoggingAspect.class);
  // This is used to verify the time taken by some method to execute it. And Performance monitoring
  @Around("execution(* com.spring_ecom.service.ProductService.*(..))")
  public Object monitorTime(ProceedingJoinPoint jp) throws Throwable {
    long start = System.currentTimeMillis();
    Object obj = jp.proceed();
    long end = System.currentTimeMillis();

    Logger.info("Time taken : " + (end-start) + " ms");

    return obj;
  }

}
