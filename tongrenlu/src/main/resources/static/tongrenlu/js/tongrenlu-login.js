var login = function(options){
	var $signinModal = $('#signinModal');
	var $loginForm = $('#signinModal #login-form');
	
	var that = $.extend({
		signin: function(loginUser) {
	        $(document).data('loginUser', loginUser);
			if(!loginUser.guest) {
				$('#nav-signin').addClass('hidden').removeClass('show');
		        $('#nav-console').addClass('show').removeClass('hidden');
		        $('#nav-nickname').text(loginUser.nickname + '#' + loginUser.id);
		        $(document).trigger('signin', loginUser);
			} else {
				$('#nav-console').addClass('hidden').removeClass('show');
		        $('#nav-signin').addClass('show').removeClass('hidden');
		        $('#nav-nickname').text(loginUser.nickname);
			}
		},
		onShown: function(loginUser) {
			$('#inputEmail').focus();
		},
		onFormSubmit: function($loginForm) {
	        $.get(that.saltUrl).success(function(response){
	            if(response) {
	                var salt = response.salt;
	                var p = $('#inputPassword').val();
	                if(p){
	                    $('#hiddenPassword').val(md5(md5(p) + salt));
	                }

	                var $formGroupEmail = $('#form-group-email');
	                $formGroupEmail.removeClass('has-error has-feedback');
	                $formGroupEmail.find('.form-control-feedback').remove();
	                $formGroupEmail.find('.help-block').remove();
	                
	                var $formGroupPassword = $('#form-group-password');
	                $formGroupPassword.removeClass('has-error has-feedback');
	                $formGroupPassword.find('.form-control-feedback').remove();
	                $formGroupPassword.find('.help-block').remove();
	                
	                $('#signinModal .alert').remove();
	                
	                $.post(that.signinUrl, $loginForm.serialize()).success(function(response) {
	                    if(response) {
	                        if(response.result) {
	                        	that.signin(response.loginUser);
	                        	$signinModal.modal('hide');
	                        } else {
	                            if(response.emailError) {
	                                $formGroupEmail.addClass('has-error has-feedback')
	                                 .find('.form-control')
	                                 .after('<span class="glyphicon glyphicon-remove form-control-feedback"></span>')
	                                 .after($('<p class="help-block"></p>').text(response.emailError));
	                            }
	                            if(response.passwordError) {
	                                $formGroupPassword.addClass('has-error has-feedback')
	                                .find('.form-control')
	                                .after('<span class="glyphicon glyphicon-remove form-control-feedback"></span>')
	                                .after($('<p class="help-block"></p>').text(response.passwordError));
	                            }
	                            if(response.error) {
	                            	that.showError(response.error);
	                            }
	                        }
	                    } else {
	                    	that.onNetworkError();
	                    }
	                }).error(that.onNetworkError);
	            }
	        }).error(that.onNetworkError);
		},
		showError: function(message) {
			var $alert = $('<div class="alert alert-danger"></div>');
	        $alert.text(message);
	        $alert.append('<button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>');
	        $alert.prependTo($('#signinModal .modal-body'));
	        $alert.alert();
		},
		onNetworkError: function() {
			if(that.networkErrorMessage) {
				showError.showError(that.networkErrorMessage);
			}
		}
	}, options);
	
    $signinModal.on('shown.bs.modal', function() {
    	that.onShown();
    }).on('showerror', function(event, error) {
    	that.showError(error);
    });
    
    $loginForm.submit(function(e){
        e.preventDefault();
        that.onFormSubmit($loginForm);
    });
    return that;
}
