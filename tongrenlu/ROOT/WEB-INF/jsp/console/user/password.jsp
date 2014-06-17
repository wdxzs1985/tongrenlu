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
        <div class="page-header">
            <h1><span>修改密码</span></h1>
        </div>
        <form action="<c:url value="/console/user/password"/>" method="post" class="form-horizontal">
            <div class="form-group <c:if test="${!empty oldPassword_error}">has-error</c:if>">
                <label class="col-md-3 col-lg-2 form-label text-right" id="oldPassword">原密码</label>
                <div class="col-md-6 col-lg-4">
                    <input type="password" id="oldPassword" name="oldPassword" class="form-control" value="" />
                    <span class="help-block"><c:out value="${oldPassword_error}" default=""/></span>
                </div>
            </div>
            <div class="form-group <c:if test="${!empty password_error}">has-error</c:if>">
                <label class="col-md-3 col-lg-2 form-label text-right" id="password">新密码</label>
                <div class="col-md-6 col-lg-4">
                    <input type="password" id="password" name="password" class="form-control" value="" />
                    <span class="help-block"><c:out value="${password_error}" default=""/></span>
                </div>
            </div>
            <div class="form-group <c:if test="${!empty passwordAgain_error}">has-error</c:if>">
                <label class="col-md-3 col-lg-2 form-label text-right" id="passwordAgain">再输入一次密码</label>
                <div class="col-md-6 col-lg-4">
                    <input type="password" id="passwordAgain" name="passwordAgain" class="form-control" value="" />
                    <span class="help-block"><c:out value="${passwordAgain_error}" default=""/></span>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-3 col-md-6 col-lg-offset-2 col-lg-4">
                    <button type="submit" class="btn btn-primary">保存修改</button>
                    <a href="<c:url value="/console"/>" class="btn btn-default">取消</a>
                </div>
            </div>
        </form>
    </div>
    <%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</body>
</html>