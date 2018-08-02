package com.yuer.service;
import java.util.List;
import com.yuer.bean.UserInfo;
public interface UserService {

	/**
	 * 所有的用户详情
	 * 
	 * @param nextOpenid:从指定位置开始查询
	 * @return
	 */
	List<UserInfo> getUserInfoList(String nextOpenid);

}
