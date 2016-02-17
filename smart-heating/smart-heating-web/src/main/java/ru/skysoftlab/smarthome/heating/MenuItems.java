package ru.skysoftlab.smarthome.heating;

import ru.skysoftlab.smarthome.heating.ui.GpioView;
import ru.skysoftlab.smarthome.heating.ui.LoginView;
import ru.skysoftlab.smarthome.heating.ui.SensorsView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;

/**
 * Список меню.
 * 
 * @author Loktionov Artem
 *
 */
public enum MenuItems {

	/** Заказ-наряд */
	Orders("Заказ-наряд", null, new MenuItem[] {
			new MenuItem("Датчики", SensorsView.NAME, SensorsView.class),
			new MenuItem("Пины", GpioView.NAME, GpioView.class),
	}, null),
			
	/** Шаблоны ЗН */
//	Templates("Шаблоны ЗН", null, new MenuItem[] {
//			new MenuItem("Список", HeatingMaivView.NAME, HeatingMaivView.class),
//			new MenuItem("Создать", HeatingMaivView.NAME, HeatingMaivView.class) }, null),
	/** Блоки ЗН */
//	TemplateBlocks("Блоки ЗН", null, new MenuItem[] {
//			new MenuItem("Список", HeatingMaivView.NAME, HeatingMaivView.class),
//			new MenuItem("Создать", HeatingMaivView.NAME, HeatingMaivView.class) }, null),
	/** Виды работ */
//	Works("Виды работ", null, new MenuItem[] {
//			new MenuItem("Список", HeatingMaivView.NAME, HeatingMaivView.class),
//			new MenuItem("Создать", HeatingMaivView.NAME, HeatingMaivView.class) }, null),
			 
	Logout("Выход", LoginView.NAME, null, LoginView.class);

	private String name;

	private String url;

	private MenuItem[] items;

	private Class<? extends View> viewClass;

	private MenuItems(String name, String url, MenuItem[] items,
			Class<? extends View> viewClass) {
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

	public Class<? extends View> getViewClass() {
		return viewClass;
	}

	public void setViewClass(Class<? extends View> viewClass) {
		this.viewClass = viewClass;
	}
	
	/**
	 * Регистрируем страницы.
	 */
	public static void registrateNavigationViews(Navigator navigator) {
		for (MenuItems mainItem : MenuItems.values()) {
			if (mainItem.getUrl() != null && mainItem.getViewClass() != null) {
				navigator.addView(mainItem.getUrl(),
						mainItem.getViewClass());
			}
			if (mainItem.getItems() != null) {
				for (MenuItem item : mainItem.getItems()) {
					navigator.addView(item.getUrl(), item.getViewClass());
				}
			}
		}
	}

	public static class MenuItem {

		private String name;

		private String url;

		private Class<? extends View> viewClass;

		private MenuItem(String name, String url,
				Class<? extends View> viewClass) {
			this.name = name;
			this.url = url;
			this.setViewClass(viewClass);
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

		public Class<? extends View> getViewClass() {
			return viewClass;
		}

		public void setViewClass(Class<? extends View> viewClass) {
			this.viewClass = viewClass;
		}
	}

}
