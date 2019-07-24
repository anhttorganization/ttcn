<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<title>ST Calendar</title>
<meta charset="utf-8">
<link rel="shortcut icon" type="image/png" href="${contextPath}/resources/images/Logo.png" />
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/all.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/dashboard.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/toastr.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/dangnhap.css">
<script type="text/javascript" src="${contextPath}/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/toastr.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/setuptoartr.js"></script>

</head>
<body>
<div  style="height: 100%;" >
<div class="container h-100">
		<div class="d-flex justify-content-center h-100">
			<div class="user_card">
				<div class="d-flex justify-content-center">
					<div class="brand_logo_container">
						<img src="${contextPath}/resources/images/logoST_edited.jpg" class="brand_logo" alt="Logo">
					</div>	
				</div>
				
				<div class="d-flex justify-content-center form_container">
					<form method="POST" action="${contextPath}/j_spring_security_check" class="form-signin">
						<div class="form-group ${error != null ? 'has-error' : ''}">
							<div class="input-group mb-3">
								<div class="input-group-append">
									<span class="input-group-text"><i class="fas fa-user"></i></span>
								</div>
								
								<input name="email" type="text" class="form-control input_user" placeholder="Tài khoản" autofocus="true" required="required"/>
							</div>
							<div class="input-group mb-2">
								<div class="input-group-append">
									<span class="input-group-text"><i class="fas fa-key"></i></span>
								</div>
								<input id="password" name="password" type="password" class="form-control input_pass" placeholder="Mật khẩu" required="required"/>

							</div>
							<div>
								<span>${error}</span>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</div>
							<div class="form-group">
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input" id="customControlInline">
									<label class="custom-control-label" for="customControlInline">Nhớ tài khoản</label>
								</div>
							</div>
							<div class="d-flex justify-content-center mt-3 login_container">
								<button id="btn_submit" class="btn login_btn" type="submit" name="button">Đăng nhập</button>
							</div>
						</div>
					</form>
				</div>
				
				<div class="mt-4">
					<div class="d-flex justify-content-center links">
						Bạn chưa có tài khoản?<a href="${contextPath}/register" class="ml-2">Đăng ký</a>
					</div>
					<div class="d-flex justify-content-center links">
						<a href="${contextPath}/fogot_password">Quên mật khẩu?</a>
					</div>
					<span>${message}</span>
				</div>
			</div>
		</div>
	</div>
	</div>
	<script type="text/javascript" src="${contextPath}/resources/js/dangnhap.js"></script>
</body>
<footer id = "footer">
<div	>
	<hr>
	<p align="center">© Copyright <a href="https://st-dse.vnua.edu.vn/" target="_blank">Nhóm ST</a>, Bộ môn Công nghệ phần mềm, Khoa Công nghệ thông tin, Học viện Nông nghiệp Việt Nam</p>
</div>
</footer>
</html>

<c:if test="${not empty invalidToken}">
	<script type="text/javascript">
		alert("Confirm token không hợp lệ!");
	</script>
</c:if>

<c:if test="${not empty success}">
	<script type="text/javascript">
		alert("Kích hoạt tài khoản thành công!");
	</script>
</c:if>