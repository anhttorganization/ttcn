<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<link rel="stylesheet"
	href="${contextPath}/stcalendar/resources/css/changeprofile.css" />
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="container">
	<div class="row row-calendar">
		<div class="chucnang tdExpandInBounce animated">
			<div class="row">
				<div class="col-md-12">
					<!-- Modal update gmail -->
					<div class="modal fade" id="modal-update-email" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalCenterTitle"
						aria-hidden="true">
						<div class="modal-dialog modal-dialog-top" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<!-- <h5 class="modal-title" id="exampleModalLongTitle">Sửa
										thông tin Gmail</h5> -->
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="Post" action="change_profile/email/update"
									id="update_email_form">
									<input type="hidden" id="msgSuccess" value="${msgSuccess}"
										disabled="disabled"> <input type="hidden" id="msg"
										value="${msg}" disabled="disabled">
									<div class="modal-body">
										<label for="staticEmail2"></label> <input type="text"
											class="form-control" placeholder="Email" id="email"
											name="email"> <label for="staticEmail2"></label>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">Hủy</button>
										<button id="btn_submit_update_email" type="submit"
											class="btn btn-primary">Lưu</button>
									</div>
								</form>
							</div>
						</div>
					</div>

					<!-- Modal update first name -->
					<div class="modal fade" id="modal-update-firstname" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalCenterTitle"
						aria-hidden="true">
						<div class="modal-dialog modal-dialog-top" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLongTitle">Sửa tên</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="Post" action="change_profile/firstname/update"
									id="update_firstname_form">
									<input type="hidden" id="msgSuccess" value="${msgSuccess}"
										disabled="disabled"> <input type="hidden" id="msg"
										value="${msg}" disabled="disabled">
									<div class="modal-body">
										<label for="staticEmail2"></label> <input type="text"
											class="form-control" id="firstName" name="firstName">
										<label for="staticEmail2"></label>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">Hủy</button>
										<button id="btn_submit_update_firstname" type="submit"
											class="btn btn-primary">Lưu</button>
									</div>
								</form>
							</div>
						</div>
					</div>

					<!-- Modal update last name -->
					<div class="modal fade" id="modal-update-lastname" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalCenterTitle"
						aria-hidden="true">
						<div class="modal-dialog modal-dialog-top" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLongTitle">Sửa
										thông tin Họ và tên đệm</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="Post" action="change_profile/lastname/update"
									id="update_lastname_form">
									<input type="hidden" id="msgSuccess" value="${msgSuccess}"
										disabled="disabled"> <input type="hidden" id="msg"
										value="${msg}" disabled="disabled">
									<div class="modal-body">
										<label for="staticEmail2"></label> <input type="text"
											class="form-control" id="lastName" name="lastName"> <label
											for="staticEmail2"></label>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-dismiss="modal">Hủy</button>
										<button id="btn_submit_update_lastname" type="submit"
											class="btn btn-primary">Lưu</button>
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- Change Profile-->
					<form action="change_profile" onSubmit="return checkAcount()"
						method="post">
						<div class="title">
							<h1>Thông tin cá nhân</h1>
						</div>
						<div class="content">
							<div class="content-intput">
								<span class='label_content'>Gmail</span> <span
									class='value_content'><sec:authentication
										property="principal.email" /></span>
								<!-- <a class="edit_content"
									role="button" href="#" data-toggle="modal"
									onclick='showModalUpdateEmail(${user.getId()}, "${user.getEmail()}")'>Chỉnh
									sửa</a> -->
							</div>
							<div class="content-intput">
								<span class='label_content'>Họ và tên đệm</span> <span
									class='value_content'><sec:authentication
										property="principal.lastName" /></span> <a class="edit_content"
									role="button" href="#" data-toggle="modal"
									onclick='showModalUpdateLastName(${user.getId()}, "${user.getLastName()}")'>Chỉnh
									sửa</a>
							</div>
							<div class="content-intput">
								<span class='label_content'>Tên</span> <span
									class='value_content'><sec:authentication
										property="principal.firstName" /></span> <a class="edit_content"
									role="button" href="#" data-toggle="modal"
									onclick='showModalUpdateFirstName(${user.getId()}, "${user.getFirstName()}")'>Chỉnh
									sửa</a>
							</div>
							<div class="btn-group-custom">
								<button type="button" onclick="quay_lai_trang_truoc()"
									class="btn-back" data-dismiss="modal">Quay lại</button>
							</div>
						</div>
					</form>
					<%-- </c:if> --%>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- page-content" -->
<script type="text/javascript" src="js/myaccount.js"></script>
<script type="text/javascript"
	src="${contextPath}/resources/js/updateprofile.js"></script>
<script>
	function quay_lai_trang_truoc() {
		history.back();
	}
</script>
<script>
	$(document).ready(function() {
		message();
	});
	function message() {
		var msgSuccess = document.getElementById("msgSuccess").value;
		if (msgSuccess != null && msgSuccess != undefined
				&& msgSuccess.trim() != "") {
			toastr.success(msgSuccess);
		}

		var msg = document.getElementById("msg").value;
		if (msg != null && msg != undefined && msg.trim() != "") {
			toastr.warning(msg);
		}
	}
</script>
</body>
</html>