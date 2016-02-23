package ru.skysoftlab.smarthome.heating.ui;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.skysoftlab.smarthome.heating.MainUI;
import ru.skysoftlab.smarthome.heating.MenuItems;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Базовый класс для всех страниц для гавного меню.
 * 
 * @author Артём
 *
 */
public abstract class BaseMenuView extends CustomComponent implements View {

	private static final long serialVersionUID = 6006817026658320555L;

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

	@Override
	public void enter(ViewChangeEvent event) {

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

	@SuppressWarnings("unchecked")
	protected <B> B lookupBean(Class<B> beanClass) throws NamingException {
		return (B) new InitialContext().lookup("java:module/"
				+ beanClass.getSimpleName());
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
			getUI().getNavigator().navigateTo(view);
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
			MainUI mainUI = (MainUI) UI.getCurrent();
			mainUI.getAuthenticator().logout(
					(HttpServletRequest) VaadinService.getCurrentRequest());
			getSession().setAttribute("user", null);
			getUI().getNavigator().navigateTo(MainView.NAME);
		}

	}

}
