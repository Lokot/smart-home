package ru.skysoftlab.smarthome.heating.ui.impl;

import javax.annotation.security.RolesAllowed;

import com.vaadin.cdi.CDIView;

import ru.skysoftlab.skylibs.security.RolesList;
import ru.skysoftlab.skylibs.web.annatations.MainMenuItem;
import ru.skysoftlab.skylibs.web.annatations.MenuItemView;
import ru.skysoftlab.skylibs.web.ui.Dashboard;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuNames;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuOrder;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuOrder;

/**
 * Доска статистики.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(HeatingMenuNames.STATISTIC)
@MainMenuItem(name = "Отопление", order = MainMenuOrder.HEATING_)
@MenuItemView(name = "Статистика", order = HeatingMenuOrder.STATISTIC)
@RolesAllowed({ RolesList.USER, RolesList.ADMIN })
public class StatisticView extends Dashboard {

	private static final long serialVersionUID = -3561981958851866015L;

	@Override
	protected void buildLayout() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void configureComponents() {
		// TODO Auto-generated method stub

	}

}
