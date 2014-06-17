<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page isThreadSafe="false" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="message" />
<% 
final String SERVER_NAME = request.getServerName();
final boolean isLocal = StringUtils.contains(SERVER_NAME, "127.0.0.1")
                        || StringUtils.contains(SERVER_NAME,
                                                "192.168.11.");
final String FULL_PATH = "http://" + SERVER_NAME;
final String STATIC_PATH = "/static";
final String CSS_PATH = STATIC_PATH + "/tongrenlu/css";
final String IMAGE_PATH = STATIC_PATH + "/tongrenlu/images";
final String FILE_PATH = isLocal ? "http://192.168.11.9/resource" : FULL_PATH + "/resource";
final String CONTEXT_PATH = request.getContextPath() ;
long CURRENT_TIME = System.currentTimeMillis() ; 
%>
<!DOCTYPE html >