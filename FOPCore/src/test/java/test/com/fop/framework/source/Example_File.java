/**
 * 文件名		：Example_File.java
 * 创建日期	：2013-9-28
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package test.com.fop.framework.source;

import java.util.List;

import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.source.SourceAccessUtil;
import com.fop.framework.source.file.data.FileExchangeEnumTypes;
import com.fop.framework.source.file.data.FileInDataModel;
import com.fop.framework.source.file.data.FileResEnumTypes;

/**
 * 描述:文件资源访问测试类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class Example_File {
  //**********本地文件操作**********
  /**
   * 判断文件是否存在,true存在,false不存在
   */
  public Boolean example01(){
    String fileUrl="/test/aa.txt";
    FileInDataModel inData=new FileInDataModel(fileUrl,FileExchangeEnumTypes.IS_File_Exist);
    Boolean res=(Boolean)SourceAccessUtil.operate(inData);
    return res;
  }
  /**
   * 判断文件是否存在,true存在,false不存在
   * 设定返回值类型
   */
  public Boolean example02(){
    String fileUrl="/test/aa.txt";
    FileInDataModel inData=new FileInDataModel(fileUrl,FileExchangeEnumTypes.IS_File_Exist,FileResEnumTypes.FieldData);
    FieldData res=(FieldData)SourceAccessUtil.operate(inData);
    return (Boolean)res.getValue();
  }
  /**获取本地文件,返回byte[]数组*/
  public Object example03(){
    String fileUrl="/test/aa.txt";
    FileInDataModel inData=new FileInDataModel(fileUrl,FileExchangeEnumTypes.Read_File);
//    FileInDataModel inData=new FileInDataModel(fileUrl);//简写,默认为读取文件
    byte[] res=(byte[])SourceAccessUtil.operate(inData);
    return res;
  }
  /**
   * 获取文件列表名称,返回List<String>文件名列表
   */
  @SuppressWarnings("unchecked")
  public Object example04(){
    String fileUrl="/test/";
    FileInDataModel inData=new FileInDataModel(fileUrl,FileExchangeEnumTypes.Query_DirFileNames);
    List<String> res=(List<String>)SourceAccessUtil.operate(inData);
    return res;
  }
  /**
   * 获取文件列表名称,返回List<String>文件名列表
   * 设定返回值类型
   */
  public Object example05(){
    String fileUrl="/test/";
    FileInDataModel inData=new FileInDataModel(fileUrl,FileExchangeEnumTypes.Query_DirFileNames,FileResEnumTypes.GroupData);
    GroupData res=(GroupData)SourceAccessUtil.operate(inData);
    return res;
  }
  /**
   * 写入文件,返回Boolean,表示操作是否成功,true成功,false失败
   */
  public Boolean example06(){
    byte[] fileContent=null;//要上传的文件内容
    String fileUrl="/test/b.txt";//目标文件路径
    FileInDataModel inData=new FileInDataModel(fileContent, fileUrl, FileExchangeEnumTypes.Write_File, false, null);
//    FileInDataModel inData=new FileInDataModel(fileContent, fileUrl);//简写，默认 FileExchangeEnumTypes.Write_File, false, null
    Boolean res=(Boolean)SourceAccessUtil.operate(inData);
    return res;
  }
  
  // **********服务器文件操作**********
  //方法与本地文件操作类型，增加一个参数String fileConnBeanId
  /**读取服务器文件,返回byte[]数组*/
  public Object example07(){
    String fileConnBeanId="sftpConfig01";//xml配置的服务器连接信息BeanId
    String fileUrl="/test/aa.txt";
    FileInDataModel inData=new FileInDataModel(fileUrl,fileConnBeanId,FileExchangeEnumTypes.Read_File);
//    FileInDataModel inData=new FileInDataModel(fileUrl,fileConnBeanId);//简写,默认为读取文件
    byte[] res=(byte[])SourceAccessUtil.operate(inData);
    return res;
  }
  
}
