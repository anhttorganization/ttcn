<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<div class="container mt-4">
	<div class="row">
		<nav
			class="navbar navbar-light bg-light justify-content-between w-100">
			<button type="button" class="btn btn-success" data-toggle="modal"
				data-target="#exampleModalCenter">Thêm người dùng mới</button>
			<!-- Modal -->
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
							<label for="staticEmail2">Email:</label> <input type="text"
								class="form-control" placeholder="VD: user@gmail.com">
							<label for="staticEmail2">Tên:</label> <input type="text"
								class="form-control" placeholder="VD: A"> <label
								for="staticEmail2">Họ đệm:</label> <input type="text"
								class="form-control" placeholder="VD: Nguyễn Văn"> <label
								for="staticEmail2">Mật khẩu:</label> <input type="password"
								class="form-control"> <label for="staticEmail2">Nhập lại mật khẩu:</label> <input type="password" class="form-control">
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary">Thêm mới</button>
						</div>
					</div>
				</div>
			</div>
			<form class="form-inline">
				<input class="form-control mr-sm-2" type="search"
					placeholder="Tìm kiếm" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Tìm kiếm</button>
			</form>
		</nav>
		<!-- Modal hiển thị -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLabel">Xác nhận xóa	tài khoản</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						Bạn có chắc chắn muốn xóa tài khoản <span
							class="font-weight-bold text-danger">Đặng Quốc Thắng</span>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">Quay lại</button>
						<button type="button" class="btn btn-danger">Chắc chắn</button>
					</div>
				</div>
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
						<th scope="col"><input type="checkbox" name=""></th>
						<th scope="col">Tên người dùng</th>
						<th scope="col">Email</th>
						<th scope="col">Vai trò</th>
						<th scope="col">Hoạt động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" begin="1" end="${listResult.size()}">
						<tr>
							<th scope="row"><input type="checkbox" name=""></th>
							<td><a href="#">${listResult.get(i-1).getFirstName()} ${listResult.get(i-1).getLastName()}</a></td>
							<td>${listResult.get(i-1).getEmail()}</td>
							<td>${listResult.get(i-1).getRoleName()}</td>
							<td>
								<ul class="user_tool">
									<li><a href="">Chỉnh sửa</a></li>
									<li><a role="button" href="#" data-toggle="modal"
										data-target="#exampleModal">Xóa</a></li>
								</ul>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</c:if>
		</table>
		<nav aria-label="Page navigation User" class="m-auto">
			<ul class="pagination justify-content-center">
				<li class="page-item disabled"><a class="page-link" href="#"
					tabindex="-1">Trang trước</a></li>
				<li class="page-item active"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">Trang
						sau</a></li>
			</ul>
		</nav>
	</div>
</div>