<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="container">
	<div class="row row-calendar">
		<div class="chucnang tdExpandInBounce animated">
			<div class="row">
				<div class="col-md-12">
					<c:if test="${not empty confirmationMessage}">
						<script type="text/javascript">
							toastr.success('${confirmationMessage}');
						</script>
					</c:if>
					<c:if test="${not empty invalidToken}">
						<script type="text/javascript">
							toastr.error('${invalidToken}', 'Lỗi');
						</script>
					</c:if>
					<%-- <c:if test="${empty invalidToken}"> --%>
					<form method="POST" action="${contextPath}/change_password"
						onSubmit="return checkPassword()" class="change_password">
						<h1 style="color: white;">Đổi mật khẩu</h1>
						<div class="themlichtkb">
							<label class="mt-2" for="masv">Mật khẩu cũ:</label> <input
								type="password" class="form-control" name="oldPass" id="oldPass">
							<label class="mt-2" for="masv">Mật khẩu mới:</label> <input
								type="password" class="form-control" name="newPass" id="newPass">
							<label class="mt-2" for="masv">Nhập lại mật khẩu mới:</label> <input
								type="password" class="form-control" name="renewPass"
								id="renewPass">
							<button type="submit" id="btnSavePass"
								class="btn btn-primary mt-4 float-right w-25">Đổi mật
								khẩu</button>
							<div class="backhome">
								<a href="${contextPath}/home">Dùng các chức năng khác</a>
							</div>
						</div>
					</form>
					<%-- </c:if> --%>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function checkPassword() {
		console.log('boa bla')
		oldpassword = $("#oldPass").val();
		password = $("#newPass").val();
		repassword = $("#renewPass").val();
		if (oldpassword == "") {
			toastr.error('Mật khẩu cũ không được rỗng', 'Lỗi!');
			return false;
		} else if (password == "") {
			toastr.error('Mật khẩu mới không được rỗng', 'Lỗi!');
			return false;
		} else if (repassword == "") {
			toastr.error('Nhập lại mật khẩu mới không được rỗng', 'Lỗi!');
			return false;
		}
		if (password != repassword) {
			toastr.error('Mật khẩu không trùng khớp', 'Lỗi!');
			return false;
		}

		if (password.length < 8) {
			toastr.error('Mật khẩu phải có trên 8 kí tự', 'Lỗi!')
			console.log("213");
			return false;
		}
		if (password.length > 20) {
			toastr.error('Mật khẩu phải ngắn hơn 20 kí tự', 'Lỗi!');
			return false;
		}
		return true;
	}
</script>
