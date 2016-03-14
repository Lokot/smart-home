package ru.skysoftlab.smarthome.heating.ui;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.ServletException;

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
public class MenuProducer {

	@Inject @Any
	private Instance<BaseMenuView> viewSource;

	@Inject
	private AccessControl authenticator;
	
	private MenuBar menubar = new MenuBar();

	// @Produces
	// @SessionScoped
	// @SimpleQualifier("MainMenu")
	public MenuBar createMenuBar() {
		MenuBar rv = new MenuBar();
		rv.addItem("Главная", new NavigationCommand(NavigationService.MAIN));
		SortedMap<MenuItemDto, List<BaseMenuView>> viewsMap = getViews();
		for (MenuItemDto menuItemDto : viewsMap.keySet()) {
			rv.addItem(menuItemDto.getName(), null);
			for (BaseMenuView view : viewsMap.get(menuItemDto)) {
				CDIView cdiAnn = view.getClass().getAnnotation(CDIView.class);
				MenuItemView menuItemView = view.getClass().getAnnotation(
						MenuItemView.class);
				rv.addItem(menuItemView.name(),
						new NavigationCommand(cdiAnn.value()));
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
	public class LogOutCommand extends NavigationCommand {

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
	private SortedMap<MenuItemDto, List<BaseMenuView>> getViews() {
		SortedMap<MenuItemDto, List<BaseMenuView>> viewsForMenuBar = new TreeMap<>(new Comparator<MenuItemDto>() {

			@Override
			public int compare(MenuItemDto item1, MenuItemDto item2) {
				return item1.compareTo(item2);
			}

		});
		try {
			MenuItemView ann = new MenuItemView() {

				@Override
				public Class<? extends Annotation> annotationType() {
					return MenuItemView.class;
				}

				@Override
				public int order() {
					return 0;
				}

				@Override
				public String name() {
					return null;
				}

			};

			for (Iterator<BaseMenuView> it = viewSource.select(ann).iterator(); it
					.hasNext();) {
				BaseMenuView candidate = it.next();
				Class<?> viewClass = candidate.getClass();
				if (viewClass.isAnnotationPresent(CDIView.class)
						&& viewClass.isAnnotationPresent(MainMenuItem.class)
						&& viewClass.isAnnotationPresent(MenuItemView.class)) {
					MainMenuItem mainMenuItem = viewClass
							.getAnnotation(MainMenuItem.class);
					List<BaseMenuView> menuItems = viewsForMenuBar
							.get(mainMenuItem);
					if (menuItems == null) {
						menuItems = new ArrayList<>();
						viewsForMenuBar.put(new MenuItemDto(
								mainMenuItem.name(), mainMenuItem.order()),
								menuItems);
					}
					if (viewClass.isAnnotationPresent(RolesAllowed.class)) {
						RolesAllowed rolesAllowed = viewClass
								.getAnnotation(RolesAllowed.class);
						if (authenticator
								.isUserInSomeRole(rolesAllowed.value())) {
							menuItems.add(candidate);
						}
					} else {
						menuItems.add(candidate);
					}
				}
			}

			// сортировка пунктов подменю
			for (List<BaseMenuView> baseMenuView : viewsForMenuBar.values()) {
				Collections.sort(baseMenuView,
						MenuItemView.VIEW_QUALIFIER_ORDER);
			}
		} catch (Exception e) {
			Notification.show("Не найдены модули для отображения.",
					Type.TRAY_NOTIFICATION);
		}
		return viewsForMenuBar;
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
