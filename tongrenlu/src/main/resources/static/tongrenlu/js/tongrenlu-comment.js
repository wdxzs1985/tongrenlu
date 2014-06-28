var comment = function(options) {
	
	var settings = $.extend({
		pageNumber: 1,
		emptyText: 'no comment',
		i18n:  {
		    ONE_SECOND : 1000,
		    ONE_MINUTE : 1000 * 60,
		    ONE_HOUR : 1000 * 60 * 60,
		    ONE_DATE : 1000 * 60 * 60 * 24,
		    YEAR : "年",
		    MONTH : "月",
		    DATE : "日",
		    REP_SEARCH : "{0}",
		    HOURS_BEFORE : "{0}小时前",
		    MINUTES_BEFORE : "{0}分前",
		    SECONDS_BEFORE : "{0}秒前",
		    JUST_BEFORE : "刚刚"
		}
	}, options);
	
	var that = {
		init: function() {
			$('#comment').on('submit', '.comment-form', function(e){
				e.preventDefault();
			    var $content = $(this).find('.comment-content');
			    if($content.hasClass('disabled')){
			        return;
			    }
			    $content.addClass('disabled');
			    var content = $content.val();
			    
			    if(content.length > 0){
			        var action = $(this).prop('action');
			        that.send(action, content);
			    } else {
			        alert('你一个字都没打。');
			    }
			}).on('click', '.previous a', function(e){
				e.preventDefault();
				that.load(settings.pageNumber - 1);
			}).on('click', '.next a', function(e){
				e.preventDefault();
				that.load(settings.pageNumber + 1);
			});
			
			that.load();
		},
		load: function(page){
			var $commentForm = $('#comment .comment-form');
			var href = $commentForm.attr('action');
			var data = {
			        p : settings.pageNumber
			    }
			if(page) {
				data.p = page;
			}
			$.getJSON(href, data).done(function(response){
				if(response.page){
					settings.pageNumber = response.page.pageNumber;
					
					var $listContent = $('#comment .comment-list-content').addClass('hidden');
					var $empty = $('#comment .comment-empty').addClass('hidden');

					var $previous = $listContent.find('.previous').addClass('hidden');
					var $next = $listContent.find('.next').addClass('hidden');
					
					if(response.page.items.length == 0) {
						$empty.removeClass('hidden');
					} else {
						var $list = $listContent.find('.media-list').empty();
						for(var i = 0; i < response.page.items.length; i++){
							var item = response.page.items[i];
							item.createDate = that.formatDate(item.createDate, settings.i18n);
							$list.append(tmpl('template-comment-item', item));
						}
						if(!response.page.first) {
							$previous.removeClass('hidden');
						}
						if(!response.page.last) {
							$next.removeClass('hidden');
						}
						$listContent.removeClass('hidden');
					}
				}
			});
			
		},
		formatDate: function(date, i18n){
		    var now = new Date();
		    var date = new Date(date);
		    var n = now.getTime() - date.getTime();
		    if(n > i18n.ONE_DATE){
		        return date.getFullYear() + i18n.YEAR + (date.getMonth() + 1) + i18n.MONTH + date.getDate() + i18n.DATE;
		    }else if (n>i18n.ONE_HOUR){
		        return i18n.HOURS_BEFORE.replace(i18n.REP_SEARCH,parseInt(n/i18n.ONE_HOUR));
		    }else if (n>i18n.ONE_MINUTE){
		        return i18n.MINUTES_BEFORE.replace(i18n.REP_SEARCH,parseInt(n/i18n.ONE_MINUTE));
		    }else if (n>i18n.ONE_SECOND){
		        return i18n.SECONDS_BEFORE.replace(i18n.REP_SEARCH,parseInt(n/i18n.ONE_SECOND));
		    }else {
		        return i18n.JUST_BEFORE;
		    }
		},
		send:  function(action, content){
		    var $content = $('#content');
		    var data = {'content': content};
		    $.post(action, data, function(response){
		        if(response.result){
		            that.load(1);
		            $('#content').val('');
		        }else {
		            alert(response.error);
		        }
		        $content.removeClass('disabled');
		    }).error(function(){
		        alert('服务器⑨了');
		    });
		}
	};
	
	that.init();
};

