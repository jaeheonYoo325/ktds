package com.ktds.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Override
	public boolean registOneMember(MemberVO memberVO) {
		return this.memberDao.insertOneMember(memberVO) > 0;
	}

	@Override
	public MemberVO readOneMember(MemberVO memberVO) {
		
		MemberVO loginMemberVO = this.memberDao.selectOneMember(memberVO);
		
		if ( loginMemberVO != null ) {
			memberDao.updatePoint(memberVO.getEmail(), +2);
			int point = loginMemberVO.getPoint();
			point += 2;
			loginMemberVO.setPoint(point);
		}
		return loginMemberVO;
	}

}
