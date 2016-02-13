package org.psa.vaadinauth.ui;

import javax.annotation.PostConstruct;

import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

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

//		VerticalLayout viewLayout = new VerticalLayout(fields);
//		viewLayout.setSizeFull();
//		viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
//		viewLayout.setStyleName(Reindeer.LAYOUT_BLACK);
//		setCompositionRoot(viewLayout);
		
		layout.addComponent(fields);
	}
	
//	@PostConstruct
//	private void init() {
//		// Create a persistent person container
//		persons = JPAContainerFactory.make(Person.class, em);
//		persons.setEntityProvider(entityProvider);
//		// Bind it to a component
//		personTable = new Table("Список заказ-нарядов", persons);
//		personTable.setVisibleColumns("id", "firstName", "lastName");
//		layout.addComponent(personTable);
//	}

	@Override
	public void enter(ViewChangeEvent event) {
		
	}

}
