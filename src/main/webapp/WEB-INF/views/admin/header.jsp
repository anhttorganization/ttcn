<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<a style="color: #fff;font-weight: bold;" class="navbar-brand" href="${contextPath}/admin/user">Trang chủ</a>
<div class="navbar-collapse" id="navbarSupportedContent">
	<ul class="left_menu_calendar">
		<li class="nav-item"><a class="nav-link" href="${contextPath}/admin/semester/index">Học kỳ</a></li>
		<li class="nav-item"><a class="nav-link" href="${contextPath}/admin/slot/index">Tiết học</a></li>
		<li class="nav-item"><a class="nav-link" href="${contextPath}/admin/user">Người dùng</a></li>
	</ul>
	<ul class="navbar-nav">
		<li class="nav-item dropdown"><a
			class="nav-link dropdown-toggle profilenav" href="#"
			id="navbarDropdown" role="button" data-toggle="dropdown"
			aria-haspopup="true" aria-expanded="false"><sec:authentication
					property="principal.lastName" /> <sec:authentication
					property="principal.firstName" /></a>
			<div class="dropdown-menu" aria-labelledby="navbarDropdown">
				<a class="dropdown-item" href="#"></i> Thông tin</a> <a
					class="dropdown-item" href="${contextPath}/change_password"></i> Đổi mật khẩu</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="${contextPath}/login?logout"><i
					class="fas fa-sign-out-alt"></i> Đăng xuất</a>
			</div></li>
	</ul>
</div>