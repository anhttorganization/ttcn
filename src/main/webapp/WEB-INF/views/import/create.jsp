<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />


<div class="container">
	<div class="row row-calendar">
		<div class="chucnang tdExpandInBounce animated">
			<div class="row">
				<div class="col-md-12">
					<h1>Thêm lịch từ Excel</h1>
					<div class="themlichtkb">
						<form:form method="POST" action="${contextPath}/import/create"  enctype="multipart/form-data" modelAttribute="importCreate">
							 <a href="${contextPath}/resources/samplefile/sample.xlsx" download>Tải file mẫu ở đây </a><br>
							<br>
							<spring:bind path="calendarId">
									<label for="calendarId">Chọn lịch:</label>
									<form:select path="calendarId" class="custom-select">
										<form:option value="-" label="--Chọn một giá trị--" />
										<form:options items="${calendars}" />
									</form:select>
									<form:errors path="calendarId" />
							</spring:bind>
							<br><br>
							<span >Chọn tệp Excel:</span><br>
							<div class="custom-file" style="margin-top:10px">
								<input type="file" class="custom-file-input" id="inputGroupFile01" name="multipartFile" accept=".xls,.xlsx"> 
								<label class="custom-file-label" for="inputGroupFile01" id="filelable">Choose file</label>
							</div>
							<button type="submit"
								class="btn btn-primary mt-4 float-right w-25">Upload
								Excel</button>
						</form:form>
						<div class="backhome">
							<a href="../home">Dùng các chức năng khác</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

    $(document).ready(function(){
        $('input[type="file"]').change(function(e){
            var fileName = e.target.files[0].name;
            $('#filelable').text(fileName);
        });
    });
</script>