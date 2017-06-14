package com.flexink.common.utils;

import java.util.List;

import com.flexink.common.context.AppContextManager;
import com.flexink.domain.menu.Menu;
import com.flexink.domain.menu.Menu.UseYn;
import com.flexink.sample.service.MenuCodeService;

public class MenuUtils {
	
    /********************************************************************
     * @메소드명	: get
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: 메뉴 리스트 조회
     * @param menuGrpCd
     * @return
     * List<Menu>
     ********************************************************************/
    public static List<Menu> get(String menuGrpCd) {
    	Menu menu = new Menu();
    	menu.setMenuGrpCd(menuGrpCd);
    	menu.setUseYn(UseYn.Y);
    	
        return AppContextManager.getBean(MenuCodeService.class).getTagLibMenus(menu);
    }


}
