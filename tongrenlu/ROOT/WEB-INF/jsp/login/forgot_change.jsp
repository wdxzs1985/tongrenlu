<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
            <div class="panel-heading text-center">设置新密码</div>
            <div class="panel-body">
            <form class="form-signin clearfix" action="<c:url value="/forgot/change"/>" method="post">
                <div class="form-group <c:if test="${!empty password_error}">has-error</c:if>">
                    <label class="control-label" for="email">新密码</label>
                    <input type="password" id="p" name="password" class="form-control" placeholder="新密码">
                    <span class="help-block"><c:out value="${password_error}"/></span>
                </div>
                <button class="btn btn-primary btn-lg btn-block" type="submit">修改</button>
            </form>
            <hr>
            <div class="btn-group">
               <a href="<c:url value="/login"/>" class="btn btn-link ">返回</a>
            </div>
            </div>
        </div>
    </div> <!-- /col-lg-6 -->
    </div> <!-- /row -->
    </div> <!-- /container -->
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
</body>
</html>