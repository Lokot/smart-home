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

}
