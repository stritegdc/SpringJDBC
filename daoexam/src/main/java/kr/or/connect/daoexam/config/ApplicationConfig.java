package kr.or.connect.daoexam.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DBConfig.class}) //여러 설정 파일을 나누어서 관리
@ComponentScan(basePackages = {"kr.or.connect.daoexam.dao"})
public class ApplicationConfig {
	
}
