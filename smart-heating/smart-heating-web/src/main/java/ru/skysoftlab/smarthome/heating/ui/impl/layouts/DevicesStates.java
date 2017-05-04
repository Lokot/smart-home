package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.vaadin.teemu.switchui.Switch;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.annatations.DashBoardElementQualifier;
import ru.skysoftlab.smarthome.heating.devices.DeviceType;
import ru.skysoftlab.smarthome.heating.entitys.Device;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndDevicesProvider;
import ru.skysoftlab.smarthome.heating.ui.ReloadDataPenel;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.Label;

/**
 * Состояние устройств.
 * 
 * @author Артём
 *
 */
@UIScoped
@DashBoardElementQualifier(view = NavigationService.STATISTIC, name = "gpio", order=0)
public class DevicesStates extends ReloadDataPenel {

	private static final long serialVersionUID = -4244093789449867305L;

	// TODO удалить
	private Random randomno = new Random();

	@Inject
	private SensorsAndDevicesProvider sensorsProvider;

	public DevicesStates() {
		super("Устройства");
	}

	@Override
	public void reload() {
		List<Device> devices = sensorsProvider.getAllDevices();
		if (devices.size() > 0) {
			Collections.sort(devices, TYPE_ORDER);
			// Create the content
			GridLayout content = new GridLayout(2, devices.size());
			int y = 0;
			for (Device device : devices) {
				try {
					content.addComponent(new Label(device.getName()), 0, y);
					content.addComponent(createSwitch(device), 1, y);
					y++;
				} catch (OverlapsException | OutOfBoundsException | IOException e) {
					e.printStackTrace();
				}
			}
			content.setSizeUndefined(); // Shrink to fit
			content.setMargin(true);
			setContent(content);
		}
	}

	private Switch createSwitch(Device pin) throws IOException {
		Switch rv = new Switch();
		// TODO заглушка, поменять на
		// rv.setImmediate(PinsUtil.isEnabledPin(pin));
		rv.setValue(randomno.nextBoolean());
		// rv.setImmediate(false);
		rv.setReadOnly(true);
		return rv;
	}

	/**
	 * Сравниватель.
	 */
	public static final Comparator<Device> TYPE_ORDER = new Comparator<Device>() {
		public int compare(Device e1, Device e2) {
			if (e1.getType().equals(e2.getType())) {
				return 0;
			} else if (e1.getType().equals(DeviceType.BOILER)) {
				return 1;
			}
			return -1;
		}
	};

}
