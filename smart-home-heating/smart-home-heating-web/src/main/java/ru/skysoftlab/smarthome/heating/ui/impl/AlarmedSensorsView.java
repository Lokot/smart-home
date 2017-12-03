package ru.skysoftlab.smarthome.heating.ui.impl;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import ru.skysoftlab.skylibs.security.RolesList;
import ru.skysoftlab.skylibs.web.annatations.MainMenuItem;
import ru.skysoftlab.skylibs.web.annatations.MenuItemView;
import ru.skysoftlab.skylibs.web.ui.BaseMenuView;
import ru.skysoftlab.smarthome.heating.dto.AlarmedSensorDto;
import ru.skysoftlab.smarthome.heating.services.SensorsService;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuNames;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuOrder;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuOrder;

/**
 * Отображает датчики в состоянии сигнализации.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(HeatingMenuNames.ALARMS)
@MainMenuItem(name = "Отопление", order = MainMenuOrder.HEATING_)
@MenuItemView(name = "Датчики в сигнализации", order = HeatingMenuOrder.ALARMS)
@RolesAllowed({ RolesList.USER, RolesList.ADMIN })
public class AlarmedSensorsView extends BaseMenuView {

	private static final long serialVersionUID = -6977338109883974310L;

	private Grid grid = new Grid();

	@Inject
	private SensorsService service;

	@Override
	protected void buildLayout() {
		Label title = new Label("Датчики в сигнализации");
		VerticalLayout left = new VerticalLayout(title, grid);
		left.setSizeFull();
		grid.setSizeFull();
		left.setExpandRatio(grid, 1);

		HorizontalLayout mainLayout = new HorizontalLayout(left);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		// Split and allow resizing
		// setContent(mainLayout);
		layout.addComponent(mainLayout);
		// layout.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);
	}

	@Override
	protected void configureComponents() {

		grid.setContainerDataSource(new BeanItemContainer<>(AlarmedSensorDto.class));
		grid.setColumnOrder("name", "sensorId", "fastTemp", "low", "top");
		grid.getColumn("name").setHeaderCaption("Датчик");
		grid.getColumn("sensorId").setHeaderCaption("Идентификатор");
		grid.getColumn("fastTemp").setHeaderCaption("Примерно (C)");
		grid.getColumn("low").setHeaderCaption("Минимум (C)");
		grid.getColumn("top").setHeaderCaption("Максимум (C)");
		grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		// grid.addSelectionListener(new SelectionListener() {
		//
		// private static final long serialVersionUID = -1852701286958204444L;
		//
		// @Override
		// public void select(SelectionEvent event) {
		// Long itemId = (Long) grid.getSelectedRow();
		// if (itemId != null) {
		// entityForm.edit(jpaContainer.getItem(itemId).getEntity());
		// } else {
		// entityForm.edit(null);
		// }
		// // contactForm.edit((Sensor) contactList.getSelectedRow());
		// }
		// });
		refreshData();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		configureComponents();
		buildLayout();
	}

	public void refreshData() {
		grid.setContainerDataSource(new BeanItemContainer<>(AlarmedSensorDto.class, service.findAlarmed()));
	}

}
