package com.appdespesas.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

	@RequestMapping("/front")
	public String loginFront() {
		
		return "/front/index";
	}
}
