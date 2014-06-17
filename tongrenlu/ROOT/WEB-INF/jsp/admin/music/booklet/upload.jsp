<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
<title>博丽神社 / 音乐审核 / <c:out value="${articleBean.title}"/> / 上传BK</title>
<%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
<link rel="stylesheet" href="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/css/jquery.fileupload.css">
<link rel="stylesheet" href="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/css/jquery.fileupload-ui.css">
<style>
    td.progressbar {
        width: 200px;
    }
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
        <li class="active">上传BK</li>
    </ul>
    <div class="alert alert-info alert-block">
        <strong>推荐使用<a href="http://www.firefox.com.cn/download/" class="btn btn-default btn-xs">八云蓝浏览器</a>或<a href="http://www.google.cn/intl/zh-CN/chrome/browser/" class="btn btn-default btn-xs">Chrome浏览器</a>等支持HTML5的浏览器进行上传操作。</strong>
    </div>
    <!-- The file upload form used as target for the file upload widget -->
    <form id="fileupload" action="<c:url value="/admin/music/${articleBean.articleId}/booklet/file"/>" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="articleId" value="<c:out value="${articleBean.articleId}"/>">
        <%@ include file="/WEB-INF/jsp/inc/admin_upload_table.jsp" %>
    </form>
    <div class="form-actions">
        <a class="btn btn-default pull-left" href="<c:url value="/admin/music"/>">
            <i class="glyphicon glyphicon-arrow-left"></i> <span>返回列表</span>
        </a>
        <a class="btn btn-primary pull-right" href="<c:url value="/admin/music/${articleBean.articleId}/booklet/sort"/>">
            <span>文件排序</span> <i class="glyphicon glyphicon-arrow-right "></i> 
        </a>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/admin_upload_template.jsp" %>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/vendor/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/JavaScript-Templates-2.4.0/js/tmpl.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/JavaScript-Load-Image-1.9.1/js/load-image.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/JavaScript-Canvas-to-Blob-2.0.7/js/canvas-to-blob.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/Gallery-2.11.4/js/blueimp-gallery.min.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/jquery.fileupload.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/jquery.fileupload-process.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/jquery.fileupload-image.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/jquery.fileupload-audio.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/jquery.fileupload-video.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/jquery.fileupload-validate.js"></script>
<script type="text/javascript" src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/jquery.fileupload-ui.js"></script>
<script type="text/javascript">
$(function () {
    'use strict';

    // Initialize the jQuery File Upload widget:
    $('#fileupload').fileupload({
        limitMultiFileUploads: 1,
        limitConcurrentUploads: 1,
        sequentialUploads: true,
        url: '<c:url value="/admin/music/${articleBean.articleId}/booklet/file"/>'
    });

    // Load existing files:
    $('#fileupload').addClass('fileupload-processing');
    $.ajax({
        // Uncomment the following to send cross-domain cookies:
        //xhrFields: {withCredentials: true},
        url: $('#fileupload').fileupload('option', 'url'),
        dataType: 'json',
        context: $('#fileupload')[0]
    }).always(function () {
        $(this).removeClass('fileupload-processing');
    }).done(function (result) {
        $(this).fileupload('option', 'done')
            .call(this, $.Event('done'), {result: result});
    });


});

</script>
<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
<!--[if (gte IE 8)&(lt IE 10)]>
<script src="<%= STATIC_PATH %>/jQuery-File-Upload-9.0.1/js/cors/jquery.xdr-transport.js"></script>
<![endif]-->
</body> 
</html>