package ru.skysoftlab.smarthome.heating.ui.impl;

import static ru.skysoftlab.smarthome.heating.cdi.OwfsProducer.OWFS_SERVER;
import static ru.skysoftlab.smarthome.heating.quartz.JobController.INTERVAL;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.NavigationService.ConfigMenu;
import ru.skysoftlab.smarthome.heating.NavigationService.MainMenu;
import ru.skysoftlab.smarthome.heating.annatations.MainMenuItem;
import ru.skysoftlab.smarthome.heating.annatations.MenuItemView;
import ru.skysoftlab.smarthome.heating.entitys.properties.api.PropertyProvider;
import ru.skysoftlab.smarthome.heating.events.SystemConfigEvent;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;

/**
 * Системные настройки.
 * 
 * @author Артём
 *
 */
@CDIView(NavigationService.SYSTEM)
@MainMenuItem(name = "Настройки", order = MainMenu.CONFIG)
@MenuItemView(name = "Система", order = ConfigMenu.SYSTEM)
@RolesAllowed({ RolesList.ADMIN })
public class SystemConfig extends BaseMenuView {

	private static final long serialVersionUID = 2039928987238266962L;

	@Resource
	private UserTransaction utx;

	@Inject
	private PropertyProvider propertyProvider;

	@Inject
	private javax.enterprise.event.Event<SystemConfigEvent> systemEvent;

	private TextField intervalField = new TextField("Интервал сканирования:");
	private TextField urlField = new TextField("Url cервера OWSF:");
	private Button save = new Button("Сохранить");

	private void configureComponents() {
		intervalField.setValue(propertyProvider.getIntegerValue(INTERVAL).toString());
		urlField.setValue(propertyProvider.getStringValue(OWFS_SERVER).toString());
		save.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 6589973284665779345L;

			@Override
			public void buttonClick(ClickEvent event) {
				Map<String, Object> params = new HashMap<>();
				params.put(OWFS_SERVER, urlField.getValue());
				params.put(INTERVAL, Integer.valueOf(intervalField.getValue()));
				try {
					try {
						utx.begin();
						propertyProvider.setStringValue(OWFS_SERVER, urlField.getValue(), "Url cервера OWSF");
						propertyProvider.setIntegerValue(INTERVAL, Integer.valueOf(intervalField.getValue()),
								"Интервал сканирования");
						utx.commit();
					} finally {
						if (utx.getStatus() == 0) {
							utx.rollback();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				// событие изменения настроек
				systemEvent.fire(new SystemConfigEvent(params));
			}
		});
	}

	private void buildLayout() {
		Label title = new Label("Системные настройки");
		FormLayout form = new FormLayout(urlField, intervalField, save);
		VerticalLayout left = new VerticalLayout(title, form);
		left.setExpandRatio(form, 1);
		left.setSizeFull();

		HorizontalLayout mainLayout = new HorizontalLayout(left);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);

		layout.addComponent(mainLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		configureComponents();
		buildLayout();
	}

}
