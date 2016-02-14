package ru.skysoftlab.smarthome.heating.ui.forms;

import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.ui.SensorsUI;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Форма датчиков.
 * 
 * @author Loktionov Artem
 *
 */
public class SensorsForm extends FormLayout {

	private static final long serialVersionUID = 2372643403143137631L;
	
	private SensorsUI sensorsView;

	Button save = new Button("Save", new Button.ClickListener() {

		private static final long serialVersionUID = 4489078023605774389L;

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				
				// TODO продумать про обновление 41 и 44 строки
				
				// Commit the fields from UI to DAO
				formFieldBindings.commit();

				// Save DAO to backend with direct synchronous service API
				sensorsView.getSensors().addEntity(contact);

				String msg = String.format("Saved '%s %s'.", contact.getName(),
						contact.getMaxTemp());
				Notification.show(msg, Type.TRAY_NOTIFICATION);
				sensorsView.refreshContacts();
			} catch (FieldGroup.CommitException e) {
				// Validation exceptions could be shown here
			}

		}
	});

	Button cancel = new Button("Cancel", new Button.ClickListener() {

		private static final long serialVersionUID = -5244857856527344654L;

		@Override
		public void buttonClick(ClickEvent event) {
			// Place to call business logic.
			Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
			sensorsView.contactList.select(null);
		}
	});
	TextField sensorId = new TextField("sensorId");
	TextField name = new TextField("name");
	TextField maxTemp = new TextField("maxTemp");

	Sensor contact;

	// Easily bind forms to beans and manage validation and buffering
	BeanFieldGroup<Sensor> formFieldBindings;

	public SensorsForm(SensorsUI sensorsView) {
		this.sensorsView = sensorsView;
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(false);
	}

	private void buildLayout() {
		setSizeUndefined();
		setMargin(true);

		HorizontalLayout actions = new HorizontalLayout(save, cancel);
		actions.setSpacing(true);
		
		

		addComponents(actions, sensorId, name, maxTemp);
	}

	public void edit(Sensor contact) {
		this.contact = contact;
		if (contact != null) {
			// Bind the properties of the contact POJO to fiels in this form
			formFieldBindings = BeanFieldGroup
					.bindFieldsBuffered(contact, this);
			sensorId.focus();
		}
		setVisible(contact != null);
	}

	// @Override
	// public SensorsUI getUI() {
	// return (SensorsUI) super.getUI();
	// }

}
