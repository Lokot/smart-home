window.ru_skysoftlab_crongen_CronGenField = function() {
	var e = this.getElement();
	var domId = this.getState().domId;
	
	// Getter and setter for the value property
	this.getValue = function () {
		return e.value;
	};
	
	this.setValue = function (value) {
		e.value = value;
	};
	
	this.onStateChange = function() {
		e.innerHTML = '<input id="'+domId+'_input" class="v-textfield v-widget v-textfield-required v-required v-has-width" />';
		$('#' + domId + '_input').cronGen();
	}
	
};