<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<div class="container">
	<div class="row row-calendar">
		<div class="chucnang tdExpandInBounce animated">
			<div class="row">
				<div class="col-md-12">
					<c:if test="${not empty msgSuccess}">
						<script type="text/javascript">
							toastr.success('${msgSuccess}');
						</script>
					</c:if>
					<input type="hidden" id="msg" value="${msg}" disabled="disabled">

					<!-- Modal update gmail -->
					<div class="modal fade" id="modal-update-email" tabindex="-1"
						role="dialog" aria-labelledby="exampleModalCenterTitle"
						aria-hidden="true">
						<div class="modal-dialog modal-dialog-top" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLongTitle">Gmail</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="Post" action="change_profile/email/update" id="update_email_form">
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
									<h5 class="modal-title" id="exampleModalLongTitle">Họ và tên đệm</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="Post" action="change_profile/firstname/update" id="update_firstname_form">
									<div class="modal-body">
										<label for="staticEmail2"></label> <input
									type="text" class="form-control" 
									id="firstName" name="firstName"> <label for="staticEmail2"></label>
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
									<h5 class="modal-title" id="exampleModalLongTitle">Tên</h5>
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<form method="Post" action="change_profile/lastname/update" id="update_lastname_form">
									<div class="modal-body">
										<label for="staticEmail2"></label> <input
									type="text" class="form-control" 
									id="lastName" name="lastName"> <label for="staticEmail2"></label>
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
						<h1>Thông tin cá nhân</h1>
						<div class="themlichtkb">
							<table>
								<tr>
									<th>Gmail</th>
									<td><sec:authentication property="principal.email" /></td>
									<td><a role="button" href="#" data-toggle="modal"
										onclick='showModalUpdateEmail(${user.getId()}, "${user.getEmail()}")'>Chỉnh
											sửa</a>
								</tr>
								<tr>
									<th>Họ đệm</th>
									<td><sec:authentication property="principal.firstName" /></td>
									<td><a role="button" href="#" data-toggle="modal"
										onclick='showModalUpdateFirstName(${user.getId()}, "${user.getFirstName()}")'>Chỉnh sửa</a></td>
								</tr>
								<tr>
									<th>Tên</th>
									<td><sec:authentication property="principal.lastName" /></td>
									<td><a role="button" href="#" data-toggle="modal"
										onclick='showModalUpdateLastName(${user.getId()}, "${user.getLastName()}")'>Chỉnh sửa</a></td>
								</tr>
							</table>
							<div class="btn-group-custom"
								style="width: fit-content; margin: 0 auto; margin-top: 30px;">
								<button type="button" onclick="quay_lai_trang_truoc()"
									class="btn btn-secondary" data-dismiss="modal"
									style="float: left">Quay lại</button>
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