<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title><fmt:message key="app.name"/> > <fmt:message key="app.comic"/></title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_meta.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= STATIC_PATH %>/jquery.jpaginate/css/style.css" media="screen"/>
</head>
<body>
    <div id="header">
        <c:set var="NAV_SEARCH" value="true"/>
        <%@ include file="/WEB-INF/jsp/inc/home_header.jsp" %>
        <div class="container">
            <div class="col-md-offset-3 col-md-6" style="margin-top:100px;margin-bottom:100px;">
            <h1 class="text-center">东方搜集录</h1>
            <form id="search-form" action="<c:url value="/search"/>" class="form-search">
                <div class="input-group">
                    <input type="text" class="form-control" name="q" value="<c:out value="${searchQuery}"/>">
                    <span class="input-group-btn">
                        <button  type="submit" class="btn btn-primary" type="button">搜　索</button>
                    </span>
                </div><!-- /input-group -->
            </form>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/jquery.jpaginate/jquery.paginate.js"></script>
</body>
</html>