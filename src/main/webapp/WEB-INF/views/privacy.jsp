<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<h3 id="title">${title }</h3>
<div id="content">
	<c:forEach items="${paragraphs}" var="item">
	    <p>${item}</p>
	</c:forEach>
</div>

<style>
#title {
	text-align: center;
	padding-top: 20px;
}

#content {
	padding: 2% 5%;
}

#content p {
	font-size: 12pt;
	text-align: justify;
	margin-bottom: 0;
	text-indent: 3em;
	margin-top: 0;
	padding-bottom: 0.5em;
}

</style>