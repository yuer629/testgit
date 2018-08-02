package com.yuer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.omg.CORBA.ARG_IN;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yuer.bean.RecMessage;
import com.yuer.bean.kfmsg.MsgType;
import com.yuer.bean.kfmsg.news.ArticleItem;
import com.yuer.bean.kfmsg.news.Articles;
import com.yuer.bean.kfmsg.news.NewsMsg;
import com.yuer.bean.kfmsg.text.TextMsg;
import com.yuer.bean.kfmsg.text.TextMsgItem;
import com.yuer.msg.KFMsgProxy;
import com.yuer.service.KFMsgService;
import com.yuer.util.RedisPoolUtil;
import com.thoughtworks.xstream.XStream;

import redis.clients.jedis.Jedis;

@Service
public class KFMsgServiceImpl implements KFMsgService {

	@Override
	public String sendKfTextMsg(String openId, String content) {

		// 1.调用proxy获取数据

		// 2.需要自己封装参数(Map)

		// {
		// "touser":"OPENID",
		// "msgtype":"text",
		// "text":
		// {
		// "content":"Hello World"
		// }
		// }

		TextMsg textMsg = new TextMsg();
		textMsg.setTouser(openId);
		textMsg.setMsgtype(MsgType.TEXT);

		TextMsgItem text = new TextMsgItem();
		text.setContent(content);

		textMsg.setText(text);

		// 转为json 字符串

		// 参数封装完成
		String jsonString = JSONObject.toJSONString(textMsg);

		String msg = KFMsgProxy.sendKfMsg(jsonString);

		return msg;
	}

	@Override
	public String sendKfNewsMsg(String openId) {

		// 正常参数应该是参入过来
		// 暂时不从页面获取

		// 测试图文消息
		NewsMsg newsMsg = new NewsMsg();
		newsMsg.setTouser(openId);
		newsMsg.setMsgtype(MsgType.NEWS);

		Articles news = new Articles();
		List<ArticleItem> articles = new ArrayList<>();

		// 图文一
		ArticleItem item1 = new ArticleItem();
		item1.setTitle("手抓羊肉");
		item1.setPicurl(
				"https://fuss10.elemecdn.com/f/7d/4517a349c01590d57a6f5f8c8b5bejpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/85");
		item1.setUrl("https://www.ele.me/shop/159447834#797579001");
		item1.setDescription(" 感觉不好吃...");

		// 图文二
		ArticleItem item2 = new ArticleItem();

		item2.setTitle("手抓狗肉");
		item2.setPicurl(
				"https://fuss10.elemecdn.com/b/d0/d87650e6b2ffd9219eefa4c51f95ejpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/85");
		item2.setUrl("https://www.ele.me/shop/159447834#797579001");
		item2.setDescription(" 感觉不好吃...");

		articles.add(item1);
		articles.add(item2);

		news.setArticles(articles);

		newsMsg.setNews(news);

		String string = JSONObject.toJSONString(newsMsg);
		System.out.println(string);

		String msg = KFMsgProxy.sendKfMsg(string);

		return msg;
	}

	public static void main(String[] args) {

		// String sendKfMsg = new KFMsgServiceImpl().sendKfMsg("AAAAA", "NIHAO
		// ");
		// System.out.println(sendKfMsg);

		// 测试实体类 text

		TextMsg textMsg = new TextMsg();
		textMsg.setTouser("oJ1R0w2mTtOncwUWHxKadSGI8ous");
		textMsg.setMsgtype(MsgType.TEXT);

		TextMsgItem text = new TextMsgItem();
		text.setContent("你好");

		textMsg.setText(text);

		// 转为json 字符串
		String jsonString = JSONObject.toJSONString(textMsg);
		System.out.println(jsonString);

		// 测试图文消息
		NewsMsg newsMsg = new NewsMsg();
		newsMsg.setTouser("oJ1R0w2mTtOncwUWHxKadSGI8ous");
		newsMsg.setMsgtype(MsgType.NEWS);

		Articles news = new Articles();
		List<ArticleItem> articles = new ArrayList<>();

		// 图文一
		ArticleItem item1 = new ArticleItem();
		item1.setTitle("手抓羊肉");
		item1.setPicurl(
				"https://fuss10.elemecdn.com/f/7d/4517a349c01590d57a6f5f8c8b5bejpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/85");
		item1.setUrl("https://www.ele.me/shop/159447834#797579001");
		item1.setDescription(" 感觉不好吃...");

		// 图文二
		ArticleItem item2 = new ArticleItem();

		item2.setTitle("手抓狗肉");
		item2.setPicurl(
				"https://fuss10.elemecdn.com/b/d0/d87650e6b2ffd9219eefa4c51f95ejpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/85");
		item2.setUrl("https://www.ele.me/shop/159447834#797579001");
		item2.setDescription(" 感觉不好吃...");

		articles.add(item1);
		articles.add(item2);

		news.setArticles(articles);

		newsMsg.setNews(news);

		String string = JSONObject.toJSONString(newsMsg);
		System.out.println(string);

		String msg = KFMsgProxy.sendKfMsg(jsonString);

	}

	@Override
	public String autoSendTextMsg(RecMessage message) {

		RecMessage sendMssage = new RecMessage();

		// 1.将接受信息和发送信息 进行互转
		sendMssage.setFromUserName(message.getToUserName());
		sendMssage.setToUserName(message.getFromUserName());
		sendMssage.setCreateTime(new Date().getTime());

		sendMssage.setMsgType(message.getMsgType());

		// 2.根据关键字 去 redis查找是否有记录
		Jedis jedis = RedisPoolUtil.getJedis();
		String key = message.getContent();
		String value = jedis.get(key);

		if (value != null && value != "") {
			sendMssage.setContent(value);
		} else {
			sendMssage.setContent("没有对应的内容");
		}

		// 3.封装回复的内容

		XStream xStream = new XStream();
		xStream.alias("xml", RecMessage.class);
		String xml = xStream.toXML(sendMssage);

		return xml;

	}

	@Override
	public String autoSendErrorTextMsg(RecMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

}
