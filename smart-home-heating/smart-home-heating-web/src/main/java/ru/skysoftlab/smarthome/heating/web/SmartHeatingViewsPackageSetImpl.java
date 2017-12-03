package ru.skysoftlab.smarthome.heating.web;

import ru.skysoftlab.skylibs.web.ui.ViewsPackageSet;

public class SmartHeatingViewsPackageSetImpl implements ViewsPackageSet {

	@Override
	public String[] getViewsPackages() {
		return new String[] { "ru.skysoftlab.smarthome.heating.ui.impl" };
	}

}
