<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<div class="container mt-4">
	<div class="row">
		<nav
			class="navbar navbar-light bg-light justify-content-between w-100">
			<button type="button" class="btn btn-success" data-toggle="modal"
				data-target="#exampleModalCenter">Thêm người dùng mới</button>
			<!-- Modal -->
			<form action="user/add" method="post"
				id="add_user_form">
				<input type="hidden" id="msgSuccess" value="${msgSuccess}"
					disabled="disabled"> <input type="hidden" id="msg"
					value="${msg}" disabled="disabled">
				<div class="modal fade" id="exampleModalCenter" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalCenterTitle"
					aria-hidden="true">
					<div class="modal-dialog modal-dialog-top" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLongTitle">Thêm
									người dùng mới</h5>
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
							</div>
							<div class="modal-body">
								<label for="staticEmail2"></label> <input type="text"
									class="form-control" placeholder="Email" id="email_add"
									name="email"> <label for="staticEmail2"></label><input
									type="text" class="form-control" placeholder="Tên"
									id="firstname_add" name="firstName"> <label
									for="staticEmail2"></label><input type="text"
									class="form-control" placeholder="Họ đệm" id="lastname_add"
									name="lastName"> <label for="staticEmail2"></label><input
									type="password" class="form-control" placeholder="Mật khẩu"
									id="password_add"> <label for="staticEmail2"></label><input
									type="password" name="password" class="form-control"
									placeholder="Nhập lại mật khẩu" id="passwordConfirm"> <label
									for="staticEmail2">Quyền: </label> <input type="radio"
									name="role" id="role_admin_add" value="ROLE_ADMIN"> Quản
								trị viên <input type="radio" name="role" id="role_user_add"
									value="ROLE_USER"> Người dùng
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Hủy</button>
								<button id="btn_submit_add_user" type="submit"
									class="btn btn-primary">Thêm mới</button>
							</div>
						</div>
					</div>
				</div>
			</form>
			<!-- Modal Change User-->
			<div class="modal fade" id="modal-update-user" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-top" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLongTitle">Sửa thông
								tin</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<form method="Post" action="user/update" id="update_user_form">
						<div class="modal-body">
							<label for="staticEmail2"></label> <input type="text"
								class="form-control" placeholder="Email" id="email" name="email">
							<label for="staticEmail2"></label><input type="text"
								class="form-control" placeholder="Tên" id="firstName"
								name="firstName"> <label for="staticEmail2"></label><input
								type="text" class="form-control" placeholder="Họ đệm"
								id="lastName" name="lastName"> <label
								for="staticEmail2">Quyền: </label> <input type="radio"
									name="role" id="role_admin" value="ROLE_ADMIN"> Quản
								trị viên <input type="radio" name="role" id="role_user"
									value="ROLE_USER"> Người dùng
							</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Hủy</button>
							<button id="btn_submit_update_user" type="submit" class="btn btn-primary">Lưu</button>
						</div>
						</form>
					</div>
				</div>
			</div>
			<%-- <!-- Tim kiem nguoi dung theo firstname -->
			<form action="quan-ly-nguoi-dung/user.do" method="post"
				class="form-inline">
				<input path="firstName" name="firstName"
					class="form-control mr-sm-2" type="search" placeholder="Tên"
					aria-label="Search" />
				<button value="Search" class="btn btn-outline-success my-2 my-sm-0"
					type="submit">Tìm kiếm</button>
			</form> --%>
		</nav>
		<!-- Modal xóa tài khoản -->
		<div class="modal fade" id="modalDelete" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<form action="user/delete" method="post">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Xác nhận xóa
								tài khoản</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body"></div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Hủy</button>
							<button type="submit" class="btn btn-danger">Đồng ý</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!-- Modal hiển thị -->
		<table class="table">
			<c:if test="${listResult.size()==0 }">
				<c:out value="Không có dữ liệu để hiển thị"></c:out>
			</c:if>
			<c:if test="${listResult.size()>0 }">
				<thead class="thead-light">
					<tr>
				<th scope="col">#</th>
						<th scope="col">Tên người dùng</th>
						<th scope="col">Email</th>
						<th scope="col">Vai trò</th>
						<th scope="col">Hoạt động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" begin="1" end="${listResult.size()}">
						<tr>
					<td scope="row">${i}</td>
							<td><a href="#">${listResult.get(i-1).getFirstName()}
									${listResult.get(i-1).getLastName()}</a></td>
							<td>${listResult.get(i-1).getEmail()}</td>
							<td>${listResult.get(i-1).getRoleName()}</td>
							<td>
								<ul class="user_tool">
									<li><a role="button" href="#" data-toggle="modal"
										onclick='showModalUpdateUser(${listResult.get(i-1).getId()}, "${listResult.get(i-1).getFirstName()}", "${listResult.get(i-1).getLastName()}", "${listResult.get(i-1).getEmail()}", "${listResult.get(i-1).getRoleName()}")'>Chỉnh sửa</a></li>
									<li><a role="button" href="#" data-toggle="modal"
										onclick='showModalDeleteUser(${listResult.get(i-1).getId()})'>Xóa</a></li>
								</ul>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</c:if>
		</table>
		<!-- <nav aria-label="Page navigation User" class="m-auto">
			<ul class="pagination justify-content-center">
				<li class="page-item disabled"><a class="page-link" href="#"
					tabindex="-1">Trang trước</a></li>
				<li class="page-item active"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">Trang
						sau</a></li>
			</ul>
		</nav> -->
	</div>
</div>
<script type="text/javascript"
	src="${contextPath}/resources/js/adduser.js"></script>
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