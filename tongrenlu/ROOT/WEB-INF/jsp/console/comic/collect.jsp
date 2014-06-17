<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title><fmt:message key="app.name"/> / 博丽神社 > 我收藏的同人志</title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= STATIC_PATH %>/jquery.jpaginate/css/style.css" media="screen"/>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-lg-3">
                <%@ include file="/WEB-INF/jsp/inc/console_menu.jsp" %>
                <%@ include file="/WEB-INF/jsp/inc/admin_menu.jsp" %>
            </div>
            <div class="col-md-8 col-lg-9">
		        <div class="page-header">
		            <h1><span>我收藏的同人志</span></h1>
		        </div>
                <div class="media-list cover-list">
                    <c:forEach items="${paginateSupport.items}" var="articleBean">
                        <div class="media <c:if test="${articleBean.redFlg eq '1'}">red</c:if>">
                            <a href="<c:url value="/comic/${articleBean.articleId}"/>" 
                                title="<c:out value="${articleBean.title}"/>"
                                class="thumbnail pull-left">
                                <img src="<%= FILE_PATH %>/${articleBean.articleId}/cover_120.jpg"
                                    alt="<c:out value="${articleBean.title}"/>" class="media-object"/>
                            </a>
                            <div class="media-body">
                                <div class="pull-right hidden-sm">
                                    <span class="label label-success">浏览:<c:out value="${articleBean.accessCount}"/></span>
                                    <span class="label label-info">吐槽:<c:out value="${articleBean.commentCount}"/></span> 
                                    <span class="label label-warning">收藏:<c:out value="${articleBean.collectCount}"/></span>
                                </div>
                                <div class="media-heading">
                                    <h5>
                                        <a href="<c:url value="/comic/${articleBean.articleId}"/>"
                                            title="<c:out value="${articleBean.title}"/>">
                                            <c:out value="${articleBean.title}"/>
                                        </a>
                                    </h5>
                                </div>
                                <p class="">
                                    <a href="<c:url value="/user/${articleBean.userBean.userId}"/>">
                                        <c:out value="${articleBean.userBean.nickname}"/>
                                    </a> / 
                                    <span class="text-muted">发布于  <fmt:formatDate value="${articleBean.publishDate}" pattern="yyyy'年'MM'月'dd'日'"/></span>
                                </p>
                                <p class="">
                                    <c:out value="${articleBean.description}"/>
                                </p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <ul class="pagination visible-xs visible-sm clearfix">
                  <c:forEach begin="1" end="${paginateSupport.pagenum}" varStatus="status">
                  <li class="<c:if test="${paginateSupport.page eq status.count}">active</c:if>">
                      <a href="<c:url value="/console/comic/collect?page=${status.count}"/>">
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
            window.location.href = '<c:url value="/console/comic/collect"/>?page=' + currval;
            return false;
        }
    });
});
</script>
</body>
</html>