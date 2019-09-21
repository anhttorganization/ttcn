<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%@ page contentType="text/html; charset=utf-8" language="java" import="javax.xml.parsers.DocumentBuilderFactory,javax.xml.parsers.DocumentBuilder,org.w3c.dom.*,vn.edu.vnua.dse.calendar.common.AppUtils" errorPage=""%>
<%
	String contact = AppUtils.getString("contact-menu").trim();
	String fita = AppUtils.getString("fita-name-footer").trim();
	String fitaURL = AppUtils.getString("fita-website-url").trim();	
	String department = AppUtils.getString("department-name-footer").trim();
	String vnua = AppUtils.getString("vnua-name-footer").trim();
	String phone = AppUtils.getString("contact-phone").trim();
	String email = AppUtils.getString("st-email").trim();
	String website = AppUtils.getString("st-website").trim();
	String websiteURL = AppUtils.getString("st-website-url").trim();
	String copyright = AppUtils.getString("copyright").trim();
	String vnuaURL = AppUtils.getString("vnua-website-url").trim();
%>


<div class="row" style="margin: 0">
			<div class="col-md-8">
				<div class="row">
					<div class="col-md-1 offset-md-2" style="max-width: 100%;padding: 0;">
						<div class="st_text" >
							<a href="<%=websiteURL%>" target="_blank"><span>S</span><span>T</span></a>
						</div>
					</div>
					<div class="col-md-8">
						<p>Địa chỉ: <%=department%>, <a href="<%=fitaURL%>" target="_blank"><%=fita%></a>,  <a href="<%=vnuaURL%>" target="_blank"><%=vnua%></a> <br />ĐT: <%=phone%> - Email: <%=email%> - Website: <%=website%></p>
					</div>
				</div>
			</div>
			<div class="col-md-4" style="text-align: center;">
				<p>
					Copyright © 2019 <a href="<%=websiteURL%>"
						target="_blank"><%=copyright%></a>. All rights reserved
				</p>
			</div>
		</div>