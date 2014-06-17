<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="apple-mobile-web-app-capable" content="yes" />
<link href="<%= STATIC_PATH %>/bootstrap-3.1.1/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="<%= CSS_PATH %>/styles_20130806.css" rel="stylesheet" type="text/css"/>
<% 
if (!isLocal){
%>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?bb5b28c66654c5ac1e987bd55a006657";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<% 
}
%>