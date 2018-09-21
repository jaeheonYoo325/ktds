package com.ktds.member.service;

import com.ktds.member.vo.MemberVO;

public interface MemberService {

	public boolean registOneMember(MemberVO memberVO);
	
	public MemberVO readOneMember(MemberVO memberVO);
}
