package kr.or.ddit.member.service;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.or.ddit.AbstractTestCase;
import kr.or.ddit.member.dao.MemberDAO;
import lombok.extern.slf4j.Slf4j;

// Mock 모조request를 만들어서 테스트 하려는 것

@Slf4j
public class MemberServiceImplTest extends AbstractTestCase {

	@Inject
	private MemberService memberService;

	@Test
	public void testCreateMember() {
		log.info("주입된 객체 : {}" + memberService);
	}

	@Test
	public void testRetrieveMemberList() {
		log.info("주입된 객체 : {}", memberService);
	}

	@Test
	public void testRetrieveMember() {
		log.info("주입된 객체 : {}", memberService);
	}

	@Test
	public void testModifyMember() {
		log.info("주입된 객체 : {}", memberService);
	}

	@Test
	public void testRemoveMember() {
		log.info("주입된 객체 : {}", memberService);
	}

}
