package com.fop.framework.tcpip.format;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fop.framework.context.ApplicationContextUtil;
import com.fop.framework.context.FOPContext;
import com.fop.framework.data.FieldData;
import com.fop.framework.data.GroupData;
import com.fop.framework.tcpip.ServerSocket;
import com.fop.framework.tcpip.util.TCPTools;

/**
 * 描述:格式化配置监听，用于初始化格式化类
 * 
 * @version 1.00
 * @author user
 * 
 */
public class FormatConfigListener implements ServletContextListener {

  private static final Log logger = LogFactory
      .getLog(FormatConfigListener.class);// 日志记录对象

  private SAXReader saxReader = new SAXReader(); // xml文件解析器

  /**
   * XML后缀
   */
  public final static String XMLPOSTFIX = ".xml";

  /**
   * format配置类bean名称
   */
  public final static String FORMATCONFIG = "formatConfig";

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
   * ServletContextEvent)
   */
  @Override
  public void contextInitialized(ServletContextEvent event) {
    // String contextPath = this.getClass().getResource("").getPath();
    // String contextPath = event.getServletContext().getRealPath("/");
    
    String contextPath = new File(this.getClass().getClassLoader().getResource("/").getPath())
        .getParentFile().getParent();
    
    logger.info("contextPath=" + contextPath);
    
    initFormat(contextPath);
  }

  private FormatConfigContainer getFormatConfigContainer() {
    return (FormatConfigContainer) ApplicationContextUtil.getBean(FORMATCONFIG);
  }

  // Test
  // private FormatConfigContainer getFormatConfigContainer() {
  // FormatConfigContainer fc = new FormatConfigContainer();
  // Map<String, String> configs = new HashMap<String, String>();
  // Map<String, String> defines = new HashMap<String, String>();
  // configs.put("client", "META-INF");
  // defines.put("FmtDefine",
  // "com.fop.framework.tcpip.format.xml.FormatDefine");
  // defines.put("FmtGroup", "com.fop.framework.tcpip.format.xml.FormatGroup");
  // defines.put("FmtRepeat",
  // "com.fop.framework.tcpip.format.xml.FormatRepeat");
  // defines.put("FmtField", "com.fop.framework.tcpip.format.xml.FormatField");
  // defines.put("StringFmtDefine",
  // "com.fop.framework.tcpip.format.string.StringFormatDefine");
  // defines.put("StringFmtGroup",
  // "com.fop.framework.tcpip.format.string.StringFormatGroup");
  // defines.put("StringFmtRepeat",
  // "com.fop.framework.tcpip.format.string.StringFormatRepeat");
  // defines.put("StringFmtField",
  // "com.fop.framework.tcpip.format.string.StringFormatField");
  // fc.setConfigs(configs);
  // fc.setDefines(defines);
  // return fc;
  // }

  /**
   * 初始化的格式化类
   * 
   * @param contextPath
   */
  private void initFormat(String contextPath) {
    logger.debug("初始化格式化配置对象列表开始.");
    Map<String, String> configMap = null;
    try {
      configMap = getFormatConfigContainer().getConfigs();
      Iterator<String> configIt = configMap.keySet().iterator();

      String configValue;
      File dir;
      File[] fileList;

      while (configIt.hasNext()) {
        configValue = configMap.get(configIt.next());

        dir = new File(contextPath, configValue);
        if (dir.isDirectory()) {
          fileList = dir.listFiles();
          for (File file : fileList) {
            if (file.getName().indexOf(XMLPOSTFIX) == -1) {
              continue;
            }
            // 循环解析配置文件
            loadConfigFile(file);
          }
        }
      }
    } catch (Exception e) {
      logger.error("初始化格式化配置对象列表失败!", e);
      return;
    }
    logger.info("初始化格式化配置对象列表成功, 共" + configMap.size() + "个文件夹.");
  }

  /**
   * 查找格式化类配置文件，进行加载
   * 
   * @param file
   *          文件对象
   * @throws Exception
   *           查找格式化异常
   */
  @SuppressWarnings("unchecked")
  private void loadConfigFile(File file) throws Exception {
    Document document = null;

    try {
      document = saxReader.read(file);
      Element root = document.getRootElement();

      // 获取配置文件的根节点

      List<Element> elements = root.elements();
      IFormat fmt = null;

      // 根据XML节点对象循环解析每一个格式化元素定义配置
      for (Element element : elements) {
        // 创建格式化元素
        fmt = createFmtElement(element);

        // 解析格式化元素
        parseFmtElement(fmt, element);

        // 向容器中添加格式化元素 name
        FormatConfigContainer.addFormat(fmt.getDataName(), fmt);

        logger.info("初始化格式化配置对象" + fmt.getDataName() + "成功.");
      }

    } catch (Exception e) {
      logger.error("格式化配置文件" + file.getName() + "解析失败", e);
      throw new Exception(e);
    }
  }

  /**
   * 根据配置创建格式化类
   * 
   * @param element
   *          节点配置
   * @return 格式化元素
   * @throws Exception
   *           创建格式化元素失败异常
   */
  private IFormat createFmtElement(Element element) throws Exception {
    String tagName;
    String className;
    IFormat fmt;

    tagName = element.getQualifiedName();
    className = getFormatConfigContainer().getDefines().get(tagName);

    if (className == null) {
      logger.error("Tag" + tagName + "未定义!");
      throw new Exception();
    }

    try {
      fmt = (IFormat) Class.forName(className).newInstance();
    } catch (Exception e) {
      logger.error("实例化Class对象异常", e);
      throw new Exception(e);
    }

    return fmt;
  }

  /**
   * 解析格式化元素
   * 
   * @param fmt
   *          格式化元素
   * @param element
   *          XML节点对象
   * @throws Exception
   *           解析失败异常
   */
  @SuppressWarnings("unchecked")
  private void parseFmtElement(IFormat fmt, Element element) throws Exception {
    IFormat child;

    // 为格式化元素赋值
    setFmtValues(fmt, element);

    // 判断fmt.class是否是Decorator.class或子类
    // if (!com.chinamworld.mbp.comm.access.format.Decorator.class
    // .isAssignableFrom(fmt.getClass())) {
    List<Element> eList = element.elements();
    for (Element aElement : eList) {
      child = createFmtElement(aElement);

      parseFmtElement(child, aElement);

      // if (com.chinamworld.mbp.comm.access.format.Decorator.class
      // .isAssignableFrom(child.getClass())) {
      // fmt.addDecorator((Decorator)child);
      // } else {
      fmt.addChildFormat(child);
      // if (AdapterFormat.class.isAssignableFrom(fmt.getClass())) {
      // ((AdapterFormat) fmt).addIFormat(child);
      // }
    }
  }

  /**
   * 为格式化元素赋值
   * 
   * @param fmt
   *          格式化元素
   * @param element
   *          XML节点对象
   * @throws Exception
   *           赋值失败异常
   */
  @SuppressWarnings("unchecked")
  private void setFmtValues(IFormat fmt, Element element) throws Exception {
    List<Attribute> attrs = element.attributes();
    String vName = null;
    String methodName = null;
    Method method;

    try {
      for (Attribute attr : attrs) {
        vName = attr.getName();
        methodName = vName;

        methodName = "set" + methodName.substring(0, 1).toUpperCase()
            + methodName.substring(1);

        method = fmt.getClass().getMethod(methodName, java.lang.String.class);

        method.invoke(fmt, attr.getValue());
      }
    } catch (Exception e) {
      logger.error("变量" + vName + "赋值失败, 不存在" + methodName + "方法!");
      throw new Exception(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
   * ServletContextEvent)
   */
  @Override
  public void contextDestroyed(ServletContextEvent event) {

    // TODO Auto-generated method stub

  }

  public static void main(String[] args) throws Exception {
    FormatConfigListener.testEsbA();

  }

  public static void testString() {
    FormatConfigListener fl = new FormatConfigListener();
    fl.initFormat("E:/llbt/nybank/workspace/FOPCore/src/main/java");

    FOPContext fc = new FOPContext();

    FieldData flag = new FieldData("flag", "0");
    FieldData verify = new FieldData("verify", "MD5");
    FieldData channelCode = new FieldData("channelCode", "MBS");
    FieldData mobile = new FieldData("mobile", "18620095921");
    FieldData msg = new FieldData("msg", "ESP");
    FieldData verifyValue = new FieldData("verifyValue",
        "cfc7afff14490c0d5f794487f5fc2dd7");

    fc.addFOPData(flag);
    fc.addFOPData(verify);
    fc.addFOPData(channelCode);
    fc.addFOPData(mobile);
    fc.addFOPData(msg);
    fc.addFOPData(verifyValue);

    IFormat f = FormatConfigContainer.getFormat("sendstring2001");
    Map<String, IFormat> m = FormatConfigContainer.formats;
    StringBuffer s = new StringBuffer();
    f.format(s, fc);
    System.out.println(s.toString());
  }

  public static void testEsbA() throws Exception {
    FormatConfigListener fl = new FormatConfigListener();
    fl.initFormat("E:/llbt/nybank/workspace/FOPCore/src/main/java");

    FOPContext fc = new FOPContext();

    FieldData _esbServiceCode = new FieldData("_esbServiceCode", "11002000003");
    FieldData _esbServiceScene = new FieldData("_esbServiceScene", "01");
    FieldData _esbConsumerSeqNo = new FieldData("_esbConsumerSeqNo",
        "0101042013112500000003");
    FieldData _esbTranDate = new FieldData("_esbTranDate", "20131125");
    FieldData _esbTimeStamp = new FieldData("_esbTimeStamp", "112615767");

    FieldData _esbUserId = new FieldData("_esbUserId", "10");
    FieldData _esbBusSeqNo = new FieldData("_esbBusSeqNo",
        "0101042013112500000002");

    FieldData mobile = new FieldData("mobile", "18620095921");
    FieldData msg = new FieldData("msg", "ESP");
    String value = TCPTools.getMD5("MBSf7qw44" + mobile + msg, "UTF-8");
    FieldData verifyValue = new FieldData("verifyValue", value);

    fc.addFOPData(_esbServiceCode);
    fc.addFOPData(_esbServiceScene);
    fc.addFOPData(_esbConsumerSeqNo);
    fc.addFOPData(_esbTranDate);
    fc.addFOPData(_esbTimeStamp);
    fc.addFOPData(_esbUserId);
    fc.addFOPData(_esbBusSeqNo);
    fc.addFOPData(mobile);
    fc.addFOPData(msg);
    fc.addFOPData(verifyValue);

    IFormat f = FormatConfigContainer.getFormat("send11002000003");
    Map<String, IFormat> m = FormatConfigContainer.formats;
    StringBuffer s = new StringBuffer();
    Document document = DocumentHelper.createDocument();
    Element e = document.addElement("SDOROOT");
    f.format(e, fc);
    String ss = e.asXML();
    System.out.println(ss.length() + ss);

    // ss="000"+ss.length()+ss;

    File ff = new File(
        "E:\\llbt\\nybank\\workspace\\FOPCore - 20131120\\src\\main\\java\\META-INF\\1002.txt");
    SAXReader ss1 = new SAXReader();
    Document document1 = ss1.read(ff);
    String mess = document1.asXML();
    Document doc = DocumentHelper.parseText(mess);
    Element ele = doc.getRootElement();

    System.out.println(ele.asXML().length() + "@!!!!!!!!"
        + (ele.asXML().getBytes()).length);

    ss = "000" + (ele.asXML().getBytes()).length + ele.asXML();
    System.out.println(ss.length() + ss + (ele.asXML().getBytes()).length);

    // new ESBCommProcessorImpl().decode(ss.getBytes(),"11002000003");
  }

  public void testEsb() throws Exception {

    FormatConfigListener fl = new FormatConfigListener();
    fl.initFormat("E:/llbt/nybank/workspace/FOPCore/src/main/java");
    IFormat f = FormatConfigContainer.getFormat("send2001");
    Map<String, IFormat> m = FormatConfigContainer.formats;
    GroupData data = new GroupData();

    GroupData system = new GroupData("system");

    FieldData serviceCode = new FieldData("serviceCode", "11002000003");
    FieldData consumerId = new FieldData("consumerId", "010104");
    FieldData serviceScene = new FieldData("serviceScene", "01");
    FieldData seqNo = new FieldData("seqNo", "0404012013102514300195");
    FieldData tranDate = new FieldData("tranDate", "20130918");
    FieldData retStatus = new FieldData("retStatus", "S");
    FieldData timeStamp = new FieldData("timeStamp", "143001828");

    system.addFieldData(serviceCode);
    system.addFieldData(consumerId);
    system.addFieldData(serviceScene);
    system.addFieldData(seqNo);
    system.addFieldData(tranDate);
    system.addFieldData(retStatus);
    system.addFieldData(timeStamp);

    GroupData app = new GroupData("app");
    FieldData userId = new FieldData("userId", "E0100");
    FieldData branchId = new FieldData("branchId", "0100");
    app.addFieldData(userId);
    app.addFieldData(branchId);

    GroupData body = new GroupData("body");

    FieldData flag = new FieldData("flag", "0");
    FieldData verify = new FieldData("verify", "MD5");
    FieldData channelCode = new FieldData("channelCode", "MBS");
    FieldData mobile = new FieldData("mobile", "18620095921");
    FieldData msg = new FieldData("msg", "ESP");
    FieldData verifyValue = new FieldData("verifyValue",
        "cfc7afff14490c0d5f794487f5fc2dd7");

    body.addFieldData(flag);
    body.addFieldData(verify);
    body.addFieldData(channelCode);
    body.addFieldData(mobile);
    body.addFieldData(msg);
    body.addFieldData(verifyValue);

    data.addGroupData(system);
    data.addGroupData(app);
    data.addGroupData(body);

    System.out.println(data.toString());

    // GroupData data = new GroupData();
    // GroupData system = new GroupData("system");
    // FieldData tranFlow = new FieldData("tranFlow", "100010");
    // RepeatData tranIds = new RepeatData("tranIds");
    // GroupData tranGroup = new GroupData();
    // FieldData tranId = new FieldData("tranId", "tran100010");
    // tranGroup.addFieldData(tranId);
    // GroupData tranGroup2 = new GroupData();
    // FieldData tranId2 = new FieldData("tranId", "tran100012");
    // tranGroup2.addFieldData(tranId2);
    // tranIds.add(tranGroup);
    // tranIds.add(tranGroup2);
    //
    // system.addFieldData(tranFlow);
    // system.addRepeatData(tranIds);
    //
    // data.addGroupData(system);
    //
    // System.out.println(data.toString());

    System.out.println("---------------分割线-------------");
    // f.format(data);
    // System.out.println(f);
    File ff = new File(
        "E:\\llbt\\nybank\\workspace\\FOPCore\\src\\main\\java\\META-INF\\1002.txt");
    SAXReader ss1 = new SAXReader();
    Document document = ss1.read(ff);
    String mess = document.asXML();
    Document doc = DocumentHelper.parseText(mess);
    Element ele = doc.getRootElement();
    GroupData data2 = new GroupData();
    IFormat format = FormatConfigContainer.getFormat("receive" + 2001);
    format.unFormat(ele, data2);
    System.out.println(data2.toString());
    // IFormat f1 = FormatConfigContainer.getFormat("receive2001");
    // Document doc = DocumentHelper.parseText(((FormatDefine) f).toString());
    // Element ele = doc.getRootElement();
    // GroupData data1 = new GroupData();
    // f1.unFormat(ele, data1);
    // System.out.println("---------------分割线2-------------");
    // System.out.println(data1.toString());
    new ServerSocket(23, 10000L);
    // List<String> l = new ArrayList<String>();
    // //l.add("11.8.126.18:30094");
    // l.add("127.0.0.1:23");
    // ClientSocket c = new ClientSocket(l , 10000L);
    // //c.test();
    // c.sendAndWait("2001", data, 100000L);
  }
}
