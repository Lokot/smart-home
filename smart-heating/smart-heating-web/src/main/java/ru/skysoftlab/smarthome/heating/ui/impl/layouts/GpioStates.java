package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.vaadin.teemu.switchui.Switch;

import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.gpio.GpioPinType;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

/**
 * Состояние устройств.
 * 
 * @author Артём
 *
 */
public class GpioStates extends Panel {

	private static final long serialVersionUID = -4244093789449867305L;

	public GpioStates() {
		super("Переключатели");
	}

	public void buildLayout(List<GpioPin> pins) {
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
	
	private Switch createSwitch(GpioPin pin) throws IOException{
		Switch rv = new Switch(pin.getName());
		// TODO заглушка, поменять на rv.setImmediate(PinsUtil.isEnabledPin(pin));
		Random r = new Random();
		rv.setImmediate(r.nextBoolean());
		rv.setReadOnly(true);
		return rv;
	}

	static final Comparator<GpioPin> TYPE_ORDER = new Comparator<GpioPin>() {
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
