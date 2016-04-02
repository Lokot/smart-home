package ru.skysoftlab.crongen;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.server.AbstractJavaScriptExtension;
import com.vaadin.ui.TextField;

@JavaScript({ "glyphicons-halflings.png", "glyphicons-halflings-white.png", "jquery-1.9.1.min.js", "bootstrap.min.js",
		"cronGen.min.js", "cronGen-connector.js" })
@StyleSheet({ "bootstrap-responsive.min.css", "bootstrap.min.css" })
public class CronGenExt extends AbstractJavaScriptExtension {

	private static final long serialVersionUID = 6683800926143994421L;

	public void extend(TextField component) {
		super.extend(component);
	}
}
