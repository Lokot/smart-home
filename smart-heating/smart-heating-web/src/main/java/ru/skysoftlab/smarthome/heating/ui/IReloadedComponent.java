package ru.skysoftlab.smarthome.heating.ui;

import java.io.Serializable;

/**
 * Перезагружаемый компонент.
 * 
 * @author Артём
 *
 */
public interface IReloadedComponent extends Serializable {

	/**
	 * Перегрузить данные.
	 */
	public void reload();
}
