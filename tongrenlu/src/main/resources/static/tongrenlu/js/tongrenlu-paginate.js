var paginate = function(options) {
	var settings = $.extend({
		count       : 20,
        start       : 1,
        display     : 10,
        border                  : false,
        images                  : false,
        onChange                : function (currval){
            return false;
        }
	}, options);
	
	$("#jpaginate").paginate(settings);
	
	var $select = $('<select class="form-control"></select>').appendTo('#select-paginate');
	$select.change(function(){
		settings.onChange($select.val());
	});
	for(var i=0;i<settings.count;i++) {
		var page = 1 + i;
		$select.append($('<option ></option>').val(page).text(page));
	}
	$select.val(settings.start);
}