package ru.skysoftlab.smarthome.heating.ui;

import java.io.Serializable;

import com.vaadin.ui.Panel;

/**
 * Панель с перегружаемыми данными.
 * 
 * @author Артём
 *
 */
public abstract class ReloadDataPenel extends Panel implements Serializable {

	private static final long serialVersionUID = 8083276825243115359L;

	public ReloadDataPenel(String caption) {
		super(caption);
	}

	/**
	 * Перегрузить данные.
	 */
	public abstract void reload();
}
