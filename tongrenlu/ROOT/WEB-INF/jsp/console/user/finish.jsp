<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.name"/> / 博丽神社</title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
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
                <div class="alert alert-block alert-success">
                    <h4>个人信息修改成功！</h4>
                    <p><c:out value="${content}"/></p>
                    <a href="<c:url value="/console"/>" class="btn btn-default">返回</a>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</body>
</html>