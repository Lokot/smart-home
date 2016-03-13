package ru.skysoftlab.smarthome.heating.ui;

import javax.inject.Inject;
import javax.servlet.ServletException;

import ru.skysoftlab.smarthome.heating.MenuItems;
import ru.skysoftlab.smarthome.heating.NavigationEvent;
import ru.skysoftlab.smarthome.heating.NavigationService;

import com.vaadin.cdi.access.AccessControl;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

/**
 * Базовый класс для всех страниц для гавного меню.
 * 
 * @author Артём
 *
 */
public abstract class BaseMenuView extends CustomComponent implements View {

	private static final long serialVersionUID = 6006817026658320555L;

	@Inject
	private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

	@SuppressWarnings("unused")
	@Inject
	private AccessControl authenticator;

	protected final VerticalLayout layout = new VerticalLayout();

	protected MenuBar barmenu = new MenuBar();

	public BaseMenuView() {
		super();
		// setSizeFull();
		setCompositionRoot(layout);
		layout.setMargin(true);
		layout.addComponent(barmenu);
		createMenu();

		// layout.setSizeFull();
		// layout.setStyleName(Reindeer.LAYOUT_BLACK);
	}

	/**
	 * Создает меню.
	 */
	private void createMenu() {
		for (MenuItems myMenu : MenuItems.values()) {
			if (myMenu.equals(MenuItems.Logout)) {
				barmenu.addItem(myMenu.getName(), null, new LogOutCommand());
			} else {
				MenuItem item = barmenu.addItem(myMenu.getName(), null, null);
				if (myMenu.getItems() != null) {
					for (MenuItems.MenuItem myItem : myMenu.getItems()) {
						item.addItem(myItem.getName(), new NavigationCommand(
								myItem.getUrl()));
					}
				}
			}
		}
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
		private String view;

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
	public class LogOutCommand implements MenuBar.Command {

		private static final long serialVersionUID = -2959468243274000189L;

		@Override
		public void menuSelected(MenuItem selectedItem) {
			try {
				JaasAccessControl.logout();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				getSession().setAttribute("user", null);
				navigationEvent.fire(new NavigationEvent(
						NavigationService.LOGIN));
			}
		}

	}

}
