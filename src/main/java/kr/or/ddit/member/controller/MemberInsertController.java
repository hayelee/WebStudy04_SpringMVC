package kr.or.ddit.member.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

/**
 *  Backend controller(command handler) --> Plain Old Java Object
 */
@Slf4j
@Controller
@RequestMapping("/member/memberInsert.do") // 이 요청만 내가 제한할거야
public class MemberInsertController {
//	서비스와의 의존관계 코드
	@Inject
	private MemberService service;
	
	@ModelAttribute("member")
	public MemberVO member() { //한사람의 정보가 등록될 때까지 MemberVO가 재사용됨
		log.info("@ModelAttribute 메소드 실행 -> member 속성 생성");
		return new MemberVO();
	}
	
//	@RequestMapping("/member/memberInsert.do")// 어떤 메소드를 받겠다는지 전혀 몰라
	@GetMapping // 정확히 get메소드만 받겠다
	public String memberForm(@ModelAttribute("member") MemberVO member) {
		return "member/MemberForm";
		
	}
	
//	@RequestMapping(value="/member/memberInsert.do", method=RequestMethod.POST) // 이 주소로 받은 것 중에 post요청반 받을 수 있다!
	@PostMapping // 동일요청의 post요청만
	public String memberInsert(
		HttpServletRequest req
		, @Validated(InsertGroup.class) @ModelAttribute("member") MemberVO member
		, Errors errors
	) throws ServletException, IOException {
			
		// 여기서 검증~
		
		boolean valid = !errors.hasErrors();
		
		String viewName = null;
		
		if(valid) {
			ServiceResult result = service.createMember(member);
			switch (result) {
			case PKDUPLICATED:
				// 아이디 중복됐으니 다시 입력해라~
				req.setAttribute("message", "아이디 중복");
				viewName = "member/MemberForm";
				break;
			case FAIL:
				// 다시 가입버튼을 누를 수 있는 곳으로 돌아가라~
				req.setAttribute("message", "서버에 문제 있음. 쫌따 다시 하셈.");
				viewName = "member/MemberForm";
				break;
				
			default:
				// 성공했으니 돌아가라~
				viewName = "redirect:/";
				break;
			}
		}else {
			viewName = "member/MemberForm";
		}
		return viewName;
	}
}
