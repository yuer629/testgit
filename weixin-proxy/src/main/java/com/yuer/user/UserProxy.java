package com.yuer.user;

import com.alibaba.fastjson.JSONObject;
import com.yuer.WeixinNet;
import com.yuer.util.WXTokenUtil;


/**
 * 代理和用户相关的信息
 * 
 * @author THINK
 *
 */
public class UserProxy {

	private static final String TOKEN = WXTokenUtil.getAccessToken();

	// 获取用户的openid
	private static String USER_LIST_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + TOKEN;

	// 批量获取用户基本信息
	private static String USER_LIST_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="
			+ TOKEN;

	// 获取用户列表
	public static String getUserList(String nextOpeinId) {

		if (nextOpeinId != null) {
			USER_LIST_URL_PREFIX = USER_LIST_URL_PREFIX + "&next_openid=" + nextOpeinId;
		}

		String body = WeixinNet.get(USER_LIST_URL_PREFIX);

		// 判断是否获取成功
		if (JSONObject.parseObject(body).containsKey("data")) {
			return body;
		}

		System.out.println("userlist-->" + body);

		return null;
	}

	// 获取用户列表详细
	public static String getUserListInfo(String requestBody) {

		String body = WeixinNet.post(USER_LIST_INFO_URL, requestBody);

		if (JSONObject.parseObject(body).containsKey("user_info_list")) {
			return body;
		}

		System.out.println("userListInfo---->" + body);

		return null;
	}

}
