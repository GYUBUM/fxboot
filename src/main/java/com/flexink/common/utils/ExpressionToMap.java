package com.flexink.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.querydsl.core.types.Expression;

import lombok.extern.slf4j.Slf4j;

/**************************************************************
 * @FileName	: ExpressionToMap.java
 * @Project		: fxBoot
 * @Package_Name: com.flexink.common.utils
 * @Date		: 2017. 5. 16. 
 * @작성자		: KIMSEOKHOON
 * @변경이력		:
 * @프로그램 설명 	: QueryDsl Projection.Map 사용시 Expression -> Map 변환
 **************************************************************/
@Slf4j
public class ExpressionToMap {
	

	public static List<Map<String, Object>> convert(List<Map<Expression<?>, ?>> expList) {
		List<Map<String, Object>> list = new ArrayList<>();
		
		for(Map<Expression<?>, ?> row : expList) {
			list.add(convert(row));
		}
		
		return list;
	}
	
	static private Map<String, Object> map;
	
	public static Map<String, Object> convert(Map<Expression<?>, ?> expMap) {
		
		map = new HashMap<String, Object>();
		
		Iterator<Expression<?>> keys = expMap.keySet().iterator();
		while( keys.hasNext() ) {
			Expression<?> key = keys.next();
			Object value = expMap.get(key);
			String oKey = key.toString();
			
			String newKey = "";
			if(oKey.lastIndexOf(" ") > 0) {
				newKey = oKey.substring(oKey.lastIndexOf(" ")+1);
			} else if(oKey.lastIndexOf(".") > 0) {
				newKey = oKey.substring(oKey.lastIndexOf(".")+1);
			} else {
				newKey = oKey;
			}
			String getKey = getKey(newKey); 
			map.put(getKey, value);
		}
		//log.debug(map.toString());
		return map;
	}
	
	private static String getKey(String key) {
		if(map.containsKey(key)) {
			if(key.lastIndexOf("_") > 0) {
				int indexOf = key.lastIndexOf("_") + 1; 
				int num = Integer.parseInt(key.substring(indexOf)) + 1;
				key = key.substring(0, indexOf) + num;
			} else {
				key = key + "_1";
			}
			return getKey(key);
		}
		return key;
	}
}
