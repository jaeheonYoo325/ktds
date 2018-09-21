package com.ktds.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktds.board.dao.BoardDao;
import com.ktds.board.vo.BoardVO;
import com.ktds.common.exceptions.PolicyViolationException;
import com.ktds.member.dao.MemberDao;
import com.ktds.member.vo.MemberVO;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public boolean createOneBoard(BoardVO boardVO, MemberVO memberVO) {
		
		boolean isUploadFile = boardVO.getOriginFileName() != null;
		
		int point = 10;
		if ( isUploadFile ) {
			point += 10;
		}
		
		this.memberDao.updatePoint(memberVO.getEmail(), point);
		
		int memberPoint = memberVO.getPoint();
		memberPoint += 10;
		memberVO.setPoint(memberPoint);
		
		System.out.println("ServiceImple : " + memberVO.getPoint());
		
		return this.boardDao.insertOneBoard(boardVO) > 0;
	}

	@Override
	public List<BoardVO> readAllBoard() {
		return this.boardDao.selectAllBoard();
	}

	@Override
	public BoardVO readOneBoard(int id) {
		return this.boardDao.selectOneBoard(id);
	}
	
	@Override
	public BoardVO readOneBoard(int id, MemberVO memberVO) {
		
		BoardVO boardVO = this.readOneBoard(id);
		
		if ( !boardVO.getEmail().equals( memberVO.getEmail() )) {
			
			if ( memberVO.getPoint() < 2 ) {
				throw new PolicyViolationException("포인트가 부족합니다.", "/board/list");
			}
			
			this.memberDao.updatePoint(memberVO.getEmail(), -2);
			
			int point = memberVO.getPoint();
			point -= 2;
			memberVO.setPoint(point);
		}
		return boardVO;
	}

	@Override
	public boolean deleteOneBoard(int id) {
		return this.boardDao.deleteOneBoard(id) > 0;
	}

	@Override
	public boolean updateOneBoard(BoardVO boardVO) {
		// TODO Auto-generated method stub
		return false;
	}

}
