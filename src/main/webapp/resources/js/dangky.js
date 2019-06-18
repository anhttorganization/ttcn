$(document).ready(function() {

	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(String(email).toLowerCase());
	}



	
	$("#btn_submit_register").click(function(){
		if($("#email").val() == ""){
			toastr.warning('Vui lòng nhập địa chỉ Email!','Thông báo');
		}else if($("#username").val() == ""){
			toastr.warning('Vui lòng nhập tên tài khoản!','Thông báo');
		}else if($("#firstname").val() == ""){
			toastr.warning('Vui lòng nhập tên!','Thông báo');
		}else if($("#lastname").val() == ""){
			toastr.warning('Vui lòng nhập họ và tên đệm!','Thông báo');
		}else if($("#password").val() == ""){
			toastr.warning('Vui lòng nhập mật khẩu!','Thông báo');
		}else if($("#passwordConfirm").val() == ""){
			toastr.warning('Vui lòng nhập lại mật khẩu lần nữa!','Thông báo');
		}else{
			if($("#email").val() != ""){
				if(!validateEmail($("#email").val())){
					toastr.warning('Vui lòng nhập đúng định dạng địa chỉ Email!','Thông báo');
				}else if($("#password").val() != $("#passwordConfirm").val()){
					toastr.warning('Mật khẩu nhập lại không trùng khớp!','Thông báo');
				}else{
					 $("#register_form").submit();
				}
			}
		}
	});
	if(document.getElementById("email.errors") != ""){
	toastr.warning(document.getElementById("email.errors").textContent,'Thông báo');
	document.getElementById("email.errors").textContent="";
	}
});