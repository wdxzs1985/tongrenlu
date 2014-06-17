<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.name"/>隙间</title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<link href="<%= STATIC_PATH %>/jquery-ui/css/bootstrap/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css" />
  <!--[if IE]>
  <link rel="stylesheet" type="text/css" href="css/custom-theme/jquery.ui.1.8.16.ie.css"/>
  <![endif]-->
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="span3">
                <%@ include file="/WEB-INF/jsp/inc/console_menu.jsp" %>
                <%@ include file="/WEB-INF/jsp/inc/admin_menu.jsp" %>
            </div>
            <div class="span9">
                <div class="">
                    <ul class="breadcrumb">
                        <li>我现在的位置： </li>
                        <li><a href="<c:url value="/console"/>">博丽神社</a> <span class="divider">></span></li>
                        <li>活动</li>
                    </ul>
                </div>
                
                <c:if test="${empty paginateSupport.items}">
                    <div class="alert alert-block">
                        <h4>还没有任何活动！</h4>
                    </div>
                </c:if>
                <c:if test="${!empty paginateSupport.items}">
                    <table class="table table-striped table-bordered table-hover table-condensed">
                        <tr>
                            <th>活动名称</th>
                            <th>活动日期</th>
                        </tr>
                        <c:forEach items="${paginateSupport.items}" var="eventBean">
                            <tr>
                                <td><c:out value="${eventBean.eventName}"/></td>
                                <td><fmt:formatDate value="${eventBean.eventDate}" pattern="yyyy/MM/dd"/></td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
                <div class="btn-group">
                    <a href="#myModal" role="button" class="btn btn-large btn-primary" data-toggle="modal">添加新活动</a>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>


<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
<form action="<c:url value="/admin/event/input"/>" method="POST" class="" style="margin:0;">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">新活动</h3>
  </div>
  <div class="modal-body">
    <div class="control-group <c:if test="${!empty eventName_error}">error</c:if>">
        <h4>活动名称    <small>在这里输入活动名称，不能超过20个字</small></h4>
        <div class="">
            <input type="text" id="eventName" name="eventName" class="input-block-level" value="<c:out value="${eventBean.eventName}" default=""/>">
            <span class="help-block"><c:out value="${eventName_error}" default=""/></span>
        </div>
    </div>
    <div class="control-group <c:if test="${!empty eventDate_error}">error</c:if>">
        <h4>活动日期    <small>在这里输入活动日期</small></h4>
        <div class="">
            <input type="text" id="eventDate" name="eventDate" class="input-block-level" value="<fmt:formatDate value="${eventBean.eventDate}" pattern="yyyy/MM/dd"/>">
            <span class="help-block"><c:out value="${eventDate_error}" default=""/></span>
        </div>
    </div>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    <button type="submit" class="btn btn-primary">保存</button>
  </div>
</form>
</div>

<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script src="<%= STATIC_PATH %>/jquery-ui/js/jquery-ui-1.9.2.custom.min.js"></script>
<script>
    $(function(){
        $('#eventDate').datepicker({ dateFormat: "yy/mm/dd" });
    });
</script>
</body>
</html>