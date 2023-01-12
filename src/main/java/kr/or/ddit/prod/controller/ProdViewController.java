package kr.or.ddit.prod.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.prod.service.ProdServiceImpl;
import kr.or.ddit.vo.ProdVO;


/**
 * 상품 상세 조회시, 해당 거래처의 모든 정보 함께 조회함.
 * 상품 상세 조회시, 구매자 리스트(회원아이디, 회원명, 휴대폰, 이메일, 마일리지) 함께 조회.
 * 분류명도 함께 조회함.
 */
@Controller
public class ProdViewController extends HttpServlet {
	@Inject
	 private ProdService service;
	 
	 @RequestMapping("/prod/{prodId}") //파라미터가 아니라 경로변수로 변경(식별자를 노출시키지 않는 장점이 있음)
		public String prodView(
			@PathVariable String prodId
			, Model model
		){
	      ProdVO prod = service.retrieveProd(prodId);
	      
	      model.addAttribute("prod", prod);
	      
	      return "prod/prodView"; // logical view name
	      
	   }
}
