package smarthome;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class TempStatistic implements Serializable {
	public int getOutSideTemp() {
		return -25;
	}

	public int getFlowTemp() {
		return 60;
	}

	public int getReturnTemp() {
		return 40;
	}

	public Float[] getRoomsTemp() {
		Float[] rv = new Float[Sensors.values().length];
		for (Sensors sensor : Sensors.values()) {
			try {
				rv[sensor.getIndex()] = TempScanerBean.getTemp(sensor);
			} catch (Exception ex) {
				rv[sensor.getIndex()] = new Float("555");
			}
		}
		return rv;
	}
}
