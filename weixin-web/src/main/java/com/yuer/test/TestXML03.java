package com.yuer.test;

import org.junit.Test;

import com.yuer.bean.kfmsg.BaseMsg;
import com.thoughtworks.xstream.XStream;

/**
 * 对象和xml互转
 * 
 * @author THINK
 *
 */
public class TestXML03 {

	/**
	 * xml--javabean
	 */
	@Test
	public void test01() {

		// String xml
		String xml = "<xml><touser>1000001</touser><msgtype>text</msgtype></xml>";
		XStream xStream = new XStream();

		//设置别名
		xStream.alias("xml", BaseMsg.class);
		
		BaseMsg fromXML = (BaseMsg) xStream.fromXML(xml);

		System.out.println(fromXML);

	}

	/**
	 * javabean ---xml
	 */
	@Test
	public void test02() {

		BaseMsg baseMsg = new BaseMsg();
		baseMsg.setTouser("1000001");
		baseMsg.setMsgtype("test----type");

		// 默认根节点就是对应的类的全路径

		XStream xStream = new XStream();
		xStream.alias("xml", BaseMsg.class);

		String xml = xStream.toXML(baseMsg);
		System.out.println(xml);
	}

}
