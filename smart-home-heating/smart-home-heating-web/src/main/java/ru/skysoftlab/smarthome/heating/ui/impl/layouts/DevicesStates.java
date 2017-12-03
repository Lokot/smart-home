package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import org.vaadin.teemu.switchui.Switch;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.Label;

import io.silverspoon.bulldog.core.gpio.DigitalOutput;
import io.silverspoon.bulldog.core.pin.Pin;
import io.silverspoon.bulldog.core.platform.Board;
import ru.skysoftlab.skylibs.web.annatations.DashBoardElementQualifier;
import ru.skysoftlab.skylibs.web.ui.ReloadDataPenel;
import ru.skysoftlab.smarthome.heating.devices.DeviceType;
import ru.skysoftlab.smarthome.heating.entitys.Device;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndDevicesProvider;
import ru.skysoftlab.smarthome.navigation.NavigationMainMenu.HeatingMenuNames;

/**
 * Состояние устройств.
 * 
 * @author Артём
 *
 */
@UIScoped
@DashBoardElementQualifier(view = HeatingMenuNames.STATISTIC, name = "gpio", order = 0)
public class DevicesStates extends ReloadDataPenel {

	private static final long serialVersionUID = -4244093789449867305L;

	/**
	 * Сравниватель.
	 */
	public static final Comparator<Device> TYPE_ORDER = new Comparator<Device>() {
		@Override
		public int compare(Device e1, Device e2) {
			if (e1.getType().equals(e2.getType())) {
				return 0;
			} else if (e1.getType().equals(DeviceType.BOILER)) {
				return 1;
			}
			return -1;
		}
	};

	@Inject
	private Board board;

	@Inject
	private SensorsAndDevicesProvider sensorsProvider;

	public DevicesStates() {
		super("Устройства");
	}

	private Switch createSwitch(Device device) throws IOException {
		Switch rv = new Switch();
		Pin pin = board.getPin(device.getDef().getName());
		DigitalOutput output = pin.as(DigitalOutput.class);
		if (device.getNormaliClosed()) {
			rv.setValue(output.isHigh());
		} else {
			rv.setValue(output.isLow());
		}
		rv.setReadOnly(true);
		return rv;
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

}
