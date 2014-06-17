<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.name"/></title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/home_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="span12">
                <div class="alert alter-error">
                    服务器⑨了。。。<a href="<c:url value="/" />">返回</a>
                </div>
                <img src="<%=STATIC_PATH%>/tongrenlu/images/tokiomi.jpg">
            </div>
        </div>
    </div>
    <%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</body>
</html>