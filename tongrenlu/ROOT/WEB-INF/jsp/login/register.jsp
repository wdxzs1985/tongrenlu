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
    <div class="col-lg-4 col-lg-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading text-center">要成为幻想乡的住民，就先注册吧</div>
            <div class="panel-body">
            <form id="register-form" action="<c:url value="/register"/>" method="post">
                <c:if test="${!empty error}">
                    <div class="alert alert-danger"><c:out value="${error}"/></div>
                </c:if>
                <div class="form-group <c:if test="${!empty email_error}">has-error</c:if>">
                    <label class="control-label" for="email">电子邮箱</label>
                    <input type="email" id="email" name="email" class="form-control" placeholder="电子邮箱" value="<c:out value="${userBean.email}"/>">
                    <span class="help-block"><c:out value="${email_error}"/></span>
                </div>
                <div class="form-group <c:if test="${!empty nickname_error}">has-error</c:if>">
                    <label class="control-label" for="nickname">昵称</label>
                    <input type="text" id="nickname" name="nickname" class="form-control" placeholder="昵称" value="<c:out value="${userBean.nickname}"/>">
                    <span class="help-block"><c:out value="${nickname_error}"/></span>
                </div>
                <div class="form-group <c:if test="${!empty password_error}">has-error</c:if>">
                    <label class="control-label" for="password">密码</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="密码">
                    <span class="help-block"><c:out value="${password_error}"/></span>
                </div>
                <button class="btn btn-success btn-lg btn-block" type="submit">注册</button>
            </form>
            <hr>
            <div class="btn-group btn-group-justified">
               <a href="<c:url value="/login"/>" class="btn btn-link ">进入神社</a>
               <a href="<c:url value="/forgot"/>" class="btn btn-link">找回密码</a>
            </div>
            </div>
        </div>
    </div> <!-- /col-lg-6 -->
    </div> <!-- /row -->
    </div> <!-- /container -->
    <%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</body>
</html>