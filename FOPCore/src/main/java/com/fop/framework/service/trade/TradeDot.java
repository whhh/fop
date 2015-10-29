/**
 * 文件名		：TradeDot.java
 * 创建日期	：2013-9-10
 * Copyright (c) 2003-2013 北京联龙博通 * All rights reserved.
 */
package com.fop.framework.service.trade;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * 描述: 交易步骤注解
 * Scope为prototype表示将Bean的作用范围为prototype，意为每次取到的对象为新实例，相当于new出来的实例。
 * Target为ElementType.TYPE表示该注解用于 类，接口（包括注解类型）或enum声明
 * Retention为RetentionPolicy.RUNTIME表示VM将在运行期也保留注释
 * 标注Service表示同时具有Service的业务组件功效
 * 
 * @version 1.00
 * @author xieyg
 * 
 */
@Target({ElementType.TYPE})   
@Retention(RetentionPolicy.RUNTIME)   
@Documented 
@Scope("prototype")
@Service
public @interface TradeDot {


}
