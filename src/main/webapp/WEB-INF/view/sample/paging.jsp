<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf" %>

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
				Pagination <small>Pagination..</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
				<li class="active">Here</li>
			</ol>
		</section>
		<section class="content">
			Main
			<div class="row">
		        <div class="col-xs-12">
		        	<div class="box">
			            <div class="box-header with-border">
			              <h3 class="box-title">Bordered Table</h3>
			              
			              <form>
				              <div class="input-group input-group-sm col-xs-4">
				                <input type="text" class="form-control" id="filter" name="filter" value="${filter}">
				                    <span class="input-group-btn">
				                      <button type="button" class="btn btn-info btn-flat" onclick="search();">Search!</button>
				                    </span>
				              </div>
			              </form>
			              
			            </div>
			            <div class="box-body">
			              <table class="table table-bordered">
			                <tbody>
				                <tr>
				                  <th style="width: 12px">#</th>
				                  <th>Group Code</th>
				                  <th>Group Name</th>
				                  <th>Code</th>
				                  <th>Value</th>
								  <th>Sort</th>
								  <th>UseYn</th>
								  <th>Remark</th>
								  <th>Data1</th>
								  <th>Data2</th>
								  <th>Data3</th>
								  <th>Data4</th>
								  <th>Date</th>				                  
				                </tr>
				                <c:forEach var="list" items="${data.content}" varStatus="status">
			                		<tr>
					                  <td><fx:row-number sort="desc" totalElements="${data.totalElements}" number="${data.number}" size="${data.size}" count="${status.count}"/></td>
					                  <%-- <td><fx:rowNumberAsc number="${data.number}" size="${data.size}" count="${status.count}"/></td> --%>
					                  <td>${list.groupCd}</td>
					                  <td>${list.groupNm}</td>
					                  <td>${list.code}</td>
					                  <td>${list.name}</td>
					                  <td>${list.sort}</td>
					                  <td>${list.useYn}</td>
					                  <td>${list.remark}</td>
					                  <td>${list.data1}</td>
					                  <td>${list.data2}</td>
					                  <td>${list.data3}</td>
					                  <td>${list.data4}</td>
					                  <td>12</td>
					                </tr>
			                	</c:forEach>
				          	</tbody>
			              </table>
			            </div>
			            <!-- /.box-body -->
			            <div class="box-footer clearfix">
			            	<!-- 페이징 view -->
			            	<fx:pagination pageViewCount="9" totalPages="${data.totalPages}" number="${data.number}" />
			            </div>
			          </div>
		        </div>
		    </div>
		</section>
	</div>
</body>

</html>