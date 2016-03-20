package ru.skysoftlab.smarthome.heating.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletException;

import org.reflections.Reflections;

import ru.skysoftlab.smarthome.heating.NavigationEvent;
import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.annatations.MainMenuItem;
import ru.skysoftlab.smarthome.heating.annatations.MenuItemView;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.access.AccessControl;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Меню.
 * 
 * @author Артём
 *
 */
public class MenuProducer implements Serializable {

	private static final long serialVersionUID = -2477114251740004956L;

	@Inject
	protected javax.enterprise.event.Event<NavigationEvent> navigationEvent;

	@Inject
	private AccessControl authenticator;

	// TODO выкинуть в настройки ???
	private String[] packages = { "ru.skysoftlab.smarthome.heating.ui.impl" };

	@Produces
	public MenuBar createMenuBar() {
		MenuBar rv = new MenuBar();
		rv.addItem("Главная", new NavigationCommand(NavigationService.MAIN));
		SortedMap<MenuItemDto, List<Class<? extends BaseMenuView>>> viewsMap = getMenuViews(getViewsClasses(packages));
		for (MenuItemDto menuItemDto : viewsMap.keySet()) {
			List<Class<? extends BaseMenuView>> subMenuViewsClasses = viewsMap.get(menuItemDto);
			if (subMenuViewsClasses != null && subMenuViewsClasses.size() > 0) {
				MenuItem item = rv.addItem(menuItemDto.getName(), null);
				for (Class<? extends BaseMenuView> view : subMenuViewsClasses) {
					CDIView cdiAnn = view.getAnnotation(CDIView.class);
					MenuItemView menuItemView = view.getAnnotation(MenuItemView.class);
					item.addItem(menuItemView.name(), new NavigationCommand(cdiAnn.value()));
				}
			}
		}
		rv.addItem("Выход", new LogOutCommand());
		return rv;
	}

	private Set<Class<? extends BaseMenuView>> getViewsClasses(String... packages) {
		Set<Class<? extends BaseMenuView>> classes = new HashSet<>();
		for (String packageName : packages) {
			Reflections reflections = new Reflections(packageName);
			for (Class<?> cdiViewClass : reflections.getTypesAnnotatedWith(CDIView.class)) {
				try {
					Class<? extends BaseMenuView> bmvClass = cdiViewClass.asSubclass(BaseMenuView.class);
					classes.add(bmvClass);
				} catch (ClassCastException e) {
				}
			}
		}
		return classes;
	}

	/**
	 * Комнда для навигации.
	 * 
	 * @author Артём
	 *
	 */
	public class NavigationCommand implements MenuBar.Command {

		private static final long serialVersionUID = -2959468243274000189L;

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
	private SortedMap<MenuItemDto, List<Class<? extends BaseMenuView>>> getMenuViews(
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
				MenuItemDto key = createDto(viewClass);
				List<Class<? extends BaseMenuView>> menuItems = viewsForMenuBar.get(key);
				// добавляем главный пункт
				if (menuItems == null) {
					menuItems = new ArrayList<>();
					viewsForMenuBar.put(key, menuItems);
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
