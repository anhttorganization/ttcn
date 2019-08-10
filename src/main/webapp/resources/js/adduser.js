//adduser.js
function checkName(str) {
	partern = "[0-9~!@#$%^&*()-<>?]";
	newRe = new RegExp(partern, 'g')
	return newRe.test(str);
}

$(document)
		.ready(
				function() {

					function validateEmail(email) {
						var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
						return re.test(String(email).toLowerCase());
					}

					$("#btn_submit_add_user")
							.click(
									function(event) {
										event.preventDefault();

										if ($("#email_add").val() == "") {
											toastr
													.warning(
															'Vui lòng nhập địa chỉ Gmail!',
															'Thông báo');
										} else if ($("#username").val() == "") {
											toastr
													.warning(
															'Vui lòng nhập tên tài khoản!',
															'Thông báo');
										} else if ($("#firstname_add").val() == "") {
											toastr.warning(
													'Vui lòng nhập tên!',
													'Thông báo');
										} else if (checkName($("#firstname_add")
												.val())) {
											toastr
													.warning(
															'Tên không bao gồm ký tự số và ký tự đặc biệt',
															'Thông báo');
										} else if ($("#lastname_add").val() == "") {
											toastr
													.warning(
															'Vui lòng nhập họ và tên đệm!',
															'Thông báo');
										} else if (checkName($("#lastname_add")
												.val())) {
											toastr
													.warning(
															'Họ không bao gồm ký tự số và ký tự đặc biệt',
															'Thông báo');
										} else if ($("#password_add").val() == "") {
											toastr.warning(
													'Vui lòng nhập mật khẩu!',
													'Thông báo');
										} else if ($("#passwordConfirm").val() == "") {
											toastr
													.warning(
															'Vui lòng nhập lại mật khẩu lần nữa!',
															'Thông báo');
										} else if (!$("#role_admin_add").prop(
												"checked")
												&& !$("#role_user_add").prop(
														"checked")) {
											toastr.warning(
													'Vui lòng chọn quyền!',
													'Thông báo');
										} else {
											if ($("#email_add").val() != "") {
												if (!validateEmail($("#email_add")
														.val())) {
													toastr
															.warning(
																	'Vui lòng nhập đúng định dạng địa chỉ Gmail!',
																	'Thông báo');
												} else if ($("#password_add").val() != $(
														"#passwordConfirm")
														.val()) {
													toastr
															.warning(
																	'Mật khẩu nhập lại không trùng khớp!',
																	'Thông báo');
												} else {
													$("#add_user_form")
															.submit();
												}
											}
										}
									});
					var msg = $("#msg").text();
					if (msg) {
						toastr.warning(msg);
					}

					var success = $("#msgSuccess").text();
					if (success) {
						toastr.success(msgSuccess);
					}
				});
function showModalDeleteUser(id) {
	// show modal
	$('#modalDelete').modal('show');
	$('#modalDelete .modal-body').append(
			'<p>Bạn có chắc muốn xóa người dùng này?</p>');
	$('#modalDelete .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = " + id
					+ " />");

	$('#modalDelete #id').hide();
}

function showModalUpdateUser(id, firstName, lastName, email, role) {
	$('#modal-update-user').modal('show');
	$('#modal-update-user .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = " + id
					+ " />");
	$('#modal-update-user #email').val(email);
	$('#modal-update-user #firstName').val(firstName);
	$('#modal-update-user #lastName').val(lastName);
	if (role === 'Quản trị viên') {
		$('#modal-update-user #role_admin').prop('checked', true);
	}
	if (role === 'Người dùng') {
		$('#modal-update-user #role_user').prop('checked', true);
	}

	$('#id').hide();

}
$("#modal-update-user").on("hidden.bs.modal", function() {
	$('#modal-update-user #email').val("");
	$('#modal-update-user #firstName').val("");
	$('#modal-update-user #lastName').val("");
	$('#modal-update-user #role_admin').prop('checked', false);
	$('#modal-update-user #role_user').prop('checked', false);
	// clear input id
	$('#modal-update-user #id').remove();
});

$("#modalDelete").on("hidden.bs.modal", function() {
	$('#modalDelete #id').remove();
	$('#modalDelete p').remove();
});
$(document).ready(
		function() {

			function validateEmail(email) {
				var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				return re.test(String(email).toLowerCase());
			}

			$("#btn_submit_update_user").click(
					
							function(event) {
								event.preventDefault();
								if ($("#email").val() == "") {
									toastr.warning('Vui lòng nhập địa chỉ Gmail!','Thông báo');
								} else if ($("#firstName").val() == "") {
									toastr.warning('Vui lòng nhập tên!','Thông báo');
								} else if (checkName($("#firstName").val())) {
									toastr.warning('Tên không bao gồm ký tự số và ký tự đặc biệt','Thông báo');
								} else if ($("#lastName").val() == "") {
									toastr.warning('Vui lòng nhập họ và tên đệm!','Thông báo');
								} else if (checkName($("#lastName").val())) {
									toastr.warning('Họ không bao gồm ký tự số và ký tự đặc biệt','Thông báo');
								}else if (!$("#role_admin").prop("checked") && !$("#role_user").prop("checked")) {
									toastr.warning('Vui lòng chọn quyền!','Thông báo');
								} else {
									if ($("#email").val() != "") {
										if (!validateEmail($("#email").val())) {
											toastr.warning('Vui lòng nhập đúng định dạng địa chỉ Gmail!','Thông báo');
										}else {
											$("#update_user_form").submit();
										}
									}
								}
							});
			var msg = $("#msg").text();
			if (msg) {
				toastr.warning(msg);
			}

			var success = $("#msgSuccess").text();
			if (success) {
				toastr.success(msgSuccess);
			}
		});