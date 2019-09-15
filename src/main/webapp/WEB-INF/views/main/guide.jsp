<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<div class="container-tabs d-inline-block">
	<!-- <h2 class="text-center">HÆ°á»ng dáº«n sá»­ dá»¥ng</h2> -->
	<div class="tab">
		<span class="tab-header">CHỨC NĂNG</span> <a class="tablinks active"
			id="tab-default" onclick="openTab(event, 'dangky')">Đăng ký</a> <a
			class="tablinks" onclick="openTab(event, 'dangnhap')">Đăng nhập</a> <a
			class="tablinks" onclick="openTab(event, 'capquyen')">Cấp quyền</a> <a
			class="tablinks" onclick="openTab(event, 'themtkb')">Thêm thời
			khóa biểu</a> <a class="tablinks" onclick="openTab(event, 'themlichthi')">Thêm
			lịch thi</a> <a class="tablinks" onclick="openTab(event, 'import')">Import
			file Exel</a>

	</div>
	<div id="dangky" class="tabcontent active">
		<h3>Đăng ký</h3>
		&nbsp
		<p style="font-size: 17px;">
			Bước 1: Người dùng click vào nút <strong>[Đăng ký]</strong> tại trang chủ của phần
			mềm, giao diện Đăng ký được mở ra. <br> <br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/dangky.PNG"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
			<!-- <p style="text-align: center; color: gray;">Hình 1.Giao diện trang chủ</p> -->
			Bước 2: Người dùng nhập <strong>[Gmail]</strong> là email có đuôi
			@gmail.com và nhập đầy đủ các thông tin như <strong>[Họ đệm]</strong>,
			<strong>[Tên]</strong>, <strong>[Mật khẩu]</strong>, <strong>[Nhập
				lại mật khẩu]</strong>. Sau khi nhập đầy đủ thông tin, tiến hành click vào
			nút <strong>[Đăng ký]</strong>, khi đó hệ thống sẽ gửi một email chứa
			đường dẫn để kích hoạt tài khoản.<br> <br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/dangky1.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
			Bước 3: Kiểm tra email và chọn vào nút <strong>[Xác nhận]</strong> để
			kích hoạt tài khoản. <br> <br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/xacnhandk.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
			Bước 4: Sau khi kích hoạt thành công tài khoản vừa đăng ký có thể
			đăng nhập vào hệ thống để sử dụng.
		</p>

	</div>

	<div id="dangnhap" class="tabcontent">
		<h3>Đăng nhập</h3>
		&nbsp
		<p style="font-size: 17px;">
			Người dùng đăng nhập với <strong>[Tên đăng nhập]</strong> là gmail đã
			đăng ký và <strong>[Mật khẩu]</strong> là mật khẩu đã đặt. Click vào
			<strong>[Đăng nhập]</strong> để sử dụng phần mềm. Chọn <strong>[Quên
				mật khẩu]</strong> trong trường hợp quên mật khẩu đăng nhập hoặc click <strong>[Đăng
				ký]</strong> nếu người dùng chưa đăng ký.
		</p>
		<br> <br> <img
			src="${contextPath}/stcalendar/resources/images/hdsd/login.png"
			alt="Hình ảnh không tồn tại" width="800px"><br> <br>
	</div>

	<div id="capquyen" class="tabcontent">
		<h3>Cấp quyền</h3>
		&nbsp
		<p style="font-size: 17px;">
			Khi người dùng sử dụng các chức năng Thêm thời khóa biểu, thêm lịch
			thi, Import file exel, ST Calendar sẽ yêu cầu người dùng cấp quyền
			cho hệ thống truy cập và thay đổi được Calendar của người dùng. <br>Bước
			1: Tại trang chủ, người dùng click vào <strong>[Thời khóa
				biểu]</strong>, <strong>[Thêm lịch thi]</strong> hoặc <strong>[Import
				file exel]</strong> thì giao diện trang đăng nhập với Google được mở ra. Tại
			đây người dùng chọn vào gmail đang được sử dụng cho tài khoản. <br>
			<br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/capquyen.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
			Bước 2: Sau khi chọn gmail, màn hình cảnh báo của Google được mở ra,
			click vào <strong>[Nâng cao]</strong> để hiển thị đường dẫn tới trang
			cấp quyền : <br> <br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/capquyen1.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
			Bước 3: Sau đó, người dùng click vào <strong>[Đi tới
				vnua.edu.vn (không an toàn)]</strong> để chuyển hướng sang trang cấp quyền. <br>
			<br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/capquyen2.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
			Bước 4: Tại trang cấp quyền cho hệ thống, người dùng click vào nút <strong>[Cho
				phép]</strong>để cấp quyền cho hệ thống truy cập vào Calendar của người dùng.
			<br> <br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/capquyen3.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
			Sau khi cấp quyền, màn hình sẽ quay trở lại trang chủ với thông báo
			Cấp quyền thành công.
		</p>

	</div>
	<div id="themtkb" class="tabcontent">
		<h3>Thêm thời khóa biểu</h3>
		&nbsp
		<p style="font-size: 17px;">
			Trên giao diện trang chủ, để sử dụng chức năng thêm thời khóa biểu,
			người dùng click vào <strong>[Thời khóa biểu]</strong>. Với lần sử
			dụng đầu tiên, hệ thống sẽ yêu cầu người dùng cấp quyền.<br>
			Quay lại mục <strong>Cấp quyền</strong> để xem các bước cấp quyền cho
			hệ thống. Sau khi cấp quyền cho hệ thống, người dùng click vào <strong>[Thời
				khóa biểu]</strong> thì giao diện trang Thêm thời khóa biểu được mở ra. Tại
			đây, người dùng chọn một giá trị học kỳ, điền mã sinh viên/giảng viên
			và click vào <strong>[Thêm lịch]</strong> để thêm thời khóa biểu.
			Hoặc click vào <strong>[Dùng các chức năng khác]</strong> để quay lại
			trang chủ.<br> <br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/themtkb.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>

		</p>
	</div>
	<div id="themlichthi" class="tabcontent">
		<h3>Thêm lịch thi</h3>
		&nbsp
		<p style="font-size: 17px;">
			Trên giao diện trang chủ, để sử dụng chức năng thêm lịch thi, người
			dùng click vào <strong>[Lịch thi]</strong>. Với lần sử dụng đầu tiên,
			hệ thống sẽ yêu cầu người dùng cấp quyền. Quay lại mục 2.1 để xem các
			bước cấp quyền cho hệ thống. Sau khi cấp quyền cho hệ thống, người
			dùng click vào <strong>[Lịch thi]</strong> thì giao diện trang Thêm
			lịch thi được mở ra. Tại đây, người dùng điền mã sinh viên và click
			vào <strong>[Thêm lịch]</strong> để thêm lịch thi. <br> <br>
			<img src="${contextPath}/stcalendar/resources/images/hdsd/themlt.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>

		</p>
	</div>
	<div id="import" class="tabcontent">
		<h3>Import file Exel</h3>
		&nbsp
		<p style="font-size: 17px;">
			Trên giao diện trang chủ, để sử dụng chức năng import file exel,
			người dùng click vào <strong>[Import]</strong>. Với lần sử dụng đầu
			tiên, hệ thống sẽ yêu cầu người dùng cấp quyền. Quay lại mục <strong>>Cấp
				quyền</strong> để xem các bước cấp quyền cho hệ thống. <br>Bước 1: Sau
			khi cấp quyền cho hệ thống, người dùng click vào <strong>[Import]</strong>
			thì giao diện trang Thêm lịch từ Exel được mở ra. Tại đây, người dùng
			click vào <strong>[Tải file mẫu ở đây]</strong> để lấy file mẫu về
			(file mẫu có tên sample) <br> <br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/filemau.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
			Bước 2: Sau khi điền thông tin vào file mẫu, người dùng chọn một lịch
			đã có sẵn trong Calendar ở trường <strong>[Chọn lịch]</strong>, kéo thả file mẫu vào
			trang web hoặc click <strong>[Browse]</strong> để chọn file upload. Sau đó người dùng
			click <strong>[Upload Exel]</strong> để upload file.
			<br> <br> <img
				src="${contextPath}/stcalendar/resources/images/hdsd/themtuexel.png"
				alt="Hình ảnh không tồn tại" width="800px"><br> <br>
		</p>
	</div>
