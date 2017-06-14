<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jspf/inc_taglib.jspf" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
</head>
<body class="hold-transition register-page">
<div class="register-box">
  <div class="register-logo">
    <a href="../../index2.html"><b>Admin</b>LTE</a>
  </div>

  <div class="register-box-body">
    <p class="login-box-msg">Register a new membership</p>

    <form:form action="register" method="post" commandName="user">
      <div class="form-group has-feedback <form:errors path="${securityUsername}">has-error</form:errors>">
        <form:input type="text" class="form-control" placeholder="User ID" path="${securityUsername}" />
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <span class="help-block"><form:errors path="${securityUsername}" /></span>
        
      </div>
      <div class="form-group has-feedback <form:errors path="${securityPassword}">has-error</form:errors>">
        <form:input type="password" class="form-control" placeholder="Password" path="${securityPassword}" />
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        <span class="help-block"><form:errors path="${securityPassword}" /></span>
      </div>
      <div class="form-group has-feedback <form:errors path="retypePassword">has-error</form:errors>">
        <form:input type="password" class="form-control" placeholder="Retype password" path="retypePassword"/>
        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
        <span class="help-block"><form:errors path="retypePassword" /></span>
      </div>
      <div class="form-group has-feedback <form:errors path="email">has-error</form:errors>">
        <form:input type="email" class="form-control" placeholder="Email" path="email"/>
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
        <span class="help-block"><form:errors path="email"/></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <input type="checkbox"> I agree to the <a href="#">terms</a>
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">Register</button>
        </div>
        <!-- /.col -->
      </div>
    </form:form>

    <div class="social-auth-links text-center">
      <p>- OR -</p>
      <a href="#" class="btn btn-block btn-social btn-facebook btn-flat"><i class="fa fa-facebook"></i> Sign up using
        Facebook</a>
      <a href="#" class="btn btn-block btn-social btn-google btn-flat"><i class="fa fa-google-plus"></i> Sign up using
        Google+</a>
    </div>

    <a href="/security/login" class="text-center">I already have a membership</a>
  </div>
  <!-- /.form-box -->
</div>
<!-- /.register-box -->

<script>
  $(function () {
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
  });
</script>
</body>
</html>