<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<h3 id="title">${title }</h3>
<p id="content">
	${content }
</p>

<style>
	#title{
		text-align: center;
		padding-top: 30px;
	}
	#content{
		padding:20px 200px;
		font-size: 12pt;
		text-align:justify;
	}
</style>