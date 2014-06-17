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
        <div class="page-header">
            <h1><span><c:out value="${login_user.nickname}"></c:out>的个人资料</span></h1>
        </div>
        <form class="" action="<c:url value="/console/user/setting"/>" method="post" enctype="multipart/form-data">
	        <div class="row">
	            <div class="col-md-4 col-lg-3">
                    <div class="form-group <c:if test="${!empty cover_error}">has-error</c:if>">
                        <div class="thumbnail append-bottom">
	                       <img class="" src="<%= FILE_PATH %>/${login_user.userId}/avatar_400.jpg?cache=<%= CURRENT_TIME %>" style="">
                        </div>
                        <input type="file" id="avatar" name="avatar" class="form-control" accept="image/jpeg,image/png">
                        <span class="help-block"><c:out value="${cover_error}" default=""/></span>
                    </div>
	            </div>
	            <div class="col-md-8 col-lg-9">
                    <h4>基本信息</h4>
                    <hr>
                    <div class="form-group">
	                    <div class="row">
                            <div class="col-lg-2 text-right"><label id="nickname" class="form-label ">昵称</label></div>
	                        <div class="col-lg-3"><input type="text" id="nickname" name="nickname" class="form-control" value="<c:out value="${login_user.nickname}"/>" /></div>
	                    </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-lg-2 text-right"><label id="signature" class="form-label ">个性签名</label></div>
                            <div class="col-lg-6"><textarea id="signature" name="signature" class="form-control" rows="8"><c:out value="${login_user.signature}"/></textarea></div>
                        </div>
                    </div>
                    <h4>偏好设置</h4>
                    <hr>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-lg-offset-2 col-lg-3">
                            <label class="checkbox">
                                <input type="checkbox" name="redFlg" value="1" <c:if test="${login_user.redFlg eq '1'}">checked</c:if> > 允许显示<span class="label label-danger">成年向</span>同人志
                            </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-lg-offset-2 col-lg-3">
                            <label class="checkbox">
                                <input type="checkbox" name="translateFlg" value="0" <c:if test="${login_user.translateFlg ne '1'}">checked</c:if> > 允许显示<span class="label label-warning">生肉</span>同人志
                            </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-lg-offset-2 col-lg-3">
                                <button type="submit" class="btn btn-primary">保存修改</button>
                                <a href="<c:url value="/console"/>" class="btn btn-default">取消</a>
                            </div>
                        </div>
                    </div>
			    </div>
			</div>
        </form>
    </div>
    <%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
</body>
</html>