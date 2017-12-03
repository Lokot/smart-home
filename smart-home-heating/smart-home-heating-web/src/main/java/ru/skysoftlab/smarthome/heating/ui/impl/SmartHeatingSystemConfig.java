package ru.skysoftlab.smarthome.heating.ui.impl;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.teemu.switchui.Switch;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import ru.skysoftlab.crongen.CronGenExt;
import ru.skysoftlab.skylibs.events.SystemConfigEvent;
import ru.skysoftlab.skylibs.security.RolesList;
import ru.skysoftlab.smarthome.common.SmartHomeConfigTabs;
import ru.skysoftlab.smarthome.common.TabDto;
import ru.skysoftlab.smarthome.heating.dto.SystemConfigDto;
import ru.skysoftlab.smarthome.heating.services.ConfigService;

/**
 * Системные настройки отопления.
 * 
 * @author Артём
 *
 */
@RolesAllowed({ RolesList.ADMIN })
public class SmartHeatingSystemConfig implements SmartHomeConfigTabs, Button.ClickListener, ValueChangeListener {

	private static final long serialVersionUID = 2039928987238266962L;

	private TextField alarmInterval = new TextField("Интервал сканирования сигнализации:");

	@Inject
	private ConfigService configService;

	@Inject
	@RequestScoped
	private SystemConfigDto dto;

	private BeanFieldGroup<SystemConfigDto> formFieldBindings;

	private Switch holiday = new Switch("Режим отпуск:");

	private TextField holidayMaxTemp = new TextField("Максимальная температура:");

	private PopupDateField holidayStart = new PopupDateField("Начало отпуска:");
	private PopupDateField holidayStop = new PopupDateField("Конец отпуска:");
	private Logger LOG = LoggerFactory.getLogger(SmartHeatingSystemConfig.class);
	private Button save = new Button("Сохранить", this);
	private Switch summerMode = new Switch("Режим лето:");
	@Inject
	private javax.enterprise.event.Event<SystemConfigEvent> systemEvent;
	private TextField tempInterval = new TextField("Интервал сканирования температур:");
	private TextField url = new TextField("Url cервера OWSF:");

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			formFieldBindings.commit();
			// Save DAO to backend with direct synchronous service API
			try {
				configService.saveConfig(dto);
				// событие изменения настроек
				systemEvent.fire(new SystemConfigEvent(dto.getDataForEvent()));
				Notification.show("Настройки отопления сохранены.", Type.TRAY_NOTIFICATION);
			} catch (Exception e) {
				String msg = "System configuration save error";
				LOG.error(msg, e);
				Notification.show(msg, Type.ERROR_MESSAGE);
			}
		} catch (FieldGroup.CommitException e) {
			// Validation exceptions could be shown here
		}
	}

	protected void configureComponents() {
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

	@Override
	public TabDto getConfigTab() {
		configureComponents();
		FormLayout form = new FormLayout(url, alarmInterval, tempInterval, summerMode, holiday, holidayMaxTemp,
				holidayStart, holidayStop, save);
		formFieldBindings = BeanFieldGroup.bindFieldsBuffered(dto, this);
		return new TabDto(form, "Отопление");
	}

	private void setReadOnlyField(Boolean readOnly) {
		boolean value = (readOnly != null) ? readOnly : false;
		holidayMaxTemp.setReadOnly(!value);
		holidayStart.setReadOnly(!value);
		holidayStop.setReadOnly(!value);
	}

	@Override
	public void valueChange(ValueChangeEvent event) {
		setReadOnlyField((Boolean) event.getProperty().getValue());
	}

}
