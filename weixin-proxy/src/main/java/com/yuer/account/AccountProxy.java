package com.yuer.account;

import javax.xml.ws.RequestWrapper;

import org.apache.http.impl.execchain.RetryExec;

import com.alibaba.fastjson.JSONObject;
import com.yuer.WeixinNet;
import com.yuer.util.WXTokenUtil;


/**
 * 代理模块 代理微信二维码生成
 * 
 * @author THINK
 *
 */
public class AccountProxy {

	private static String token = WXTokenUtil.getAccessToken();
	// 获取ticket url
	private static String ticket_url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + token;

	// 通过ticket获取二维码的url
	private static String cole_url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

	/**
	 * 
	 * @return 二维码的路径
	 */
	public static String createShowqrcode() {

		// 1.获取ticket
		String requestBody = "{\"expire_seconds\":604800,\"action_name\":\"QR_SCENE\",\"action_info\":{\"scene\":{\"scene_id\":123}}}"; // json
		String post = WeixinNet.post(ticket_url, requestBody);

		// post :
		// {"ticket":"gQH47joAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2taZ2Z3TVRtNzJXV1Brb3ZhYmJJAAIEZ23sUwMEmm
		// 3sUw==","expire_seconds":60,"url":"http://weixin.qq.com/q/kZgfwMTm72WWPkovabbI"}

		// 把json字符串转为json对象
		JSONObject jsonObject = JSONObject.parseObject(post);

		String ticket = null;

		// ticket:如果服务器响应正确，给定值的
		if (jsonObject.containsKey("ticket")) {
			ticket = (String) jsonObject.get("ticket");

			// 凭据获取成功，换取二维码
			return cole_url + ticket;
		}

		return null;
	}
	public static void main(String[] args) {
		String erweima = createShowqrcode();
		System.out.println(erweima);
	}

}
