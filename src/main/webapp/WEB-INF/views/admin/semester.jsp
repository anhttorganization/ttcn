<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="container mt-4">
	<div class="row">
		<nav
			class="navbar navbar-light bg-light justify-content-between w-100">
			<button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter">Thêm học kỳ mới</button>
		
			<!-- Modal thêm mới start -->
			<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-top" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLongTitle">Thêm	học kỳ mới</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<form action="add" method="post" onsubmit="addSemester()" >
						<div class="card-block p-3">
							<label class="font-weight-bold" for="inputGroupSelect01">ID kì học: </label>
							<input type="text" class="form-control" name="id" id="id"/>
							
							<label class="font-weight-bold" for="inputGroupSelect01">Tên kì học: </label>
							<input type="text" class="form-control" name="name" id="name"/>
							
							<label class="font-weight-bold mt-1" for="inputGroupSelect01">Thời gian bắt đầu: </label>
							<div class="input-group date" id="thoigianbatdau" data-target-input="nearest">
							
								<input type="text" class="form-control datetimepicker-input" data-target="#thoigianbatdau" id="date"/>
								<div class="input-group-append" data-target="#thoigianbatdau" data-toggle="datetimepicker">
									<div class="input-group-text"><i class="fas fa-clock"></i></div>
								</div>
							</div>
							<script type="text/javascript">
								$(function () {
									$('#thoigianbatdau').datetimepicker({
										format : "DD/MM/YYYY"
									});
								});
							</script>

					</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal"  id="close">Close</button>
							<button type="button" class="btn btn-primary" onclick="addSemester()">Thêm mới</button>
						</div>
						</form>
					</div>
				</div>
			</div>
			<!-- Modal thêm mới end -->
			
			
			<!-- Modal update start-->
			<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
				<div class="modal-dialog modal-dialog-top" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLongTitle">Thêm	học kỳ mới</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<form action="add" method="post" onsubmit="addSemester()" >
						<div class="card-block p-3">
							<label class="font-weight-bold" for="inputGroupSelect01">ID kì học: </label>
							<input type="text" class="form-control" name="id" id="id_update" readonly="readonly"/>
							
							<label class="font-weight-bold" for="inputGroupSelect01">Tên kì học: </label>
							<input type="text" class="form-control" name="name" id="name_update"/>
							
							<label class="font-weight-bold mt-1" for="inputGroupSelect01">Thời gian bắt đầu: </label>
							<div class="input-group date" id="thoigianbatdau_update" data-target-input="nearest">
							
								<input type="text" class="form-control datetimepicker-input" data-target="#thoigianbatdau_update" id="date_update"/>
								<div class="input-group-append" data-target="#thoigianbatdau_update" data-toggle="datetimepicker">
									<div class="input-group-text"><i class="fas fa-clock"></i></div>
								</div>
							</div>
							<script type="text/javascript">
								$(function () {
									$('#thoigianbatdau_update').datetimepicker({
										format : "DD/MM/YYYY"
									});
								});
							</script>

					</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal"  id="close_update">Close</button>
							<button type="button" class="btn btn-primary" id="submit_update">Cập nhật</button>
						</div>
						</form>
					</div>
				</div>
			</div>
			
			<!-- Modal update end-->
			
		<%-- 	<!-- Modal xóa start -->
			<div class="modal fade" id="exampleModal_${listResult.get(i-1).getId()}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Xác nhận xóa	tài khoản</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							Bạn có chắc chắn muốn xóa học kỳ <span class="font-weight-bold text-danger">${listResult.get(i-1).getName()}</span>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"	data-dismiss="modal">Quay lại</button>
							<button type="button" class="btn btn-danger" onclick="deleteSemester('${contextPath}/admin/semester/delete/${listResult.get(i-1).getId()}', 'exampleModal_${listResult.get(i-1).getId()}')">Chắc chắn</button>
						</div>
					</div>
				</div>
			</div>
			<!-- Modal xóa end -->
			 --%>
			
			<%-- <form class="form-inline">
				<input class="form-control mr-sm-2" type="search"
					placeholder="Tìm kiếm" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Tìm kiếm</button>
			</form> --%>
		</nav>

		<table class="table">
			<c:if test="${listResult.size()==0 }">
				<c:out value="Không có dữ liệu để hiển thị"></c:out>
			</c:if>
			<c:if test="${listResult.size()>0 }">
				<thead class="thead-light">
					<tr>
						<!-- <th scope="col"><input type="checkbox" name=""></th> -->
						<th scope="col">Id</th>
						<th scope="col">Học kỳ</th>
						<th scope="col">Thời gian bắt đầu</th>
						<th scope="col">Hoạt động</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" begin="1" end="${listResult.size()}">
						<tr>
							<td>${listResult.get(i-1).getId()}</td>
							<td><a href="#">${listResult.get(i-1).getName()}</a></td>
						<%-- 	<% System.out.println(request.getAttribute(listResult.get(i-1))); %>
 --%>						
							<td>${listResult.get(i-1).getStartDate()}</td>
							<td>
								<ul class="user_tool">
									<li><a role="button" href="#" onclick="editSemester('${listResult.get(i-1).getId()}', '${listResult.get(i-1).getName()}', '${listResult.get(i-1).getStartDate()}')" data-toggle="modal" data-target="#updateModal" >Chỉnh sửa</a></li>
									<li><a role="button" href="#" data-toggle="modal" data-target="#exampleModal_${listResult.get(i-1).getId()}">Xóa</a></li>
								</ul>
							</td>
						</tr>
						
						<!-- Modal hiển thị -->
						<div class="modal fade" id="exampleModal_${listResult.get(i-1).getId()}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog" role="document">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title" id="exampleModalLabel">Xác nhận xóa	tài khoản</h5>
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
									</div>
									<div class="modal-body">
										Bạn có chắc chắn muốn xóa học kỳ <span class="font-weight-bold text-danger">${listResult.get(i-1).getName()}</span>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"	data-dismiss="modal">Quay lại</button>
										<button type="button" class="btn btn-danger" onclick="deleteSemester('${contextPath}/admin/semester/delete/${listResult.get(i-1).getId()}', 'exampleModal_${listResult.get(i-1).getId()}')">Chắc chắn</button>
									</div>
								</div>
							</div>
						</div>
						<!-- Modal hiển thị -->
						
					</c:forEach>
				</tbody>
			</c:if>
		</table>
	<!-- 	<nav aria-label="Page navigation User" class="m-auto">
			<ul class="pagination justify-content-center">
				<li class="page-item disabled"><a class="page-link" href="#"
					tabindex="-1">Trang trước</a></li>
				<li class="page-item active"><a class="page-link" href="#">1</a></li>
				<li class="page-item"><a class="page-link" href="#">2</a></li>
				<li class="page-item"><a class="page-link" href="#">3</a></li>
				<li class="page-item"><a class="page-link" href="#">Trang sau</a></li>
			</ul>
		</nav> -->
	</div>
</div>
	<script>
		var number = ${number};
		console.log(number)
	</script>

<script type="text/javascript" src="${contextPath}/resources/js/semester.js"></script>