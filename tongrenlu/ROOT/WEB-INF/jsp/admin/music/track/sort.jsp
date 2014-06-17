<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<title>博丽神社 / 音乐审核 / <c:out value="${articleBean.title}"/> / 曲目信息</title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<link href="<%= STATIC_PATH %>/jquery-ui/css/no-theme/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
<style>
</style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
<div class="container">
    <ul class="breadcrumb">
        <li>我现在的位置</li>
        <li><a href="<c:url value="/console"/>">博丽神社</a></li>
        <li><a href="<c:url value="/admin/music"/>">音乐审核</a></li>
        <li><c:out value="${articleBean.title}" default=""/></li>
        <li class="active">曲目信息</li>
    </ul>
    <form action="<c:url value="/admin/music/${articleBean.articleId}/track/sort"/>" method="post" class="">
        <div class="btn-toolbar">
            <div class="btn-group ">
                <button class="btn btn-success" id="name-sort"><i class="glyphicon glyphicon-file"></i> 按文件名排序</button>
                <button class="btn btn-info" id="time-sort"><i class="glyphicon glyphicon-time"></i> 按时间排序</button>
            </div>
            <div class="btn-group ">
                <button class="btn btn-primary" id="open-panel"><i class="glyphicon glyphicon-plus"></i> 展开编辑</button>
                <button class="btn btn-warning" id="close-panel"><i class="glyphicon glyphicon-minus"></i> 收起编辑</button>
            </div>
            <div class="btn-group ">
                <a href="<c:url value="/static/tongrenlu/html/touhou_ost.html"/>" class="btn btn-default" target="_blank">
                    <i class="glyphicon glyphicon-new-window"></i> 原曲列表
                </a>
            </div>
        </div>
        <hr>
        <div id="sort-file-list" class="row cover-grid">
            <c:forEach items="${trackBeanList}" var="track">
                <div class="col-xs-6 col-sm-4 col-md-2 col-lg-2 cover-object" data-file-id="<c:out value="${track.fileBean.fileId}"/>" data-name="<c:out value="${track.songTitle}"/>" >
                    <a class="thumbnail">
                        <img class="media-object" src="<%= FILE_PATH %>/${track.fileBean.articleId}/cover_180.jpg" style="">
                    </a>
                    <div class="media-body">
                        <input type="hidden" name="fileId[]" value="<c:out value="${track.fileBean.fileId}"/>">
                        <p class="text-center"><c:out value="${track.fileBean.name}"/></p>
                        <div class="hidden form-group">
                            <div class="row ">
                                <div class="col-md-8 col-lg-8">
                                    <label for="form-label">曲名：</label>
                                    <input type="text" name="songTitle[]" class="form-control" value="<c:out value="${track.songTitle}"/>">
                                </div>
                                <div class="col-md-4 col-lg-4">
                                    <label class="form-label" for="">表演者：</label>
                                    <input type="text" name="leadArtist[]" class="form-control" value="<c:out value="${track.leadArtist}"/>">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="form-label" for="">原曲：</label>
                                <textarea name="originalTitle[]" class="form-control" rows="3"><c:out value="${track.originalTitle}"/></textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="form-actions">
            <a class="btn btn-default pull-left" href="<c:url value="/admin/music/${articleBean.articleId}/track/upload"/>">
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
    // $( "#sort-file-list" ).disableSelection();
    
    var $listItem = $('#sort-file-list .cover-object');
    
    $( "#open-panel" ).click(function(){
    	$listItem.removeClass('col-xs-6 col-sm-4 col-md-2 col-lg-2').addClass('media col-lg-12');
    	$listItem.find('p').removeClass('text-center');
        $listItem.find('.thumbnail').addClass('pull-left');
    	$listItem.find('.form-group').removeClass('hidden');
        return false;
    });
    $( "#close-panel" ).click(function(){
        $listItem.removeClass('media col-lg-12').addClass('col-xs-6 col-sm-4 col-md-2 col-lg-2');
        $listItem.find('p').addClass('text-center');
        $listItem.find('.thumbnail').removeClass('pull-left');
        $listItem.find('.form-group').addClass('hidden');
        return false;
    });
    
    $( "#open-panel" ).click();

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
}) ;
</script>
</body>
</html>