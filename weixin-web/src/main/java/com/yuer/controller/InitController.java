package com.yuer.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.thoughtworks.xstream.XStream;
import com.yuer.bean.RecMessage;
import com.yuer.service.KFMsgService;
import com.yuer.util.SecurityKit;

@Controller
public class InitController {

	@Autowired
	private KFMsgService kfMsgService;

	private String token = "bj1801_token"; // 随机指定 需要和页面中设置token保持一致

	// @RequestMapping(value = "/init", method = RequestMethod.GET)
	@GetMapping("/init")
	@ResponseBody
	public String init(String signature, String timestamp, String nonce, String echostr) {

		// 1.如果是weixin api 会自动四个参数

		// 2. 验证成功则返回 echostr

		// 3.验证规则
		// 1）将token、timestamp、nonce三个参数进行字典序排序
		String[] params = { token, timestamp, nonce };
		Arrays.sort(params);
		// 2）将三个参数字符串拼接成一个字符串进行sha1加密
		StringBuffer sb = new StringBuffer();
		for (String string : params) {
			sb.append(string);
		}

		String sha1 = SecurityKit.sha1(sb.toString());

		// 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		if (sha1.equals(signature)) {
			return echostr;
		}

		return null;
	}

	/**
	 * 从微信服务器过来的内容，会发送post请求到该路径
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	// 自动回复 根据关键字回复
	// 1.只回复文字消息，如果用户发送的是非文字的请求，就回复“正在开发中....”
	// 2.没有找到对应的关键字 。尚未找到该内容
	// 3.使用redis保存关键字
	@PostMapping("/init")
	@ResponseBody
	public String initPost(HttpServletRequest request) throws Exception {

		// a.先获取用户发送过来的请求，判断是否为text类型

		Document document = new SAXReader().read(request.getInputStream());
		// String recXml = document.asXML();
		Element root = document.getRootElement();
		String msgtype = root.element("MsgType").getText();

		if (msgtype.contains("text")) {
			// 对象
			XStream xStream = new XStream();
			xStream.alias("xml", RecMessage.class);
			RecMessage messageObj = (RecMessage) xStream.fromXML(document.asXML());

			// if (messageObj.getMsgType().contains("text")) {
			// 定义回复的方法
			String xml = kfMsgService.autoSendTextMsg(messageObj);
			System.out.println("---->接收:" + document.asXML());
			System.out.println("---->回复:" + xml);

			return xml;
		}

		else {
			String toUserName = root.element("ToUserName").getText();
			String fromUserName = root.element("FromUserName").getText();

			RecMessage recMessage = new RecMessage();
			recMessage.setFromUserName(toUserName);
			recMessage.setToUserName(fromUserName);
			recMessage.setCreateTime(new Date().getTime());
			recMessage.setMsgType("text");
			recMessage.setContent("【暂时支持文字....】");

			XStream xStream = new XStream();
			xStream.alias("xml", RecMessage.class);

			String xml = xStream.toXML(recMessage);

			System.out.println("---->接收:" + document.asXML());
			System.out.println("---->回复:" + xml);

			return xml;
		}
		// } else {

		// }

		// b.封装返回的数据

		// return null;
	}

}
