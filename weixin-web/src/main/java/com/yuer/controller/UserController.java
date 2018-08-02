package com.yuer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuer.account.AccountProxy;
import com.yuer.bean.UserInfo;
import com.yuer.service.UserService;
import com.yuer.user.UserProxy;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/get")
	@ResponseBody
	public Object getUserList() {

		String list = UserProxy.getUserList("oJ1R0w-F0XykTbSKwrZBVLsB4rw0");

		return list;
	}

	@RequestMapping("/get/list")
	public String getUserInfoList(Model model) {

		// 后去二维码
		String showqrcode = AccountProxy.createShowqrcode();

		List<UserInfo> userInfoList = userService.getUserInfoList(null);

		model.addAttribute("userInfoList", userInfoList);
		model.addAttribute("img_url", showqrcode);
		return "/Main.jsp";
	}

}
