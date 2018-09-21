package com.ktds.board.service;

import java.util.List;

import com.ktds.board.vo.BoardVO;
import com.ktds.member.vo.MemberVO;

public interface BoardService {

	public boolean createOneBoard(BoardVO boardVO, MemberVO memberVO);
	
	public List<BoardVO> readAllBoard();
	public BoardVO readOneBoard(int id);
	public BoardVO readOneBoard(int id, MemberVO memberVO);
	
	public boolean deleteOneBoard(int id);
	
	public boolean updateOneBoard(BoardVO boardVO);
}
