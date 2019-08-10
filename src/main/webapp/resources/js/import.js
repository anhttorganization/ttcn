$(document).ready(function() {
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
	$("#import_submit").click(function() {
		if($("#calendarId").val() == ""){
			toastr.warning('Vui lòng chọn một lịch!','Lỗi');
		}else if($("#inputGroupFile01").val() == ""){
			toastr.warning('Vui lòng chọn file excel!','Lỗi');
		}else{
			$("#import_form").submit();
		}
	});
 
	
});

