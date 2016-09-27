package ru.skysoftlab.smarthome.heating.toDelete;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.onewire.IAlarmScannerJob;

/**
 * Инициализация приложения.
 * 
 * @author Артём
 *
 */
public class HeatingInitializer implements Serializable{

	private static final long serialVersionUID = -7599221334121731640L;

//	@Inject
//	private AlarmScannerBean alarmScanner;
	
//	public void initialized(
//			@Observes @Initialized(ApplicationScoped.class) Object event) {
//		
//		System.out
//				.println("*************************************************************");
//		System.out.println(alarmScanner);
//		System.out.println(event);
//		System.out
//				.println("**************************************************************");
//	}
}
