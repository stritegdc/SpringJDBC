package kr.or.connect.daoexam.dao;

import java.util.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.daoexam.dto.Role;

import static kr.or.connect.daoexam.dao.RoleDaoSqls.*; //static import를 통해 class 이름을 쓰지않고 변수를 바로 가져와 사용할 수 있음.

import java.util.Collections;
import javax.sql.DataSource;

@Repository
public class RoleDao {
	private NamedParameterJdbcTemplate jdbc; //이름을 이용해서 바인딩하거나 결과값을 가져올 때 사용할 수 있음.
	private RowMapper<Role> rowMapper = BeanPropertyRowMapper.newInstance(Role.class);
	private SimpleJdbcInsert insertAction;
	//BeanPropertyRowMapper는 dbms와 java의 이름 규칙을 맞춰줌. (dbms는 _이용 단어  연결, java는 대소문자 구분으로 단어 연결)
	
	public RoleDao(DataSource dataSource){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource).withTableName("role");
	}
	
	public List<Role> selectAll(){
		return jdbc.query(SELECT_ALL, Collections.emptyMap(), rowMapper);
		//rowMapper: select 한 건 한 건의 결과를 dto에 저장.
		//query 메소드는 결과가 여러 건이었을 때, 내부적으로 반복하면서 dto를 생성, 생성한 dto를 list에 담아주고 있음. 해당 list 반환.
	}
	
	public int insert(Role role) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(role);
		return insertAction.execute(params);
	}
	
	public int update(Role role) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(role);
		//role을 맵객체로 바꿔줌. 
		return jdbc.update(UPDATE, params);
	}
	
	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("roleId", id); 
		//값을 한 건만 넣을 때 굳이 객체를 생성하지 않고 하기 위해 사용하기 좋음.
		return jdbc.update(DELETE_BY_ROLE_ID, params);
		
	}
	
	public Role selectById(Integer id) {
		try {
			Map<String, ?> params = Collections.singletonMap("roleId", id);
			return jdbc.queryForObject(SELECT_BY_ROLE_ID, params, rowMapper);
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
}
