package ru.skysoftlab.smarthome.heating.annatations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Comparator;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * Аннотация компонентов для доски.
 * 
 * @author Артём
 *
 */
@Target({ TYPE, METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface DashBoardElementQualifier {

	String view();

	@Nonbinding
	String name();

	@Nonbinding
	int order();

	/**
	 * Сравниватель.
	 */
	public static final Comparator<Object> VIEW_QUALIFIER_ORDER = new Comparator<Object>() {
		public int compare(Object e1, Object e2) {
			int e1Order = e1.getClass().getAnnotation(DashBoardElementQualifier.class).order();
			int e2Order = e2.getClass().getAnnotation(DashBoardElementQualifier.class).order();
			return Integer.compare(e1Order, e2Order);
		}
	};
}
