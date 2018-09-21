package com.ktds.member.dao;

import com.ktds.member.vo.MemberVO;

public interface MemberDao {

	public int insertOneMember(MemberVO memberVO);
	
	public MemberVO selectOneMember(MemberVO memberVO);
	
	public int updatePoint(String email, int point);
	
}
