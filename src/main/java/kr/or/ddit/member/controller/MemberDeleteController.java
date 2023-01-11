package kr.or.ddit.member.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.vo.MemberVO;

@Controller
@RequestMapping("/member/memberDelete.do")
public class MemberDeleteController {
	@Inject
	private MemberService service;
	
	@PostMapping
	public String memberDelete(
			@RequestParam(value="memPass", required=true) String memPass
			, @SessionAttribute(value="authMember", required=true) MemberVO authMember
			, HttpSession session
			, RedirectAttributes redirectAttributes
			) {
//		1.
		String memId = authMember.getMemId();
		
		MemberVO inputData = new MemberVO();
		inputData.setMemId(memId);
		inputData.setMemPass(memPass);
		
		
		String viewName = null;
		
		ServiceResult result = service.removeMember(inputData);
		switch (result) {
		case INVALIDPASSWORD:
			redirectAttributes.addFlashAttribute("message", "비번 오류");
			viewName = "redirect:/mypage.do";
			break;
		case FAIL:
			redirectAttributes.addFlashAttribute("message", "서버 오류");
			viewName = "redirect:/mypage.do";
			break;
		default:
			// 세션을 invalidate시키고 로그인폼으로 돌아가라~
			session.invalidate();
			viewName = "redirect:/";
			break;
		}
		
		return viewName;
	}
}
