<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<title>Quên mật khẩu</title>
<meta charset="utf-8">
<link rel="shortcut icon" type="image/png" href="${contextPath}/resources/images/Logo.png" />
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/all.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/dashboard.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/toastr.min.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/dangnhap.css">
<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/admin.css">

<script type="text/javascript" src="${contextPath}/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/toastr.min.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/setuptoartr.js"></script>
<script type="text/javascript" src="${contextPath}/resources/js/common.js"></script> 
</head>
<body>
	<div class="container h-100">
		<div class="d-flex justify-content-center h-100">
			<div class="user_card">
				<div class="d-flex justify-content-center">
					<div class="brand_logo_container">
						<img src="${contextPath}/resources/images/logoST_edited.jpg" class="brand_logo" alt="Logo">
					</div>
				</div>
				<div class="d-flex justify-content-center form_container quenmatkhau">
					<form  method="POST" modelAttribute="user"  action="" id="fogot_password_form">
						<div class="input-group mb-3">
							<div class="input-group-append">
								<span class="input-group-text"><i class="fas fa-at"></i></span>
							</div>
							<input id="email" type="text" name="email" class="form-control input_user" placeholder="Nhập email">
						</div>
					</form>
				</div>
				<div class="d-flex justify-content-center mt-3 login_container">
					<button id="btn_submit" type="button" name="button" class="btn login_btn">Lấy lại mật khẩu</button>
				</div>
				<div class="mt-4">
					<div class="d-flex justify-content-center links">
						Bạn đã có tài khoản?<a href="${contextPath}/login" class="ml-2">Đăng nhập</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer id = "footer">
		<%-- <div class="bottom_footer">
			<a class="left" href="https://st-dse.vnua.edu.vn/" target="_blank"><img class="logo_bottom" src="${contextPath}/resources/images/logoST_edited.jpg" alt="STCalendar"></a>
			<div class="bottom_footer_1 left">
				<p>Copyright © 2019 <a href="https://st-dse.vnua.edu.vn/" target="_blank">ST-DSE</a>. All rights reserved.</p>
				<p>Địa chỉ: P.310A, Tầng 3, Nhà hành chính, HVNNVN</p>
			</div>
			<div class="bottom_footer_2 right">
				<p><i class="fa fa-envelope" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Email: stdse@vnua.edu.vn</p>
				<p><i class="fa fa-phone fa-rotate-90" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Điện thoại: 0912 817 498</p>
			</div>
		</div> --%>
		<div class="row" style="margin:0">
			<div class="col-md-6">
				<div class="row" style="margin:0">
					<div class="col-md-4">
						<img class="logo_bottom" src="${contextPath}/resources/images/logoST_edited.jpg" alt="STCalendar">
					</div>
					<div class="col-md-8">
					<p>Copyright © 2019 <a href="https://st-dse.vnua.edu.vn/" target="_blank">ST-DSE</a>. All rights reserved.</p>
					<p>Địa chỉ: P.310A, Tầng 3, Nhà hành chính, Học viện Nông nghiệp Việt Nam</p>
					</div>
				</div>
				
			</div>
			<div class="col-md-6">
				<p><i class="fa fa-envelope" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Email: stdse@vnua.edu.vn</p>
				<p><i class="fa fa-phone fa-rotate-90" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Điện thoại: 0912 817 498</p>
			</div>
		</div>
</footer>
	<input type="hidden" id="error_message" value='${error}' />
	<input type="hidden" id="success_message" value='${success}' />
	<input type="hidden" id="info_message" value='${info}' />
	<input type="hidden" id="sendemail" value='${sendmail}' />
	<script type="text/javascript" src="${contextPath}/resources/js/quenmatkhau.js"></script>
</body>
<div id="spinner" class="spinner" style="display: none;"></div>
</html>
