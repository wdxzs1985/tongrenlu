(function($){
	$.fn.trianglify = function(options) {
		var defaults = {};
		
		var settings = $.extend(defaults, options);
		
	    var t = new Trianglify({noiseIntensity: 0});
	    if(settings.gradient) {
	    	t.options.x_gradient = settings.gradient;
	    } else {
	    	t.options.x_gradient = Trianglify.randomColor();
	    }
	    
	    t.options.y_gradient = t.options.x_gradient.map(function(c){return d3.rgb(c).brighter(.5)});
	    var pattern = t.generate(this.width(), this.outerHeight());
	    this.css('background-image', pattern.dataUrl);
	    
	    return this;
	}
})(jQuery);