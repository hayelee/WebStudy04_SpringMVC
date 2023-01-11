package kr.or.ddit.login.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {
	// a태그를 사용했으니까 doGet(아무리 용을 써도 post 사용 못해 form을 사용해야 post 사용 가능)
	// handler adapter를 통해 필요한 거 받아오면 돼~!
	@PostMapping("/login/logout.do")
	public String process(HttpSession session) {
		// 세션속성 지워줘야해~~
//		session.removeAttribute("authMember");
		session.invalidate();
		
		return "redirect:/";
	}
} 
