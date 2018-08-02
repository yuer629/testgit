package com.yuer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuer.service.KFMsgService;

/**
 * 主动发送消息 从公众号---->用户列表（openId）
 * 
 * @author THINK
 *
 */
@Controller
@RequestMapping("/kfmsg")
public class MsgController {

	@Autowired
	private KFMsgService kFMsgService;

	// 1.service处理发送的具体业务

	// 2.weixin-proxy:获取微信服务数据:(是否需要参数;url地址;返回值是什么)
	// https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547
	// 客服接口-发消息

	// 3.weixin-net，作为底层的支持

	@RequestMapping("/send/text")
	@ResponseBody
	public String sendKfMsg(String openId, String content) {
		String msg = kFMsgService.sendKfTextMsg(openId, content);
		return msg;
	}

	@RequestMapping("/send/news")
	@ResponseBody
	public String sendKfMsg(String openId) {
		String msg = kFMsgService.sendKfNewsMsg(openId);
		return msg;
	}

}
