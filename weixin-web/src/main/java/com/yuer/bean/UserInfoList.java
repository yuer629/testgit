package com.yuer.bean;

import java.util.List;

public class UserInfoList {

	private List<UserInfo> user_info_list;

	public List<UserInfo> getUser_info_list() {
		return user_info_list;
	}

	public void setUser_info_list(List<UserInfo> user_info_list) {
		this.user_info_list = user_info_list;
	}

	@Override
	public String toString() {
		return "UserInfoList [user_info_list=" + user_info_list + "]";
	}

}
