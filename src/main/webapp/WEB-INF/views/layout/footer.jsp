<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!-- <p align="center">Copyright © 2019 <a href="https://st-dse.vnua.edu.vn/" target="_blank">ST-DSE</a>. All rights reserved.</p>
<p>Email: stdse@vnua.edu.vn Điện thoại: 0912 817 498</p> -->
<!-- <div class="container">
  <div class="row">
    <div class="col">Copyright © 2019 <a href="https://st-dse.vnua.edu.vn/" target="_blank">ST-DSE</a>. All rights reserved.</div>
    <div class="col">Địa chỉ: P.310A, Tầng 3, Nhà hành chính, HVNNVN</div>
    <div class="w-100"></div>
    <div class="col">Email: stdse@vnua.edu.vn</div>
    <div class="col">Điện thoại: 0912 817 498</div>
  </div>
</div>
 -->
<div class="bottom_footer">
	<%-- <a class="left" href="https://st-dse.vnua.edu.vn/" target="_blank"><img class="logo_bottom" src="${contextPath}/resources/images/logoST_edited.jpg" alt="STCalendar"></a>
	<div class="bottom_footer_1 left">
		<p>Copyright © 2019 <a href="https://st-dse.vnua.edu.vn/" target="_blank">ST-DSE</a>. All rights reserved.</p>
		<p>Địa chỉ: P.310A, Tầng 3, Nhà hành chính, HVNNVN</p>
	</div>
	<div class="bottom_footer_2 right">
		<p><i class="fa fa-envelope" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Email: stdse@vnua.edu.vn</p>
		<p><i class="fa fa-phone fa-rotate-90" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Điện thoại: 0912 817 498</p>
	</div> --%>
	<div class="row" style="margin:0">
			<div class="col-md-6">
				<div class="row" style="margin:0">
					<div class="col-md-4">
						<img class="logo_bottom" src="${contextPath}/resources/images/logoST_edited.jpg" alt="STCalendar">
					</div>
					<div class="col-md-8" style="margin-top:6px;">
					<p>Copyright © 2019 <a href="https://st-dse.vnua.edu.vn/" target="_blank">ST-DSE</a>. All rights reserved.</p>
					<p>Địa chỉ: P.310A, Tầng 3, Nhà hành chính, Học viện Nông nghiệp Việt Nam</p>
					</div>
				</div>
				
			</div>
			<div class="col-md-6" style="margin-top:6px;">
				<p style="text-align:center"><i class="fa fa-envelope" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Email: stdse@vnua.edu.vn</p>
				<p style="text-align:center"><i class="fa fa-phone fa-rotate-90" aria-hidden="true"></i>&nbsp;&nbsp;&nbsp;Điện thoại: 0912 817 498</p>
			</div>
		</div>
</div>