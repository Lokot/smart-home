package ru.skysoftlab.smarthome.heating.ui.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.skysoftlab.smarthome.heating.ui.AbstractGridView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

public abstract class AbstractForm<T> extends FormLayout {

	private static final long serialVersionUID = -2748949323565272299L;

	protected AbstractGridView<T, ? extends AbstractForm<T>, ?> gridView;
	protected T entity;

	protected Button save = new Button("Сохранить", new Button.ClickListener() {

		private static final long serialVersionUID = 4489078023605774389L;

		@Override
		public void buttonClick(ClickEvent event) {
			try {
				
				// TODO продумать про обновление 41 и 44 строки
				
				// Commit the fields from UI to DAO
				formFieldBindings.commit();

				// Save DAO to backend with direct synchronous service API
				getGridView().getJpaContainer().addEntity(entity);

				String msg = String.format("Saved '%s'.", entity.toString());
				Notification.show(msg, Type.TRAY_NOTIFICATION);
				getGridView().refreshData();
			} catch (FieldGroup.CommitException e) {
				// Validation exceptions could be shown here
			}

		}
	});

	protected Button cancel = new Button("Cancel", new Button.ClickListener() {

		private static final long serialVersionUID = -5244857856527344654L;

		@Override
		public void buttonClick(ClickEvent event) {
			// Place to call business logic.
			Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
			getGridView().getGrid().select(null);
			setVisible(false);
		}
	});

	// Easily bind forms to beans and manage validation and buffering
	protected BeanFieldGroup<T> formFieldBindings;

	public AbstractForm() {
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

		//actions, sensorId, name, maxTemp
		
		List<Component> components = new ArrayList<>();
		components.add(actions);
		components.addAll(getInputs());
		addComponents(components.toArray(new Component[components.size()]));
	}

	protected abstract Collection<? extends Component> getInputs();

	public void edit(T object) {
		this.entity = object;
		if (object != null) {
			// Bind the properties of the contact POJO to fiels in this form
			formFieldBindings = BeanFieldGroup
					.bindFieldsBuffered(object, this);
			setFocus();
		}
		setVisible(object != null);
	}
	
	protected abstract void setFocus();

	public AbstractGridView<T, ? extends AbstractForm<T>, ?> getGridView() {
		return gridView;
	}

	public void setGridView(AbstractGridView<T, ? extends AbstractForm<T>, ?> gridView) {
		this.gridView = gridView;
	}
}
