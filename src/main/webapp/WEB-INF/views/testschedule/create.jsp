<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="container">
	<div class="row row-calendar">
		<div class="chucnang tdExpandInBounce animated">
			<div class="row">
				<div class="col-md-12">
					<h1>Thêm lịch thi</h1>
					<div class="themlichtkb">
<%-- 						<form method="POST" action="${contextPath}/testschedule/create">
							<label class="mt-4" for="masv">Nhập mã sinh viên:</label> <input class="form-control"
								type="text" pattern=".{6,6}" title="Mã sinh viên bao gồm 6 kí tự" name="studentId">
							<button type="submit" class="btn btn-primary mt-4 float-right w-25">Thêm lịch thi</button>
						</form> --%>

						<form:form method="POST" modelAttribute="scheduleCreate" action="${contextPath}/testschedule/create" id="testschedule_create">
							<spring:bind path="studentId">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label class="mt-4" for="studentId">Mã sinh viên:</label>
									<form:input path="studentId" id="studentId" class="form-control"/>
									<form:errors path="studentId" />
								</div>
							</spring:bind>

							<button type="submit" class="btn btn-primary mt-4 float-right w-25" id="btn_submit">Thêm lịch</button>
						</form:form>
						<div class="backhome">
							<a href="../home">Dùng các chức năng khác</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<input type="hidden" id="error_message" value='${error}'/>
<input type="hidden" id="success_message" value='${success}'/>
<input type="hidden" id="info_message" value='${info}'/>

<div id="spinner" class="spinner" style="display: none;"></div>
<script type="text/javascript" src="${contextPath}/resources/js/testschedule.js"></script>

<%-- <c:choose>
	<c:when test="${not empty exist}">
		<script type="text/javascript">
			alert("Lịch đã tồn tại!");
		</script>
	</c:when>
	<c:when test="${not empty updateCalendar}">
		<script type="text/javascript">
			alert("Lịch thi có thay đổi, cập nhật thành công!");
		</script>
	</c:when>
	<c:when test="${not empty update}">
		<script type="text/javascript">
			alert("Website Đào tạo đang update dữ liệu!");
		</script>
	</c:when>
	<c:when test="${not empty error}">
		<script type="text/javascript">
			alert("Thêm lịch không thành công!");
		</script>
	</c:when>
	<c:when test="${not empty success}">
		<script type="text/javascript">
			alert("Thêm lịch thành công!");
		</script>
	</c:when>
	<c:otherwise>
		
	</c:otherwise>
</c:choose> --%>