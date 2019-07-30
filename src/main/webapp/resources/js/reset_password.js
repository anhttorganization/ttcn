$(document).ready(function() {

	$("#btn_submit").click(function(){
		if($("#password").val() == ""){
			toastr.warning('Vui lòng nhập mật khẩu!','Thông báo');
		}else if($("#passwordConfirm").val() == ""){
			toastr.warning('Vui lòng nhập lại mật khẩu lần nữa!','Thông báo');
		}else{
			if($("#password").val() != $("#passwordConfirm").val()){
				toastr.warning('Mật khẩu nhập lại không trùng khớp!','Thông báo');
			}else{
				 $("#reset-pass-form").submit();
			}
		}
	});
	
	var msg = $("#message").text();
	if(msg){
		toastr.warning(msg);
	}
	
});