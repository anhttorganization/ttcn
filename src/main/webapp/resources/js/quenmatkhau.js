$(document).ready(function() {

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
					 $("#btn_submit").click();
				}
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