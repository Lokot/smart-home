package ru.skysoftlab.smarthome.heating;

/**
 * Событие навигации.
 * 
 * @author Локтионов А.Г.
 *
 */
public class NavigationEvent {

	private final String navigateTo;

	public NavigationEvent(String navigateTo) {
		this.navigateTo = navigateTo;
	}

	public String getNavigateTo() {
		return navigateTo;
	}

}
