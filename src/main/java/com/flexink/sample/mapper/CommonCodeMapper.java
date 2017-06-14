package com.flexink.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.flexink.common.utils.EgovMap;
import com.flexink.vo.CommonVo;

@Mapper
public interface CommonCodeMapper {

	//@Select("SELECT * FROM COMMON_CODE_M")
	List<EgovMap> readPage(String filter);
	
	List<EgovMap> readPage(CommonVo param);
	
}
