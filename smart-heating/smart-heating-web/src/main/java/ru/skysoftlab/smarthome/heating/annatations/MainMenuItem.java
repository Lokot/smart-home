package ru.skysoftlab.smarthome.heating.annatations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

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
public @interface MainMenuItem {

	@Nonbinding
	String name();

	@Nonbinding
	int order() default 0;

	/**
	 * Сравниватель.
	 */
//	public static final Comparator<Object> VIEW_QUALIFIER_ORDER = new Comparator<Object>() {
//		public int compare(Object e1, Object e2) {
//			int e1Order = e1.getClass().getAnnotation(MainMenuItem.class)
//					.order();
//			int e2Order = e2.getClass().getAnnotation(MainMenuItem.class)
//					.order();
//			if (e1Order == e2Order) {
//				return 0;
//			} else if (e1Order > e2Order) {
//				return 1;
//			} else {
//				return -1;
//			}
//		}
//	};
}
