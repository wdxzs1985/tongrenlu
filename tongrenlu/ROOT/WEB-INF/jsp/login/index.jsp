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
            <div class="panel-heading text-center">什么人？快快报上名来。</div>
            <div class="panel-body">
                <form id="login-form" action="<c:url value="/login"/>" method="post">
                    <c:if test="${!empty error}">
                        <div class="alert alert-danger"><c:out value="${error}"/></div>
                    </c:if>
                    <div class="form-group <c:if test="${!empty email_error}">has-error</c:if>">
                        <label class="control-label" for="email">电子邮箱</label>
                        <input type="email" id="email" name="email" class="form-control" placeholder="电子邮箱">
                        <span class="help-block"><c:out value="${email_error}"/></span>
                    </div>
                    <div class="form-group">
                        <label class="control-label" for="p">密码</label>
                        <input type="password" id="p" class="form-control" placeholder="密码">
                        <input type="hidden" id="ep" name="password">
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="remember" value="1" checked> 记住我
                        </label>
                    </div>
                    <button class="btn btn-danger btn-lg btn-block" type="submit">进入神社</button>
                </form>
                <hr>
                <div class="btn-group btn-group-justified">
                   <a href="<c:url value="/register"/>" class="btn btn-link ">注册</a>
                   <a href="<c:url value="/forgot"/>" class="btn btn-link">找回密码</a>
                </div>
            </div>
        </div>
    </div> <!-- /col-lg-6 -->
    </div> <!-- /row -->
    </div> <!-- /container -->
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/plugins/md5.js"></script>
<script type="text/javascript">
    $(function(){
        $('#login-form').submit(function(){
            $("#ep").val(MD5_hexhash(MD5_hexhash($("#p").val())));
            return true;
        });
    });
</script>
</body>
</html>