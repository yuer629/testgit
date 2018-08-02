package com.yuer.bean.kfmsg.text;

import com.yuer.bean.kfmsg.BaseMsg;

public class TextMsg extends BaseMsg {

	private TextMsgItem text; // 发送文件的具体的内容对象

	public TextMsgItem getText() {
		return text;
	}

	public void setText(TextMsgItem text) {
		this.text = text;
	}

}
