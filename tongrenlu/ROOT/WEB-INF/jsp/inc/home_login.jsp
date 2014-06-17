<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
$(function(){
    window.init = function(user){
        window.user = user;
    	if(user){
            $('#nav-login').replaceWith(tmpl('login', {}));
    	} else {
            $('#nav-login').replaceWith(tmpl('logout', {}));
    	}
    };
    
    window.dologin = function(){
    	$('#login-modal').modal();
    }
    window.dologout = function(){
        $.ajax('<c:url value="/logout"/>').done(function(){
        	window.init();
        });
    }
    $('#login-submit').click(function(){
    	var email = $("#email").val();
    	var ep = MD5_hexhash(MD5_hexhash($("#p").val()));
        var remember = $('#remember').prop('checked')? '1' : '0';
        $.post('<c:url value="/login"/>', {email : email, password: ep, remember: remember}, function(response){
        	if(response.result) {
                window.init({
                	userId: response.userId,
                    nickname: response.nickname
                });
                $('#login-modal').modal('toggle')
            } else {
            	window.init();
	            if(response.error){
	                alert(response.error);
	            } else if(response.email_error){
	                alert(response.email_error);
	            } else if(response.password_error){
	                alert(response.password_error);
	            }
            }
        });
        return false;
    });
});
</script>
<c:if test="${empty login_user}">
<script type="text/javascript">
$(function(){
    window.init();
});
</script>
</c:if>
<c:if test="${!empty login_user}">
<script type="text/javascript">
$(function(){
    window.init({
        userId: '<c:out value="${login_user.userId}"/>',
        nickname: '<c:out value="${login_user.nickname}"/>'
    });
});
</script>
</c:if>

<script type="text/x-tmpl" id="login">
<ul id="nav-login" class="nav navbar-nav navbar-right ">
    <li><a href="<c:url value="/console"/>" title="<fmt:message key="app.console"/>"><fmt:message key="app.console"/></a></li>
    <li><a href="javascript:dologout();" title="<fmt:message key="app.logout"/>"><fmt:message key="app.logout"/></a></li>
</ul>
</script>
<script type="text/x-tmpl" id="logout">
<ul id="nav-login" class="nav navbar-nav navbar-right ">
    <li><a href="javascript:dologin();" title="<fmt:message key="app.login"/>"><fmt:message key="app.login"/></a></li>
    <li><a href="<c:url value="/register"/>" title="<fmt:message key="app.register"/>"><fmt:message key="app.register"/></a></li>
</ul>
</script>
<!-- Modal -->
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="login-modal-label" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="login-modal-label">什么人？快快报上名来。</h4>
      </div>
      <div class="modal-body">
          <c:if test="${!empty error}">
              <div class="alert alert-danger"><c:out value="${error}"/></div>
          </c:if>
          <div class="form-group <c:if test="${!empty email_error}">has-error</c:if>">
              <label class="control-label" for="email">电子邮箱</label>
              <input type="email" id="email" class="form-control" placeholder="电子邮箱">
              <span class="help-block"><c:out value="${email_error}"/></span>
          </div>
          <div class="form-group">
              <label class="control-label" for="p">密码</label>
              <input type="password" id="p" class="form-control" placeholder="密码">
          </div>
          <div class="checkbox">
              <label>
                  <input type="checkbox" id="remember" value="1" checked> 记住我
              </label>
          </div>
          <button id="login-submit" class="btn btn-danger btn-lg btn-block">进入神社</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->