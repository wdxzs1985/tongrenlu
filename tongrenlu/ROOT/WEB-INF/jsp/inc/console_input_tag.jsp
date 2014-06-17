<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="form-group" data-step="4" data-intro="在这里输入标签，输入标签后回车即可成功添加一个标签">
    <h4>标签    <small>在文本框输入标签后回车即可成功添加一个标签</small></h4>
    <div id="tag-area" class="">
        <div class="auto-add clearfix" style="margin-bottom:5px;">
            <c:forEach items="${tagBeanList}" var="tagBean">
                <div class="btn-group remove">
                    <a href="<c:url value="/tag/${tagBean.tagId}"/>" class="btn btn-default btn-mini" target="_blank">
                        <c:out value="${tagBean.tag}"></c:out>
                    </a>
                    <button class='btn btn-default btn-mini btn-danger'
                            data-article-id='<c:out value="${articleBean.articleId}"/>'
                            data-tag-id='<c:out value="${tagBean.tagId}"/>'>
                        <i class="glyphicon glyphicon-remove"></i>
                    </button>
                </div>
            </c:forEach>
            <c:forEach items="${inputTagBeanList}" var="tagBean">
                <div class="btn-group">
                    <a href="<c:url value="/tag/${tagBean.tagId}"/>" class="btn btn-default btn-mini" target="_blank">
                        <c:out value="${tagBean.tag}"></c:out>
                    </a>
                    <input type="hidden" name="tag[]" value="<c:out value="${tagBean.tag}"/>" />
                    <input type="hidden" name="tagId[]" value="<c:out value="${tagBean.tagId}"/>" />
                    <button class="btn btn-mini btn-danger" data-tag-id="<c:out value="${tagBean.tagId}"/>"><i class="glyphicon glyphicon-remove"></i></button>
                </div>
            </c:forEach>
        </div>
        <input type="text" data-name="tag[]" class="form-control auto-add" placeholder="" data-provide="typeahead" />
    </div>
</div>