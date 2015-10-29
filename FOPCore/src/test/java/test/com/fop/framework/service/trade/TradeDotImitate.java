/**
 * 文件名		：TradeDotImitate.java
 * 创建日期	：2013-10-8
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package test.com.fop.framework.service.trade;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 描述:仿制一个TradeDot，但Scope不一样，为singleton，表示单例，用做测试
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@Target({ElementType.TYPE})   
@Retention(RetentionPolicy.RUNTIME)   
@Documented 
@Scope("singleton")
@Service
public @interface TradeDotImitate {

}
