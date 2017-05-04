package ru.skysoftlab.smarthome.heating.onewire;

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
	private String sensorId;
	/** Примерное значение. */
	private Float fastTemp;

	/**
	 * Создает событие понижения температуры.
	 * 
	 * @param deviceName
	 * @param temp
	 * @return
	 */
	public static TempAlarmingEvent createLowTempEvent(String deviceName, Float temp) {
		return new TempAlarmingEvent(TempAlarmingType.LOW, deviceName, temp);
	}

	/**
	 * Создает событие повышения температуры.
	 * 
	 * @param deviceName
	 * @param temp
	 * @return
	 */
	public static TempAlarmingEvent createTopTempEvent(String deviceName, Float temp) {
		return new TempAlarmingEvent(TempAlarmingType.TOP, deviceName, temp);
	}

	private TempAlarmingEvent(TempAlarmingType type, String sensorId, Float fastTemp) {
		this.type = type;
		this.sensorId = sensorId;
		this.fastTemp = fastTemp;
	}

	public TempAlarmingType getType() {
		return type;
	}

	public String getSensorId() {
		return sensorId;
	}

	public Float getFastTemp() {
		return fastTemp;
	}

	@Override
	public String toString() {
		return "TempAlarmingEvent [type=" + type + ", sensorId=" + sensorId + ", fastTemp=" + fastTemp + "]";
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
