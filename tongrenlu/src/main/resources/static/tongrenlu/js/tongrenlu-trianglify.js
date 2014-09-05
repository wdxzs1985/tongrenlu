(function($){
	
	var PALETTE_DEEP_PURPLE = ['#673ab7','#9575cd','#7e57c2','#b39ddb','#ede7f6','#d1c4e9'];
	var PALETTE_CYAN 		= ['#00bcd4','#4dd0e1','#26c6da','#80deea','#e0f7fa','#b2ebf2'];
	var PALETTE_AMBER 		= ['#ff6f00','#ffa000','#ff8f00','#ffcc80','#ffc107','#ffb300'];
	var PALETTE_ORANGE 		= ['#ff9800','#ffb74d','#ffa726','#ffcc80','#fff3e0','#ffe0b2'];
	var PALETTE_DEEP_ORANGE = ['#ff5722','#ff8a65','#ff7043','#ffab91','#fbe9e7','#ffccbc'];
	//var PALETTE_DEEP_PURPLE = ['#673ab7','#9575cd','#7e57c2','#b39ddb','#ede7f6','#d1c4e9'];
	
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
		default:
			settings.x_gradient = PALETTE_CYAN;
			break;
		}
		
		
	    var t = new Trianglify(settings);
	    var pattern = t.generate(this.width(), this.outerHeight());
	    this.css('background-image', pattern.dataUrl);
	    return this;
	}
})(jQuery);