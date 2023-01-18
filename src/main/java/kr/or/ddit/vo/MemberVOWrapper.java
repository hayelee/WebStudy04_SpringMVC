package kr.or.ddit.vo;

import java.security.Principal;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class MemberVOWrapper extends User { //3.implements 바꿔
	private MemberVO realMember;

	public MemberVOWrapper(MemberVO realMember) { // 이 생성자가 만들어지는 순간 기본생성자가 사라짐(어댑터는 기본생성자X)
		super(realMember.getMemId(), realMember.getMemPass(), 
				AuthorityUtils.createAuthorityList(realMember.getMemRole()));
		this.realMember = realMember;
	}
	
	public MemberVO getRealMember() {
		return realMember;
	}
	
	@Override
	public boolean isEnabled() {
		return !realMember.isMemDelete();
	}
}
