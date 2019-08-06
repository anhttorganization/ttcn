function validateSlotTime() {
	var startTime = document.getElementById("startTime").value;
	var endTime = document.getElementById("endTime").value;
	var regex = /^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$/;

	if (startTime == '' || startTime == null) {
		toastr.warning("Thời gian bắt đầu không được để trống", "Lỗi");
		return false;
	}

	if (endTime == '' || endTime == null) {
		toastr.warning("Thời gian kết thúc không được để trống", "Lỗi");
		return false;
	}

	if (regex.test(startTime) == false) {
		toastr.warning("Thời gian bắt đầu không hợp lệ", "Lỗi");
		return false;
	}

	if (regex.test(endTime) == false) {
		toastr.warning("Thời gian kết thúc không hợp lệ", "Lỗi");
		return false;
	}

	if (startTime.substring(0, 2) > endTime.substring(0, 2)) {
		toastr.warning("Thời gian kết thúc phải lớn hơn thời gian bắt đầu",
				"Lỗi");
		return false;
	}

	if (startTime.substring(0, 2) == endTime.substring(0, 2)
			&& startTime.substring(3, 5) > endTime.substring(3, 5)) {
		toastr.warning("Thời gian kết thúc phải lớn hơn thời gian bắt đầu",
				"Lỗi");
		return false;
	}

	if (startTime.substring(0, 2) == endTime.substring(0, 2)
			&& startTime.substring(3, 5) == endTime.substring(3, 5)
			&& startTime.substring(6, 8) >= endTime.substring(6, 8)) {
		toastr.warning("Thời gian kết thúc phải lớn hơn thời gian bắt đầu",
				"Lỗi");
		return false;
	}
	return true;
}

function validateSlotTimeUpdate() {
	var startTime = document.getElementById("startTimeUpdate").value;
	var endTime = document.getElementById("endTimeUpdate").value;
	var regex = /^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$/;

	if (startTime == '' || startTime == null) {
		toastr.warning("Thời gian bắt đầu không được để trống", "Lỗi");
		return false;
	}

	if (endTime == '' || endTime == null) {
		toastr.warning("Thời gian kết thúc không được để trống", "Lỗi");
		return false;
	}

	if (regex.test(startTime) == false) {
		toastr.warning("Thời gian bắt đầu không hợp lệ", "Lỗi");
		return false;
	}

	if (regex.test(endTime) == false) {
		toastr.warning("Thời gian kết thúc không hợp lệ", "Lỗi");
		return false;
	}

	if (startTime.substring(0, 2) > endTime.substring(0, 2)) {
		toastr.warning("Thời gian kết thúc phải lớn hơn thời gian bắt đầu",
				"Lỗi");
		return false;
	}

	if (startTime.substring(0, 2) == endTime.substring(0, 2)
			&& startTime.substring(3, 5) > endTime.substring(3, 5)) {
		toastr.warning("Thời gian kết thúc phải lớn hơn thời gian bắt đầu",
				"Lỗi");
		return false;
	}

	if (startTime.substring(0, 2) == endTime.substring(0, 2)
			&& startTime.substring(3, 5) == endTime.substring(3, 5)
			&& startTime.substring(6, 8) >= endTime.substring(6, 8)) {
		toastr.warning("Thời gian kết thúc phải lớn hơn thời gian bắt đầu",
				"Lỗi");
		return false;
	}
	return true;
}

$(document).ready(function() {
	message();
});
function message() {
	var msgSuccess = document.getElementById("msgSuccess").value;
	if (msgSuccess != null && msgSuccess != undefined
			&& msgSuccess.trim() != "") {
		toastr.success(msgSuccess);
	}

	var msg = document.getElementById("msg").value;
	if (msg != null && msg != undefined && msg.trim() != "") {
		toastr.warning(msg);
	}
}

function showModalUpdateTietHoc(id, startTime, endTime) {
	// show modal
	$('#modal-update-tiethoc').modal('show');
	$("#selectBoxUpdate").val(id);
	$('#modal-update-tiethoc .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = " + id
					+ " />");
	$('#modal-update-tiethoc #startTimeUpdate').val(startTime);
	$('#modal-update-tiethoc #endTimeUpdate').val(endTime);
	$('#id').hide();
}

$("#modal-update-tiethoc").on("hidden.bs.modal", function() {
	$("#modal-update-tiethoc #startTimeUpdate").val("");
	$("#modal-update-tiethoc #endTimeUpdate").val("");
	// clear input id
	$('#modal-update-tiethoc #id').remove();
});

$("#exampleModalCenter").on("hidden.bs.modal", function() {
	$("#exampleModalCenter #startTime").val("");
	$("#exampleModalCenter #endTime").val("");
	// clear input id
	$('#exampleModalCenter #id').remove();
});

$(function() {
	$('#tietbatdau').datetimepicker({
		format : 'HH:mm:ss'
	});
	$('#tietketthuc').datetimepicker({
		format : 'HH:mm:ss'
	});
	$('#tietbatdauupdate').datetimepicker({
		format : 'HH:mm:ss'
	});
	$('#tietketthucupdate').datetimepicker({
		format : 'HH:mm:ss'
	});
});

function showModalDeleteTietHoc(id) {
	// show modal
	$('#modalDelete').modal('show');
	$('#modalDelete .modal-body').append(
			'<p>Bạn có chắc muốn xóa tiết <strong>'
					+ id + '</strong>?</p>');
	$('#modalDelete .modal-body').append(
			"<input name = \"id\" id = \"id\" type = \"text\" value = "
					+ id + " />");

	$('#modalDelete #id').hide();
}

$("#modalDelete").on("hidden.bs.modal", function() {
	$('#modalDelete #id').remove();
	$('#modalDelete p').remove();
});