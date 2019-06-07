<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="container">
	<div class="row row-calendar">
		<div class="col-md-4 col-sm-6 card-calendar mt-4">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Xem lịch</h5>
					<p class="card-text">Đang phát triển.</p>
				</div>
			</div>
		</div>
		<div class="col-md-4 col-sm-6 card-calendar mt-4">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Thêm thời khóa biểu</h5>
					<p class="card-text">Đang phát triển.</p>
					<a class="link-function" href="schedule/create"></a>
				</div>
			</div>
		</div>
		<div class="col-md-4 col-sm-6 card-calendar mt-4">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Thêm lịch thi</h5>
					<p class="card-text">With supporting text below as a natural
						lead-in to additional content.</p>
					<a class="link-function" href="testschedule/create"></a>
				</div>
			</div>
		</div>
		<div class="col-md-4 col-sm-6 card-calendar mt-4">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Thêm lịch coi thi</h5>
					<p class="card-text">Đang phát triển.</p>
					<!-- <a class="link-function" href=""></a> -->
				</div>
			</div>
		</div>
		<div class="col-md-4 col-sm-6 card-calendar mt-4">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">Thêm lịch từ excel</h5>
					<p class="card-text">With supporting text below as a natural
						lead-in to additional content.</p>
					<a class="link-function" href="import/create"></a>
				</div>
			</div>
		</div>
	</div>
	<p>${authError}</p>
	<c:if test="${not empty setPermision}">
		<a href="${setPermision}" target="_blank">Refresh token đã hết
			hạn, vui lòng nhấn vào đấy để xóa quyền truy cập của ứng dụng
			calendarapi và thử lại!</a>
	</c:if>
</div>

<c:if test="${not empty authError}">
	<script type="text/javascript">
		alert("Cấp quyền không thành công. Vui lòng thử lại!");
	</script>
</c:if>

<c:if test="${not empty authSuccess}">
	<script type="text/javascript">
		alert("Cấp quyền thành công!");
	</script>
</c:if>

