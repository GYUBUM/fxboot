package com.flexink.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.flexink.common.utils.ArrayUtils;

import lombok.ToString;

@ToString
public class ParamsVo {

	private Map<String, Object> map;
	private List<Sort.Order> sortOrders = new ArrayList<>();
	
	public ParamsVo() {
		this.map = new HashMap<>();
	}
	
	/********************************************************************
	 * @메소드명	: put
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 요청파라미터 값 추가
	 ********************************************************************/
	public void put(String key, Object value) {
        map.put(key, value);
    }
	
	/********************************************************************
	 * @메소드명	: get
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 요청파라미터 조회
	 ********************************************************************/
	public Object get(String key) {
		if (map.containsKey(key)) {
            return map.get(key);
        }
        return null;
	}
	
	/********************************************************************
	 * @메소드명	: getPageable
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 요청 페이징 객체 반환
	 * @return	: PageRequest
	 ********************************************************************/
	public Pageable getPageable() {
        int page = getInt("pageNumber", 0);
        int size = getInt("pageSize", Integer.MAX_VALUE);

        return new PageRequest(page, size, getSort());
    }
	
	/********************************************************************
	 * @메소드명	: addSort
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: 정렬 정보 수동 추가 (requestParams에 "sort" property가 없는 경우에만 사용 가능)
	 * @param	: 정렬할 Property
	 * @param	: direction
	 ********************************************************************/
	public void addSort(String value, Sort.Direction direction) {
        if (!hasSortParameter()) {
            sortOrders.add(new Sort.Order(direction, value));
        }
    }

    /********************************************************************
     * @메소드명	: getSort
     * @작성자	: KIMSEOKHOON
     * @메소드 내용	: Sort객체 반환
     ********************************************************************/
    public Sort getSort() {
        if (hasSortParameter()) {
            List<Sort.Order> orders = new ArrayList<>();
            String sortParameter = getString("sort");
            String[] sortValues = sortParameter.split(",");
            for (int i = 0; i < sortValues.length; i += 2) {
                orders.add(new Sort.Order(Sort.Direction.fromString(sortValues[i + 1]), sortValues[i]));
            }
            return new Sort(orders);
        }
        if (ArrayUtils.isNotEmpty(sortOrders)) {
            return new Sort(sortOrders);
        }
        return null;
    }
    
    public List<String> getArray(String key) {
    	if(getString(key) == null) {
    		return null;
    	}
    	List<String> array = new ArrayList<>();
    	String parameter = getString(key);
    	String[] values = parameter.split(",");
    	for (int i = 0; i < values.length; i++) {
    		array.add(values[i]);
        }
    	return array;
    }
    
    private boolean hasSortParameter() {
        return StringUtils.isNotEmpty(getString("sort"));
    }
	
	public String getString(String key, String defaultValue) {
        if (map.containsKey(key)) {
            String value = (String) map.get(key);

            if (StringUtils.isEmpty(value)) {
                return defaultValue;
            }

            return value;
        }

        return defaultValue;
    }

    public String getString(String key) {
        return getString(key, null);
    }
    
	public int getInt(String key, int defaultValue) {
        String value = getString(key);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return Integer.parseInt(value);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }
    
    public long getLong(String key, long defaultValue) {
        String value = getString(key);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return Long.parseLong(value);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }
    
	/********************************************************************
	 * @메소드명	: setParameterMap
	 * @작성자	: KIMSEOKHOON
	 * @메소드 내용	: Resolver 전용 메소드
	 ********************************************************************/
	public void setParameterMap(Map<String, String[]> map) {
        for (String key : map.keySet()) {
            String[] values = map.get(key);
            //String value = Arrays.stream(values).collect(Collectors.joining(","));
            String value = "";
            for(int i=0; i < values.length; i++) {
            	value += values[i];
            	if(values.length > i+1) {
            		value += ",";
            	}
            }
            put(key, value);
        }
    }
	
	public Map<String, Object> getParamemter() {
		return this.map;
	}
	
	public String getGroupCd() {
		return getString("groupCd");
	}
}
