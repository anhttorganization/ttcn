<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ page contentType="text/html; charset=utf-8" language="java" import="javax.xml.parsers.DocumentBuilderFactory,javax.xml.parsers.DocumentBuilder,org.w3c.dom.*,vn.edu.vnua.dse.calendar.common.AppUtils" errorPage="" %>
<%
	String phone = AppUtils.getString("contact-phone");
	String email = AppUtils.getString("st-email");
	String website = AppUtils.getString("st-website");
	String websiteURL = AppUtils.getString("st-website-url");
	String stName = AppUtils.getString("st-name");
	String address = AppUtils.getString("st-address-contact");
%>



<h3 id="title">Liên Hệ</h3>
<p id="content">
	<span style="font-size: 16pt"><%=stName %><br></span>
	Địa chỉ: <%=address %><br>
	Điện thoại: <%=phone %><br>
	Email: <%=email %>​
</p>

<style>
	#title{
		text-align: center;
		padding-top: 30px;
	}
	#content {
    padding: 2% 5%;
    font-size: 12pt;
    text-align: center;
}
</style>

​