$(document).ready(function() {
	// Khi người dùng nhấn xác nhận
	$("#btn_submit").click(function() {
		if($("#studentId").val() == ""){
			toastr.warning('Vui lòng nhập mã sinh viên/giảng viên!','Lỗi');
		}else if($("#studentId").val().length < 5 || $("#studentId").val().length > 6){
			toastr.warning('Mã sinh viên/giảng viên không hợp lệ!','Lỗi');
		}else{
			$('#spinner').show();
			$("#testschedule_create").submit();
		}
	});
	
    $("#spinner").bind("ajaxSend", function() {
        $(this).show();
    }).bind("ajaxStop", function() {
        $(this).hide();
    }).bind("ajaxError", function() {
        $(this).hide();
    });
	
});

