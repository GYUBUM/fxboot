<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf" %>

<html>
<head>

<link rel="stylesheet" href="${contextPath}/webjars/ax5ui-uploader/1.4.53/dist/ax5uploader.css">
<link rel="stylesheet" href="${contextPath}/webjars/ax5ui-dialog/1.4.20/dist/ax5dialog.css">

<script src="${contextPath}/webjars/ax5core/1.4.20/dist/ax5core.min.js"></script>
<script src="${contextPath}/webjars/ax5ui-uploader/1.4.53/dist/ax5uploader.min.js"></script>
<script src="${contextPath}/webjars/ax5ui-dialog/1.4.20/dist/ax5dialog.min.js"></script>
<script src="https://cdn.rawgit.com/thomasJang/jquery-direct/master/dist/jquery-direct.min.js"></script>

<script src="${contextPath}/resources/lib/ckeditor/ckeditor.js"></script>
<script src="${contextPath}/webjars/uri.js/1.17.1/src/URI.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			              <h3 class="box-title">글 쓰기</h3>
			            </div>
			            <!-- /.box-header -->
			            <!-- form start -->
			            <form:form class="form-horizontal" id="boardFrom" commandName="board" method="post" action="article" enctype="multipart/form-data"  onsubmit="return checkSaveArticle();">
			              <div class="box-body">
			              
			                <div class="form-group">
			                  <label for="title" class="col-sm-2 control-label">Title</label>
			                  <div class="col-sm-10">
			                    <form:input type="text" class="form-control" path="title" placeholder="Title" value="${article.title}"/>
			                    <form:errors path="title" />
			                  </div>
			                </div>
			                
			                <div class="form-group">
			                  <label for="inputContent" class="col-sm-2 control-label">Content</label>
			                  <div class="col-sm-10">
			                    
			                    <textarea id="editor" rows="10" cols="80" name="content">
			                    	${article.content}
					            </textarea>
					            
			                  </div>
			                </div>
			                
			                <div class="form-group">
			                  <div class="col-sm-offset-2 col-sm-10">
			                    <div class="checkbox">
			                      <label>
			                        <form:checkbox path="secret" value="true" /> 비밀 글
			                        <!-- <input type="checkbox" id="secret" name="secret" value="true" /> 비밀 글 -->
			                      </label>
			                    </div>
			                  </div>
			                </div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<div data-ax5uploader="upload1">
									    <button type="button" data-ax5uploader-button="selector" class="btn btn-primary">Select File (*/*)</button>
									    (Upload Max fileSize 20MB)
									    <div data-uploaded-box="upload1" data-ax5uploader-uploaded-box="inline"></div>
									</div>
								</div>
							</div>

						  </div>
			              <!-- /.box-body -->
			              <div class="box-footer">
			                <button type="button" class="btn btn-default" onclick="history.back();">Cancel</button>
			                <!-- <button type="submit" class="btn btn-info pull-right">Save</button> -->
			                <!-- <button type="button" class="btn btn-info pull-right" onclick="saveArticle();">Save</button> -->
			                <button type="submit" class="btn btn-info pull-right" >Save</button>
			                
			              </div>
			              <!-- /.box-footer -->
			              <%-- <input type="hidden" name="type" value="${boardType}" /> --%>
			              <input type="hidden" id="boardId" name="id" value="${article.id}" />
			            </form:form>
			          </div>
		        </div>
		    </div>
		</section>
	</div>
	
	
	<script src="${resourcePath}/js/file/axfile.js"></script>
	<script type="text/javascript">
		// CKEditor
		var editor = CKEDITOR.replace( 'editor', {
        	customConfig: '${contextPath}/resources/lib/ckeditor/config.js'
        });
        
		// 글 저장
        /* function saveArticle() {
        	$.ajax({
                method: "POST",
                url: '${contextPath}/sample/article',
                contentType: 'application/json',
                //data: $('#boardFrom').serialize()+'&'+$.param(axfile.getUploadedFileIds(), true) +'&content='+editor.getData(),
                traditional: true,
                success: function (res) {
                    if (res.error) {
                        alert(res.error.message);
                        return;
                    }
                    location.replace('viewArticle?id='+res.id);
                }
            });
        } */
		
		function checkSaveArticle() {
        	// 유효성 체크
        	
        	// 파일
			var fileIds = axfile.getUploadedFileIds().fileIds;
			for(var i=0; i < fileIds.length; i++) {
				var file = '<input type="hidden" name="fileIds" value="'+ fileIds[i] +'" />';
				$('#boardFrom').append(file);
			}
			
			return true;
		}
        
        $(function() {
        	// axfile
        	var config = {
   	    		target : $('[data-ax5uploader="upload1"]'),
   	    		dropZone: {
   	    			target: $('[data-uploaded-box="upload1"]')
   	    		},
   	    		uploadBox: {
   	    			target: $('[data-uploaded-box="upload1"]')
   	    		},
   	    		fileLength: 5,
   	    		fileSize: 20
   	    	}
        	$.extend(axFileConfig, config);
        	axfile.init();
        	if(!_.isEmpty("${article.id}")) {
        		axfile.setUploadedFiles({targetType:'${boardType}', targetId:'${article.id}'});
        	}
        	
        });
        
        /* $(function() {
        	editor.on( 'change', function( evt ) {
        	    // getData() returns CKEditor's HTML content.
        	    console.log( 'Total bytes: ' + evt.editor.getData().length );
        	});
        }); */
    </script>
</body>

</html>