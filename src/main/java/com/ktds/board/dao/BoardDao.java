package com.ktds.board.dao;

import java.util.List;

import com.ktds.board.vo.BoardVO;

public interface BoardDao {

	
	public int insertOneBoard(BoardVO boardVO);
	
	public List<BoardVO> selectAllBoard();
	public BoardVO selectOneBoard(int id);
	
	public int deleteOneBoard(int id);
	
	public int updateOneBoard(BoardVO boardVO);

}
