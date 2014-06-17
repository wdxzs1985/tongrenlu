<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title>博丽神社 / 我投稿的同人志</title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= STATIC_PATH %>/jquery.jpaginate/css/style.css" media="screen"/>
</head>
<body>
    <div id="header">
        <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-md-offset-4 col-md-8 col-lg-offset-3 col-lg-9">
                    <form id="search-form" action="<c:url value="/console/comic"/>" class="">
                        <div class="input-group">
                            <input type="text" class="form-control" name="q" value="<c:out value="${searchQuery}"/>">
                            <span class="input-group-btn">
                                <button  type="submit" class="btn btn-primary" type="button">搜索</button>
                            </span>
                        </div><!-- /input-group -->
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-lg-3">
                <%@ include file="/WEB-INF/jsp/inc/console_menu.jsp" %>
                <%@ include file="/WEB-INF/jsp/inc/admin_menu.jsp" %>
            </div>
            <div class="col-md-8 col-lg-9">
                <ul class="breadcrumb ">
                    <li>我现在的位置 </li>
                    <li><a href="<c:url value="/console"/>">博丽神社</a></li>
                    <li class="active">我投稿的同人志</li>
                </ul>
                <ul class="media-list cover-list">
                    <c:forEach items="${paginateSupport.items}" var="articleBean">
                        <li class="media <c:if test="${articleBean.redFlg eq '1'}">red</c:if>">
                            <a href="<c:url value="/console/comic/${articleBean.articleId}"/>" class="pull-left thumbnail">
                                <img class="media-object" src="<%= FILE_PATH %>/${articleBean.articleId}/cover_90.jpg?cache=<%= CURRENT_TIME %>">
                            </a>
                            <div class="pull-right">
                                <div class="btn-group">
                                    <a href="<c:url value="/comic/${articleBean.articleId}"/>" target="_blank" class="btn btn-default">预览</a>
                                    <a href="<c:url value="/console/comic/${articleBean.articleId}/delete"/>" class="btn btn-danger btn-confirm">删除</a>
                                </div>
                            </div>
                            <div class="media-body">
                                <div class="media-heading">
                                    <c:if test="${articleBean.publishFlg eq '0'}"><span class="label label-warning">待审核</span>&nbsp;</c:if>
                                    <c:if test="${articleBean.publishFlg eq '1'}"><span class="label label-success">已审核</span>&nbsp;</c:if>
	                                <c:if test="${articleBean.redFlg ne '1'}"><span class="label label-success">全年龄</span>&nbsp;</c:if>
	                                <c:if test="${articleBean.redFlg eq '1'}"><span class="label label-danger">成年向</span>&nbsp;</c:if>
                                    <c:if test="${articleBean.translateFlg ne '0'}"><span class="label label-info">熟肉</span>&nbsp;</c:if>
                                    <c:if test="${articleBean.translateFlg eq '0'}"><span class="label label-warning">生肉</span>&nbsp;</c:if>
                                    <a class="" href="<c:url value="/console/comic/${articleBean.articleId}"/>"><c:out value="${articleBean.title}"/></a>
                                </div>
                                <p class="muted"><c:out value="${articleBean.description}"/></p>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
                <ul class="pagination visible-xs visible-sm clearfix">
                    <c:forEach begin="1" end="${paginateSupport.pagenum}" varStatus="status">
                        <li class="<c:if test="${paginateSupport.page eq status.count}">active</c:if>">
                            <a href="<c:url value="/console/comic?page=${status.count}&q=${searchQuery}"/>">
                                <c:out value="${status.count}"/>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
                <div id="jpaginate" class="hidden-xs hidden-sm"></div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/jquery.jpaginate/jquery.paginate.js"></script>
<script type="text/javascript">
$(function() {
    $("#jpaginate").paginate({
        count       : <c:out value="${paginateSupport.pagenum}"/>,
        start       : <c:out value="${paginateSupport.page}"/>,
        display     : 10,
        border                  : true,
        border_color            : '#fff',
        text_color              : '#fff',
        background_color        : '#3498db',  
        border_hover_color      : '#3498db',
        text_hover_color        : '#fff',
        background_hover_color  : '#2980b9', 
        images                  : false,
        onChange                : function (currval){
            window.location.href = '<c:url value="/console/comic?q=${searchQuery}"/>&page=' + currval;
            return false;
        }
    });
	$('.btn-confirm').click(function(){
		if(confirm('是否真的要删除这个投稿？')){
			return true;
		}
		return false;
	});
});
</script>
</body>
</html>