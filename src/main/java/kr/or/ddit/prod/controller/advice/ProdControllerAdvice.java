package kr.or.ddit.prod.controller.advice;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.vo.BuyerVO;

@ControllerAdvice("kr.or.ddit.prod.controller") // 이 경로에 있는 컨트롤러 패키지에 얘를 위빙하겠다
public class ProdControllerAdvice {
	@Inject
	private OthersDAO othersDAO;
   
	@ModelAttribute("lprodList")
	public List<Map<String, Object>> buyerList() {
		return othersDAO.selectLprodList();
	}
   
	@ModelAttribute("buyerList")
	public List<BuyerVO> lprodList() {
		return othersDAO.selectBuyerList(null);
	}
}
