package ru.skysoftlab.smarthome.heating.ui.impl;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.NavigationService.AlarmMenu;
import ru.skysoftlab.smarthome.heating.NavigationService.MainMenu;
import ru.skysoftlab.smarthome.heating.annatations.MainMenuItem;
import ru.skysoftlab.smarthome.heating.annatations.MenuItemView;
import ru.skysoftlab.smarthome.heating.dto.AlarmedSensorDto;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.services.SensorsService;
import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Отображает датчики в состоянии сигнализации.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(NavigationService.ALARMS)
@MainMenuItem(name = "Сигнализация", order = MainMenu.ALARM)
@MenuItemView(name = "Датчики в сигнализации", order = AlarmMenu.ALARMS)
@RolesAllowed({ RolesList.USER, RolesList.ADMIN })
public class AlarmedSensorsView extends BaseMenuView {

	private static final long serialVersionUID = -6977338109883974310L;

	private Grid grid = new Grid();

	@Inject
	private SensorsService service;

	public void refreshData() {
		grid.setContainerDataSource(new BeanItemContainer<>(
				AlarmedSensorDto.class, service.findAlarmed()));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {

		grid.setContainerDataSource(new BeanItemContainer<>(
				AlarmedSensorDto.class));
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

	private void buildLayout() {
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

}
