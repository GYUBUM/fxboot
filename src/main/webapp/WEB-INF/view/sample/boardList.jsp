<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf" %>

<!DOCTYPE html>
<html>
<head>
<script>
function search() {
	var uri = new URI(window.location);
	uri.setSearch("filter", $('#filter').val());
	uri.setSearch("pageNumber", 0);
	location.href = uri;
}
</script>
<title>Main</title>
</head>
<body>
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>
				Board <small>Board..</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
				<li class="active">Here</li>
			</ol>
		</section>
		<section class="content">
			<div class="row">
		        <div class="col-xs-12">
		        	<div class="box">
			            <div class="box-header with-border">
			              <h3 class="box-title">Board Sample</h3>
			              
			            </div>
			            <div class="box-body">
			              <table class="table table-bordered">
			                <tbody>
				                <tr>
				                  <th style="width: 12px">#</th>
				                  <th>Title</th>
				                  <th>Writer</th>
				                  <th>View</th>
				                  <th>Date</th>
				                </tr>
				                <c:forEach var="row" items="${list.content}" varStatus="status">
				                	
			                		<tr>
					                  <td><fx:row-number sort="desc" totalElements="${list.totalElements}" number="${list.number}" size="${list.size}" count="${status.count}"/></td>
					                  <td><a href="${contextPath}/board/${boardType}/article/${row.board.id}">
					                  		<p>${row.board.title} ${row.commentCount gt 0 ? '(' : ''}${row.commentCount gt 0 ? row.commentCount : ''}${row.commentCount gt 0 ? ')' : ''}</p>
					                  </a></td>
					                  <td>${row.board.createdBy}</td>
					                  <td>${row.board.viewCount}</td>
					                  <td><fmt:formatDate pattern="yyyy-MM-dd H:m:s" value="${row.board.createdAt}" /></td>
					                </tr>
					                
			                	</c:forEach>
				          	</tbody>
			              </table>
		              		<div class="box-footer">
		              			<div class="row">
		              				<div class="col-md-4"></div>
		              				<div class="col-md-4">
		              					<form>
							              <div class="input-group input-group-sm">
							                <input type="text" class="form-control" id="filter" name="filter" value="${filter}">
							                    <span class="input-group-btn">
							                      <button type="button" class="btn btn-info btn-flat" onclick="search();">Search!</button>
							                    </span>
							              </div>
							        	</form>
					              	</div>
					              	<div class="col-md-4 text-right">
					              		<sec:authorize access="isAuthenticated()">
					              			<button type="submit" class="btn btn-primary" onclick="location.href='${contextPath}/board/${boardType}/article'">글쓰기</button>
					              		</sec:authorize>
									</div>
								</div>
							</div>
			            </div>
			            <!-- /.box-body -->
			            <div class="box-footer clearfix text-center ">
			            	<!-- 페이징 view -->
			            	<fx:pagination pageViewCount="9" totalPages="${list.totalPages}" number="${list.number}" />
			            </div>
			          </div>
		        </div>
		    </div>
		</section>
	</div>
</body>

</html>