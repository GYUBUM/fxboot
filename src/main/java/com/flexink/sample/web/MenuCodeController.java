package com.flexink.sample.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flexink.domain.menu.Menu;
import com.flexink.sample.service.MenuCodeService;
import com.flexink.vo.ParamsVo;

@Controller
@RequestMapping("/system/menuMng")
public class MenuCodeController {
	
	@Autowired
	MenuCodeService menuService;

	/********************************************************************
	 * @메소드명	: viewMenu
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 메뉴관리 페이지
	 ********************************************************************/
	@GetMapping
	public String viewMenu() {
		return "/sample/menuMng";
	}
	
	/********************************************************************
	 * @메소드명	: getMenus
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 그리드 메뉴 리스트
	 ********************************************************************/
	@GetMapping(value="/menus", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public List<Menu> getMenus(ParamsVo params) {
		return menuService.getMenus(params);
	}
	
	/********************************************************************
	 * @메소드명	: saveMenus
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 그리드용 CUD
	 ********************************************************************/
	//@PutMapping(value="/menus", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(value="/menus", method = {RequestMethod.PUT}, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public boolean saveMenus(@RequestBody List<Menu> menus) {
		System.out.println(menus);
		menuService.saveMenus(menus);
		return true;
	}
}
