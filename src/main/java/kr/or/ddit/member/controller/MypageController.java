package kr.or.ddit.member.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.security.AuthMember;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;

@Controller
public class MypageController {
	@RequestMapping("/mypage")
	public String process(
		Authentication authentication
		, @AuthenticationPrincipal MemberVOWrapper principal //5.받아야하는 어노테이션
		, @AuthenticationPrincipal(expression="realMember") MemberVO member 
		, Model model
	) {
//		MemberVOWrapper principal = (MemberVOWrapper) req.getUserPrincipal();
//		MemberVO authMember = principal.getRealMember();
//		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		model.addAttribute("member", member);
		return "member/mypage"; // logical view name

	}
}
