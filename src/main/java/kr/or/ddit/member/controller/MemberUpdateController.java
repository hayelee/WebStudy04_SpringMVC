package kr.or.ddit.member.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.MemberVOWrapper;
import kr.or.ddit.vo.ProdVO;
import lombok.RequiredArgsConstructor;


//@RequestMapping("/member/memberUpdate.do")

@RequiredArgsConstructor
@Controller
@RequestMapping("/member/memberUpdate.do")
public class MemberUpdateController {
//	서비스와의 의존관계 코드

	private final MemberService service;
	
	@GetMapping
	public String updateForm(
			@SessionAttribute("authMember") MemberVO authMember // 나중에 resolver만들어보기~ // 이 이름의 속성이 없어도 새로 만들지 않고 400에러 발생시켜
			, Model model
	) {
		
		// 상세조회
		MemberVO member = service.retrieveMember(authMember.getMemId());
		
		// 모델로 공유
		model.addAttribute("member", member);
		
		return "member/MemberForm";
		
	}
	
	@PostMapping
	public String updateProcess(
			@Validated(UpdateGroup.class) @ModelAttribute("member") MemberVO member //필요한 파라미터 다 넣어줌! 현재 공유된 속성중에 이 이름이 있는 객체가 있으면 가져오고, 없으면 만들어줘
//			, @RequestPart(value="memImage", required=false) MultipartFile memImage
			, BindingResult errors
//			, @Valid ProdVO prod
//			, Errors errors
			, HttpSession session
			, Model model
		) throws IOException{
		
		String viewName = null;
		
		if(!errors.hasErrors()) {
			ServiceResult result = service.modifyMember(member);
			switch (result) {
			case INVALIDPASSWORD:
				// 비번 오류~
				model.addAttribute("message", "비밀번호 오류");
				viewName = "member/MemberForm";	
				break;
			case FAIL:
				// 서버 문제 발생~
				model.addAttribute("message", "서버 오류~ 쫌따 다시~");
				viewName = "member/MemberForm";	
				break;
			default:
				// 성공적으로 수정 완료~
				MemberVO modifiedMember = service.retrieveMember(member.getMemId());
				session.setAttribute("authMember", modifiedMember);
				viewName = "redirect:/mypage.do";
				break;
			}
		}else {
			viewName = "member/MemberForm";	
		}
		
		return viewName;
	}
}
