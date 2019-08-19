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
	$('input').keypress(function (e) {
		if (e.which == 13) {
			$("#btn_submit").click();
		  return false;    //<---- Add this line
		}
	  });
});