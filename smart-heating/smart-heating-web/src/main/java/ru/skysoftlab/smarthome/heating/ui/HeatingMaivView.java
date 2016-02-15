package ru.skysoftlab.smarthome.heating.ui;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class HeatingMaivView extends BaseMenuView {

	private static final long serialVersionUID = 4326909760534322574L;
	
	public static final String NAME = "heating";
	
	public HeatingMaivView() {
		super();
		setSizeFull();
		Label text = new Label("wwwwwwwwwwwwwwwwwwwwwwwwwww");
		HorizontalLayout fields = new HorizontalLayout(text);
		fields.setSpacing(true);
		fields.setMargin(new MarginInfo(true, true, true, false));
		fields.setSizeUndefined();

		layout.addComponent(fields);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
