/**
 * 文件名		：FileResModel.java
 * 创建日期	：2013-10-17
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.data;

/**
 * 描述:文件资源访问结果信息模型
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileResModel {
  private FileResEnumTypes fileResEnumType;
  
  /**默认为FileResEnumTypes.NoConvert*/
  protected FileResModel(){
    this.fileResEnumType=FileResEnumTypes.NoConvert;
  }
  
  /**
   * 目前只支持 FieldData、GroupData、RepeatData 三种数据类型
   * @param resultDataType 结果数据类型
   */
  public FileResModel(FileResEnumTypes resultDataType){
      this.fileResEnumType=resultDataType;
  }

  /**
   * @return the fileResEnumType
   */
  public FileResEnumTypes getFileResEnumType() {
    return fileResEnumType;
  }
  
}
