package kr.or.connect.guestbook.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.guestbook.dao.GuestbookDao;
import kr.or.connect.guestbook.dao.LogDao;
import kr.or.connect.guestbook.dto.Guestbook;
import kr.or.connect.guestbook.dto.Log;
import kr.or.connect.guestbook.service.GuestbookService;

@Service
public class GuestbookServiceImpl implements GuestbookService{
	@Autowired //자동으로 bean 등록. 선언만 하면 알아서 생성 및 주입 해 줌.
	GuestbookDao guestbookDao; 
	
	@Autowired
	LogDao logDao;

	@Override
	@Transactional //트랜잭션 처리. 읽기만하는 메소드-> 트랜잭션 처리 시, 내부적으로 readOnly라는 형태로 connection을 사용.
	public List<Guestbook> getGuestbooks(Integer start) {
		List<Guestbook> list = guestbookDao.selectAll(start, GuestbookService.LIMIT);
		return list;
	}

	@Override
	@Transactional(readOnly=false) 
	public int deleteGuestbook(Long id, String ip) {
		int deleteCount = guestbookDao.deleteById(id);
		Log log = new Log(); //삭제 시 log 기록 남기기
		log.setIp(ip);
		log.setMethod("delete");
		log.setRegdate(new Date());
		logDao.insert(log);
		return deleteCount;
	}

	@Override
	@Transactional(readOnly=false)
	public Guestbook addGuestbook(Guestbook guestbook, String ip) {
		guestbook.setRegdate(new Date()); //컨트롤러 단에서 전달 받은 guestbook 객체. date 정보는 넘어오지 않아 추가 필요.
		Long id = guestbookDao.insert(guestbook);
		guestbook.setId(id); //id는 입력 시 자동으로 만들어짐. 로그 정보를 남기기 위해 추가 저장.
		
//		if(1 == 1)
//			throw new RuntimeException("test exception");
//		@Transactional 어노테이션에 의해 트랜잭션으로 해당 메소드를 처리하고 있음. 
//		따라서 guestbook.insert()가 제대로 수행된 후 발생한 exception에 의해 log insert 명령 시 오류가 발생한다면, guestbook도 테이블에 입력되지 않음. 
		
		Log log = new Log(); //삽입 시 log 기록 남기기
		log.setIp(ip);
		log.setMethod("insert");
		log.setRegdate(new Date());
		logDao.insert(log);
		
		
		return guestbook;
	}

	@Override
	public int getCount() {
		return guestbookDao.selectCount();
	}
	
	
}