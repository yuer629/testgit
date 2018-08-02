package com.yuer.util;

import com.alibaba.fastjson.JSONObject;
import com.yuer.WeixinNet;

import redis.clients.jedis.Jedis;

public class WXTokenUtil {

	private static final String APPID = "wxce974e75ef1de20f";
	private static final String APPSECRET = "03d9f05064f2aba0299396402ef09068";

	private static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
			+ APPID + "&secret=" + APPSECRET;

	/**
	 * 用来获取access_token
	 * 
	 * @return
	 */
	public static String getAccessToken() {

		Jedis jedis = RedisPoolUtil.getJedis();

		// 2， 判断如果redis 服务器中存在token,直接从redis获取
		String token = jedis.get("wx_access_token");

		if (token == null || token.equals("")) {

			System.out.println("-----> 远程获取token.....");

			// 3， 不存在从微信api中获取。获取成功之后放到redis服务中.

			String body = WeixinNet.get(token_url);

			JSONObject jsonObject = JSONObject.parseObject(body);

			if (jsonObject != null) {
				if (jsonObject.containsKey("access_token")) {
					token = jsonObject.getString("access_token");

					jedis.set("wx_access_token", token);

					// 设置有效时间 7000秒，微信api规定最大时间7200秒
					jedis.expire("wx_access_token", 7000);
					// 4， 同时设置失效时间，小于7200秒

				} else {
					System.out.println("token获取异常....." + body);
				}
			}
		}

		return token;
	}

	public static void main(String[] args) {
		String accessToken = getAccessToken();
		System.out.println(accessToken);
	}

}
