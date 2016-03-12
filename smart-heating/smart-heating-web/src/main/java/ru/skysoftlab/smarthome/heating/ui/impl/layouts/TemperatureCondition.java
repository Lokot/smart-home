package ru.skysoftlab.smarthome.heating.ui.impl.layouts;

import java.util.Collection;

import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.NavigationService;
import ru.skysoftlab.smarthome.heating.annatations.ViewComponentQualifier;
import ru.skysoftlab.smarthome.heating.dto.TemperatureDto;
import ru.skysoftlab.smarthome.heating.services.SensorsService;
import ru.skysoftlab.smarthome.heating.ui.ReloadDataPenel;

import com.vaadin.cdi.UIScoped;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.GridLayout.OutOfBoundsException;
import com.vaadin.ui.GridLayout.OverlapsException;
import com.vaadin.ui.Label;

@UIScoped
@ViewComponentQualifier(view = NavigationService.STATISTIC, name = "temperature", order=2)
public class TemperatureCondition extends ReloadDataPenel {

	private static final long serialVersionUID = 687213563573955336L;

	@Inject
	private SensorsService sensorsService;

	public TemperatureCondition() {
		super("Температура");
	}

	@Override
	public void reload() {
		Collection<TemperatureDto> sensors = sensorsService.findTemperatures();
		if (sensors.size() > 0) {
			// Create the content
			GridLayout content = new GridLayout(2, sensors.size());
			int y = 0;
			for (TemperatureDto tempDto : sensors) {
				try {
					content.addComponent(new Label(tempDto.getSensorName()), 0,
							y);
					content.addComponent(
							new Label(tempDto.getTemp().toString()), 1, y);
					y++;
				} catch (OverlapsException | OutOfBoundsException e) {
					e.printStackTrace();
				}
			}
			content.setSizeUndefined(); // Shrink to fit
			content.setMargin(true);
			setContent(content);
		}
	}
}
