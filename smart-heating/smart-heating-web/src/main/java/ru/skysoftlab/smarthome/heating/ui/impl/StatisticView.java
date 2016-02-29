package ru.skysoftlab.smarthome.heating.ui.impl;

import java.util.List;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndGpioProvider;
import ru.skysoftlab.smarthome.heating.ui.BaseMenuView;
import ru.skysoftlab.smarthome.heating.ui.impl.layouts.GpioStates;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;

@CDIView(NavigationService.STATISTIC)
public class StatisticView extends BaseMenuView {

	private static final long serialVersionUID = -3561981958851866015L;

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	@Override
	public void enter(ViewChangeEvent event) {
		GpioStates gpioStates = new GpioStates();
		List<GpioPin> pins = sensorsProvider.getAllGpioPins();
		gpioStates.buildLayout(pins);
		
		HorizontalLayout mainLayout = new HorizontalLayout(gpioStates);
		mainLayout.setSizeFull();
		layout.addComponent(mainLayout);
	}

}
