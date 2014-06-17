<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title>博丽神社 / 同人志投稿</title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<link href="<%= STATIC_PATH %>/introjs/introjs.min.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <ul class="breadcrumb">
            <li>我现在的位置</li>
            <li><a href="<c:url value="/console"/>">博丽神社</a></li>
            <li><a href="<c:url value="/console/comic"/>">同人志</a></li>
            <li class="active">投稿</li>
        </ul>
        <form action="<c:url value="/console/comic/input"/>" method="POST" enctype="multipart/form-data" class="">
	        <div class="row">
                <div class="col-md-4 col-lg-3">
                    <%@ include file="/WEB-INF/jsp/inc/console_input_cover.jsp" %>
                </div>
                <div class="col-md-8 col-lg-9">
                    <%@ include file="/WEB-INF/jsp/inc/console_input_article.jsp" %>
                    <%@ include file="/WEB-INF/jsp/inc/console_input_tag.jsp" %>
                    <div data-step="5" data-intro="设置适当的分级">
                        <h4>分级</h4>
                        <div class="form-group">
                            <label class="radio-inline ">
                                <input type="radio" name="redFlg" value="0" <c:if test="${articleBean.redFlg ne '1'}">checked</c:if> > <span class="label label-success">全年龄向</span>
                            </label>&nbsp;&nbsp;
                            <label class="radio-inline">
                                <input type="radio" name="redFlg" value="1" <c:if test="${articleBean.redFlg eq '1'}">checked</c:if> > <span class="label label-danger">成年向</span>
                            </label>
                        </div>
                        <div class="form-group">
                            <label class="radio-inline">
                                <input type="radio" name="translateFlg" value="1" <c:if test="${articleBean.translateFlg ne '0'}">checked</c:if> > <span class="label label-info">熟肉</span>
                            </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <label class="radio-inline ">
                                <input type="radio" name="translateFlg" value="0" <c:if test="${articleBean.translateFlg eq '0'}">checked</c:if> > <span class="label label-warning">生肉</span>
                            </label>
                        </div>
                    </div>
                    <hr>
                    <div class="form-group" data-step="6" data-intro="最后保存投稿">
                        <button type="submit" class="btn btn-primary"><i class="icon icon-white icon-ok-sign"></i>保存</button>
                        <a class="btn btn-success" href="javascript:void(0);" onclick="javascript:introJs().start();"><i class="icon icon-white icon-question-sign"></i>投稿帮助</a>
                        <a href="<c:url value="/console/comic"/>" class="btn btn-default">取消</a>
                    </div>
	            </div>
	        </div>
        </form>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/introjs/intro.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/tongrenlu/js/input-tag.js"></script>
</body>
</html>