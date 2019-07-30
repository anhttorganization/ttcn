<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<div class="container mt-3">
		<div class="card mb-4 w-100">
			<div class="card-block">
				<ul class="ul_option">
					<li><a href="admin.html">Thiết lập tiết học</a></li>
					<li class="active"><a href="">Thiết lập học kì</a></li>
				</ul>
			</div>
		</div>
		<!-- Thiết lập thời gian bắt đầu, kết thúc-->
		<div class="row">
			<div class="col-md-4">
				<div class="card mb-4">
					<h4 class="card-title border-bottom p-2 bg-primary">Thêm kì học</h4>
					<div class="card-block p-3">
						<form>
							<label class="font-weight-bold" for="inputGroupSelect01">ID kì học: </label>
							<input type="text" class="form-control"/>
							<label class="font-weight-bold" for="inputGroupSelect01">Tên kì học: </label>
							<input type="text" class="form-control"/>
							<label class="font-weight-bold mt-1" for="inputGroupSelect01">Thời gian bắt đầu: </label>
							<div class="input-group date" id="thoigianbatdau" data-target-input="nearest">
								<input type="text" class="form-control datetimepicker-input" data-target="#thoigianbatdau"/>
								<div class="input-group-append" data-target="#thoigianbatdau" data-toggle="datetimepicker">
									<div class="input-group-text"><i class="fas fa-clock"></i></div>
								</div>
							</div>
							<button type="submit" class="btn btn-primary mb-2 mt-3 float-right">Thêm tiết học</button>
							<script type="text/javascript">
								$(function () {
									$('#thoigianbatdau').datetimepicker({
										format: 'L'
									});
								});
							</script>

						</form>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="card">
					<div class="card-block">
						<table class="table lesson_table">
							<thead class="thead-light">
								<tr>
									<th scope="col">#ID</th>
									<th scope="col">Tên</th>
									<th scope="col">Thời gian bắt đầu</th>
									<th scope="col">Hoạt động</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th scope="row">20182</th>
									<td>Học kỳ 2 - Năm học 2018-2019</td>
									<td>28-12-2018</td>
									<td class="tool_lesson">
										<ul>
											<li><a href="">Sửa</a></li>
										<li><a href="">Xóa</a></li>
									</ul>
								</td>
							</tr>
							<tr>
									<th scope="row">20183</th>
									<td>Học kỳ 2 - Năm học 2018-2019</td>
									<td>28-12-2018</td>
									<td class="tool_lesson">
										<ul>
											<li><a href="">Sửa</a></li>
										<li><a href="">Xóa</a></li>
									</ul>
								</td>
						</tbody>
					</table>
					<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog" role="document">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
									<button type="button" class="close" data-dismiss="modal" aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
								<div class="modal-body">
									...
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
									<button type="button" class="btn btn-primary">Save changes</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
		<!-- Thiết lập kì học -->