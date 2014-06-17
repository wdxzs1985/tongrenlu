<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="panel panel-default">
    <div class="panel-heading">评论</div>
    <div class="panel-body">
        <div id="comment-area" class="append-bottom" 
            data-href="<c:url value="/article/${articleBean.articleId}/comment"/>"
            data-empty-text="目前尚未有评论">
        </div>
        <form id="comment-form" action="<c:url value="/article/${articleBean.articleId}/comment"/>" method="post" >
            <div class="form-group">
                <textarea id="content" placeholder="我评论发自真心" rows="8" class="form-control"></textarea>
            </div>
            <button type="submit" class="btn btn-primary"><i class="icon icon-comment icon-white"></i> 发表</button>
        </form>
    </div>
</div>