$(document).ready(function() {

	function validateEmail(email) {
		var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		return re.test(String(email).toLowerCase());
	}
	
	$("#btn_submit").click(function(){
		if($("#email").val() == ""){
			toastr.warning('Vui lòng nhập địa chỉ Gmail!','Thông báo');
		}else{
			if($("#email").val() != ""){
				if(!validateEmail($("#email").val())){
					toastr.warning('Vui lòng nhập đúng định dạng địa chỉ Gmail!','Thông báo');
				}else{
					 $('#spinner').show();
					 $("#fogot_password_form").submit();
				}
			}
		}
	});
	
	var msg = $("#message").text();
	if(msg){
		toastr.warning(msg);
	}
	
	var success = $("#success").text();
	if(success){
		toastr.success(success);
	}
});