package ru.skysoftlab.smarthome.heating.ui;

import com.vaadin.ui.Panel;

/**
 * Панель с перегружаемыми данными.
 * 
 * @author Артём
 *
 */
public abstract class ReloadDataPenel extends Panel implements IReloadedComponent {

	private static final long serialVersionUID = 8083276825243115359L;

	public ReloadDataPenel(String caption) {
		super(caption);
	}
	
}
