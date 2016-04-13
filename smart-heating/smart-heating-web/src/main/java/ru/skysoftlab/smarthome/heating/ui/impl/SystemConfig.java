package ru.skysoftlab.smarthome.heating.ui.impl;

import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_MAX_TEMP;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_START;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.HOLIDAY_MODE_STOP;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.INTERVAL;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.OWFS_SERVER;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.SUMMER_MODE;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.TEMP_INTERVAL;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.teemu.switchui.Switch;

import ru.skysoftlab.crongen.CronGenExt;
import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.NavigationService.ConfigMenu;
import ru.skysoftlab.smarthome.heating.NavigationService.MainMenu;
import ru.skysoftlab.smarthome.heating.annatations.MainMenuItem;
import ru.skysoftlab.smarthome.heating.annatations.MenuItemView;
import ru.skysoftlab.smarthome.heating.dto.SystemConfigDto;
import ru.skysoftlab.smarthome.heating.entitys.properties.api.PropertyProvider;
import ru.skysoftlab.smarthome.heating.events.SystemConfigEvent;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupDateField;
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
public class SystemConfig extends BaseMenuView implements Button.ClickListener, ValueChangeListener {

	private static final long serialVersionUID = 2039928987238266962L;

	private Logger LOG = LoggerFactory.getLogger(SystemConfig.class);

	@Resource
	private UserTransaction utx;

	@Inject
	private PropertyProvider propertyProvider;

	@Inject
	private javax.enterprise.event.Event<SystemConfigEvent> systemEvent;

	private BeanFieldGroup<SystemConfigDto> formFieldBindings;

	@Inject
	@RequestScoped
	private SystemConfigDto dto;

	private TextField alarmInterval = new TextField("Интервал сканирования сигнализации:");
	private TextField tempInterval = new TextField("Интервал сканирования температур:");
	private TextField url = new TextField("Url cервера OWSF:");
	private Switch summerMode = new Switch("Режим лето:");
	private Switch holiday = new Switch("Режим отпуск:");
	private TextField holidayMaxTemp = new TextField("Максимальная температура:");
	private PopupDateField holidayStart = new PopupDateField("Начало отпуска:");
	private PopupDateField holidayStop = new PopupDateField("Конец отпуска:");
	private Button save = new Button("Сохранить", this);

	private void configureComponents() {
		Boolean readOnly = dto.getHoliday();
		setReadOnlyField(readOnly);
		holiday.addValueChangeListener(this);
	}

	private void buildLayout() {
		Label title = new Label("Системные настройки");
		CronGenExt ext = new CronGenExt();
		ext.extend(alarmInterval);
		CronGenExt ext1 = new CronGenExt();
		ext1.extend(tempInterval);
		FormLayout form = new FormLayout(url, alarmInterval, tempInterval, summerMode, holiday, holidayMaxTemp,
				holidayStart, holidayStop, save);
		formFieldBindings = BeanFieldGroup.bindFieldsBuffered(dto, this);
		VerticalLayout left = new VerticalLayout(title, form);
		left.setExpandRatio(form, 1);
		left.setSizeFull();
		left.setHeight("600px");
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

	private void setReadOnlyField(Boolean readOnly) {
		boolean value = (readOnly != null) ? readOnly : false;
		holidayMaxTemp.setReadOnly(!value);
		holidayStart.setReadOnly(!value);
		holidayStop.setReadOnly(!value);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			formFieldBindings.commit();
			// Save DAO to backend with direct synchronous service API
			try {
				try {
					utx.begin();
					propertyProvider.setStringValue(OWFS_SERVER, dto.getUrl(), "Url cервера OWSF");
					propertyProvider.setStringValue(INTERVAL, dto.getAlarmInterval(),
							"Интервал сканирования сигнализации");
					propertyProvider.setStringValue(TEMP_INTERVAL, dto.getTempInterval(),
							"Интервал сканирования температур");
					propertyProvider.setBooleanValue(SUMMER_MODE, dto.getSummerMode(), "Режим лето");
					propertyProvider.setBooleanValue(HOLIDAY_MODE, dto.getHoliday(), "Режим отпуск");
					propertyProvider.setDateValue(HOLIDAY_MODE_START, dto.getHolidayStart(), "Начало отпуска");
					propertyProvider.setDateValue(HOLIDAY_MODE_STOP, dto.getHolidayStop(), "Конец отпуска");
					propertyProvider.setFloatValue(HOLIDAY_MODE_MAX_TEMP, dto.getHolidayMaxTemp(),
							"Максимальная температура");

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
			systemEvent.fire(new SystemConfigEvent(dto.getDataForEvent()));
			String msg = String.format("Saved '%s'.", dto.toString());
			Notification.show(msg, Type.TRAY_NOTIFICATION);
		} catch (FieldGroup.CommitException e) {
			// Validation exceptions could be shown here
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		setReadOnlyField((Boolean) event.getProperty().getValue());
	}

}
