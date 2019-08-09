$(document).ready(function() {
	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(String(email).toLowerCase());
	}

	var error_message = $('#error_message').val();
	var success_message = $('#success_message').val();
	var info_message = $('#info_message').val();

	if (error_message != "") {
		toastr.warning(`${error_message}`, 'Lỗi');
	} else if (success_message != "") {
		toastr.success(`${success_message}`, 'Thành công')
	} else if (info_message != "") {
		toastr.info(`${info_message}`, 'Thông báo')
	}

	// Khi người dùng nhấn xác nhận
	$("#btn_submit").click(function() {
		if($("#email").val() == ""){
			toastr.warning('Vui lòng nhập địa chỉ Gmail!','Lỗi');
		}else if(!validateEmail($("#email").val())){
			toastr.warning('Vui lòng nhập đúng định dạng địa chỉ Gmail!','Lỗi');
		}else if($("#password").val() == ""){
			toastr.warning('Vui lòng nhập mật khẩu!','Lỗi');
		}else{
			$("#login_form").submit();
		}
	});
 
	
});

