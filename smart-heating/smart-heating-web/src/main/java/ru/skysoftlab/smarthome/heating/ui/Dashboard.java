package ru.skysoftlab.smarthome.heating.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import ru.skysoftlab.smarthome.heating.annatations.DashBoardElementQualifier;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

/**
 * Горизонтальная доска.
 * 
 * @author Артём
 *
 */
public abstract class Dashboard extends BaseMenuView {

	private static final long serialVersionUID = -4806338542441515393L;

	@Inject
	@Any
	private Instance<IDashboardModule> componentSource;

	@PostConstruct
	public void init() {
		List<Component> compList = new ArrayList<>();
		for (IDashboardModule module : getModules()) {
			module.setWidth("100%");
			if (module instanceof Component) {
				compList.add((Component) module);
			}
		}
		HorizontalLayout mainLayout = new HorizontalLayout(
				compList.toArray(new Component[compList.size()]));
		mainLayout.setSizeFull();
		// if (compList.size() > 0) {
		// mainLayout.setExpandRatio(compList.get(0), 1);
		// }
		layout.addComponent(mainLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		reload();
	}

	private void reload() {
		for (IDashboardModule module : getModules()) {
			module.reload();
		}
	}

	private List<IDashboardModule> getModules() {
		List<IDashboardModule> rv = new ArrayList<>();
		DashBoardElementQualifier ann = new DashBoardElementQualifier() {

			@Override
			public Class<? extends java.lang.annotation.Annotation> annotationType() {
				return DashBoardElementQualifier.class;
			}

			@Override
			public String view() {
				return Dashboard.this.getClass().getAnnotation(CDIView.class)
						.value();
			}

			@Override
			public String name() {
				return null;
			}

			@Override
			public int order() {
				return 0;
			}

		};

		try {
			for (Iterator<IDashboardModule> it = componentSource.select(ann)
					.iterator(); it.hasNext();) {
				IDashboardModule candidate = it.next();
				rv.add(candidate);
			}
			Collections.sort(rv, DashBoardElementQualifier.VIEW_QUALIFIER_ORDER);
		} catch (Exception e) {
			Notification.show("Не найдены модули для отображения.",
					Type.TRAY_NOTIFICATION);
		}
		return rv;
	}

}
