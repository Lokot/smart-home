package ru.skysoftlab.smarthome.heating.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletException;

import org.reflections.Reflections;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.AccessControl;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import ru.skysoftlab.smarthome.heating.NavigationEvent;
import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.annatations.MainMenuItem;
import ru.skysoftlab.smarthome.heating.annatations.MenuItemView;

/**
 * Меню.
 * 
 * @author Артём
 *
 */
public class MenuProducer {

	@Inject
	private AccessControl authenticator;
	
	@Produces
	// @SessionScoped
	// @SimpleQualifier("MainMenu")
	public MenuBar createMenuBar() {
		MenuBar rv = new MenuBar();
		rv.addItem("Главная", new NavigationCommand(NavigationService.MAIN));
		Reflections reflections = new Reflections("ru.skysoftlab.smarthome.heating.ui.impl");
		Set<Class<? extends BaseMenuView>> classes = reflections.getSubTypesOf(BaseMenuView.class);
		SortedMap<MenuItemDto, List<Class<? extends BaseMenuView>>> viewsMap = getViews(classes);
		for (MenuItemDto menuItemDto : viewsMap.keySet()) {
			List<Class<? extends BaseMenuView>> subMenuViewsClasses = viewsMap.get(menuItemDto);
			if (subMenuViewsClasses != null && subMenuViewsClasses.size() > 0) {
				rv.addItem(menuItemDto.getName(), null);
				for (Class<? extends BaseMenuView> view : subMenuViewsClasses) {
					CDIView cdiAnn = view.getAnnotation(CDIView.class);
					MenuItemView menuItemView = view.getClass().getAnnotation(MenuItemView.class);
					rv.addItem(menuItemView.name(), new NavigationCommand(cdiAnn.value()));
				}
			}
		}
		rv.addItem("Выход", new LogOutCommand());
		return rv;
	}

	/**
	 * Комнда для навигации.
	 * 
	 * @author Артём
	 *
	 */
	public class NavigationCommand implements MenuBar.Command {

		private static final long serialVersionUID = -2959468243274000189L;

		@Inject
		protected javax.enterprise.event.Event<NavigationEvent> navigationEvent;

		/** Наименование страницы. */
		protected String view;

		public NavigationCommand(String view) {
			this.view = view;
		}

		@Override
		public void menuSelected(MenuItem selectedItem) {
			navigationEvent.fire(new NavigationEvent(view));
		}

	}

	/**
	 * Комнда для навигации.
	 * 
	 * @author Артём
	 *
	 */
	public final class LogOutCommand extends NavigationCommand {

		private static final long serialVersionUID = -2959468243274000189L;

		public LogOutCommand() {
			super(NavigationService.LOGIN);
		}

		@Override
		public void menuSelected(MenuItem selectedItem) {
			try {
				JaasAccessControl.logout();
			} catch (ServletException e) {
				Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
			} finally {
				navigationEvent.fire(new NavigationEvent(view));
			}
		}

	}

	/**
	 * Возвращает виды для построения меню.
	 * 
	 * @return
	 */
	private SortedMap<MenuItemDto, List<Class<? extends BaseMenuView>>> getViews(
			Set<Class<? extends BaseMenuView>> classes) {
		SortedMap<MenuItemDto, List<Class<? extends BaseMenuView>>> viewsForMenuBar = new TreeMap<>(
				new Comparator<MenuItemDto>() {

					@Override
					public int compare(MenuItemDto item1, MenuItemDto item2) {
						return item1.compareTo(item2);
					}

				});

		for (Class<? extends BaseMenuView> viewClass : classes) {

			if (viewClass.isAnnotationPresent(CDIView.class) && viewClass.isAnnotationPresent(MainMenuItem.class)
					&& viewClass.isAnnotationPresent(MenuItemView.class)) {
				MainMenuItem mainMenuItem = viewClass.getAnnotation(MainMenuItem.class);
				List<Class<? extends BaseMenuView>> menuItems = viewsForMenuBar.get(mainMenuItem);
				// добавляем главный пункт
				if (menuItems == null) {
					menuItems = new ArrayList<>();
					viewsForMenuBar.put(createDto(viewClass), menuItems);
				}
				// добавляем пункт
				if (isShow(viewClass)) {
					menuItems.add(viewClass);
				}
			}

		}

		// сортировка всех подменю
		for (List<Class<? extends BaseMenuView>> baseMenuView : viewsForMenuBar.values()) {
			Collections.sort(baseMenuView, MenuItemView.VIEW_QUALIFIER_ORDER);
		}

		return viewsForMenuBar;
	}

	private boolean isShow(Class<?> viewClass) {
		if (viewClass.isAnnotationPresent(RolesAllowed.class)) {
			RolesAllowed rolesAllowed = viewClass.getAnnotation(RolesAllowed.class);
			if (authenticator.isUserInSomeRole(rolesAllowed.value())) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	private MenuItemDto createDto(Class<? extends BaseMenuView> viewClass) {
		MainMenuItem mainMenuItem = viewClass.getAnnotation(MainMenuItem.class);
		return new MenuItemDto(mainMenuItem.name(), mainMenuItem.order());
	}

	/**
	 * 
	 * @author Артём
	 *
	 */
	private class MenuItemDto implements Comparable<MenuItemDto> {

		private String name;
		private int order;

		public MenuItemDto(String name, int order) {
			this.name = name;
			this.order = order;
		}

		public String getName() {
			return name;
		}

		public int getOrder() {
			return order;
		}

		@Override
		public int compareTo(MenuItemDto o) {
			return Integer.compare(order, o.getOrder());
		}
	}
}
