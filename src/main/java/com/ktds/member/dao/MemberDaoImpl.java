package com.ktds.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ktds.common.dao.support.BindData;
import com.ktds.member.vo.MemberVO;

@Repository
public class MemberDaoImpl implements MemberDao{

	public interface Query {
		int INSERT_MEMBER_QUERY = 0;
		int SELECT_ONE_MEMBER_QUERY = 1;
		int POINT_UPDATE_QUERY = 2;
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="memberQueries")
	private List<String> memberQueries;
	
	@Override
	public int insertOneMember(MemberVO memberVO) {
		return this.jdbcTemplate.update(
				this.memberQueries.get(Query.INSERT_MEMBER_QUERY)
				, memberVO.getEmail()
				, memberVO.getName()
				, memberVO.getPassword()
			);
	}

	@Override
	public MemberVO selectOneMember(MemberVO memberVO) {
		return this.jdbcTemplate.queryForObject(
				this.memberQueries.get(Query.SELECT_ONE_MEMBER_QUERY)
				, new Object[] {memberVO.getEmail(), memberVO.getPassword()}
				, new RowMapper<MemberVO>() {

					@Override
					public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						return BindData.bindData(rs, new MemberVO());
					}
				}
			);
	}

	@Override
	public int updatePoint(String email, int point) {
		return this.jdbcTemplate.update(
				this.memberQueries.get(Query.POINT_UPDATE_QUERY)
				, point
				, email
			);
	}

}
