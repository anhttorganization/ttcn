$(document).ready(function() {
	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(String(email).toLowerCase());
	}
	var sendemail = $('#sendemail').val();
	if(sendemail != ""){
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
		
		toastr.success(`${sendemail}`,'Thành công');
		registerOK();
		$('body').find('#toast-container > div').style.opacity = '1';
		$('body').find('#toast-container > div').style.height = '100%';
		$('body').find('#toast-container > div').style.alignItems="center";
		$('body').find('#toast').style.position='absolute !important';
		$('body').find('#toast').style.top = '50%';
		$('body').find('#toast').style.left = '50%';
		$('body').find('#toast').style.transform = 'translateX(-50%) translateY(-50%)';
		
		
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
	
	$('input').keypress(function (e) {
		  if (e.which == 13) {
			  $("#btn_submit_register").click();
		    return false;    //<---- Add this line
		  }
		});
	
	$("#btn_submit_register").click(function(){
		var email = $("#email").val().toLowerCase();
		$("#email").val(email);
		
		if($("#email").val() == ""){
			toastr.warning('Vui lòng nhập địa chỉ Gmail!','Lỗi');
		}else if(!validateEmail($("#email").val())){
			toastr.warning('Vui lòng nhập đúng định dạng địa chỉ Gmail!','Lỗi');
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
				if($("#password").val() != $("#passwordConfirm").val()){
					toastr.warning('Mật khẩu nhập lại không trùng khớp!','Lỗi');
				}else{
					$('#spinner').show();
					 $("#register_form").submit();
				}
			}
		}
	});
});
