<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.console"/> / <fmt:message key="app.comic"/> / <fmt:message key="console.input.finish"/></title>
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
                    <h4><fmt:message key="console.input.finish"/></h4>
                    <p>请耐心等待猴子们的审核</p>
                </div>
                <div>
                    <a href="<c:url value="/console/comic"/>" class="btn btn-default ">
                        <span>返回列表</span>
                    </a>
                    <a href="<c:url value="/console/comic/input"/>" class="btn btn-primary">
                        <span>继续投稿</span>
                    </a>
                    <c:if test="${login_user.adminFlg eq '1'}">
                        <a href="<c:url value="/admin/comic"/>" class="btn btn-danger">
                            <span>去审核</span>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</body>
</html>