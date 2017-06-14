<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf" %>

<html>
<head>
  <script src="${contextPath}/webjars/jquery/1.12.4/jquery.min.js"></script>
  <!-- Bootstrap 3.3.6 -->
  <script src="${contextPath}/webjars/AdminLTE/2.3.8/bootstrap/js/bootstrap.min.js"></script>
  <!-- iCheck -->
  <script src="${contextPath}/webjars/AdminLTE/2.3.8/plugins/iCheck/icheck.min.js"></script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <title>Admin | <sitemesh:write property='title' /></title>
  <sitemesh:write property='head' />

  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="${contextPath}/webjars/AdminLTE/2.3.8/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="${contextPath}/webjars/AdminLTE/2.3.8/dist/css/AdminLTE.min.css">
  <!-- iCheck -->
  <link rel="stylesheet" href="${contextPath}/webjars/AdminLTE/2.3.8/plugins/iCheck/square/blue.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

	<script>
	/* <c:out value="${sessionScope.LAST_USERNAME}"/> */
	/* <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}"> 
		<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"></c:out> 
	</c:if>  */
		$(function() {
			var viewMessage = "${sessionScope.errorMessage}";
			if(viewMessage != '' && viewMessage != null) {
				alert(viewMessage);
			}
		});
	</script>
</head>

<body class="hold-transition register-page">
	<sitemesh:write property='body' />
</body>

</html>
<c:remove scope="session" var="errorMessage"/>