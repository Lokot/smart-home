package smarthome;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "rooms")
@SessionScoped
public class Rooms implements Serializable {
	private static final String[] names = null;

	public String[] getNames() {
		return getRoomsNames();
	}

	public static String[] getRoomsNames() {
		String[] rv = new String[Sensors.values().length];
		for (Sensors sensor : Sensors.values()) {
			if (sensor.getGpioPins() != null) {
				rv[sensor.getIndex()] = sensor.getName();
			}
		}
		return rv;
	}
}