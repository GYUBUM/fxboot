<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>EhCache</title>
</head>
<body>
	<div class="content-wrapper">
		<section class="content-header">
			<h1>
				EhCache <small>sample</small>
			</h1>
			<ol class="breadcrumb">
				<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
				<li><a href="active">EhCache</a></li>
			</ol>
		</section>

		<!-- Main content -->
		<section class="content">
			<div class="row">
				
				<!-- basic -->
				<div class="col-md-6">
					<div class="box box-primary">
						<div class="box-header with-border">
							<h3 class="box-title">Cache Example</h3>
						</div>
						
							<div class="box-body">
								<div class="form-group">
									<label for="exampleInputFile">Result</label>
									<!-- <input type="text" id="result" name="result"> -->
									<textarea rows="10" cols="120" id="result" name="result" readonly ></textarea>
									<p class="help-block">Example block-level help text here.</p>
								</div>

							</div>

							<div class="box-footer">
								<!-- <div class="form-group">
									<label for="exampleInputFile">Name</label>
									<input type="text" id="name" name="name"><br>
								</div> -->
							<div class="form-group">
								<label>
									<input type="radio" name="cacheRadio" id="cache_1" value="Cache_1" checked>
									Cache_1
								</label>
								<label>
									<input type="radio" name="cacheRadio" id="cache_2" value="Cache_2">
									Cache_2
								</label>
								<label>
									<input type="radio" name="cacheRadio" id="cache_3" value="Cache_3">
									Cache_3
								</label>
							</div>

							<button type="button" class="btn btn-primary" onclick="javascript:nonCache();">NonCache</button>
								<button type="button" class="btn btn-primary" onclick="javascript:cache();">Get Cache</button>
								<button type="button" class="btn btn-primary" onclick="javascript:refresh();">Remove</button>
							</div>
							
					</div>
				</div>
				
			</div>
		</section>
		
	</div>
	<script>
		function nonCache() {
			get('${contextPath}/sample/cache/getNoCache');
		}
		function cache() {
			get('${contextPath}/sample/cache/getCache');
		}
		function refresh() {
			get('${contextPath}/sample/cache/refresh');
		}
		function get(url, data) {
			$.get(url + '/' + $(':radio[name=cacheRadio]:checked').val(), data, function(res) {
				console.log(res);
				$('#result').val($('#result').val()+res+'\n');
				result.scrollTop = result.scrollHeight;
			});
		}
	</script>
</body>
</html>