package ru.skysoftlab.smarthome.heating.ui.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;
import ru.skysoftlab.smarthome.heating.ui.impl.layouts.GpioStates;
import ru.skysoftlab.smarthome.heating.ui.impl.layouts.TemperatureCondition;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;

/**
 * Страница сос статистикой.
 * 
 * @author Артём
 *
 */
@CDIView(NavigationService.STATISTIC)
public class StatisticView extends BaseMenuView {

	private static final long serialVersionUID = -3561981958851866015L;

	// TODO сделать связь через CDI аннотацию и получать все по интерфейсу

	@Inject
	private GpioStates gpioStates;

	@Inject
	private TemperatureCondition temperatureCondition;

	@PostConstruct
	public void init() {
		gpioStates.setWidth("100%");
		temperatureCondition.setWidth("100%");
		HorizontalLayout mainLayout = new HorizontalLayout(gpioStates,
				temperatureCondition);
		mainLayout.setSizeFull();
		// mainLayout.setExpandRatio(gpioStates, 1);
		layout.addComponent(mainLayout);
		// reload();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		reload();
	}

	private void reload() {
		gpioStates.reload();
		temperatureCondition.reload();
	}

}
