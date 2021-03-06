<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ page contentType="text/html; charset=utf-8" language="java" import="javax.xml.parsers.DocumentBuilderFactory,javax.xml.parsers.DocumentBuilder,org.w3c.dom.*,vn.edu.vnua.dse.calendar.common.AppUtils" errorPage=""%>
<%
	String contact = AppUtils.getString("contact-menu").trim();
	String fita = AppUtils.getString("fita-name-footer").trim();
	String fitaURL = AppUtils.getString("fita-website-url").trim();	
	String department = AppUtils.getString("department-name-footer").trim();
	String vnua = AppUtils.getString("vnua-name-footer").trim();
	String phone = AppUtils.getString("contact-phone").trim();
	String email = AppUtils.getString("st-email").trim();
	String website = AppUtils.getString("st-website").trim();
	String websiteURL = AppUtils.getString("st-website-url").trim();
	String copyright = AppUtils.getString("copyright").trim();
	String vnuaURL = AppUtils.getString("vnua-website-url").trim();
%>

<!DOCTYPE html>
<html>

<head>
<title>ST Calendar</title>
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

				<div class="d-flex justify-content-center form_container">
					<form method="POST" action="${contextPath}/j_spring_security_check"
						class="form-signin" id="login_form">
						<div class="form-group ${error != null ? 'has-error' : ''}">
							<div class="input-group mb-3">
								<div class="input-group-append">
									<span class="input-group-text"><i class="fas fa-user"></i></span>
								</div>

								<input id="email" name="email" type="text"
									class="form-control input_user" placeholder="Tài khoản"
									autofocus="true" />
							</div>
							<div class="input-group mb-2">
								<div class="input-group-append">
									<span class="input-group-text"><i class="fas fa-key"></i></span>
								</div>
								<input id="password" name="password" type="password"
									class="form-control input_pass" placeholder="Mật khẩu" />

							</div>
							<div>
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</div>
							<!-- <div class="form-group">
								<div class="custom-control custom-checkbox">
									<input type="checkbox" class="custom-control-input"
										id="customControlInline"> <label
										class="custom-control-label" for="customControlInline">Nhớ
										tài khoản</label>
								</div>
							</div> -->
						</div>
					</form>
				</div>
				<div class="d-flex justify-content-center mt-3 login_container">
					<button id="btn_submit" class="btn login_btn" type="submit"
						name="button">Đăng nhập</button>
				</div>

				<div class="mt-4">
					<div class="d-flex justify-content-center links">
						Bạn chưa có tài khoản?<a href="${contextPath}/register"
							class="ml-2">Đăng ký</a>
					</div>
					<div class="d-flex justify-content-center links">
						<a href="${contextPath}/fogot_password">Quên mật khẩu?</a>
					</div>
					<span id="message" hidden>${message}</span>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="${contextPath}/resources/js/dangnhap.js"></script>
	<footer id="footer">
		<div class="row" style="margin: 0">
			<div class="col-md-8">
				<div class="row">
					<div class="col-md-1 offset-md-2"
						style="max-width: 100%; padding: 0;">
						<div class="st_text">
							<a href="<%=websiteURL%>" target="_blank"><span>S</span><span>T</span></a>
						</div>
					</div>
					<div class="col-md-8">
						<p>Địa chỉ: <%=department%>, <a href="<%=fitaURL%>" target="_blank"><%=fita%></a>,  <a href="<%=vnuaURL%>" target="_blank"><%=vnua%></a> <br />ĐT: <%=phone%> - Email: <%=email%> - Website: <%=website%></p>
					</div>
				</div>
			</div>
			<div class="col-md-4" style="text-align: center; padding-top: 25px;">
				<p>
					Copyright © 2019 <a href="<%=websiteURL%>"
						target="_blank"><%=copyright%></a>. All rights reserved
				</p>
			</div>
		</div>
	</footer>
	<input type="hidden" id="error_message" value='${error}' />
	<input type="hidden" id="success_message" value='${success}' />
	<input type="hidden" id="info_message" value='${info}' />
</body>
<div id="spinner" class="spinner" style="display: none;"></div>