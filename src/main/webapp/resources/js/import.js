$(document).ready(function() {
	// Khi người dùng nhấn xác nhận
	$("#import_submit").click(function() {
		if($("#calendarId").val() == ""){
			toastr.warning('Vui lòng chọn một lịch!','Lỗi');
		}else if($("#inputGroupFile01").val() == ""){
			toastr.warning('Vui lòng chọn file excel!','Lỗi');
		}else{
			$('#spinner').show();
			$("#import_form").submit();
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

