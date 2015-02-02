(function($){
	//	NAME				= ['500',    '300',    '#400',   '#200',   '#50',    '#100'];
	var PALETTE_DEEP_PURPLE = ['#673ab7','#9575cd','#7e57c2','#b39ddb','#ede7f6','#d1c4e9'];
	var PALETTE_CYAN 		= ['#00bcd4','#4dd0e1','#26c6da','#80deea','#e0f7fa','#b2ebf2'];
	var PALETTE_AMBER 		= ['#ff6f00','#ffa000','#ff8f00','#ffcc80','#ffc107','#ffb300'];
	var PALETTE_ORANGE 		= ['#ff9800','#ffb74d','#ffa726','#ffcc80','#fff3e0','#ffe0b2'];
	var PALETTE_DEEP_ORANGE = ['#ff5722','#ff8a65','#ff7043','#ffab91','#fbe9e7','#ffccbc'];
	var PALETTE_TEAL 		= ['#009688','#4DB6AC','#26A69A','#80CBC4','#E0F2F1','#B2DFDB'];
	
	$.fn.trianglify = function(options) {
		var defaults = {
			noiseIntensity: 0,
			cellpadding: 0
		};
		
		var settings = $.extend(defaults, options);
		
		switch(settings.palette) {
		case 'console':
			settings.x_gradient = PALETTE_DEEP_PURPLE;
			break;
		case 'shoppingcart':
			settings.x_gradient = PALETTE_AMBER;
			break;
		default:
			settings.x_gradient = PALETTE_TEAL;
			break;
		}
		
	    var t = new Trianglify(settings);
	    var pattern = t.generate(2000, 1000);
	    this.css('background-image', pattern.dataUrl);
	    return this;
	}
})(jQuery);