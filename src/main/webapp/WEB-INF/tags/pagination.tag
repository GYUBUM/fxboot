<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="pageViewCount" required="true" description="화면에 보여질 페이지 수" %>
<%@ attribute name="number" required="true" description="현재 페이지 번호" %>
<%@ attribute name="totalPages" required="true" description="총 페이지 수" %>

<c:set var="begin" value="${number+1 -((pageViewCount / 2) - ((pageViewCount / 2) % 1)) < 1 ? 1 : number+1 -((pageViewCount / 2) - ((pageViewCount / 2) % 1))}" />
<c:set var="end" value="${begin + pageViewCount-1 > totalPages ? totalPages : begin + pageViewCount-1}" />
<c:set var="first" value="${number eq 0 ? true : false}" />
<c:set var="last" value="${number+1 eq totalPages ? true : false}" />

<ul class="pagination no-margin">
	<c:if test="${!first}">
		<li><a href="javascript:movePage(0);"><i class="fa fa-angle-double-left"></i>&nbsp;</a></li>
		<li><a href="javascript:movePage(${number-1});"><i class="fa fa-angle-left"></i>&nbsp;</a></li>
	</c:if>
	<c:forEach var="page" begin="${begin}" end="${end}" varStatus="index">
		<li class="${index.index eq number+1 ? 'active' : '' }">
			<a href="javascript:movePage(${index.index-1});">${index.index}</a>
		</li>
	</c:forEach>
	<c:if test="${!last and totalPages > 1}">
		<li><a href="javascript:movePage(${number+1});"><i class="fa fa-angle-right"></i>&nbsp;</a></li>
		<li><a href="javascript:movePage(${totalPages-1});"><i class="fa fa-angle-double-right"></i>&nbsp;</a></li>
	</c:if>
</ul>

<c:set var="contextPath" value="<%= request.getContextPath()%>"></c:set>
<script src="${contextPath}/webjars/uri.js/1.17.1/src/URI.min.js"></script>
<script>
function movePage(page){
	var uri = new URI(window.location);
	uri.setSearch("pageNumber", page);
	location.href = uri;
}
</script>