package ru.skysoftlab.smarthome.ui;

import java.util.Locale;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;

import ru.skysoftlab.crongen.ParseCronGenComponent;
import ru.skysoftlab.skylibs.events.SystemConfigEvent;
import ru.skysoftlab.skylibs.security.RolesList;
import ru.skysoftlab.skylibs.web.annatations.MainMenuItem;
import ru.skysoftlab.skylibs.web.annatations.MenuItemView;
import ru.skysoftlab.skylibs.web.ui.BaseMenuView;
import ru.skysoftlab.smarthome.common.SmartHomeConfigTabs;
import ru.skysoftlab.smarthome.common.TabDto;
import ru.skysoftlab.smarthome.dto.SystemConfigDto;
import ru.skysoftlab.smarthome.impl.DataBaseProvider;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.ConfigMenuOrder;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuNames;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuOrder;

/**
 * Системные настройки.
 * 
 * @author Артём
 *
 */
@CDIView(MainMenuNames.SYSTEM)
@MainMenuItem(name = "Настройки", order = MainMenuOrder.CONFIG_)
@MenuItemView(name = "Конфигурация", order = ConfigMenuOrder.CONFIG_CONFIG)
@RolesAllowed({ RolesList.ADMIN })
public class SystemConfig extends BaseMenuView implements ClickListener, ValueChangeListener {

	private static final long serialVersionUID = 2039928987238266962L;

	@Inject
	private DataBaseProvider dataBaseProvider;

	@Inject
	@RequestScoped
	private SystemConfigDto dto;

	private BeanFieldGroup<SystemConfigDto> formFieldBindings;

	@Inject
	@Any
	private Instance<SmartHomeConfigTabs> instanceTabs;

	private Logger LOG = LoggerFactory.getLogger(SystemConfig.class);

	private final Locale ruLocale = new Locale("ru");

	private Button save = new Button("Сохранить", this);

	private ParseCronGenComponent simpleProp = new ParseCronGenComponent("Простое свойство:", ruLocale);

	@Inject
	private javax.enterprise.event.Event<SystemConfigEvent> systemEvent;

	private TabSheet tabSheet = new TabSheet();

	@Override
	protected void buildLayout() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		FormLayout form = new FormLayout(simpleProp, save);
		formFieldBindings = BeanFieldGroup.bindFieldsBuffered(dto, this);

		tabSheet.addTab(form, "Системные настройки");
		// ------------------
		tabSheet.setHeight(100.0f, Unit.PERCENTAGE);
		tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
		tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		for (SmartHomeConfigTabs cp : instanceTabs) {
			TabDto tab = cp.getConfigTab();
			tabSheet.addTab(tab.getLayout(), tab.getName());
		}

		layout.addComponent(tabSheet);
		layout.setComponentAlignment(tabSheet, Alignment.TOP_CENTER);

	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
			formFieldBindings.commit(); // Save DAO to backend with direct synchronous service API
			try {
				dataBaseProvider.saveConfig(dto); // событие изменения настроек
				systemEvent.fire(new SystemConfigEvent(dto.getDataForEvent()));
				Notification.show("Настройки сохранены.", Type.TRAY_NOTIFICATION);
			} catch (Exception e) {
				String msg = "System configuration save error";
				LOG.error(msg, e);
				Notification.show(msg, Type.ERROR_MESSAGE);
			}
		} catch (FieldGroup.CommitException e) { // Validation exceptions could be shown here
		}
	}

	@Override
	protected void configureComponents() {

	}

	@Override
	public void valueChange(ValueChangeEvent event) {

	}

}
