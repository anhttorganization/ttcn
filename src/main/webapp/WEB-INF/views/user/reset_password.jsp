<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<title>Đặt lại mật khẩu</title>
<meta charset="utf-8">
<link rel="shortcut icon" type="image/png"
	href="${contextPath}/resources/images/Logo.png" />
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/all.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/dashboard.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/toastr.min.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/style.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/dangnhap.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/admin.css">

<script type="text/javascript"
	src="${contextPath}/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/toastr.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/setuptoartr.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/common.js"></script>
</head>
<body>
	<div class="container h-100">
		<div class="d-flex justify-content-center h-100">
			<div class="user_card">
				<div class="d-flex justify-content-center">
					<div class="brand_logo_container">
						<img src="${contextPath}/resources/images/logoST_edited.jpg"
							class="brand_logo" alt="Logo">
					</div>
				</div>

				<div
					class="mt-4 d-flex justify-content-center form_container quenmatkhau">
					<form id="reset-pass-form" method="post">
						<input name="email" value="${email}" hidden>
						<div class="input-group mb-3">
							<!-- <div class="input-group-append">
								<span class="input-group-text"><i class="fas fa-eye"></i></span>
							</div> -->
							<input id="password" type="password" name="password"
								class="form-control input_user" placeholder="Nhập mật khẩu mới">
						</div>

						<div class="input-group mb-3">
							<!-- <div class="input-group-append">
								<span class="input-group-text"><i class="fas fa-eye"></i></span>
							</div> -->
							<input id="passwordConfirm" type="password"
								name="passwordConfirm" class="form-control input_user"
								placeholder="Nhập lại mật khẩu">
						</div>
					</form>
				</div>
				<div
					class=" mt-1 d-flex justify-content-center mt-3 login_container">
					<button id="btn_submit" type="button" name="button"
						class="btn login_btn">Lưu thay đổi</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="error_message" value='${error}' />
	<input type="hidden" id="success_message" value='${success}' />
	<input type="hidden" id="info_message" value='${info}' />
	<footer id="footer">
		<div class="bottom_footer">
			<a class="left" href="https://st-dse.vnua.edu.vn/" target="_blank"><img
				class="logo_bottom"
				src="${contextPath}/resources/images/logoST_edited.jpg"
				alt="STCalendar"></a>
			<div class="bottom_footer_1 left">
				<p>
					Copyright © 2019 <a href="https://st-dse.vnua.edu.vn/"
						target="_blank">ST-DSE</a>. All rights reserved.
				</p>
				<div class="col-md-8">
					<p>
						Địa chỉ: Bộ môn Công nghệ phần mềm, <a
							href="https://fita.vnua.edu.vn/" target="_blank">Khoa CNTT</a>, <a
							href="https://vnua.edu.vn/" target="_blank">Học viện Nông
							nghiệp Việt Nam</a> <br />ĐT: 0912 817 498 - Email:
						stdse@vnua.edu.vn - Website: st-dse.vnua.edu.vn
					</p>
				</div>
			</div>
			<div class="bottom_footer_2 right">
				<p>
					<i class="fa fa-envelope" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Email:
					stdse@vnua.edu.vn
				</p>
				<p>
					<i class="fa fa-phone fa-rotate-90" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Điện
					thoại: 0912 817 498
				</p>
			</div>
		</div>
	</footer>
	<script type="text/javascript"
		src="${contextPath}/resources/js/reset_password.js"></script>
</body>
</html>