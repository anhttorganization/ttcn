<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
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
	href="${contextPath}/resources/css/dangky.css">
<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/admin.css">

<link rel="stylesheet" type="text/css"
	href="${contextPath}/resources/css/admin.css">
<script type="text/javascript"
	src="${contextPath}/resources/js/jquery-3.4.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/toastr.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/common.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/setuptoartr.js"></script>
</head>

<body>
	<div class="container">
		<div class="d-flex justify-content-center">
			<div class="user_card">
				<div class="d-flex justify-content-center">
					<div class="brand_logo_container">
						<img src="${contextPath}/resources/images/logoST_edited.jpg"
							class="brand_logo" alt="Logo">
					</div>
				</div>
				<div class="d-flex justify-content-center form_container">
					<form:form method="POST" modelAttribute="user" class="form-signin"
						action="register" id="register_form">

						<div class="input-group mb-1">
							<spring:bind path="email">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input type="text" id="email" path="email"
										class="form-control input_pass" placeholder="Địa chỉ Gmail"
										autofocus="true"></form:input>
									<div hidden="">
										<form:errors path="email"></form:errors>
									</div>
								</div>
							</spring:bind>
						</div>
						<div class="input-group mb-1">
							<spring:bind path="lastName">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input id="lastname" type="text" path="lastName"
										class="form-control input_pass firstnamecss"
										placeholder="Họ đệm"></form:input>
									<form:input id="firstname" type="text" path="firstName"
										class="form-control input_user lastnamecss" placeholder="Tên"></form:input>
									<div hidden="">
										<form:errors path="lastName"></form:errors>
									</div>
									<div hidden="">
										<form:errors path="firstName"></form:errors>
									</div>
								</div>
							</spring:bind>
						</div>
						<%-- <div class="input-group mb-2">
							<spring:bind path="firstName">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input id="firstname" type="text" path="firstName" class="form-control input_user" placeholder="Tên"></form:input>
									<div hidden = ""><form:errors path="firstName"></form:errors></div>
								</div>
							</spring:bind>
						</div> --%>
						<div class="input-group mb-1">
							<spring:bind path="password">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input id="password" type="password" path="password"
										class="form-control input_pass" placeholder="Mật khẩu"></form:input>
									<div hidden="">
										<form:errors path="password"></form:errors>
									</div>
								</div>
							</spring:bind>
						</div>
						<div class="input-group mb-1">
							<spring:bind path="passwordConfirm">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:input id="passwordConfirm" type="password"
										path="passwordConfirm" class="form-control input_pass"
										placeholder="Nhập lại mật khẩu"></form:input>
									<div hidden="">
										<form:errors path="passwordConfirm"></form:errors>
									</div>
								</div>
							</spring:bind>
						</div>
					</form:form>
				</div>
				<div class="d-flex justify-content-center mt-2 login_container">
					<button id="btn_submit_register" type="button" name="button"
						class="btn login_btn">Đăng ký</button>
				</div>
				<div class="mt-4">
					<div class="d-flex justify-content-center links">
						Đã có tài khoản? <a href="${contextPath}/login" class="ml-2"
							id="login">Đăng nhập</a>
					</div>
					<!-- <div class="d-flex justify-content-center links">
						<a href="#">Forgot your password?</a>
					</div> -->
				</div>
			</div>
		</div>
	</div>

	<footer id="footer">
		<div class="row" style="margin: 0">
			<div class="col-md-8">
				<div class="row">
					<div class="col-md-1 offset-md-2"
						style="max-width: 100%; padding: 0;">
						<div class="st_text">
							<span>S</span><span>T</span>
						</div>
					</div>
					<div class="col-md-8">
						<p>
							Địa chỉ: Bộ môn Công nghệ phần mềm, <a
								href="https://fita.vnua.edu.vn/" target="_blank">Khoa CNTT</a>,
							<a href="https://vnua.edu.vn/" target="_blank">Học viện Nông
								nghiệp Việt Nam</a> <br />ĐT: 0912 817 498 - Email:
							stdse@vnua.edu.vn - Website: st-dse.vnua.edu.vn
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-4" style="text-align: center; padding-top: 25px;">
				<p>
					Copyright © 2019 <a href="https://st-dse.vnua.edu.vn/"
						target="_blank">ST-DSE</a>. All rights reserved
				</p>
			</div>
		</div>
	</footer>
	<input type="hidden" id="error_message" value='${error}' />
	<input type="hidden" id="success_message" value='${success}' />
	<input type="hidden" id="info_message" value='${info}' />
	<input type="hidden" id="sendemail" value='${sendmail}' />
	<script type="text/javascript"
		src="${contextPath}/resources/js/dangky.js"></script>
</body>
<div id="spinner" class="spinner" style="display: none;"></div>
</html>
