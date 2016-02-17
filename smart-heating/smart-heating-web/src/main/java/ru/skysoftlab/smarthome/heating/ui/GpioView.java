package ru.skysoftlab.smarthome.heating.ui;

import ru.skysoftlab.smarthome.heating.ejb.GpioPinEntityProviderBean;
import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.ui.forms.GpioForm;

import com.vaadin.data.Container.Indexed;

public class GpioView extends
		AbstractGridView<GpioPin, GpioForm, GpioPinEntityProviderBean> {

	private static final long serialVersionUID = 6698245813955647506L;

	public static final String NAME = "gpio";

	public GpioView() {
		super(GpioPin.class, GpioForm.class, GpioPinEntityProviderBean.class);
	}

	@Override
	protected String getNewButtonLabel() {
		return "Новый пин";
	}

	@Override
	protected Object[] getRemoveColumn() {
		return new String[] { "id" };
	}

	@Override
	protected Object[] getColumnOrder() {
		return new String[] { "gpio", "pin", "def", "name", "normaliClosed" };
	}

	@Override
	protected Indexed refreshData(String value) {
		return getJpaContainer();
	}

}
