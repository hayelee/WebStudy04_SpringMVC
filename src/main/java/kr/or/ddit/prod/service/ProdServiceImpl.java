package kr.or.ddit.prod.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import kr.or.ddit.enumpkg.ServiceResult;
import kr.or.ddit.prod.dao.ProdDAO;
import kr.or.ddit.vo.PagingVO;
import kr.or.ddit.vo.ProdVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProdServiceImpl implements ProdService {
	
	private final ProdDAO prodDAO;
	
	@Inject
	private WebApplicationContext context;
	private File saveFolder;
	   
    @PostConstruct
    public void init() throws IOException {

		String saveFolderURL = "/resources/prodImages";
//		ServletContext application = req.getServletContext(); // application 기본 객체가 들어감!
//		String saveFolderPath = application.getRealPath(saveFolderURL);
		Resource saveFolderRes = context.getResource(saveFolderURL);
		saveFolder = saveFolderRes.getFile();
		if(!saveFolder.exists()) // savefolder가 없으면~
				saveFolder.mkdirs(); //mkdirs로 해야 계층구조로 쫙 만들어줘!
    }
	
    private void processProdImage(ProdVO prod) {
//		1. 저장
    	try {
    		if(1==1) throw new RuntimeException("트랜잭션 관리 여부 확인을 위한 강제 발생 예외");
			prod.saveTo(saveFolder);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
	@Override
	public ProdVO retrieveProd(String prodId) {
		ProdVO prod = prodDAO.selectProd(prodId);

		if(prod==null)
			throw new RuntimeException(String.format("%s는 없는 상품.", prodId));
		return prod;
	}

	@Override
	public List<ProdVO> retrieveProdList(PagingVO<ProdVO> pagingVO) {
		int totalRecord = prodDAO.selectTotalRecord(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<ProdVO> prodList = prodDAO.selectProdList(pagingVO);
		pagingVO.setDataList(prodList);
		return prodList;
	}

	@Inject
	private SqlSessionFactory SqlSessionFactory;
	@Override
	public ServiceResult createProd(ProdVO prod) {
//		session open
		try(
			SqlSession sqlSession = SqlSessionFactory.openSession(); //트랜잭션 시작
		){
			//트랜젝션 관리, 트랜젝션의 원자성
			int rowcnt = prodDAO.insertProd(prod, sqlSession); //이진데이터
			
			processProdImage(prod); //이미지 저장 
			sqlSession.commit();
			
			return rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
		}
	}

	@Override
	public ServiceResult modifyProd(ProdVO prod) {
		retrieveProd(prod.getProdId());
		
		int rowcnt = prodDAO.updateProd(prod);
		processProdImage(prod);
		
		return rowcnt > 0 ? ServiceResult.OK : ServiceResult.FAIL;
	}
}
