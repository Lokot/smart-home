package ru.skysoftlab.smarthome.heating.ui.impl;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
import ru.skysoftlab.smarthome.heating.events.SystemConfigEvent;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.services.ConfigService;
import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
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

	@Inject
	private ConfigService configService;

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
		CronGenExt ext = new CronGenExt();
		ext.extend(alarmInterval);
		CronGenExt ext1 = new CronGenExt();
		ext1.extend(tempInterval);

		Boolean readOnly = dto.getHoliday();
		setReadOnlyField(readOnly);
		holiday.addValueChangeListener(this);

		holidayStart.setResolution(Resolution.MINUTE);
		holidayStart.setLocale(new Locale("ru", "RU"));
		holidayStart.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));

		holidayStop.setResolution(Resolution.MINUTE);
		holidayStop.setLocale(new Locale("ru", "RU"));
		holidayStop.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
	}

	private void buildLayout() {
		Label title = new Label("Системные настройки");
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
				configService.saveConfig(dto);
				// событие изменения настроек
				systemEvent.fire(new SystemConfigEvent(dto.getDataForEvent()));
				String msg = String.format("Saved '%s'.", dto.toString());
				Notification.show(msg, Type.TRAY_NOTIFICATION);
			} catch (Exception e) {
				String msg = "System configuration save error";
				LOG.error(msg, e);
				Notification.show(msg, Type.ERROR_MESSAGE);
			}
		} catch (FieldGroup.CommitException e) {
			// Validation exceptions could be shown here
		}
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		setReadOnlyField((Boolean) event.getProperty().getValue());
	}

}
