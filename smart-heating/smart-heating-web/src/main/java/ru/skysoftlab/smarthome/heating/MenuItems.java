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
			new MenuItem("Свойства системы", NavigationService.CONFIG),
			new MenuItem("Пины", NavigationService.GPIO),
			new MenuItem("Датчики", NavigationService.SENSORS) }),

	ALARMS("Сигнализация", null, new MenuItem[] {
			new MenuItem("Датчики в сигнализации", NavigationService.ALARMS),
			new MenuItem("Статистика", NavigationService.STATISTIC) }),

	Logout("Выход", NavigationService.LOGIN, null);

	private String name;

	private String url;

	private MenuItem[] items;

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

	public static class MenuItem {

		private String name;

		private String url;

		private MenuItem(String name, String url) {
			this.name = name;
			this.url = url;
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
	}
}
