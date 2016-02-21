package ru.skysoftlab.smarthome.heating.owfs;

/**
 * Событие изменения температуры.
 * 
 * @author Артём
 *
 */
public class TempAlarmingEvent {

	/** Тип. */
	private TempAlarmingType type;
	/** Имя датчика. */
	private String deviceName;
	private Float fastTemp;

	/**
	 * Создает событие понижения температуры.
	 * 
	 * @param deviceName
	 * @param temp
	 * @return
	 */
	public static TempAlarmingEvent createLowTempEvent(String deviceName,
			Float temp) {
		return new TempAlarmingEvent(TempAlarmingType.LOW, deviceName, temp);
	}

	/**
	 * Создает событие повышения температуры.
	 * 
	 * @param deviceName
	 * @param temp
	 * @return
	 */
	public static TempAlarmingEvent createTopTempEvent(String deviceName,
			Float temp) {
		return new TempAlarmingEvent(TempAlarmingType.TOP, deviceName, temp);
	}

	private TempAlarmingEvent(TempAlarmingType type, String deviceName,
			Float fastTemp) {
		this.type = type;
		this.deviceName = deviceName;
		this.fastTemp = fastTemp;
	}

	public TempAlarmingType getType() {
		return type;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public Float getFastTemp() {
		return fastTemp;
	}

	/**
	 * Тип события.
	 * 
	 * @author Артём
	 *
	 */
	public enum TempAlarmingType {
		TOP, LOW;
	}
}
