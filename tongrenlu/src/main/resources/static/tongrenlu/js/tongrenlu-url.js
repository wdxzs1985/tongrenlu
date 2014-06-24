$.fn.url = function(){
	var $this = $(this);
    var content = $this.html();
    content = content.replace(/((https?:\/\/)([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?(\?([\da-zA-Z\.-]+=[\da-zA-Z\.-]*(&amp;)?)*)?)/gi, '<a href="$01" target="_blank">$01</a>');
    $this.html(content);
};
