/**
 * 文件名		：DBResModel.java
 * 创建日期	：2013-10-16
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.db.data;


/**
 * 描述:数据库资源返回结果参数模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class DBResModel {
    private DBResEnumTypes dbResEnumType;
    
    /**默认为DBResEnumTypes.NoConvert*/
    protected DBResModel(){
      this.dbResEnumType=DBResEnumTypes.NoConvert;
    }
    
    /**
     * 目前只支持 FieldData、GroupData、RepeatData 三种数据类型
     * @param resultDataType 结果数据类型
     */
    public DBResModel(DBResEnumTypes resultDataType){
        this.dbResEnumType=resultDataType;
    }
    
    /**
     * @return the dbResEnumType
     */
    public DBResEnumTypes getDbResEnumType() {
      return dbResEnumType;
    }
    
}
