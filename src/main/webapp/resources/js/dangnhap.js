$(document).ready(function() {
	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(String(email).toLowerCase());
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
			$('#spinner').show();
			$("#login_form").submit();
		}
	});
 
	
});

