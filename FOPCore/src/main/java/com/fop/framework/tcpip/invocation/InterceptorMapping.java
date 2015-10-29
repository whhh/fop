/**
 * 文件名		：InterceptorMapping.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.tcpip.invocation;


public class InterceptorMapping {

    private String name;
    private Interceptor interceptor;

    public InterceptorMapping(String name, Interceptor interceptor) {
        this.name = name;
        this.interceptor = interceptor;
    }

    public String getName() {
        return name;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final InterceptorMapping that = (InterceptorMapping) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (name != null ? name.hashCode() : 0);
        return result;
    }
}
