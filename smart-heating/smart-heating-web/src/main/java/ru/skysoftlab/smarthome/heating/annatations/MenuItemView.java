package ru.skysoftlab.smarthome.heating.annatations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Comparator;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Аннотация для компонентов с меню.
 * 
 * @author Артём
 *
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface MenuItemView {

	@Nonbinding
	String name();

	@Nonbinding
	int order() default 0;

	/**
	 * Сравниватель.
	 */
	public static final Comparator<Class<?>> VIEW_QUALIFIER_ORDER = new Comparator<Class<?>>() {
		public int compare(Class<?> e1, Class<?> e2) {
			int e1Order = e1.getAnnotation(MenuItemView.class).order();
			int e2Order = e2.getAnnotation(MenuItemView.class).order();
			return Integer.compare(e1Order, e2Order);
		}
	};
}
