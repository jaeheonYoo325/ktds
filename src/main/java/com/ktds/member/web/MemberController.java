package com.ktds.member.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member/regist")
	public String viewRegistMemberPage() {
		return "member/regist";
	}
	
	@PostMapping("/member/regist")
	public String doMemberRegistAction(@ModelAttribute MemberVO memberVO) {
		
		return this.memberService.registOneMember(memberVO) ? 
				"redirect:/member/login" : "redirect:/member/regist";
	}
	
	@GetMapping("/member/login")
	public String viewloginMemberPage() {
		return "member/login";
	}
	
	@PostMapping("/member/login")
	public String doMemberLoginAction(
			@ModelAttribute MemberVO memberVO
			, HttpSession session) {
		
		MemberVO loginMember = this.memberService.readOneMember(memberVO);
		session.setAttribute("_USER_", loginMember);
		return "redirect:/board/list";
	}
}
