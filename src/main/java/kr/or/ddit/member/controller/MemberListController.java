package kr.or.ddit.member.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.member.service.MemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.SearchVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor //inject가 없어도 있는 것처럼 동작
@Controller
public class MemberListController { // 완전한 POJO가 되었어요~

	private final MemberService service;

	@RequestMapping("/member/memberList.do") // single annotation 메소드가 get으로 숨어있어요~
	public ModelAndView memberList( // 개발자의 자유도가 높아짐~~
		@RequestParam(value="page", required=false, defaultValue="1") int currentPage
		, @ModelAttribute SearchVO simpleCondition // CoC

	){
		
		PagingVO<MemberVO> pagingVO = new PagingVO<>(4,2);
		pagingVO.setCurrentPage(currentPage);// 첫번째 setter 호출
		pagingVO.setSimpleCondition(simpleCondition);
		
		ModelAndView mav = new ModelAndView();
		
		List<MemberVO> memberList = service.retrieveMemberList(pagingVO); // 받아올 필요 없어! service에서 다 받아놨으니까!
		mav.addObject("pagingVO", pagingVO);
		
		log.info("paging data : {}", pagingVO);
		
		mav.setViewName("member/memberList");
		
		return mav;
	}
}
