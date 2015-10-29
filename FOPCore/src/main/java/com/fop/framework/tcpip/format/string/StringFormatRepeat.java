package com.fop.framework.tcpip.format.string;


import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FOPData;
import com.fop.framework.data.GroupData;
import com.fop.framework.data.RepeatData;
import com.fop.framework.tcpip.format.BaseFormat;
import com.fop.framework.tcpip.format.FormatContainer;
import com.fop.framework.tcpip.format.IFormat;

/**
 * 描述:字符串格式化循环类
 * 
 * @version 1.00
 * @author user
 * 
 */
public class StringFormatRepeat extends BaseFormat implements IFormat {


  private FormatContainer childContainer = new FormatContainer();

  
  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#format(java.lang.Object, com.fop.framework.data.FOPData)
   * <TRAN_LIST_ARRAY>
   *     <SDO>
   *        <TRANS_TYPE>1</TRANS_TYPE>
   *        <CASH_FLAG>2</CASH_FLAG>
   *        <CHK_NAME_FLAG>0</CHK_NAME_FLAG>
   *     <SDO>
   *     <SDO>
   *        <TRANS_TYPE>2</TRANS_TYPE>
   *        <CASH_FLAG>3</CASH_FLAG>
   *        <CHK_NAME_FLAG>4</CHK_NAME_FLAG>
   *     <SDO>
   *  <TRAN_LIST_ARRAY>
   *  2|1|2|0|2|3|4|
   */
  public void format(Object buffer, FOPData data) {

    //Element ele = ((Element)buffer).addElement(getFieldName());

    FOPData repeatData = ((GroupData) data).getFOPData(getDataName());
    
    RepeatData dataTemp =(RepeatData) repeatData;
    
    int count = dataTemp.size();
    
    ((StringBuffer)buffer).append(count).append(StringFormatDefine.delimiter);

    for(FOPData childData : dataTemp){
      //Element sdoChild = ele.addElement(SDO);
      childContainer.formatElement(buffer, childData);
    }
  }
  
  
  public void format(Object buffer, FOPContext context) {

    //Element ele = ((Element)buffer).addElement(getFieldName());
    
    FOPData repeatData = context.getFOPData(getDataName());
    
    RepeatData dataTemp =(RepeatData) repeatData;
    
    int count = dataTemp.size();
    
    ((StringBuffer)buffer).append(count).append(StringFormatDefine.delimiter);

    for(FOPData childData : dataTemp){
      //Element sdoChild = ele.addElement(SDO);
      childContainer.formatElement(buffer, childData);
    }
  }


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#unFormat(java.lang.Object, com.fop.framework.data.FOPData)
   * 2|1|2|0|2|3|4|
   */
  public void unFormat(Object buffer, FOPData data) {   
    StringBuffer sb = (StringBuffer)buffer;
    int start = sb.indexOf(StringFormatDefine.delimiter);
    int count = Integer.valueOf(sb.substring(0,start)); 
    sb = sb.delete(0,start +1);
    RepeatData repeatData = new RepeatData(getDataName());
    ((GroupData) data).addRepeatData(repeatData);
    for(int i= 0 ; i< count ;i++){
      GroupData g = new GroupData();
      repeatData.add(g);
      childContainer.unFormatElement(sb, g);
    }
  }
    
    public void unFormat(Object buffer, FOPContext context) {
      StringBuffer sb = (StringBuffer)buffer;
      int start = sb.indexOf(StringFormatDefine.delimiter);
      int count = Integer.valueOf(sb.substring(0,start)); 
      sb = sb.delete(0,start +1);
      RepeatData repeatData = new RepeatData(getDataName());
      context.addFOPData(repeatData);
      for(int i= 0 ; i< count ;i++){
        GroupData g = new GroupData();
        repeatData.add(g);
        childContainer.unFormatElement(sb, g);
      }

  }
  


  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#format(com.fop.framework.data.FOPData)
   */
  @Override
  public void format(FOPData data) {
    // TODO Auto-generated method stub
    
  }
  
  /* (non-Javadoc)
   * @see com.fop.framework.tcpip.format.IFormat#addChildFormat(com.fop.framework.tcpip.format.IFormat)
   */
  @Override
  public void addChildFormat(IFormat format) {
    childContainer.addIFormat(format);
    
  }
  
  public static void main(String[] args){
    String test = "2|1|2|0|2|3|4|2222|";
    
    StringBuffer sb = new StringBuffer(test);
    int start = sb.indexOf("|");
    String temp = sb.substring(0,start);
    int count = Integer.valueOf(sb.substring(0,start)); 
    sb = sb.delete(0,start +1);
    for(int i= 0 ; i< count ;i++){
      int start1 = sb.indexOf("|");
      System.out.print(sb.substring(0,start1));
      sb = sb.delete(0,start1 +1);
      int start2 = sb.indexOf("|");
      System.out.print(sb.substring(0,start2));
      sb = sb.delete(0,start2 +1);
      int start3 = sb.indexOf("|");
      System.out.print(sb.substring(0,start3));
      sb = sb.delete(0,start3 +1);
      
    }
  }

}
