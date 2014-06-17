<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title><fmt:message key="app.name"/> / 博丽神社</title>
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
                    <h1><span>我的关注</span></h1>
                </div>
                <c:if test="${!empty page.items}">
                    <div class="media-list cover-list">
                        <c:forEach items="${page.items}" var="userBean" varStatus="status">
                            <li class="media">
                                <a href="<c:url value="/user/${userBean.userId}"/>" class="pull-left thumbnail">
                                    <img class="media-object" src="<%= FILE_PATH %>/${userBean.userId}/avatar_90.jpg">
                                </a>
                                <div class="pull-right">
                                    <span class="label label-info">关注:<c:out value="${userBean.followCount}"/></span> 
                                    <span class="label label-warning">粉丝:<c:out value="${userBean.fansCount}"/></span>
                                </div>
                                <div class="media-body">
                                    <div class="media-heading">
                                        <a class="" href="<c:url value="/user/${userBean.userId}"/>"><c:out value="${userBean.nickname}"/></a>
                                    </div>
                                    <p class="muted">
                                        <c:out value="${userBean.signature}"/>
                                    </p>
                                </div>
                            </li>
                        </c:forEach>
                    </div>
                    <ul class="pagination visible-xs visible-sm clearfix">
                      <c:forEach begin="1" end="${page.pagenum}" varStatus="status">
                          <li class="<c:if test="${page.page eq status.count}">active</c:if>">
                              <a href="<c:url value="/console/user/follow?page=${status.count}"/>"><c:out value="${status.count}"/></a>
                          </li>
                      </c:forEach>
                    </ul>
                    <div id="jpaginate" class="hidden-xs hidden-sm"></div>
                </c:if>
                <c:if test="${empty page.items}">
                    <div class="alert">
                        <h4>你还没有关注任何人</h4>
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
            window.location.href = '<c:url value="/console/user/follow"/>?page=' + currval;
            return false;
        }
    });
});
</script>
</body>
</html>