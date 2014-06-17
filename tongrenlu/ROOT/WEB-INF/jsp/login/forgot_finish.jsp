<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title>博丽神社 > 密码修改成功</title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-lg-offset-3">
                <div class="alert alert-block alert-success">
                    <h4>密码修改成功！</h4>
                    <p>&nbsp;</p>
                    <div>
                        <a href="<c:url value="/login"/>" class="btn btn-default">进入神社</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</body>
</html>