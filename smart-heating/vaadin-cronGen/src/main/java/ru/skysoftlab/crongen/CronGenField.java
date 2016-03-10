package ru.skysoftlab.crongen;

import java.io.Serializable;
import java.util.ArrayList;

import com.vaadin.annotations.JavaScript;
import com.vaadin.ui.AbstractJavaScriptComponent;

@JavaScript({ "jquery-1.9.1.min.js", "bootstrap.js", "cronGen.min.js", "cronGen-connector.js" })
public class CronGenField extends AbstractJavaScriptComponent {

	private static final long serialVersionUID = -5602694232509310156L;
	
	public static int currChartId = 0;
    protected int chartId = nextChartId();
    
    public CronGenField() {
    	setId(getDomId());
        getState().domId = getDomId();
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

}
