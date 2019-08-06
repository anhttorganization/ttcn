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
				data-target="#exampleModalCenter">Thêm tiết học mới</button>
			<input type="hidden" id="msg" value="${msg}" disabled="disabled">
			<input type="hidden" id="msgSuccess" value="${msgSuccess}"
				disabled="disabled">
			<!-- Modal -->
			<div class="modal fade" id="exampleModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-top" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLongTitle">Thêm tiết
								học mới</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<form method="Post" action="add"
							onSubmit="return validateSlotTime()">
							<div class="modal-body">
								<div class="card-block p-3">

									<label class="font-weight-bold" for="inputGroupSelect01">Chọn
										tiết: </label> <select class="custom-select" id="inputGroupSelect01"
										name="id">
										<option value="1" selected>Tiết 1</option>
										<option value="2">Tiết 2</option>
										<option value="3">Tiết 3</option>
										<option value="4">Tiết 4</option>
										<option value="5">Tiết 5</option>
										<option value="6">Tiết 6</option>
										<option value="7">Tiết 7</option>
										<option value="8">Tiết 8</option>
										<option value="9">Tiết 9</option>
										<option value="10">Tiết 10</option>
										<option value="11">Tiết 11</option>
										<option value="12">Tiết 12</option>
										<option value="13">Tiết 13</option>

									</select> <label class="font-weight-bold mt-1" for="inputGroupSelect01">Thời
										gian bắt đầu: </label>
									<div class="input-group date" id="tietbatdau"
										data-target-input="nearest">
										<input id="startTime" name="startTime" type="text"
											class="form-control datetimepicker-input"
											data-target="#tietbatdau" />
										<div class="input-group-append" data-target="#tietbatdau"
											data-toggle="datetimepicker">
											<div class="input-group-text">
												<i class="fas fa-clock"></i>
											</div>
										</div>
									</div>
									<label class="font-weight-bold mt-1" for="inputGroupSelect01">Thời
										gian kết thúc: </label>
									<div class="input-group date" id="tietketthuc"
										data-target-input="nearest">
										<input id="endTime" name="endTime" type="text"
											class="form-control datetimepicker-input"
											data-target="#tietketthuc" />
										<div class="input-group-append" data-target="#tietketthuc"
											data-toggle="datetimepicker">
											<div class="input-group-text">
												<i class="fas fa-clock"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Close</button>
								<button type="submit" class="btn btn-primary">Thêm mới</button>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- Modal Update -->
			<div class="modal fade" id="modal-update-tiethoc" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-top" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLongTitle">Cập nhật
								tiết học</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<form method="Post" action="update"
							onSubmit="return validateSlotTimeUpdate()">
							<div class="modal-body">
								<div class="card-block p-3">

									<label class="font-weight-bold" for="inputGroupSelect01">Chọn
										tiết: </label> <select disabled class="custom-select"
										id="selectBoxUpdate" name="id">
										<option value="1" selected>Tiết 1</option>
										<option value="2">Tiết 2</option>
										<option value="3">Tiết 3</option>
										<option value="4">Tiết 4</option>
										<option value="5">Tiết 5</option>
										<option value="6">Tiết 6</option>
										<option value="7">Tiết 7</option>
										<option value="8">Tiết 8</option>
										<option value="9">Tiết 9</option>
										<option value="10">Tiết 10</option>
										<option value="11">Tiết 11</option>
										<option value="12">Tiết 12</option>
										<option value="13">Tiết 13</option>

									</select> <label class="font-weight-bold mt-1" for="inputGroupSelect01">Thời
										gian bắt đầu: </label>
									<div class="input-group date" id="tietbatdauupdate"
										data-target-input="nearest">
										<input id="startTimeUpdate" name="startTime" type="text"
											class="form-control datetimepicker-input"
											data-target="#tietbatdauupdate" />
										<div class="input-group-append"
											data-target="#tietbatdauupdate" data-toggle="datetimepicker">
											<div class="input-group-text">
												<i class="fas fa-clock"></i>
											</div>
										</div>
									</div>
									<label class="font-weight-bold mt-1" for="inputGroupSelect01">Thời
										gian kết thúc: </label>
									<div class="input-group date" id="tietketthucupdate"
										data-target-input="nearest">
										<input id="endTimeUpdate" name="endTime" type="text"
											class="form-control datetimepicker-input"
											data-target="#tietketthucupdate" />
										<div class="input-group-append"
											data-target="#tietketthucupdate" data-toggle="datetimepicker">
											<div class="input-group-text">
												<i class="fas fa-clock"></i>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-secondary"
									data-dismiss="modal">Close</button>
								<button type="submit" class="btn btn-primary">Lưu</button>
							</div>
						</form>
					</div>
				</div>
			</div>

<%-- 			<form class="form-inline">
				<input class="form-control mr-sm-2" type="search"
					placeholder="Tìm kiếm" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Tìm
					kiếm</button>
			</form> --%>
		</nav>
		<!-- Modal hiển thị -->
		<div class="modal fade" id="modalDelete" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog" role="document">
				<form action="delete" method="post">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Xác nhận xóa
								tiết học</h5>
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
						<!-- <th scope="col"><input type="checkbox" name=""></th> -->
						<th scope="col">#</th>
						<th scope="col">Tiết</th>
						<th scope="col">Thời gian bắt đầu</th>
						<th scope="col">Thời gian kết thúc</th>
						<th scope="col">Hoạt động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" begin="1" end="${listResult.size()}">
						<tr>
							<!-- <th scope="row"><input type="checkbox" name=""></th> -->
							<td>${listResult.get(i-1).getId()}</td>
							<td>Tiết ${listResult.get(i-1).getId()}</td>
							<td>${listResult.get(i-1).getStartTime()}</td>
							<td>${listResult.get(i-1).getEndTime()}</td>
							<td>
								<ul class="user_tool">
									<li><a href="#"
										onclick='showModalUpdateTietHoc(${listResult.get(i-1).getId()}, "${listResult.get(i-1).getStartTime()}", "${listResult.get(i-1).getEndTime()}")'>Chỉnh
											sửa</a></li>
									<li><a role="button" data-toggle="modal" href="#"
										onclick='showModalDeleteTietHoc(${listResult.get(i-1).getId()})'>Xóa</a></li>
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
<script type="text/javascript" src="${contextPath}/resources/js/slot.js"></script>
