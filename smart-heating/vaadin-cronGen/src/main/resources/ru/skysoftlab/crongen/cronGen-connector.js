window.ru_skysoftlab_crongen_CronGenField = function() {
	var e = this.getElement();
	var domId = this.getState().domId;
	
	e.innerHTML = '<input id="'
		+ domId
		+ '_input" class="v-textfield v-widget v-textfield-required v-required v-has-width" />';
	$('#' + domId + '_input').cronGen(domId);
	
	var connector = this;
	$('#' + domId + '_input').change(function() {
		connector.getState().value = $('#' + domId + '_input').val();
//		e.value
	});

	// Getter and setter for the value property
//	this.setValue = function() {
//		return this.getState().value;
//	};

	this.onStateChange = function() {
		$('#' + domId + '_input').val(this.getState().value);
	}

};