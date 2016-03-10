window.ru_skysoftlab_crongen_CronGenField = function() {
	var e = this.getElement();
	var domId = this.getState().domId;
	
	this.onStateChange = function() {
		e.innerHTML = '<input id="'+domId+'_input" />';
		$('#' + domId + '_input').cronGen();
	}
	
};