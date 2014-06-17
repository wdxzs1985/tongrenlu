<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.name"/></title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
    <%@ include file="/WEB-INF/jsp/inc/home_meta.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/jsp/inc/home_header.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-lg-3">
            <%@ include file="/WEB-INF/jsp/inc/home_user_menu.jsp" %>
        </div>
        <div class="col-md-8 col-lg-9">
            <h1><c:out value="${userBean.nickname}"></c:out></h1>
            <blockquote><c:out value="${userBean.signature}"></c:out></blockquote>
            <hr>
            <div class="">
                <div class="panel panel-default">
                    <div class="panel-heading">留言</div>
                    <div class="panel-body">
                        <div id="comment-area" class="append-bottom" 
                            data-href="<c:url value="/user/${userBean.userId}/comment"/>"
                            data-empty-text="尚未有留言">
                        </div>
                        <form id="comment-form" action="<c:url value="/user/${userBean.userId}/comment"/>" method="post" >
                            <div class="form-group">
                                <textarea id="content" placeholder="留言" rows="8" class="form-control"></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary"><i class="icon icon-white icon-comment"></i> 发表</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/home_login.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/comment.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/follow.js"></script>
<script type="text/javascript">
$(function(){
    new commentObject('<%= FILE_PATH %>');
});
</script>
</body>
</html>