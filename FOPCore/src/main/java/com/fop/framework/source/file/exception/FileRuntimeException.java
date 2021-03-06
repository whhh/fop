/**
 * 文件名		：FileRuntimeException.java
 * 创建日期	：2013-10-12
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package com.fop.framework.source.file.exception;

import java.util.HashMap;

import com.fop.framework.exception.FOPRuntimeException;
import com.fop.framework.util.StringUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

/**
 * 描述:文件资源运行时操作异常类
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class FileRuntimeException extends FOPRuntimeException{
  private static final HashMap<Integer, String> errorMsgMap;
  static{
    errorMsgMap=new HashMap<Integer, String>();
    errorMsgMap.put(ChannelSftp.SSH_FX_OK,                "SSH_FX_OK");
    errorMsgMap.put(ChannelSftp.SSH_FX_EOF,               "SSH_FX_EOF");
    errorMsgMap.put(ChannelSftp.SSH_FX_NO_SUCH_FILE,      "SSH_FX_NO_SUCH_FILE");
    errorMsgMap.put(ChannelSftp.SSH_FX_PERMISSION_DENIED, "SSH_FX_PERMISSION_DENIED");
    errorMsgMap.put(ChannelSftp.SSH_FX_FAILURE,           "SSH_FX_FAILURE");
    errorMsgMap.put(ChannelSftp.SSH_FX_BAD_MESSAGE,       "SSH_FX_BAD_MESSAGE");
    errorMsgMap.put(ChannelSftp.SSH_FX_NO_CONNECTION,     "SSH_FX_NO_CONNECTION");
    errorMsgMap.put(ChannelSftp.SSH_FX_CONNECTION_LOST,   "SSH_FX_CONNECTION_LOST");
    errorMsgMap.put(ChannelSftp.SSH_FX_OP_UNSUPPORTED,    "SSH_FX_OP_UNSUPPORTED");
  }
  /**
   * 
   */
  private static final long serialVersionUID = 9001724897408075889L;

  /**
   * @param errorCode 异常代码
   */
  public FileRuntimeException(String errorCode) {
    super(errorCode);
  }

  /**
   * 
   * @param cause 异常对象
   */
  public FileRuntimeException(Throwable cause) {
    if(cause instanceof SftpException){
      int errId=((SftpException)cause).id;
      String errorCode=errorMsgMap.get(errId);
      if(!StringUtil.isStrEmpty(errorCode)){
        //update by syl 20140228 避免死循环
        //throw new FileRuntimeException(errorCode, cause);
        throw new FOPRuntimeException(errorCode, cause);
      }else{
      //update by syl 20140228 避免死循环
        //throw new FileRuntimeException(cause);
        throw new FOPRuntimeException(cause);
      }
    }else{
    //update by syl 20140228 避免死循环
      //throw new FileRuntimeException(cause);
      throw new FOPRuntimeException(cause);
    }
  }

  /**
   * @param errorCode 异常代码
   * @param cause 异常对象
   */
  public FileRuntimeException(String errorCode, Throwable cause) {
    super(errorCode,cause);
  }

//  /**
//   * @param errorCode 异常代码
//   */
//  public FileRuntimeException(String errorCode, String defaultErrMessage) {
//    super(errorCode,defaultErrMessage);
//  }
  
}
