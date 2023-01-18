package kr.or.ddit.commons;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/index.do")
	public String process(HttpServletRequest req) {
		req.setAttribute("contentPage", "/WEB-INF/jsp/index.jsp");
		return "layout";
	}
}
