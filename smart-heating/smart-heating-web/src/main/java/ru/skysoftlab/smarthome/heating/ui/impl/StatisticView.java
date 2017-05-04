package ru.skysoftlab.smarthome.heating.ui.impl;

import javax.annotation.security.RolesAllowed;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.NavigationService.AlarmMenu;
import ru.skysoftlab.smarthome.heating.NavigationService.MainMenu;
import ru.skysoftlab.smarthome.heating.annatations.MainMenuItem;
import ru.skysoftlab.smarthome.heating.annatations.MenuItemView;
import ru.skysoftlab.smarthome.heating.security.RolesList;
import ru.skysoftlab.smarthome.heating.ui.Dashboard;

import com.vaadin.cdi.CDIView;

/**
 * Доска статистики.
 * 
 * @author Loktionov Artem
 *
 */
@CDIView(NavigationService.STATISTIC)
@MainMenuItem(name = "Сигнализация", order = MainMenu.ALARM)
@MenuItemView(name = "Статистика", order = AlarmMenu.STATISTIC)
@RolesAllowed({ RolesList.USER, RolesList.ADMIN })
public class StatisticView extends Dashboard {

	private static final long serialVersionUID = -3561981958851866015L;

}
