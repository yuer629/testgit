package com.yuer.test;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * 解析xml
 * 
 * @author THINK
 *
 */
public class TestXml {

	/**
	 * 获取score.xml配置文件
	 * 
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {

		// 1.获取document 对象

		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read("src/main/resources/score.xml");

		// 2.操作节点
		Element rootElement = document.getRootElement();
		System.out.println(rootElement);

		// 去指定的内容值 :王同
		List<Element> list = rootElement.elements();

		// element:student
		for (Element element : list) {
			
			//获取属性
			Attribute attribute = element.attribute("class");
			System.out.println(attribute.getName()+":属性值:"+attribute.getValue());
			
			List<Element> elements = element.elements();

			System.out.println(element.getName() + "------>");

			for (Element element2 : elements) {
				System.out.println(element2.getStringValue());
			}
		}
	}

	@Test
	public void test2() throws Exception {

		// 1.获取document 对象
		String msg = "<xml> <FromUserName>xxxxxxpppppppaaaall></FromUserName> <CreateTime>12345678</CreateTime></xml>";
		Document document = DocumentHelper.parseText(msg);

		// 查找FromUserName
		Element xml = document.getRootElement();

		Element element = xml.element("FromUserName");

		System.out.println(element.getText());

	}

}