</div>

<script>
	// openTab(document.getElementById('PHP'), 'PHP');
	//   openTab("", "PHP");
	//   document.getElementById('PHP').className += " active";
	document.getElementById('tab-default').click();
	function openTab(evt, id) {
		//láº¥y cÃ¡c tháº» a Äáº¡i diá»n cho cÃ¡c tab
		var buttons = document.getElementsByClassName('tablinks');
		//láº¥y cÃ¡c pháº§n ná»i dung
		var contents = document.getElementsByClassName('tabcontent');
		//Äá»nh nghÄ©a hÃ m hiá»n thá» ná»i dung theo id
		function showContent(id) {
			for (var i = 0; i < contents.length; i++) {
				contents[i].style.display = 'none';
			}
			var content = document.getElementById(id);
			content.style.display = 'block';
		}
		//láº·p qua cÃ¡c tab vÃ  gÃ¡n sá»± kiá»n click
		// for (var i = 0; i < buttons.length; i++) {
		// buttons[i].addEventListener("click", function () {
		//láº¥y vÄn báº£n trong tháº» a Äáº¡i diá»n cho id cá»§a ná»i dung
		// var id = this.getAttribute('id');
		//bá» active táº¥t cáº£ cÃ¡c tab
		for (var i = 0; i < buttons.length; i++) {
			buttons[i].classList.remove("active");
		}
		//active tab ÄÆ°á»£c click
		this.className += " active";
		//show ná»i dung theo id láº¥y ÄÆ°á»£c
		showContent(id);
		// });
		// }
		//máº·c Äá»nh hiá»n thá» tab PHP
		///showContent('PHP');

		evt.currentTarget.className += " active";
	}
</script>