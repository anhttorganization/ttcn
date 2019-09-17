<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">

<head>
    <title><tiles:getAsString name="title" /></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<link rel="shortcut icon" type="image/png" href="${contextPath}/resources/images/Logo.png" />
    <link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Nunito+Sans:200,300,400,700,900|Roboto+Mono:300,400,500">
    <link rel="stylesheet" href="${contextPath}/resources/fonts/icomoon/style.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/magnific-popup.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/jquery-ui.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="${contextPath}/resources/css/animate.css">


    <link rel="stylesheet" href="${contextPath}/resources/fonts/flaticon/font/flaticon.css">

    <link rel="stylesheet" href="${contextPath}/resources/css/aos.css">

    <link rel="stylesheet" href="${contextPath}/resources/css/main.css">
    

</head>

<body>
<div id="menu">
            <div class="site-mobile-menu">
                <div class="site-mobile-menu-header">
                    <div class="site-mobile-menu-close mt-3">
                        <span class="icon-close2 js-menu-toggle"></span>
                    </div>
                </div>
                <div class="site-mobile-menu-body"></div>
            </div> <!-- .site-mobile-menu -->

            <div class="site-navbar-wrap bg-white">
                <div class="site-navbar-top">
                    <div class="container py-2">
                        <div class="row align-items-center">
                            <div class="col-6">
                                <a href="mailto:stdse@vnua.edu.vn">
                                    <span class="icon-envelope mr-2"></span>
                                    <span class="d-none d-md-inline-block">stdse@vnua.edu.vn</span>
                                </a>&nbsp;
                                <a href="tel://12912830302">
                                    <span class="icon-phone mr-2"></span>
                                    <span class="d-none d-md-inline-block">0912 817 498</span>
                                </a>
                                <!-- <a href="#" class="p-2 pl-0"><span class="icon-instagram"></span></a> -->
                            </div>
                            <div class="col-6">
                                <div class="d-flex ml-auto">
                                    <a href="login" class="d-flex align-items-center ml-auto mr-4">Đăng
                                        Nhập</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a href="register" class="d-flex align-items-center">Đăng Ký</a>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="site-navbar-wrap bg-white">

                <div class="container">
                    <div class="site-navbar bg-light">
                        <div class="py-1">
                            <div class="row align-items-center" style="height: 50px;">
                                <div class="col-1">
<%--                                    <h2 class="mb-0 site-logo"><a href="${pageContext.request.contextPath}"><strong>ST</strong>Calendar</a></h2>  --%>
                            			<a href="https://st-dse.vnua.edu.vn/" target="blank"><img id="home_logo" src="${contextPath}/resources/images/Logo.png"></a>
                                </div>
                                <div class="col-11">
                                    <nav class="site-navigation text-left" role="navigation">
                                        <div class="container">
                                            <div class="d-inline-block d-lg-none ml-md-0 mr-auto py-3"><a href="#"
                                                    class="site-menu-toggle js-menu-toggle text-black"><span
                                                        class="icon-menu h3"></span></a></div>

                                            <ul class="site-menu js-clone-nav d-none d-lg-block" id="ultab">
                                                <li id="home">
                                                    <a href="${contextPath}">Trang Chủ</a>
                                                </li>
                                                <li id="guide">
                                                    <a href="${contextPath}/guide">Hướng Dẫn</a>
                                                </li>

                                                <li  id="policy" class="has-children">
                                                    <a id="policy" href="#">Chính Sách</a>
                                                    <ul class="dropdown arrow-top">
                                                        <li><a href="terms.html">Bảo Mật Thông Tin</a></li>
                                                        <li><a href="privacy.html">Quy Định Sử Dụng</a></li>
                                                    </ul>
                                                </li>
                                                <li id="contact"><a href="contact">Liên Hệ</a></li>
                                            </ul>
                                        </div>
                                    </nav>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

</div>
    <div class="wrapper">
        <div class="site-wrap">

        	<tiles:insertAttribute name="body" />
        </div>
    </div>
    <footer class="site-footer">
        <div class="container">
        	<tiles:insertAttribute name="footer" />
        </div>
    </footer>


	<script type="text/javascript" src="${contextPath}/resources/js/jquery-3.4.1.min.js"></script>
    <script src="${contextPath}/resources/js/jquery-migrate-3.0.1.min.js"></script>
    <script src="${contextPath}/resources/js/jquery-ui.js"></script>
    <script src="${contextPath}/resources/js/popper.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resources/js/bootstrap.min.js"></script>
    <script src="${contextPath}/resources/js/owl.carousel.min.js"></script>
    <script src="${contextPath}/resources/js/jquery.stellar.min.js"></script>
    <script src="${contextPath}/resources/js/jquery.countdown.min.js"></script>
    <script src="${contextPath}/resources/js/jquery.magnific-popup.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap-datepicker.min.js"></script>
    <script src="${contextPath}/resources/js/aos.js"></script>

    <script src="${contextPath}/resources/js/main.js"></script>
	<script type="text/javascript" charset="utf-8">
	
	</script>
</body>

</html>