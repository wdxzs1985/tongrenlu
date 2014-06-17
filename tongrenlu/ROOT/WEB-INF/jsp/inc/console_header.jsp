<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="navbar navbar-static-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
            <a class="navbar-brand" href="<c:url value="/console"/>" title="博丽神社">
                <span>博丽神社</span>
            </a>
        </div>
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="navbar-nav nav navbar-right">
                <li><a href="<c:url value="/"/>"><fmt:message key="app.name"/></a></li>
                <c:if test="${!empty login_user}">
                    <li><a href="<c:url value="/logout"/>">退出</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</div>