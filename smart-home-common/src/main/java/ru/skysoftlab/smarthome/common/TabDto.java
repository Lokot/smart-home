package ru.skysoftlab.smarthome.common;

import com.vaadin.ui.Layout;

/**
 * 
 * @author Lokot
 *
 */
public class TabDto {

	private Layout layout;
	private String name;

	public TabDto() {
		super();
	}

	public TabDto(Layout layout, String name) {
		super();
		this.layout = layout;
		this.name = name;
	}

	public Layout getLayout() {
		return layout;
	}

	public String getName() {
		return name;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public void setName(String name) {
		this.name = name;
	}

}
