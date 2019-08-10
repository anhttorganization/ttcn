$(document).ready(function() {

	var error_message = $('#error_message').val();
	var success_message = $('#success_message').val();
	var info_message = $('#info_message').val();

	if(error_message != ""){
		toastr.warning(`${error_message}`,'Lỗi');
	}else if(success_message != ""){
		toastr.success(`${success_message}`, 'Thành công')
	}else if(info_message != ""){
		toastr.info(`${info_message}`, 'Thông báo')
	}
	
	
	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(String(email).toLowerCase());
	}



	
	$("#btn_submit_register").click(function(){
		if($("#email").val() == ""){
			toastr.warning('Vui lòng nhập địa chỉ Gmail!','Lỗi');
		}else if($("#firstname").val() == ""){
			toastr.warning('Vui lòng nhập tên!','Lỗi');
		}else if($("#lastname").val() == ""){
			toastr.warning('Vui lòng nhập họ và tên đệm!','Lỗi');
		}else if($("#password").val() == ""){
			toastr.warning('Vui lòng nhập mật khẩu!','Lỗi');
		}else if($("#passwordConfirm").val() == ""){
			toastr.warning('Vui lòng nhập lại mật khẩu lần nữa!','Lỗi');
		}else{
			if($("#email").val() != ""){
				if(!validateEmail($("#email").val())){
					toastr.warning('Vui lòng nhập đúng định dạng địa chỉ Gmail!','Lỗi');
				}else if($("#password").val() != $("#passwordConfirm").val()){
					toastr.warning('Mật khẩu nhập lại không trùng khớp!','Lỗi');
				}else{
					 $("#register_form").submit();
				}
			}
		}
	});
	if(document.getElementById("email.errors") != ""){
	toastr.warning(document.getElementById("email.errors").textContent,'Lỗi');
	document.getElementById("email.errors").textContent="";
	}
});