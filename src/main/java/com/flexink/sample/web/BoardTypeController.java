package com.flexink.sample.web;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flexink.domain.board.BoardType;
import com.flexink.sample.service.BoardTypeService;
import com.flexink.vo.ParamsVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/system/boardMng")
public class BoardTypeController {

    @Autowired
    private BoardTypeService boardTypeService;
    
    @GetMapping
	public String viewBoardMng() {
		return "/sample/boardMng";
	}
    
    @GetMapping(value="/boardTypes", produces = "application/json; charset=UTF-8")
    @ResponseBody
	public Page<BoardType> list(ParamsVo paramsVo) {
        return boardTypeService.getBoardTypes(paramsVo);
    }

    @RequestMapping(value="/boardTypes", method = {RequestMethod.PUT}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public boolean save(@NotNull @RequestBody List<BoardType> boardTypes) {
        boardTypeService.saveBoardType(boardTypes);
        return true;
    }

}
