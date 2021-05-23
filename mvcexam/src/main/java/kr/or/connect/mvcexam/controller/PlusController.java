package kr.or.connect.mvcexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PlusController {
	@GetMapping(path="/plusform")
	public String plusform() {
		return "plusForm"; //요청이 들어오면 해당 view name에 맞는 view를 보여줘라.
	}
	
	@PostMapping(path="/plus")
	public String plus(@RequestParam(name="value1", required=true) int value1, 
			@RequestParam(name="value2", required=true) int value2, 
			ModelMap modelMap) {
		
		int result = value1 + value2;
		
		modelMap.addAttribute("value1", value1);
		modelMap.addAttribute("value2", value2);
		modelMap.addAttribute("result", result); //spring이 request scope에 매핑시켜줌. HttpServletRequest를 인자로 넣어줘서 setAttribute를 할 수도 있으나 종속성이 강화되기 때문에 지양.
		return "plusResult";
	}
}
