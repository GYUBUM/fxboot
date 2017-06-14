package com.flexink.sample.web;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flexink.common.utils.CommonCodeUtils;
import com.flexink.domain.code.CommonCode;
import com.flexink.sample.service.CommonCodeService;
import com.flexink.vo.ParamsVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/system")
public class CommonCodeController {

    @Autowired
    private CommonCodeService basicCodeService;
    
    @GetMapping(value="/code")
	public String viewCode() throws Exception {
		return "/sample/codeMng/codeMng";
	}

    @RequestMapping(value="/commonCodes", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
	public Page<CommonCode> list(ParamsVo paramsVo, CommonCode code) {
    	
    	// 페이지 객체 수신시 파라미터
    	//address?pageNumber=0&pageSize=10&sort=gorupCd,desc
    	
    	// 페이지 객체 직접 생성시
    	//PageRequest pq = new PageRequest(0, 10, new Sort(Sort.Direction.DESC, "groupCd"));
    	
    	//log.debug("수신된 Pageable PageNumber {}", pageable.getPageNumber());
    	//log.debug("수신된 Pageable PageSize {}", pageable.getPageSize());
    	//System.out.println(pageable.getSort());
    	
        //List<CommonCode> basicCodes = basicCodeService.get(requestParams);
    	
    	Page<CommonCode> basicCodes = basicCodeService.get(paramsVo);

        return basicCodes;
    }

    @RequestMapping(value="/commonCodes", method = {RequestMethod.PUT}, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public boolean save(@Valid @NotNull @RequestBody List<CommonCode> basicCodes) {
        basicCodeService.saveCommonCode(basicCodes);
        return true;
    }

    @RequestMapping(value = "/getAllByMap", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Map<String, List<CommonCode>> getAllByMap() {
        return CommonCodeUtils.getAllByMap();
    }
}
