package ru.skysoftlab.smarthome.web;

import ru.skysoftlab.skylibs.web.ui.ViewsPackageSet;

public class ViewsPackageSetImpl implements ViewsPackageSet {

	@Override
	public String[] getViewsPackages() {
		return new String[] { "ru.skysoftlab.smarthome.ui" };
	}

}
