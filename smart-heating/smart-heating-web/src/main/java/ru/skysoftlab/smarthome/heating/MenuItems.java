package ru.skysoftlab.smarthome.heating;

/**
 * Список меню.
 * 
 * @author Loktionov Artem
 *
 */
public enum MenuItems {

	/** Заказ-наряд */
	OPTIONS("Настройки", null, new MenuItem[] {
			new MenuItem("Пины", NavigationService.GPIO),
			new MenuItem("Датчики", NavigationService.SENSORS) }),

	ALARMS("Сигнализация", null, new MenuItem[] { new MenuItem(
			"Датчики в сигнализации", NavigationService.ALARMS) }),

	Logout("Выход", NavigationService.LOGIN, null);

	private String name;

	private String url;

	private MenuItem[] items;

	// private Class<? extends View> viewClass;

	private MenuItems(String name, String url, MenuItem[] items) {
		this.name = name;
		this.url = url;
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public MenuItem[] getItems() {
		return items;
	}

	// public Class<? extends View> getViewClass() {
	// return viewClass;
	// }
	//
	// public void setViewClass(Class<? extends View> viewClass) {
	// this.viewClass = viewClass;
	// }

	/**
	 * Регистрируем страницы.
	 */
	// public static void registrateNavigationViews(Navigator navigator) {
	// for (MenuItems mainItem : MenuItems.values()) {
	// if (mainItem.getUrl() != null && mainItem.getViewClass() != null) {
	// navigator.addView(mainItem.getUrl(),
	// mainItem.getViewClass());
	// }
	// if (mainItem.getItems() != null) {
	// for (MenuItem item : mainItem.getItems()) {
	// navigator.addView(item.getUrl(), item.getViewClass());
	// }
	// }
	// }
	// }

	public static class MenuItem {

		private String name;

		private String url;

		// private Class<? extends View> viewClass;

		private MenuItem(String name, String url) {
			this.name = name;
			this.url = url;
			// this.setViewClass(viewClass);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		// public Class<? extends View> getViewClass() {
		// return viewClass;
		// }
		//
		// public void setViewClass(Class<? extends View> viewClass) {
		// this.viewClass = viewClass;
		// }
	}

}
