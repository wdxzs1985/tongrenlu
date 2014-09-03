(function($){
	$.fn.trianglify = function(options) {
		var defaults = {
			noiseIntensity: 0,
			cellpadding: 0
		};
		
		var settings = $.extend(defaults, options);
	    var t = new Trianglify(settings);
	    var pattern = t.generate(this.width(), this.outerHeight());
	    this.css('background-image', pattern.dataUrl);
	    return this;
	}
})(jQuery);