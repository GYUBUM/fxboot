package com.flexink.sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flexink.domain.code.CommonCode;
import com.flexink.sample.service.PagingSampleService;
import com.flexink.vo.ParamsVo;

@Controller
@RequestMapping("/sample")
public class PagingSampleController {

	@Autowired
	PagingSampleService pagingSampleService;
	
	@GetMapping("/paging")
	public String pagingSampleView(ParamsVo vo, Model model) {
		
		vo.put("pageSize", "5");
		
		Page<CommonCode> list = pagingSampleService.get(vo);
		
		model.addAttribute("data", list);
		model.addAttribute("filter", vo.getString("filter"));
		
		return "/sample/paging";
	}
}
