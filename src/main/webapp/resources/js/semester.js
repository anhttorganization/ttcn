var datePatt = new RegExp(/(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/);
var idPatt = new RegExp(/^\d{5}$/);

function addSemester(){
	var id = $('#id').val();
	var name = $('#name').val();
	var dateInput = $("#date").val();

	if(id == ""){
		toastr.warning('Vui lòng nhập id học kỳ!','Thông báo');
		return false;
	}else if(!idPatt.test(id)){
		toastr.warning('Id phải có 5 ký tự là số!','Thông báo');
		return false;
	}else if(name == ""){
		toastr.warning('Vui lòng nhập tên học kỳ','Thông báo');
		return false;
	}else if(dateInput == ""){
		toastr.warning('Vui lòng nhập thời gian bắt đầu học kỳ','Thông báo');
		return false;
	}else if(!datePatt.test(dateInput)){
		toastr.warning('Vui lòng nhập đúng định dạng ngày dd/mm/yyyy','Thông báo');
		return false;
	}else{
		var parts = dateInput.split('/');
		var	date = parts[2] + '-' + parts[1]  + '-' + parts[0];
//		$("#close").click();
		$.ajax({
			url : "add",
			type : "post",
			data : {
				id : id,
				name : name,
				startDate : date
			},
			success : function (result){
				if(result.status){
					alert('Thêm học kỳ thành công!');
					location.reload();
				}else{
					toastr.warning(result.message,'Thông báo');
				}
			}
		});
		return true
	}
}

$('#exampleModalCenter').on('hidden.bs.modal', function () {
	$('#id').val('');
	$('#name').val('');
	$("#date").val('');
	})

function deleteSemester(url, modalId) {
	$('#' + modalId).modal('hide');
	$.ajax({
		  url: url,
		  type : "post",
		  cache: false,
		  success: function(result){
				if(result.status){
					alert('Xóa học kỳ thành công!');
					location.reload();
				}else{
					toastr.warning(result.message,'Thông báo');
				}
		  }
		});
}


function editSemester(id, name, startDate){
	$('#id_update').val(id);
	$('#name_update').val(name);
	$("#date_update").val(startDate);
}

$('#submit_update').click(
		function (){
			var id = $('#id_update').val();
			var name = $('#name_update').val();
			var dateInput = $("#date_update").val();

			if(name == ""){
				toastr.warning('Vui lòng nhập tên học kỳ','Thông báo');
				return false;
			}else if(dateInput == ""){
				toastr.warning('Vui lòng nhập thời gian bắt đầu học kỳ','Thông báo');
				return false;
			}else if(!datePatt.test(dateInput)){
				toastr.warning('Vui lòng nhập đúng định dạng ngày dd/mm/yyyy','Thông báo');
				return false;
			}else{
				var parts = dateInput.split('/');
				var	date = parts[2] + '-' + parts[1]  + '-' + parts[0];
				$.ajax({
					url : "update",
					type : "post",
					data : {
						id : id,
						name : name,
						startDate : date
					},
					success : function (result){
						if(result.status){
							alert('Cập nhật học kỳ thành công!');
							location.reload();
						}else{
							toastr.warning(result.message,'Thông báo');
						}
					}
				});
			}
		}
)



