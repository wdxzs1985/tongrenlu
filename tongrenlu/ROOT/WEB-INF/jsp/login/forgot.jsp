<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/inc/page_config.jsp" %>
<html>
<head>
    <title><fmt:message key="app.name"/>博丽神社 > 找回密码</title>
    <%@ include file="/WEB-INF/jsp/inc/meta.jsp" %>
</head>
<body>
    <%@ include file="/WEB-INF/jsp/inc/console_header.jsp" %>
    <div class="container">
    <div class="row">
    <div class="col-lg-4 col-lg-offset-4">
        <div class="panel panel-default">
            <div class="panel-heading text-center">找回密码</div>
            <div class="panel-body">
            <form id="forgot-form" action="<c:url value="/forgot"/>" method="post">
                <c:if test="${!empty error}">
                    <div class="alert alert-danger"><c:out value="${error}"/></div>
                </c:if>
                <div class="form-group">
                    <label class="control-label" for="email">电子邮箱</label>
                    <input type="text" id="email" name="email" class="form-control" placeholder="电子邮箱">
                </div>
                <button class="btn btn-primary btn-lg btn-block" type="submit">下一步 > </button>
            </form>
            <hr>
            <div class="btn-group btn-group-justified">
               <a href="<c:url value="/login"/>" class="btn btn-link ">进入神社</a>
               <a href="<c:url value="/register"/>" class="btn btn-link">注册</a>
            </div>
            </div>
        </div>
    </div> <!-- /col-lg-6 -->
    </div> <!-- /row -->
    </div> <!-- /container -->
<%@ include file="/WEB-INF/jsp/inc/footer.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/scripts.jsp" %>
<script type="text/javascript">

$(function(){

    $('#sendmail').click(function(){
        var $this = $(this);
        if($this.hasClass('disabled')){
            return false;
        }
        $this.addClass('disabled');
        var countdown = 60;
        var doCountDown = function(){
            if(countdown > 0){
                $this.text('(' + countdown + ') 秒后重新发送');
                countdown--;
                setTimeout(doCountDown, 1000);
            }else {
                $this.text('发送验证码');
                $this.removeClass('disabled');
            }
        };
        setTimeout(doCountDown, 1000);
        
        
        $.post('<c:url value="/forgot/send"/>', { email: $('#email').val() }, function(response){
            if(response.result) {
                $('#verify').addClass('in');
            } else {
                alert(response.error);
            }
        }).error(function(){
            alert('服务器⑨了');
        });
        return false;
    });

});

</script>
</body>
</html>