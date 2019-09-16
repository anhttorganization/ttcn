function showModalUpdateFirstName(id, firstName) {
	$('#modal-update-firstname').modal('show');
	$('#modal-update-firstname .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = " + id
					+ " />");
	$('#modal-update-firstname #firstName').val(firstName);
	
	$('#id').hide();

}

$("#modal-update-firstname").on("hidden.bs.modal", function() {
	$('#modal-update-firstname #firstName').val("");
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
								if ($("#firstName").val() == "") {
									toastr.warning('Vui lòng nhập họ và tên đệm!','Thông báo');
								}else {
									if ($("#firstName").val() != "") {
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

function showModalUpdateLastName(id, lastName) {
	$('#modal-update-lastname').modal('show');
	$('#modal-update-lastname .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = " + id
					+ " />");
	$('#modal-update-lastname #lastName').val(lastName);
	
	$('#id').hide();

}

$("#modal-update-lastname").on("hidden.bs.modal", function() {
	$('#modal-update-lastname #lastName').val("");
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
								if ($("#lastName").val() == "") {
									toastr.warning('Vui lòng nhập tên!','Thông báo');
								}else {
									if ($("#lastName").val() != "") {
										if (checkName($("#lastName").val())) {
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
