package ru.skysoftlab.smarthome.heating.ui;

import org.psa.vaadinauth.ui.HeatingMaivView;

import com.vaadin.navigator.View;

/**
 * Список меню.
 * 
 * @author Артём
 *
 */
public enum MenuItems {

	/** Заказ-наряд */
	Orders("Заказ-наряд", null, new MenuItem[] {
			new MenuItem("Список", HeatingMaivView.NAME, HeatingMaivView.class),
			new MenuItem("Создать", HeatingMaivView.NAME, HeatingMaivView.class) }, null),
	/** Шаблоны ЗН */
	Templates("Шаблоны ЗН", null, new MenuItem[] {
			new MenuItem("Список", HeatingMaivView.NAME, HeatingMaivView.class),
			new MenuItem("Создать", HeatingMaivView.NAME, HeatingMaivView.class) }, null),
	/** Блоки ЗН */
	TemplateBlocks("Блоки ЗН", null, new MenuItem[] {
			new MenuItem("Список", HeatingMaivView.NAME, HeatingMaivView.class),
			new MenuItem("Создать", HeatingMaivView.NAME, HeatingMaivView.class) }, null),
	/** Виды работ */
	Works("Виды работ", null, new MenuItem[] {
			new MenuItem("Список", HeatingMaivView.NAME, HeatingMaivView.class),
			new MenuItem("Создать", HeatingMaivView.NAME, HeatingMaivView.class) }, null);

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
