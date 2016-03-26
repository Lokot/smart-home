package ru.skysoftlab.smarthome.heating.impl;

import javax.ejb.Asynchronous;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.events.SystemConfigEvent;
import ru.skysoftlab.smarthome.heating.owfs.IAlarmScanner;

/**
 * Слушатель событий.
 * 
 * @author Локтионов А.Г.
 *
 */
public class AlarmScannerObserver {

//	@Inject
//	private IAlarmScanner scanner;
//
//	@Asynchronous
//	public void editIntervalEvent(@Observes SystemConfigEvent event) {
//		scanner.setInterval((Integer) event.getParams().get(AlarmScannerBean.INTERVAL));
//	}
}
