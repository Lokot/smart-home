package ru.skysoftlab.smarthome.heating.owapi.cdi;

import static ru.skysoftlab.smarthome.heating.config.ConfigNames.ONE_WIRE_ADAPTER_NAME;
import static ru.skysoftlab.smarthome.heating.config.ConfigNames.ONE_WIRE_PORT_NAME;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.skysoftlab.skylibs.annatations.AppProperty;
import ru.skysoftlab.smarthome.heating.onewire.IOneWire;
import ru.skysoftlab.smarthome.heating.owapi.DS9490UsbOneWire;

public class OneWireProducer implements Serializable {

	private static Logger LOG = LoggerFactory.getLogger(OneWireProducer.class);

	private static final long serialVersionUID = -1247489788094095747L;

	// static {
	// try {
	// NativeUtils.loadLibraryFromJar("/libs/libonewireUSB.so");
	// } catch (IOException e) {
	// LOG.error("Load Native Library From Jar error", e);
	// }
	// }

	public void deInitClient(@Disposes IOneWire client) {
		try {
			client.close();
			LOG.info("OWSF-connection closed");
		} catch (IOException e) {
			LOG.error("Close OWSF-connection error", e);
		}
		client = null;
	}

	@Produces
	public IOneWire getOwfsConnectionConfig(@AppProperty(ONE_WIRE_ADAPTER_NAME) String adapter_name,
			@AppProperty(ONE_WIRE_PORT_NAME) String port_name) {
		DS9490UsbOneWire rv = new DS9490UsbOneWire(adapter_name, port_name);
		return rv;
	}

}
