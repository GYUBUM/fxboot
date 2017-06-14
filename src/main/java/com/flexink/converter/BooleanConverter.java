package com.flexink.converter;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang3.StringUtils;

public class BooleanConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		if(attribute == null) {
			return null;
		} else {
			return attribute ? "Y" : "N";
		}
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		if(!StringUtils.isNotBlank(dbData)) {
			return null;
		} else {
			if("Y".equalsIgnoreCase(dbData)) {
				return true;
			} else if ("N".equalsIgnoreCase(dbData)) {
				return false;
			} else {
				return null;
			}
		}
	}

	
}
