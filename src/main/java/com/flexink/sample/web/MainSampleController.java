package com.flexink.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flexink.vo.ParamsVo;

@Controller
@RequestMapping("/sample")
public class MainSampleController {

	@GetMapping()
	public String pagingSampleView(ParamsVo vo) {
		
		return "/sample/main";
	}
}
