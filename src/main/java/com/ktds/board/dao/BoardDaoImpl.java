package com.ktds.board.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ktds.board.vo.BoardVO;
import com.ktds.common.dao.support.BindData;
import com.ktds.member.vo.MemberVO;

@Repository
public class BoardDaoImpl implements BoardDao{

	public interface Query {
		int INSERT_BOARD_QUERY = 0;
		int SELECT_ONE_BOARD_QUERY = 1;
		int SELECT_ALL_BOARD_QUERY = 2;
		int DELETE_ONE_BOARD_QUERY = 3;
		int UPDATE_ONE_BOARD_QUERY = 4;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="boardQueries")
	private List<String> boardQueries;
	
	
	@Override
	public int insertOneBoard(BoardVO boardVO) {
		return this.jdbcTemplate.update(
				this.boardQueries.get(Query.INSERT_BOARD_QUERY)
				, boardVO.getSubject()
				, boardVO.getContent()
				, boardVO.getEmail()
				, boardVO.getFileName()
				, boardVO.getOriginFileName()
			);
	}
	
	@Override
	public List<BoardVO> selectAllBoard() {
		// TODO Auto-generated method stub
		return this.jdbcTemplate.query(
				this.boardQueries.get(Query.SELECT_ALL_BOARD_QUERY)
				, new RowMapper<BoardVO>() {

					@Override
					public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						MemberVO memberVO = BindData.bindData(rs, new MemberVO());
						BoardVO boardVO = BindData.bindData(rs, new BoardVO());
						boardVO.setMemberVO(memberVO);
						
						return boardVO;
					}
				}
			);
	}

	@Override
	public BoardVO selectOneBoard(int id) {
		return this.jdbcTemplate.queryForObject(
				this.boardQueries.get(Query.SELECT_ONE_BOARD_QUERY)
				, new Object[] {id}
				, new RowMapper<BoardVO>() {

					@Override
					public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						MemberVO memberVO = BindData.bindData(rs, new MemberVO());
						BoardVO boardVO = BindData.bindData(rs, new BoardVO());
						boardVO.setMemberVO(memberVO);
						
						return boardVO;
					}
				}
		);
				
	}

	@Override
	public int deleteOneBoard(int id) {
		return this.jdbcTemplate.update(
				this.boardQueries.get(Query.DELETE_ONE_BOARD_QUERY)
				, id);
	}

	@Override
	public int updateOneBoard(BoardVO boardVO) {
		// TODO Auto-generated method stub
		return 0;
	}

}
