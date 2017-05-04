package smarthome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.owfs.jowfsclient.OwfsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smarthome.entitys.MaxTemp;
import smarthome.gpio.GpioPins;

@Singleton
@Startup
public class TempControlBean {
	private Logger logger = LoggerFactory.getLogger("controlTemps");
	private boolean firstStart;
	private static final boolean Open = false;
	private static final boolean Close = true;
	private ArrayList<GpioPins> toCloseList = new ArrayList<>();
	private ArrayList<GpioPins> toOpenList = new ArrayList<>();

	public TempControlBean() {
		this.firstStart = true;
	}

	@Schedule(minute = "*/5", hour = "*", persistent = false)
	private void controlTemps() {
		try {
			Thread.sleep(30000L);
		} catch (InterruptedException ex) {
			this.logger.error("Ошибка задержки: " + ex);
			ex.printStackTrace();
		}
		if (this.firstStart) {
			this.logger.info("Первый запуск");
		}
		this.logger.info(new Date().toString());
		this.logger.info("Сканирование температур.");
		int i = 0;
		for (Sensors sensor : Sensors.values()) {
			try {
				if (analiseTemp(sensor)) {
					i++;
				}
			} catch (OwfsException ex) {
				this.logger.error("Ошибка получения температуры с датчика: "
						+ ex);
				ex.printStackTrace();
			} catch (IOException ex) {
				this.logger.error("Ошибка ввода-вывода на пинах: " + ex);
				ex.printStackTrace();
			}
		}
		if ((i > 0) || (this.firstStart)) {
			try {
				analiseBoilerState();
			} catch (IOException ex) {
				this.logger.error("Ошибка включения или отключения котла: "
						+ ex);
				ex.printStackTrace();
			}
			if (this.firstStart) {
				this.firstStart = false;
			}
		}
	}

	private boolean analiseTemp(Sensors sensor) throws OwfsException,
			IOException {
		boolean rv = false;
		MaxTemp maxTemp = (MaxTemp) HibernateUtil.getEm().find(MaxTemp.class,
				sensor);
		Float sensorTemp = TempScanerBean.getTemp(sensor);
		if (sensorTemp.floatValue() <= maxTemp.getTemp() - 1.0F) {
			for (GpioPins gpioPin : sensor.getGpioPins()) {
				this.toOpenList.add(gpioPin);
			}
			rv = true;
		} else if (sensorTemp.floatValue() >= maxTemp.getTemp()) {
			for (GpioPins gpioPin : sensor.getGpioPins()) {
				this.toCloseList.add(gpioPin);
			}
			rv = true;
		}
		return rv;
	}

	private void analiseBoilerState() throws IOException {
		if (this.toOpenList.size() > 0) {
			colseOrOpen(this.toOpenList, false);

			colseOrOpen(this.toCloseList, true);
			GpioPins.Boiler.setOn();
			this.logger.info(GpioPins.Boiler.getName() + " включен.");
		} else {
			GpioPins.Boiler.setOff();
			this.logger.info(GpioPins.Boiler.getName() + " выключен.");
			openAll();
		}
		this.toOpenList.clear();
		this.toCloseList.clear();
	}

	private void openAll() {
		for (Sensors sensor : Sensors.values()) {
			if (sensor.getGpioPins() != null) {
				for (GpioPins gpioPin : sensor.getGpioPins()) {
					try {
						if (gpioPin.getState()) {
							gpioPin.setOff();
						}
					} catch (IOException ex) {
						this.logger
								.error("Ошибка ввода-вывода на пинах: " + ex);
						ex.printStackTrace();
					}
				}
			}
		}
	}

	private void colseOrOpen(List<GpioPins> list, boolean state) {
		if (state) {
			for (GpioPins gpioPin : list) {
				try {
					gpioPin.setOn();
					this.logger.info("Кран закрыт - " + gpioPin.getName());
				} catch (IOException ex) {
					this.logger.error("Ошибка закрытия крана "
							+ gpioPin.getName() + ": " + ex);
				}
			}
		} else {
			for (GpioPins gpioPin : list) {
				try {
					gpioPin.setOff();
					this.logger.info("Кран открыт - " + gpioPin.getName());
				} catch (IOException ex) {
					this.logger.error("Ошибка открытия крана "
							+ gpioPin.getName() + ": " + ex);
				}
			}
		}
	}
}