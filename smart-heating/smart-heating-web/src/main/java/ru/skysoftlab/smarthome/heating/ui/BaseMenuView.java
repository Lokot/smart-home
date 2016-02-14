package ru.skysoftlab.smarthome.heating.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

/**
 * Базовый класс для страниц.
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
		setCompositionRoot(layout);
		layout.setMargin(true);
		layout.addComponent(barmenu);
		createMenu();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
	}

	/**
	 * Создает меню.
	 */
	private void createMenu() {
		for (MenuItems myMenu : MenuItems.values()) {
			MenuItem item = barmenu.addItem(myMenu.getName(), null, null);
			for (MenuItems.MenuItem myItem : myMenu.getItems()) {
				item.addItem(myItem.getName(),
						new NavigationCommand(myItem.getUrl()));
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
			getUI().getNavigator().navigateTo(view);
		}

	}

}
