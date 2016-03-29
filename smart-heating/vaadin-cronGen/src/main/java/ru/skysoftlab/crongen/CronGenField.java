package ru.skysoftlab.crongen;

import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;

@JavaScript({ "glyphicons-halflings.png", "glyphicons-halflings-white.png",
		"jquery-1.9.1.min.js", "bootstrap.min.js", "cronGen.min.js",
		"cronGen-connector.js" })
@StyleSheet({ "bootstrap-responsive.min.css", "bootstrap.min.css" })
public class CronGenField extends AbstractJavaScriptComponentField<String> {

	private static final long serialVersionUID = -5602694232509310156L;

	public static int currChartId = 0;
	protected int chartId = nextChartId();

	public CronGenField() {
		setId(getDomId());
		getState().domId = getDomId();
	}
	
	public CronGenField(String caption) {
        this();
        setCaption(caption);
    }

	public static int nextChartId() {
		return ++currChartId;
	}

	public interface ValueChangeListener extends Serializable {
		void valueChange();
	}

	ArrayList<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();

	public void addValueChangeListener(ValueChangeListener listener) {
		listeners.add(listener);
	}

	public void setValue(String value) {
		getState().value = value;
	}

	public String getValue() {
		return getState().value;
	}

	@Override
	protected CronGenState getState() {
		return (CronGenState) super.getState();
	}

	/**
	 * Returns the DOM ID of the chart component.
	 *
	 * @return {@link String}
	 */
	public String getDomId() {
		return "cron_" + chartId;
	}

	@Override
	public boolean isEmpty() {
		return getValue().isEmpty();
	}

	@Override
	public void clear() {
		setValue("");
	}

	@Override
	public Class<? extends String> getType() {
		return String.class;
	}

}
