package kr.or.connect.daoexam.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import kr.or.connect.daoexam.config.ApplicationConfig;
import kr.or.connect.daoexam.dto.Role;
import kr.or.connect.daoexam.dao.RoleDao;

public class JDBCTest {
	public static void main(String[] args) {
		ApplicationContext ac = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		
		RoleDao roleDao = ac.getBean(RoleDao.class);
		
		Role role = new Role();
		role.setRoleId(200);
		role.setDescription("PROGRAMMER");

	//	int count = roleDao.insert(role);
	//	System.out.println(count+ "건 입력했습니다.");
	
		int updateCount = roleDao.update(role);
		System.out.println(updateCount + "건 수정했습니다.");
		
		Role resultRole = roleDao.selectById(200);
		System.out.println(resultRole);
		
		int deleteCount = roleDao.deleteById(500);
		System.out.println(deleteCount + "건 삭제하였습니다.");
	}

}
