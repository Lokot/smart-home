package ru.skysoftlab.smarthome.heating.ui.impl.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import ru.skysoftlab.smarthome.heating.entitys.Boiler;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

/**
 * Форма нагревателя.
 * 
 * @author Loktionov Artem
 *
 */
public class BoilerForm extends AbstractDeviceForm<Boiler> {

	private static final long serialVersionUID = 2372643403143137631L;

	/** Датчики. */
	private ListSelect sensorsSelectList;
	private ComboBox sensorsBox;

	@Override
	protected void clearComponents() {
		super.clearComponents();
		sensorsSelectList.removeAllItems();
		sensorsBox.removeAllItems();
	}

	@Override
	protected void setUpComponents() {
		super.setUpComponents();
		if (this.entity != null) {
			sensorsSelectList.addItems(this.entity.getSensors());
		}
		sensorsBox.addItems(sensorsProvider.getFreeSensors());
	}

	@Override
	protected Collection<Component> getAddonInputs() {
		sensorsSelectList = new ListSelect("Датчики");
		sensorsSelectList.setNullSelectionAllowed(false);
		// Show 5 items and a scrollbar if there are more
		sensorsSelectList.setRows(5);

		sensorsBox = new ComboBox("Свободные датчики");

		Button addGpio = new Button("Добавить датчик",
				new Button.ClickListener() {

					private static final long serialVersionUID = 4489078023605774389L;

					@Override
					public void buttonClick(ClickEvent event) {
						Sensor value = (Sensor) sensorsBox.getValue();
						// Add some items (here by the item ID as the caption)
						sensorsSelectList.addItems(value);
					}
				});

		Button removeGpio = new Button("Удалить датчик");

		VerticalLayout gridButtons = new VerticalLayout(addGpio, removeGpio);
		HorizontalLayout mainLayout = new HorizontalLayout(sensorsBox,
				gridButtons);
		mainLayout.setSpacing(true);

		Collection<Component> rv = new ArrayList<>();
		rv.add(mainLayout);
		rv.add(sensorsSelectList);
		return rv;
	}

	@Override
	protected String getDeviceNameTitle() {
		return "Нагреватель";
	}
	
	@Override
	protected Button.ClickListener getSaveClickListener() {
		return new Button.ClickListener() {

			private static final long serialVersionUID = 4489078023605774389L;

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				try {
					// Commit the fields from UI to DAO
					formFieldBindings.commit();
					HashSet<Sensor> rr = new HashSet<>();
					for (Sensor component : (Collection<Sensor>) sensorsSelectList.getItemIds()) {
						component.setMaster(entity);
						rr.add(component);
					}
					entity.setSensors(rr);
					// Save DAO to backend with direct synchronous service API
					gridView.getJpaContainer().addEntity(entity);
					String msg = String.format("Saved '%s'.", entity.toString());
					Notification.show(msg, Type.TRAY_NOTIFICATION);
					gridView.refreshData();
					// чистим
					clearComponents();
				} catch (FieldGroup.CommitException e) {
					// Validation exceptions could be shown here
					e.printStackTrace();
				}
			}
		};
	}

}
