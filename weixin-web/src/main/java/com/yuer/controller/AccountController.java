package com.yuer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuer.account.AccountProxy;


@Controller
public class AccountController {

	/**
	 * 获取二维码图片信息
	 * 
	 * @return
	 */
	@RequestMapping("/getCodeImg")
	public String getCodeImg(Model model) {

		String img_url = AccountProxy.createShowqrcode();
		model.addAttribute("img_url", img_url);

		return "/Main.jsp";

	}

}
