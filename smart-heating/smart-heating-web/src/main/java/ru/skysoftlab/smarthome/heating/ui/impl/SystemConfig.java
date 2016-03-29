package ru.skysoftlab.smarthome.heating.ui.impl;

import static ru.skysoftlab.smarthome.heating.cdi.OwfsProducer.OWFS_SERVER;
import static ru.skysoftlab.smarthome.heating.quartz.JobController.INTERVAL;
import static ru.skysoftlab.smarthome.heating.quartz.JobController.TEMP_INTERVAL;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.crongen.CronGenField;
import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.NavigationService.ConfigMenu;
import ru.skysoftlab.smarthome.heating.NavigationService.MainMenu;
import ru.skysoftlab.smarthome.heating.annatations.MainMenuItem;
import ru.skysoftlab.smarthome.heating.annatations.MenuItemView;
import ru.skysoftlab.smarthome.heating.entitys.properties.api.PropertyProvider;
import ru.skysoftlab.smarthome.heating.events.SystemConfigEvent;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;

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
	
	private Logger LOG = LoggerFactory.getLogger(SystemConfig.class);

	@Resource
	private UserTransaction utx;

	@Inject
	private PropertyProvider propertyProvider;

	@Inject
	private javax.enterprise.event.Event<SystemConfigEvent> systemEvent;

	private CronGenField alarmIntervalField = new CronGenField("Интервал сканирования сигнализации:");
	private CronGenField tempIntervalField = new CronGenField("Интервал сканирования температур:");
	private TextField urlField = new TextField("Url cервера OWSF:");
	private Button save = new Button("Сохранить");

	private void configureComponents() {
		alarmIntervalField.setValue(propertyProvider.getStringValue(INTERVAL));
		tempIntervalField.setValue(propertyProvider.getStringValue(TEMP_INTERVAL));
		urlField.setValue(propertyProvider.getStringValue(OWFS_SERVER));
		save.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 6589973284665779345L;

			@Override
			public void buttonClick(ClickEvent event) {
				Map<String, Object> params = new HashMap<>();
				params.put(OWFS_SERVER, urlField.getValue());
				params.put(INTERVAL, alarmIntervalField.getValue());
				params.put(TEMP_INTERVAL, tempIntervalField.getValue());
				try {
					try {
						utx.begin();
						propertyProvider.setStringValue(OWFS_SERVER, urlField.getValue(), "Url cервера OWSF");
						propertyProvider.setStringValue(INTERVAL, alarmIntervalField.getValue(),
								"Интервал сканирования сигнализации");
						propertyProvider.setStringValue(TEMP_INTERVAL, tempIntervalField.getValue(),
								"Интервал сканирования температур");
						utx.commit();
					} finally {
						if (utx.getStatus() == 0) {
							utx.rollback();
						}
					}
				} catch (Exception e) {
					LOG.error("System configuration save error", e);
				}
				// событие изменения настроек
				systemEvent.fire(new SystemConfigEvent(params));
			}
		});
	}

	private void buildLayout() {
		Label title = new Label("Системные настройки");
		FormLayout form = new FormLayout(urlField, alarmIntervalField, tempIntervalField, save);
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
