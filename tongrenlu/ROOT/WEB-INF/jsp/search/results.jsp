<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title><fmt:message key="app.name"/></title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_meta.jsp" %>
<link rel="stylesheet" type="text/css" href="<%= STATIC_PATH %>/jquery.jpaginate/css/style.css" media="screen"/>
</head>
<body>
    <div id="header">
        <c:set var="NAV_SEARCH" value="true"/>
        <%@ include file="/WEB-INF/jsp/inc/home_header.jsp" %>
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-lg-8">
                    <form id="search-form" action="<c:url value="/search"/>" class="form-search">
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
            <div class="col-md-8 col-lg-8">
                <c:if test="${ !empty  page.items }">
                    <div class="media-list cover-list">
                        <c:forEach items="${page.items}" var="doc" varStatus="status">
                            <div class="media <c:if test="${doc.redFlg eq '1'}">red</c:if>">
                                <a href="<c:url value="/${doc.category}/${doc.article_id}"/>" 
                                    title="<c:out value="${doc.title}"/>"
                                    class="thumbnail pull-left">
                                    <img src="<%= FILE_PATH %>/${doc.article_id}/cover_120.jpg"
                                        alt="<c:out value="${doc.title}"/>" class="media-object"/>
                                </a>
                                <div class="media-body">
                                    <div class="media-heading">
                                        <h5>
                                            <a href="<c:url value="/${doc.category}/${doc.article_id}"/>"
                                                title="<c:out value="${doc.title}"/>">
                                                <c:out value="${doc.title}"/>
                                            </a>
                                        </h5>
                                    </div>
                                    <p class="">
                                        <c:out value="${doc.description}"/>
                                    </p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <ul class="pagination visible-xs visible-sm clearfix">
                        <c:forEach begin="1" end="${page.pagenum}" varStatus="status">
                            <li class="<c:if test="${page.page eq status.count}">active</c:if>">
                                <a href="<c:url value="/search?page=${status.count}&q=${searchQuery}"/>"
                                    title="<c:out value="第${status.count}页"/>">
                                    <c:out value="${status.count}"/>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                    <div id="jpaginate" class="hidden-xs hidden-sm"></div>
                </c:if>
                <c:if test="${ empty  page.items }">
                    <div class="alert alert-block alert-success">
                        <p>妖梦检查了胸部，<br>但是什么也没有发现。</p>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/jquery.jpaginate/jquery.paginate.js"></script>
<script type="text/javascript">
$(function() {
    var count = $('.pagination li').length;
    $("#jpaginate").paginate({
        count       : count,
        start       : <c:out value="${page.page}"/>,
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
        	window.location.href = '<c:url value="/search?q=${searchQuery}"/>&page=' + currval;
        	return false;
        }
    });
});
</script>
</body>
</html>