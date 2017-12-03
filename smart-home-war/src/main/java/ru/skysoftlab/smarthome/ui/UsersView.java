package ru.skysoftlab.smarthome.ui;

import com.vaadin.cdi.CDIView;

import ru.skysoftlab.skylibs.web.annatations.MainMenuItem;
import ru.skysoftlab.skylibs.web.annatations.MenuItemView;
import ru.skysoftlab.skylibs.web.ui.views.UserView;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.ConfigMenuOrder;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuNames;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.MainMenuOrder;

@CDIView(MainMenuNames.USERS)
@MainMenuItem(name = "Настройки", order = MainMenuOrder.CONFIG_)
@MenuItemView(name = "Пользователи", order = ConfigMenuOrder.CONFIG_USERS)
public class UsersView extends UserView {

	private static final long serialVersionUID = 2125858000767147430L;

}
