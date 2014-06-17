<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="navbar navbar-static-top navbar-default">
    <div class="container">
        <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
            <a class="navbar-brand" href="<c:url value="/"/>" title="<fmt:message key="app.name"/>">
                <fmt:message key="app.name"/>
            </a>
        </div>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
           <ul class="nav navbar-nav">
               <li class="<c:if test="${ !empty NAV_MUSIC }">active</c:if>"><a href="<c:url value="/music"/>" title="<fmt:message key="app.music"/>"><fmt:message key="app.music"/></a></li>
               <li class="<c:if test="${ !empty NAV_COMIC }">active</c:if>"><a href="<c:url value="/comic"/>" title="<fmt:message key="app.comic"/>"><fmt:message key="app.comic"/></a></li>
               <li class="<c:if test="${ !empty NAV_SEARCH }">active</c:if>"><a href="<c:url value="/search"/>" title="搜索引擎">搜索引擎</a></li>
               <li class="divider-vertical"></li>
               <li class=""><a href="<c:url value="/fm"/>" title="<fmt:message key="app.fm"/>"><i class="icon icon-white icon-signal"></i> <fmt:message key="app.fm"/></a></li>
               <li class=""><a href="<c:url value="/static/tongrenlu/html/touhou_ost.html"/>" >原曲列表</a></li>
           </ul>
           <ul id="nav-login" class="nav navbar-nav navbar-right ">
           </ul>
       </div>
    </div>
</div>
