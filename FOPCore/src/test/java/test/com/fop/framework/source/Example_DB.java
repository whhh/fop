/**
 * 文件名		：Example_DB.java
 * 创建日期	：2013-9-28
 * Copyright (c) 2003-2013 北京联龙博通

 * All rights reserved.
 */
package test.com.fop.framework.source;

import com.fop.framework.source.SourceAccessUtil;
import com.fop.framework.source.db.SQLAdapter;
import com.fop.framework.source.db.data.DBInDataModel;
import com.fop.framework.source.db.data.DBResEnumTypes;

/**
 * DB调用过程example
 * 
 * @version 1.00
 * @author syl
 * 
 */
public class Example_DB {
  /**
   * 执行Dao接口，入参为空
   * @return
   */
  public Object example01(){
    DBInDataModel inData=new DBInDataModel(UserDao.class, "querySysDate", null);
//    DBInDataModel inData=new DBInDataModel(UserDao.class, "querySysDate", new Object[]{});
    Object res=SourceAccessUtil.operate(inData);
    return res;
  }
  /**
   * 执行Dao接口，包含入参
   * @return
   */
  public Object example02(){
    String param1=null;
    Object param2=null;
    DBInDataModel inData=new DBInDataModel(UserDao.class, "querySysDateParam", new Object[]{param1,param2});
    Object res=SourceAccessUtil.operate(inData);
    return res;
  }
  /**
   * 执行Dao接口，包含入参,设置返回值类型
   * @return
   */
  public Object example03(){
    String param1=null;
    Object param2=null;
    DBInDataModel inData=new DBInDataModel(UserDao.class, "querySysDateParam", new Object[]{param1,param2},DBResEnumTypes.FieldData);
    Object res=SourceAccessUtil.operate(inData);
    return res;
  }
  /**
   * 直接执行sql语句的方式
   * 
   * xml文件的配置
   * <typeAlias alias="sqladapter" type="com.zj.logistics.util.SQLAdapter" />
   *   
   * <select id="executeSql" parameterType="SQLAdapter" resultType="string">  
   * ${sql}  
   * </select>
   * 
   * @return
   */
  public Object getSysDataBySQLAdapter(){
    String sql="SELECT TO_CHAR(SYSDATE, 'YYYY-MM-DD') || ' ' ||  TO_CHAR(SYSDATE, 'DAY')  FROM DUAL";
    SQLAdapter adapter=new SQLAdapter(sql);
    Object res=SourceAccessUtil.operate(new DBInDataModel(UserDao.class, "executeSql", new Object[]{adapter}));
    return res;
  }
  
  
  public interface UserDao{
    public String querySysDate();
    public String querySysDateParam(String param1,Object param2);
    public String executeSql(SQLAdapter sqlAdapter);
  }
}
