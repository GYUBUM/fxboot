<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf" %>

<html>
<head>

<link rel="stylesheet" href="${contextPath}/resources/lib/ckeditor/plugins/codesnippet/lib/highlight/styles/androidstudio.css">
<script src="${contextPath}/resources/lib/ckeditor/plugins/codesnippet/lib/highlight/highlight.pack.js"></script>
<script>hljs.initHighlightingOnLoad();</script>

<script>
function addComment() {
	$.post("${contextPath}/board/article/comment", $('#comment').serialize())
	.done(function(data) {
		location.reload();
	});
}

function removeComment(commentId) {
	if(!confirm("댓글을 삭제하시겠습니까?")) {
		return false;
	}
	$.ajax({
        contentType: "application/json",
        method: "DELETE",
        url: '${contextPath}/board/article/comment/'+commentId,
        data: JSON.stringify({
            id: commentId
        }),
        success: function (res) {
            if (res.error) {
                alert(res.error.message);
                return;
            }
            location.reload();
        }
    });
}

function updateComment(obj) {
	$.ajax({
        contentType: "application/json",
        method: "PUT",
        url: '${contextPath}/board/article/comment/'+$(obj).parents('.timeline-footer').prev().children('input').val(),
        data: JSON.stringify({
            id: $(obj).parents('.timeline-footer').prev().children('input').val(),
            content: $(obj).parents('.timeline-footer').prev().children('textarea').val()
        }),
        success: function (res) {
            if (res.error) {
                alert(res.error.message);
                return;
            }
            location.reload();
        }
    });
}

function toggleComment(obj) {
	$(obj).parents('.timeline-footer').children('.comment_btn_1').toggle();
	$(obj).parents('.timeline-footer').children('.comment_btn_2').toggle();
	$(obj).parents('.timeline-footer').prev().children('div').toggle();
	$(obj).parents('.timeline-footer').prev().children('textarea').toggle();
}

function editArticle() {
	location.href="${contextPath}/board/${boardType}/article?id=${article.id}";
}

function removeArticle() {
	if(confirm("삭제하시겠습니까?")) {
		return true;
	} else {
		return false;
	}
	
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
		        
		        	<div class="box box-info">
			            <div class="box-header with-border">
			              <h3 class="box-title">Horizontal Form</h3>
			            </div>
			            <!-- /.box-header -->
			            <!-- form start -->
			            <form:form method="delete" action="${article.id}" class="form-horizontal" onsubmit="return removeArticle();">
			            	<div class="box-body">
				                <div class="form-group">
				                  <label for="title" class="col-sm-2 control-label">Title</label>
				                  <div class="col-sm-10">
				                  	<blockquote>
				                  		${article.title}
				                  	</blockquote>
				                  </div>
				                </div>
				                
				                <div class="form-group">
				                  <label for="inputContent" class="col-sm-2 control-label">Content</label>
				                  <div class="col-sm-10">
				                    <blockquote>
				                    	${article.content}
				                    </blockquote>
				                  </div>
				                </div>
				                <c:if test="${fn:length(article.files) > 0}">
				                	<div class="form-group">
					                	<label for="inputContent" class="col-sm-2 control-label">files</label>
					                	<c:forEach var="file" items="${article.files}">
						                	<div class="col-sm-10">
						                		<a href="${contextPath}/files/download/${file.id}"><i class="fa fa-fw fa-file"></i>${file.fileNm}</a>
						                	</div>
					                	</c:forEach>
					                </div>
				                </c:if>
		              		</div>
			              <!-- /.box-body -->
			              <div class="box-footer">
			                <button type="button" class="btn btn-default" onclick="history.back();">Back</button>
							<c:choose>
								<c:when test="${article.createdBy eq principal.username}">
									<button type="button" class="btn btn-info" onclick="editArticle();">Update</button>
			                		<button type="submit" class="btn btn-danger pull-right">delete</button>
								</c:when>
								<c:otherwise>
									<sec:authorize access="hasAnyRole('ROLE_ADMIN, ROLE_SYSTEM')">
										<button type="submit" class="btn btn-danger pull-right">delete</button>
									</sec:authorize>
								</c:otherwise>
							</c:choose>
			                
			              </div>
			              
		              	</form:form>
			              <!-- /.box-footer -->
			          </div>
		        </div>
		    </div>

			<div class="row">
				<div class="col-md-12">
					<!-- The time line -->
					<ul class="timeline">
						<!-- timeline time label -->
						<li class="time-label"><span class="bg-red"> Comment </span>
						</li>

						<!-- timeline item -->
						<li>
							<i class="fa fa-comments bg-yellow"></i>

							<div class="timeline-item">
								<h3 class="timeline-header">
									Add Comment
								</h3>

								<div class="timeline-body">
									<form id="comment">
										<input type="hidden" name="boardId" value="${article.id}" />
										<textarea class="form-control" rows="2"  name="content" ></textarea>
										<!-- <input type="text" class="form-control" name="content" placeholder="comment" /> -->
									</form>
								</div>
								<div class="timeline-footer">
									<a class="btn btn-primary btn-xs" onclick="addComment();">save</a>
								</div>
							</div>
						</li>
						<!-- end timeline item -->
						<c:forEach var="comment" items="${comments}">
							<!-- timeline item -->
							<li><i class="fa fa-comments bg-yellow"></i>
								<div class="timeline-item">
									<span class="time"><i class="fa fa-clock-o"></i> ${comment.createdAt}</span>
	
									<h3 class="timeline-header">
										<a href="#">${comment.createdBy}</a> commented on your post
									</h3>
	
									<div class="timeline-body">
										<input name="commentId" type="hidden" value="${comment.id}" />
										<div>${comment.content}</div>
										<textarea class="form-control" rows="2" id="comment_${comment.id}" name="content" style="display: none;">${comment.content}</textarea>
									</div>
									<div class="timeline-footer">
										<c:choose>
											<c:when test="${comment.createdBy eq principal.username}">
												<div class="comment_btn_1">
													<a class="btn btn-warning btn-xs" onclick="toggleComment(this);">Update</a>
													<a class="btn btn-danger btn-xs" onclick="removeComment('${comment.id}');">Delete</a>
												</div>
												<div class="comment_btn_2" style="display:none;">
													<a class="btn btn-info btn-xs" onclick="updateComment(this);">save</a>
													<a class="btn btn-default btn-xs" onclick="toggleComment(this);">cancel</a>
												</div>
											</c:when>
											<c:otherwise>
												<sec:authorize access="hasAnyRole('ROLE_USER, ROLE_ADMIN')">
													<a class="btn btn-danger btn-xs" onclick="removeComment('${comment.id}');">Delete</a>	
												</sec:authorize>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
							</li>
							<!-- end timeline item -->
						</c:forEach>
					</ul>
				</div>
				<!-- /.col -->
			</div>


		</section>
	</div>
	<script>
	
	</script>
</body>

</html>