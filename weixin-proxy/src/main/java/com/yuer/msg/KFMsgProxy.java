package com.yuer.msg;

import com.yuer.WeixinNet;
import com.yuer.util.WXTokenUtil;

/**
 * 发送客服消息
 * 
 * @author THINK
 *
 */
public class KFMsgProxy {

	private static final String TOKEN = WXTokenUtil.getAccessToken();
	private static final String FM_MSG_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
			+ TOKEN;

	/**
	 * 谁需要发送消息，谁自己传入参数进来
	 * 
	 * @param requestBody
	 * @return
	 */
	public static String sendKfMsg(String requestBody) {

		// 1.底层方法的支持
		String post = WeixinNet.post(FM_MSG_SEND_URL, requestBody);

		System.out.println("---->kf...msg" + post);

		return post;
	}

}
