var error_message = $('#error_message').val();
var success_message = $('#success_message').val();
var info_message = $('#info_message').val();

if(error_message != ""){
	toastr.warning(`${error_message}`,'Lỗi');
}else if(success_message != ""){
	toastr.success(`${success_message}`, 'Thông báo')
}else if(info_message != ""){
	toastr.info(`${info_message}`, 'Thông báo')
}