package com.flexink.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class FileSampleController {

	@RequestMapping("/file")
	public String fileView() {
		return "/sample/file";
	}
}
