package com.spring_ecom.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Aspect Oriented Programming
@Component 
@Aspect 
public class LoggingAspect {
  private static final Logger Logger = LoggerFactory.getLogger(LoggingAspect.class);

  // syntax of execution() method: 
  // return type fully_qualified_class_name . method_name . (args) .. means any arguments
  // multiple are also allowed - "execution(* com.spring_ecom.service.ProductService.*(..))" || "execution(* com.spring_ecom.service.ProductService.*(..))"
  @Before("execution(* com.spring_ecom.service.ProductService.*(..))")
  public void logMethodCall(JoinPoint jp) {
    Logger.info("Method called " + jp.getSignature().getName());
  }

  @After ("execution(* com.spring_ecom.service.ProductService.*(..))")
  public void logMethodExecuted(JoinPoint jp) {
    Logger.info("Method executed " + jp.getSignature().getName());
  }

  @AfterReturning  ("execution(* com.spring_ecom.service.ProductService.*(..))")
  public void logMethodExecutedAfterReturning(JoinPoint jp) {
    Logger.info("Method Returned " + jp.getSignature().getName());
  }

  @AfterThrowing ("execution(* com.spring_ecom.service.ProductService.*(..))")
  public void logMethodExecutedAfterErrorOccured(JoinPoint jp) {
    Logger.info("Some error occured " + jp.getSignature().getName());
  }
}
