function showModalUpdateFirstName(id, firstname) {
	$('#modal-update-firstname').modal('show');
	$('#modal-update-firstname .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = " + id
					+ " />");
	$('#modal-update-firstname #firstname').val(firstname);
	
	$('#id').hide();

}

$("#modal-update-firstname").on("hidden.bs.modal", function() {
	$('#modal-update-firstname #firstname').val("");
	// clear input id
	$('#modal-update-firstname #id').remove();
});

$(document).ready(
		function() {
			function checkName(str) {
				partern = "[0-9~!@#$%^&*()-<>?]";
				newRe = new RegExp(partern, 'g')
				return newRe.test(str);
			}
			$("#btn_submit_update_firstname").click(
					
							function(event) {
								event.preventDefault();
								if ($("#firstname").val() == "") {
									toastr.warning('Vui lòng nhập họ và tên đệm!','Thông báo');
								}else {
									if ($("#firstname").val() != "") {
										if (checkName($("#firstname").val())) {
											toastr.warning('Tên không bao gồm ký tự số và ký tự đặc biệt','Thông báo');
										}else {
											$("#update_firstname_form").submit();
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

function showModalUpdateLastName(id, lastname) {
	$('#modal-update-lastname').modal('show');
	$('#modal-update-lastname .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = " + id
					+ " />");
	$('#modal-update-lastname #lastname').val(lastname);
	
	$('#id').hide();

}

$("#modal-update-lastname").on("hidden.bs.modal", function() {
	$('#modal-update-lastname #lastname').val("");
	// clear input id
	$('#modal-update-lastname #id').remove();
});

$(document).ready(
		function() {
			function checkName(str) {
				partern = "[0-9~!@#$%^&*()-<>?]";
				newRe = new RegExp(partern, 'g')
				return newRe.test(str);
			}
			$("#btn_submit_update_lastname").click(
					
							function(event) {
								event.preventDefault();
								if ($("#lastname").val() == "") {
									toastr.warning('Vui lòng nhập tên!','Thông báo');
								}else {
									if ($("#lastname").val() != "") {
										if (checkName($("#lastname").val())) {
											toastr.warning('Tên không bao gồm ký tự số và ký tự đặc biệt','Thông báo');
										}else {
											$("#update_lastname_form").submit();
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

function showModalUpdateEmail(id, email) {
	$('#modal-update-email').modal('show');
	$('#modal-update-email .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = " + id
					+ " />");
	$('#modal-update-email #email').val(email);
	
	$('#id').hide();

}

$("#modal-update-email").on("hidden.bs.modal", function() {
	$('#modal-update-email #email').val("");
	// clear input id
	$('#modal-update-email #id').remove();
});

$(document).ready(
		function() {

			function validateEmail(email) {
				var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
				return re.test(String(email).toLowerCase());
			}

			$("#btn_submit_update_email").click(
					
							function(event) {
								event.preventDefault();
								if ($("#email").val() == "") {
									toastr.warning('Vui lòng nhập địa chỉ Gmail!','Thông báo');
								}else {
									if ($("#email").val() != "") {
										if (!validateEmail($("#email").val())) {
											toastr.warning('Vui lòng nhập đúng định dạng địa chỉ Gmail!','Thông báo');
										}else {
											$("#update_email_form").submit();
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
