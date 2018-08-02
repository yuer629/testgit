package com.yuer.test;

import java.io.FileWriter;
import java.io.Writer;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.BaseElement;
import org.junit.Test;

/**
 * 写
 * 
 * @author THINK
 *
 */
public class TestXML2 {

	/**
	 * 编写
	 * 
	 * @throws Exception
	 */
	@Test
	public void test01() throws Exception {

		// 1.创建xml Document文档对象
		String xml = "<depts> <dept><deptno>1</deptno><dname>运营部</dname><loc>广东深圳宝安</loc></dept><dept><deptno>2</deptno><dname>财务部</dname><loc>深圳</loc></dept> </depts>";

		Document document = DocumentHelper.parseText(xml);

		// 2.设定格式
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		outputFormat.setNewlines(true); // 新行
		outputFormat.setEncoding("UTF-8");
		outputFormat.setIndent(true);
		outputFormat.setIndent(" ");// 通过空格缩进

		Writer writer = new FileWriter("src/main/resources/dept.xml");

		// 3.输出内容
		XMLWriter xmlWriter = new XMLWriter(writer, outputFormat);
		xmlWriter.write(document);

		xmlWriter.flush();
		xmlWriter.close();

		System.out.println("成功!!");

	}

	/**
	 * 通过创建对象
	 * 
	 * @throws Exception
	 */
	@Test
	public void test02() throws Exception {

		// 1.先创建docment
		Document document = DocumentHelper.createDocument();

		Element root = new BaseElement("users");
		document.setRootElement(root);

		Element user01 = root.addElement("user");
		user01.addAttribute("class", "com.hzit.bean.Users01");
		user01.addElement("userId").addText("1001");
		user01.addElement("userNmae").addText("赵子龙");
		user01.addElement("pwd").addText("12");

		Element user02 = root.addElement("user");
		user02.addAttribute("class", "com.hzit.bean.Users02");
		user02.addElement("userId").addText("1002");
		user02.addElement("userNmae").addText("张飞");
		user02.addElement("pwd").addText("11");
		
		
		

		// 2.设定格式
		OutputFormat outputFormat = OutputFormat.createPrettyPrint();
		//outputFormat.setNewlines(true); // 新行
		outputFormat.setEncoding("UTF-8");
		outputFormat.setIndent(true);
		outputFormat.setIndent("\t");// 通过空格缩进

		Writer writer = new FileWriter("src/main/resources/users.xml");

		// 3.输出内容
		XMLWriter xmlWriter = new XMLWriter(writer, outputFormat);
		xmlWriter.write(document);

		xmlWriter.flush();
		xmlWriter.close();

		System.out.println("成功!!");
		// 2.根节点

		// 3.子节点

		// 4.可能还有子节点或者是 text内容 设置属性值

	}

}
