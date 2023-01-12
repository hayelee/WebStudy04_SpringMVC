package kr.or.ddit.prod.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.prod.dao.OthersDAO;
import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.BuyerVO;
import kr.or.ddit.vo.ProdVO;

@Controller
@RequestMapping("/prod/prodInsert.do")
public class ProdInsertController {
	
	@Inject
	private ProdService service;
    @Inject
	private OthersDAO othersDAO;

    
    @ModelAttribute("lprodList")
    public List<Map<String, Object>> buyerList() {
 	   return othersDAO.selectLprodList();
    }
    
    @ModelAttribute("prod")
    public ProdVO prod() {
    	return new ProdVO();
    }
    
    @ModelAttribute("buyerList")
    public List<BuyerVO> lprodList() {
 	   return othersDAO.selectBuyerList(null);
    }
    
	@GetMapping
	public String process(Model model) {
		return "prod/prodForm";
	}
	
	@PostMapping
	public String insertProcess(
		@Validated(InsertGroup.class) @ModelAttribute("prod") ProdVO prod //command Object 
		, Errors errors
		, Model model
	) throws IOException {
		

		
		//검증 
		String viewName = null;
		if(!errors.hasErrors()) {
			ServiceResult result = service.createProd(prod);
			if(ServiceResult.OK == result) {
				viewName = "redirect:/prod/"+prod.getProdId();
			}else {
				model.addAttribute("message", "서버 문제있음");
				viewName = "prod/prodForm";
			}
		}else {
			viewName = "prod/prodForm";
		}
		
		return viewName;
	}
}
