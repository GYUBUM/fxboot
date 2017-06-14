package com.flexink.sample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flexink.sample.service.EhCacheSampleService;

@Controller
@RequestMapping("/sample/cache")
public class EhCacheSampleController {

	@Autowired
	EhCacheSampleService ehCacheSampleService;
	
	@GetMapping
	public String view() {
		return "/sample/cache";
	}
	
	/********************************************************************
	 * @메소드명	: getNoCache
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 캐쉬 없음
	 ********************************************************************/
	@GetMapping("/getNoCache/{name}")
	@ResponseBody
	public String getNoCache(@PathVariable String name) throws Exception {
		long start = System.currentTimeMillis();
		String result = ehCacheSampleService.getNoCache(name);
		long end = System.currentTimeMillis();
		result = name + "의 NonCache 수행시간 : " + Long.toString(end-start) + result;
		return result;
	}
	
	/********************************************************************
	 * @메소드명	: getCache
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 캐쉬 호출
	 ********************************************************************/
	@GetMapping("/getCache/{name}")
	@ResponseBody
	public String getCache(@PathVariable String name) throws Exception {
		long start = System.currentTimeMillis();
		String result = ehCacheSampleService.getCache(name);
		long end = System.currentTimeMillis();
		result = name + "의 Cache 수행시간 : " + Long.toString(end-start) + result;
		return result;
	}
	
	/********************************************************************
	 * @메소드명	: getRefresh
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 캐쉬 삭제
	 ********************************************************************/
	@GetMapping("/refresh/{name}")
	@ResponseBody
	public String getRefresh(@PathVariable String name) throws Exception {
		return ehCacheSampleService.cacheRefresh(name);
	}

}
