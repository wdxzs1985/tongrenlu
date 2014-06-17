<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title>博丽神社 / 音乐 / <c:out value="${articleBean.title}" default=""/></title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <ul class="breadcrumb">
            <li>我现在的位置： </li>
            <li><a href="<c:url value="/console"/>">博丽神社</a></li>
            <li><a href="<c:url value="/console/music"/>">音乐</a></li>
            <li><c:out value="${articleBean.title}" default=""/></li>
        </ul>
        <form action="<c:url value="/console/music/${articleBean.articleId}"/>" method="POST" enctype="multipart/form-data" class="">
            <div class="row">
                <div class="col-md-4 col-lg-3">
                    <%@ include file="/WEB-INF/jsp/inc/console_input_cover.jsp" %>
                </div>
                <div class="col-md-8 col-lg-9">
                    <%@ include file="/WEB-INF/jsp/inc/console_input_article.jsp" %>
                    <%@ include file="/WEB-INF/jsp/inc/console_input_tag.jsp" %>
                    <hr>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary"><i class="icon icon-white icon-ok-sign"></i>保存</button>
                        <a href="<c:url value="/console/music"/>" class="btn btn-default">取消</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/input-tag.js"></script>
</body>
</html>