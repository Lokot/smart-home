package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.vaadin.teemu.switchui.Switch;

import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.gpio.GpioPinType;
import ru.skysoftlab.smarthome.heating.impl.SensorsAndGpioProvider;
import ru.skysoftlab.smarthome.heating.ui.ReloadDataPenel;

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
public class GpioStates extends ReloadDataPenel {

	private static final long serialVersionUID = -4244093789449867305L;

	// TODO удалить
	private Random randomno = new Random();

	@Inject
	private SensorsAndGpioProvider sensorsProvider;

	public GpioStates() {
		super("Переключатели");
	}

	@Override
	public void reload() {
		List<GpioPin> pins = sensorsProvider.getAllGpioPins();
		if (pins.size() > 0) {
			Collections.sort(pins, TYPE_ORDER);
			// Create the content
			GridLayout content = new GridLayout(2, pins.size());
			int y = 0;
			for (GpioPin pin : pins) {
				try {
					content.addComponent(new Label(pin.getName()), 0, y);
					content.addComponent(createSwitch(pin), 1, y);
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

	private Switch createSwitch(GpioPin pin) throws IOException {
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
	public static final Comparator<GpioPin> TYPE_ORDER = new Comparator<GpioPin>() {
		public int compare(GpioPin e1, GpioPin e2) {
			if (e1.getType().equals(e2.getType())) {
				return 0;
			} else if (e1.getType().equals(GpioPinType.BOILER)) {
				return 1;
			}
			return -1;
		}
	};

}
