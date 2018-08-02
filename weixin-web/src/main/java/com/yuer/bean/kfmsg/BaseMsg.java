package com.yuer.bean.kfmsg;

public class BaseMsg {

	private String touser; //openId 接收消息的人
	
	private String msgtype; //消息的类型  text,image

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	@Override
	public String toString() {
		return "BaseMsg [touser=" + touser + ", msgtype=" + msgtype + "]";
	}
	
	
	
	
	
}
