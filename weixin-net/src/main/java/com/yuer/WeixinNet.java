package com.yuer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WeixinNet {
	/**
	 * get请求工具类
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {

		try {

			// 1.获取HttpClient对象
			HttpClient ie = HttpClients.createDefault();

			// 2.创建对应的请求 get|post ...
			HttpGet get = new HttpGet(url);

			// 3.如果有请求头或者请求体 封装对应信息

			// 4.调用对应请求
			HttpResponse httpResponse = ie.execute(get);

			// 5.获取响应结果
			HttpEntity entity = httpResponse.getEntity();

			String body = EntityUtils.toString(entity, "UTF-8");

			System.out.println(body);

			return body;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("weixin-net--->get调用异常!!!");
		}
		return null;
	}

	/**
	 * weinxin-net 调用post工具类
	 * 
	 * @param url
	 * @param requestBody
	 * @return
	 */
	public static String post(String url, String requestBody) {

		try {
			HttpClient httpClient = HttpClients.createDefault();

			// token是动态变化
			// 获取二维码的ticket post

			HttpPost httpPost = new HttpPost(url);

			// 设置内容 头部信息
			httpPost.addHeader("Content-Type", "application/json");

			// 创建Entity
			HttpEntity entity = new StringEntity(requestBody, "utf-8");
			// 设置请求体内容
			httpPost.setEntity(entity);

			// 发起请求
			HttpResponse httpResponse = httpClient.execute(httpPost);

			// 响应的内容
			HttpEntity responseBody = httpResponse.getEntity();

			String body = EntityUtils.toString(responseBody, "utf-8");

			System.out.println(body);

			return body;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
//	public static void main(String[] args) {
//		String data = get("http://www.baidu.com");
//		System.out.println(data);
//	}
	
}
