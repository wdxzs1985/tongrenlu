<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.name"/>隙间</title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-lg-3">
                <%@ include file="/WEB-INF/jsp/inc/console_menu.jsp" %>
                <%@ include file="/WEB-INF/jsp/inc/admin_menu.jsp" %>
            </div>
            <div class="col-md-8 col-lg-9">
                <div class="">
                    <ul class="breadcrumb">
                        <li>我现在的位置： </li>
                        <li><a href="<c:url value="/console"/>">博丽神社</a> <span class="divider">></span></li>
                        <li>维护工具</li>
                    </ul>
                </div>
                <div class="page">
                    <div class="page-header">
                        <h3>服务器状态</h3>
                    </div>
                    <h5>硬盘使用量：[<c:out value="总容量：${diskSpace} / 已使用：${usedSpace} / 其他文件：${otherSpace} / 空闲：${freeSpace}" />]</h5>
                    <div class="progress">
                      <div class="bar bar-success" style="width: <c:out value="${usedSpacePercent }" />%;"></div>
                      <div class="bar bar-warning" style="width: <c:out value="${otherSpacePercent }" />%;"></div>
                    </div>
                </div>
                <div class="page">
	                <div class="page-header">
	                    <h3>图片转换</h3>
	                </div>
                    <button id="convert_cover" data-type="post" data-href="<c:url value="/admin/convert-cover"/>" class="btn btn-danger">重新生成封面</button>
                    <button id="convert_avatar" data-type="post" data-href="<c:url value="/admin/convert-avatar"/>" class="btn btn-danger">重新生成头像</button>
                    <button id="convert_image" data-type="post" data-href="<c:url value="/admin/convert-thumbnail"/>" class="btn btn-danger">重新生成缩略图</button>
                </div>
                <div class="page">
                    <div class="page-header">
                        <h3>搜索引擎</h3>
                    </div>
                    <button id="create_solr_index" data-type="post" data-href="<c:url value="/admin/create-solr-index"/>" class="btn btn-danger">生成搜索索引</button>
                </div>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script>
$(function(){
    $('button[data-type="post"]').click(function(){
        var $this = $(this).button('loading');
        var url = $this.data('href');
        $.post(url,function (){
            alert('执行成功！');
        }).error(function(){ alert('服务器⑨了'); })
        .complete(function() { $this.button('reset'); });
        return false;
    });
    
});
</script>
</body>
</html>