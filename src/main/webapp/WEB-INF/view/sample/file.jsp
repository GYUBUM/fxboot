<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf"%>

<html>
<head>
<link rel="stylesheet" href="${contextPath}/webjars/ax5ui-uploader/1.4.53/dist/ax5uploader.css">
<link rel="stylesheet" href="${contextPath}/webjars/ax5ui-dialog/1.4.20/dist/ax5dialog.css">

<script src="${contextPath}/webjars/ax5core/1.4.20/dist/ax5core.min.js"></script>
<script src="${contextPath}/webjars/ax5ui-uploader/1.4.53/dist/ax5uploader.min.js"></script>
<script src="${contextPath}/webjars/ax5ui-dialog/1.4.20/dist/ax5dialog.min.js"></script>
<script src="https://cdn.rawgit.com/thomasJang/jquery-direct/master/dist/jquery-direct.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Files</title>
</head>
<body>
	<div class="content-wrapper">
		<section class="content-header">
			<h1>
				File Upload <small>sample</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
				<li><a href="active">files</a></li>
			</ol>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				<!-- AX5 -->
				<div class="col-md-6">
					<div class="box box-primary">
						<div class="box-header with-border">
							<h3 class="box-title">AX5 upload</h3>
						</div>
						<div class="box-body">
							<div class="form-group">
							
							
								<div class="DH20"></div>
 
								<div data-ax5uploader="upload1">
								    <button data-ax5uploader-button="selector" class="btn btn-primary">Select File (*/*)</button>
								    (Upload Max fileSize 20MB)
								    <div data-uploaded-box="upload1" data-ax5uploader-uploaded-box="thumbnail"></div>
								</div>
								 
								<div style="padding: 0;" data-btn-wrap>
								    <h3>control</h3>
								    <button class="btn btn-default" data-upload-btn="getUploadedFiles">getUploadedFiles</button>
								    <button class="btn btn-default" data-upload-btn="removeFileAll">removeFileAll</button>
								</div>
								
								
							</div>

						</div>
					</div>
				</div>
				
				<!-- basic -->
				<div class="col-md-6">
					<div class="box box-primary">
						<div class="box-header with-border">
							<h3 class="box-title">File Example</h3>
						</div>
						<form role="form" action="/files/upload" method="post"
							enctype="multipart/form-data">
							<div class="box-body">
								<div class="form-group">
									<label for="exampleInputFile">File input</label> <input
										type="file" id="exampleInputFile" name="file"> <input
										type="hidden" name="thumbnail" value="true" /> <input
										type="hidden" name="targetType" value="0" /> <input
										type="hidden" name="targetId" value="1" />
									<p class="help-block">Example block-level help text here.</p>
								</div>

							</div>

							<div class="box-footer">
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</section>
	</div>

	<script src="${resourcePath}/js/file/axfile.js"></script>
	<script type="text/javascript">
	    $(function () {
	    	// 파일
	    	var config = {
	    		target : $('[data-ax5uploader="upload1"]'),
	    		dropZone: {
	    			target: $('[data-uploaded-box="upload1"]')
	    		},
	    		uploadBox: {
	    			target: $('[data-uploaded-box="upload1"]')
	    		},
	    		validLength: 5,
	    		validSize: 1,
	    		fileLengthErrMsg: "{0}개 이상의 파일은 업로드할 수 없습니다.",
	    		fileSizeErrMsg: "{0}MB 이상의 파일은 업로드할 수 없습니다.",
	    		fileDeleteMsg: "파일을 삭제하시겠습니까?",
	    		fileType: []
	    	}
	    	$.extend(axFileConfig, config);
	    	axfile.init();
	    	axfile.setUploadedFiles();
	 
	        // 컨트롤 버튼 이벤트 제어
	        $('[data-btn-wrap]').clickAttr(this, "data-upload-btn", {
	            "getUploadedFiles": function () {
	                var files = axfile.getUploadFiles();
	                DIALOG.alert(JSON.stringify(files));
	            },
	            "removeFileAll": function () {
	                DIALOG.confirm({
	                    title: "AX5UI",
	                    msg: "Are you sure you want to delete it?"
	                }, function () {
	                    if (this.key == "ok") {
	 						axfile.deleteFiles();
	                    }
	                });
	            }
	        });
	    });
	</script>
</body>
</html>