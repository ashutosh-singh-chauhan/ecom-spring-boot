package com.spring_ecom.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component 
@Aspect 
public class ValidationAspect {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(LoggingAspect.class);
  // This is used to validate and update the passed arguments in the api or stop further execution of method.

  @Around("execution(* com.spring_ecom.service.ProductService.getProductById(..)) && args(id) ")
  public Object validateAndUpdate(ProceedingJoinPoint jp, int id) throws Throwable {
    if (id < 0) {
      Logger.error("Product id is negative updating it");
      id = -id;
      // OR
      // Stop execution entirely and throw an exception
      // throw new IllegalArgumentException("Product ID cannot be negative");
    }

    Object obj = jp.proceed(new Object[]{id});

    return obj;
  }
}
