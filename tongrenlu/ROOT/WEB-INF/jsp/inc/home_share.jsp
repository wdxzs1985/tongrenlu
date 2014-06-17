<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="append-bottom">
    <wb:publish action="pubilish" type="web" language="zh_cn" button_type="red" 
        button_size="middle" button_text="我要点32个赞！" 
        default_text="<c:out value="${articleBean.title}"/> / UP主：@<c:out value="${articleBean.userBean.nickname}"/> / 来自#东方同人录# <%= FULL_PATH %>" 
        tag="点32个赞！" 
        appkey="2ZiUc5" ></wb:publish>
    <wb:publish action="pubilish" type="web" language="zh_cn" button_type="red" 
        button_size="middle" button_text="我要求资源！" uid="2803325782" 
        default_text="<c:out value="${articleBean.title}"/> / UP主：@<c:out value="${articleBean.userBean.nickname}"/> / 来自#东方同人录# <%= FULL_PATH %>" 
        tag="求资源！" 
        appkey="2ZiUc5" ></wb:publish>
</div>