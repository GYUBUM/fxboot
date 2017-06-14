package com.flexink.common.utils;

import org.apache.commons.collections.map.ListOrderedMap;

public class EgovMap extends ListOrderedMap{


	private static final long serialVersionUID = 5278398065706645667L;

	/**
	 * key 에 대하여 Camel Case 변환하여 super.put
	 * (ListOrderedMap) 을 호출한다.
	 * @param key
	 *        - '_' 가 포함된 변수명
	 * @param value
	 *        - 명시된 key 에 대한 값 (변경 없음)
	 * @return previous value associated with specified
	 *         key, or null if there was no mapping for
	 *         key
	 */
	@Override
	public Object put(Object key, Object value) {
		return super.put(CamelUtils.convert2CamelCase((String) key), value);
	}
	
}
