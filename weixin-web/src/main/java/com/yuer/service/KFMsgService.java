package com.yuer.service;

import com.yuer.bean.RecMessage;

public interface KFMsgService {

	String sendKfTextMsg(String openId, String content);

	/**
	 * 发送图文，测试
	 * 
	 * @param openId
	 * @return
	 */
	String sendKfNewsMsg(String openId);

	String autoSendTextMsg(RecMessage message);

	String autoSendErrorTextMsg(RecMessage message);

}
