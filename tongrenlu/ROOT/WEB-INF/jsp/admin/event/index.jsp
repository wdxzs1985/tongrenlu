<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title>博丽神社</title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
    <link href="<%= STATIC_PATH %>/jquery-ui/css/bootstrap/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="span3">
                <%@ include file="/WEB-INF/jsp/inc/console_menu.jsp" %>
                <%@ include file="/WEB-INF/jsp/inc/admin_menu.jsp" %>
            </div>
            <div class="span9">
                <div class="">
                    <ul class="breadcrumb">
                        <li>我现在的位置： </li>
                        <li><a href="<c:url value="/console"/>">博丽神社</a> <span class="divider">></span></li>
                        <li>活动</li>
                    </ul>
                </div>
                
                <c:if test="${empty paginateSupport.items}">
                    <div class="alert alert-block">
                        <h4>还没有任何活动！</h4>
                    </div>
                </c:if>
                <c:if test="${!empty paginateSupport.items}">
                    <table class="table table-striped table-bordered table-hover table-condensed">
                        <tr>
                            <th>活动名称</th>
                            <th>活动日期</th>
                        </tr>
                        <c:forEach items="${paginateSupport.items}" var="eventBean">
                            <tr>
                                <td><c:out value="${eventBean.eventName}"/></td>
                                <td><fmt:formatDate value="${eventBean.eventDate}" pattern="yyyy/MM/dd"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
                <div class="btn-group">
                    <a href="#myModal" role="button" class="btn btn-large btn-primary" data-toggle="modal">添加新活动</a>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script>
    $(function(){
    });
</script>
</body>
</html>