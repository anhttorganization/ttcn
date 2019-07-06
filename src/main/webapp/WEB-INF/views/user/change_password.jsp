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
							alert('${confirmationMessage}');
						</script>
					</c:if>
					<c:if test="${empty invalidToken}">
						<form method="POST" action="${contextPath}/change_password"
							class="change_password">
							<h1>Đổi mật khẩu</h1>
							<div class="themlichtkb">
								<form:form method="POST" action="change_password">
									<label class="mt-2" for="masv">Mật khẩu cũ:</label>
									<input type="password" class="form-control" name="oldPass"
										id="oldPass">
									<label class="mt-2" for="masv">Mật khẩu mới:</label>
									<input type="password" class="form-control" name="newPass"
										id="newPass">
									<label class="mt-2" for="masv">Nhập lại mật khẩu mới:</label>
									<input type="password" class="form-control" name="renewPass"
										id="renewPass">
									<button type="submit" id="btnSavePass"
										class="btn btn-primary mt-4 float-right w-25">Đổi mật
										khẩu</button>
								</form:form>
								<div class="backhome">
									<a href="../home">Dùng các chức năng khác</a>
								</div>
							</div>
						</form>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function savePass() {
		$("#btnSavePass").click(function(event) {
			event.preventDefault();
			oldpassword = $("#oldPass").val();
			password = $("#newPass").val();
			repassword = $("#renewPass").val();
			if (oldpassword == "") {
				toastr.error('Mật khẩu cũ không được rỗng', 'Lỗi!')
			} else if (password == "") {
				toastr.error('Mật khẩu mới không được rỗng', 'Lỗi!')
			} else if (repassword == "") {
				toastr.error('Nhập lại mật khẩu mới không được rỗng', 'Lỗi!')
			}
			if (password != repassword) {
				toastr.error('Mật khẩu không trùng khớp', 'Lỗi!')
			}
			
			if (password.length < 8) {
				toastr.error('Mật khẩu dài trên 8 kí tự', 'Lỗi!')
				console.log("213");
			}
			if (password.length > 20) {
				toastr.error('Mật khẩu ngắn hơn 20 kí tự', 'Lỗi!')
			}
		});
	}
	savePass();
</script>
