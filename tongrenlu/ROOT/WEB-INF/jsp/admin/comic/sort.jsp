<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>博丽神社 / 同人志审核 / <c:out value="${articleBean.title}"/> / 文件排序</title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<link href="<%= STATIC_PATH %>/jquery-ui/css/no-theme/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
<div class="container">
    <ul class="breadcrumb">
        <li>我现在的位置</li>
        <li><a href="<c:url value="/console"/>">博丽神社</a></li>
        <li><a href="<c:url value="/admin/comic"/>">同人志审核</a></li>
        <li><c:out value="${articleBean.title}" default=""/></li>
        <li class="active">文件排序</li>
    </ul>
    <form action="<c:url value="/admin/comic/${articleBean.articleId}/sort"/>" method="post">
        <div class="clearfix">
            <div class="btn-group pull-left">
                <button class="btn btn-success" id="name-sort"><i class="glyphicon glyphicon-file"></i> 按文件名排序</button>
                <button class="btn btn-info" id="time-sort"><i class="glyphicon glyphicon-time"></i> 按时间排序</button>
            </div>
            <div class="btn-group pull-right">
                <button class="btn btn-danger" id="convert"><i class="glyphicon glyphicon-refresh"></i> 重新生成缩略图</button>
            </div>
        </div>
        <hr>
        <div id="sort-file-list" class="row cover-grid">
            <c:forEach items="${fileBeanList}" var="fileBean">
                <div class="col-xs-6 col-sm-4 col-md-2 col-lg-2 cover-object" data-file-id="<c:out value="${fileBean.fileId}"/>" data-name="<c:out value="${fileBean.name}"/>">
                    <a class="thumbnail">
                        <img class="" src="<%= FILE_PATH %>/${fileBean.articleId}/${fileBean.fileId}_300.jpg">
                    </a>
                    <p class="">
                        <c:out value="${fileBean.name}"/>
                        <input type="hidden" name="fileId[]" value="<c:out value="${fileBean.fileId}"/>">
                    </p>
                </div>
            </c:forEach>
        </div>
        <div class="form-actions">
            <a class="btn btn-default pull-left" href="<c:url value="/admin/comic/${articleBean.articleId}/upload"/>">
                <i class="glyphicon glyphicon-arrow-left"></i> 文件上传 
            </a>
            <button class="btn btn-primary pull-right"><i class="glyphicon glyphicon-ok"></i> 保存</button>
        </div>
    </form>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script src="<%= STATIC_PATH %>/jquery-ui/js/jquery.ui.core.min.js"></script>
<script src="<%= STATIC_PATH %>/jquery-ui/js/jquery.ui.widget.min.js"></script>
<script src="<%= STATIC_PATH %>/jquery-ui/js/jquery.ui.mouse.min.js"></script>
<script src="<%= STATIC_PATH %>/jquery-ui/js/jquery.ui.sortable.min.js"></script>
<script>
$(function(){
    $( "#sort-file-list" ).sortable();
    $( "#sort-file-list" ).disableSelection();
    
    $("#time-sort").click(function(){
        var $fileList = $( "#sort-file-list .cover-object" );
        var $tempFileList = $('<div></div>');
        $.each($fileList, function(i1, e1){
            var $e1 =  $(e1);
            var fileId1 = $e1.data("fileId");
            
            $tempFileList.prepend($e1);
            $.each($tempFileList.children('.cover-object'), function(i2, e2){
                var $e2 =  $(e2);
                var fileId2 = $e2.data("fileId");
                if(fileId1 > fileId2){
                    $e1.insertAfter($e2);
                }
            });
        });
        $tempFileList.children('.cover-object').appendTo('#sort-file-list');
        return false;
    });
    $("#name-sort").click(function(){
        var $fileList = $( "#sort-file-list .cover-object" );
        var $tempFileList = $('<div></div>');
        $.each($fileList, function(i1, e1){
            var $e1 =  $(e1);
            var name1 = $e1.data("name");
            $tempFileList.prepend($e1);
            $.each($tempFileList.children('.cover-object'), function(i2, e2){
                var $e2 =  $(e2);
                var name2 = $e2.data("name");
                if(name1 > name2){
                    $e1.insertAfter($e2);
                }
            });
        });
        $tempFileList.children('.cover-object').appendTo('#sort-file-list');
        return false;
    });
    
    $("#convert").click(function(){
        var $this = $(this).button('loading');
        $.post('<c:url value="/admin/convert/thumbnail/"/>', { articleId: '${articleBean.articleId}' },function (){
            alert('缩略图转换成功！');
        }).error(function(){ alert('服务器⑨了'); })
        .complete(function() { $this.button('reset'); });
        return false;
    });
}) ;
</script>
</body>
</html>