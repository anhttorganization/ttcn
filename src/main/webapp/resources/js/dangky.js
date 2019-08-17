$(document).ready(function() {

	

	

	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(String(email).toLowerCase());
	}

	toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "newestOnTop": true,
			  "progressBar": false,
			  "positionClass": "toast-top-center",
			  "preventDuplicates": false,
			  "onclick": null,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": 0,
			  "extendedTimeOut": 0,
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut",
			  "tapToDismiss": false
			}
	
	
	var error_message = $('#error_message').val();
	var success_message = $('#success_message').val();
	var info_message = $('#info_message').val();

	if(error_message != ""){
		toastr.warning(`${error_message}`,'Lỗi');
		registerError();
	}else if(success_message != ""){
		toastr.success(`${success_message}`, 'Thành công')
		registerOK();
	}else if(info_message != ""){
		toastr.info(`${info_message}`, 'Thông báo');
		registerError();
	}
	
	function registerOK(){
		$('body').find("#ok_register").click(function (){
			window.location.href = "login";
		})
	}
	
	function registerError(){
		$('body').find("#ok_register").click(function (){
			window.location.href = "register";
		})
	}
	
	
	
	$("#btn_submit_register").click(function(){
		var email = $("#email").val().toLowerCase();
		$("#email").val(email);
		
		if($("#email").val() == ""){
			toastr.warning('Vui lòng nhập địa chỉ Gmail!','Lỗi');
		}else if($("#lastname").val() == ""){
			toastr.warning('Vui lòng nhập tên!','Lỗi');
		}else if($("#firstname").val() == ""){
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
					$('#spinner').show();
					 $("#register_form").submit();
				}
			}
		}
	});
});
