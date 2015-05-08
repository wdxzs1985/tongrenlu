(function($){
	//	NAME				= ['500',    '300',    '#400',   '#200',   '#50',    '#100'];
	var PALETTE_DEEP_PURPLE = ['#673ab7','#9575cd','#7e57c2','#b39ddb','#ede7f6','#d1c4e9'];
	var PALETTE_CYAN 		= ['#00bcd4','#4dd0e1','#26c6da','#80deea','#e0f7fa','#b2ebf2'];
	var PALETTE_AMBER 		= ['#ff6f00','#ffa000','#ff8f00','#ffcc80','#ffc107','#ffb300'];
	var PALETTE_ORANGE 		= ['#ff9800','#ffb74d','#ffa726','#ffcc80','#fff3e0','#ffe0b2'];
	var PALETTE_DEEP_ORANGE = ['#ff5722','#ff8a65','#ff7043','#ffab91','#fbe9e7','#ffccbc'];
	var PALETTE_TEAL 		= ['#009688','#4DB6AC','#26A69A','#80CBC4','#E0F2F1','#B2DFDB'];
	var PALETTE_LIGHT_BLUE 	= ['#03A9F4','#4FC3F7','#29B6F6','#81D4FA','#E1F5FE','#B3E5FC'];
	var PALETTE_GREEN 		= ['#4CAF50','#81C784','#66BB6A','#A5D6A7','#E8F5E9','#C8E6C9'];
	
	$.fn.trianglify = function(options) {
		var defaults = {
				width: window.innerWidth, 
				height: window.innerHeight
		};
		
		var settings = $.extend(defaults, options);
		
		switch(settings.palette) {
		case 'console':
			settings.x_colors = PALETTE_GREEN;
			break;
		case 'shoppingcart':
			settings.x_colors = PALETTE_AMBER;
			break;
		default:
			settings.x_colors = PALETTE_LIGHT_BLUE;
			break;
		}
		
		var that = this;
		$(window).resize(function() {
			settings.width = that.outerWidth();
			settings.height = that.outerHeight();
			
		    var pattern = Trianglify(settings);
		    that.css('background-image', 'url(' + pattern.png() + ')');
		}).resize();
		
	    return that;
	}
})(jQuery);