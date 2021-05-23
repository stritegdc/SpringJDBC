package kr.or.connect.mvcexam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//DispatcherSerlvet이 실행될 때 읽어들이는 설정파일

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "kr.or.connect.mvcexam.controller" })
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
	/*DispatcherServlet은 모든 요청을 받음(url-pattern이 /로 설정됨으로써) 
	--> css, img 등의 파일에 대한 요청까지 controller가 가진 requestMapping에서 찾으려고 하면서 오류 발생.
	--> 루트 디렉토리 아래에 있는 각각의 디렉토리를 만들어 놓고 알맞게 사용하도록 함.
	*/
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
		registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
	    registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
	    registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	    //       다음과 같이 들어온 요청은                            다음의 디렉토리에서 찾아라!라는 의미.
	}
	
	//DefaultServletHandler를 사용하도록 해줌. 
	public void configurationDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	//특정 url에 대한 처리를 컨트롤러 클래스를 작성하지 않고 매핑할 수 있게 해줌.
	public void addViewControllers(final ViewControllerRegistry registry) {
		System.out.println("addViewControllers가 호출됩니다.");
		registry.addViewController("/").setViewName("main"); //요청이 "/"로 들어오면 main이라는 이름의 view를 보여줌. view name은 view resolver 객체를 이용해서 찾음.
	}
	
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		//view resolver 객체를 이용해서 WEB-INF/views/main.jsp 파일(view)을 찾아주는 것. 
		return resolver;
	}
}
