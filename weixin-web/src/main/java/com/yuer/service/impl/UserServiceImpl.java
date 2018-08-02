package com.yuer.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yuer.bean.UserInfo;
import com.yuer.bean.UserInfoList;
import com.yuer.service.UserService;
import com.yuer.user.UserProxy;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public List<UserInfo> getUserInfoList(String nextOpenid) {

		// 1.获取所有的openid
		String openIds = UserProxy.getUserList(nextOpenid);
		// {"total":2,"count":2,"data":{"openid":["OPENID1","OPENID2"]},"next_openid":"NEXT_OPENID"}

		// 2.封装请求参数
		List<Object> jsonArray = null;
		if (openIds != null) {
			JSONObject parseObject = JSONObject.parseObject(openIds);

			// a.获取total总数

			// b.获取count 当前获取的数量

			// c.data 对应的id集合
			if (parseObject.containsKey("data")) {
				jsonArray = parseObject.getJSONObject("data").getJSONArray("openid");

			}
		}

		String requestParam = createRequestParam(jsonArray);

		// 3.获取当前openId对应的详细信息
		String userListInfo = UserProxy.getUserListInfo(requestParam);

		// 4.封装成实体对象，返回结果

		UserInfoList infoList = JSONObject.toJavaObject(JSONObject.parseObject(userListInfo), UserInfoList.class);

		return infoList.getUser_info_list();
	}

	/**
	 * 格式：
	 * 
	 * @param openids
	 * @return
	 */
	public String createRequestParam(List<Object> openids) {

		// 格式
		// {
		// "user_list": [
		// {
		// "openid": "otvxTs4dckWG7imySrJd6jSi0CWE",
		// "lang": "zh_CN"
		// },
		// {
		// "openid": "otvxTs_JZ6SEiP0imdhpi50fuSZg",
		// "lang": "zh_CN"
		// }
		// ]
		// }
		//

		// 封装对象 存放参数
		HashMap<String, List<Map<String, String>>> map = new HashMap<>();

		List<Map<String, String>> openList = new ArrayList<>();

		// 根据传入的参数 封装
		for (Object openid : openids) {

			Map<String, String> openMap = new HashMap<>();
			openMap.put("openid", (String) openid);
			openMap.put("lang", "zh_CN");
			openList.add(openMap);
		}

		map.put("user_list", openList);

		// 转为字符串

		return JSONObject.toJSONString(map);
	}

}
