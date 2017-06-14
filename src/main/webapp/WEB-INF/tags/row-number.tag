<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ tag import="org.apache.commons.lang3.StringUtils" %>

<%@ attribute name="sort" required="true" description="정렬 방법" %>
<%@ attribute name="totalElements" required="false" description="총 데이터 수" %>
<%@ attribute name="number" required="true" description="페이지 번호" %>
<%@ attribute name="size" required="true" description="페이지당 데이터 수" %>
<%@ attribute name="count" required="true" description="foreach count 값" %>

<%
	int rowNumber = 0;
	
	if(!StringUtils.isEmpty(totalElements) && "desc".equalsIgnoreCase(sort)) {
		rowNumber = (Integer.valueOf(totalElements) + 1) - (Integer.valueOf(number) * Integer.valueOf(size) + Integer.valueOf(count));		
	} else if("asc".equalsIgnoreCase(sort)) {
		rowNumber = Integer.valueOf(number) * Integer.valueOf(size) + Integer.valueOf(count);
	}
%>

<%= rowNumber %>