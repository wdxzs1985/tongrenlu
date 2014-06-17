<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.name"/>博丽神社</title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="span3">
                <ul class="nav nav-tabs nav-stacked">
                    <li class="">
                        <a href="<c:url value="/console/comic/input"/>">漫画投稿</a>
                    </li>
                    <li class="">
                        <a href="<c:url value="/console/comic"/>">漫画投稿列表</a>
                    </li>
                    <li class="">
                        <a href="<c:url value="/console/comic"/>">漫画投稿列表</a>
                    </li>
                    <li class="">
                        <a href="<c:url value="/console/comic"/>">漫画投稿列表</a>
                    </li>
                    <li class="">
                        <a href="<c:url value="/console/comic"/>">漫画投稿列表</a>
                    </li>
                </ul>
            </div>
            <div class="span9">
                <div class="alert alter-error">
                    你误闯隙间。。。<a href="<c:url value="/console" />">返回</a>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</body>
</html>