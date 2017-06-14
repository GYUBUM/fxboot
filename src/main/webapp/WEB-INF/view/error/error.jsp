<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>${errorCode} Error</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${contextPath}/webjars/AdminLTE/2.3.8/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${contextPath}/webjars/AdminLTE/2.3.8/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="${contextPath}/webjars/AdminLTE/2.3.8/dist/css/skins/_all-skins.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition login-page">
		<!-- Content Header (Page header) -->
		<section class="content-header">
		<h1>${errorCode} Error Page</h1>
		
		</section>

		<!-- Main content -->
		<section class="content">

		<div class="error-page">
			<h2 class="headline text-red">${errorCode}</h2>

			<div class="error-content">
				<h3>
					<i class="fa fa-warning text-red"></i> Oops! Something went wrong.
				</h3>

				<p>
					We will work on fixing that right away. Meanwhile, you may <a
						href="javascript:history.back();">return to back</a> or try using the
					search form.
				</p>

				<form class="search-form">
					<div class="input-group">
						<input type="text" name="search" class="form-control"
							placeholder="Search">

						<div class="input-group-btn">
							<button type="submit" name="submit"
								class="btn btn-danger btn-flat">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</div>
					<!-- /.input-group -->
				</form>
			</div>
		</div>
		<!-- /.error-page --> </section>
		<!-- /.content -->
</body>
</html>
