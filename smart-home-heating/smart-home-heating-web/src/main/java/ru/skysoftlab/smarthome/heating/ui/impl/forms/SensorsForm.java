package ru.skysoftlab.smarthome.heating.ui.impl.forms;

import static ru.skysoftlab.skylibs.web.ui.VaadinUtils.comboboxReadOnly;
import static ru.skysoftlab.skylibs.web.ui.VaadinUtils.comboboxReadOnlyAndSelectFirst;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import ru.skysoftlab.skylibs.web.ui.AbstractForm;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Sensor_;
import ru.skysoftlab.smarthome.heating.entitys.Valve;
import ru.skysoftlab.smarthome.heating.entitys.Valve_;
import ru.skysoftlab.smarthome.heating.onewire.IAlarmScannerJob;
import ru.skysoftlab.smarthome.heating.services.SensorsService;

/**
 * Форма датчиков.
 * 
 * @author Loktionov Artem
 *
 */
public class SensorsForm extends AbstractForm<Sensor> {

	private static final long serialVersionUID = 2372643403143137631L;

	@Inject
	private EntityManager em;

	private ComboBox gpioBox;

	/** Контура. */
	private ListSelect gpioPin;

	private TextField low;
	private TextField name;
	@Inject
	private IAlarmScannerJob scanner;
	private ComboBox sensorId;
	@Inject
	private SensorsService service;
	private TextField top;

	private void clearComponents() {
		sensorId.removeAllItems();
		gpioBox.removeAllItems();
		gpioPin.removeAllItems();
	}

	@Override
	public void edit(Sensor sensor) {
		this.entity = sensor;
		clearComponents();
		if (sensor != null) {
			// Bind the properties of the contact POJO to fiels in this form
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(sensor, this);
			setUpComponents();
			if (sensor.getSensorId() != null) {
				// добавляем редактируемый
				sensorId.addItem(sensor.getSensorId());
				sensorId.select(sensor.getSensorId());
			}
			setFocus();
		}
		setVisible(sensor != null);
	}

	@Override
	protected Button.ClickListener getCancelClickListener() {
		return new Button.ClickListener() {

			private static final long serialVersionUID = -5244857856527344654L;

			@Override
			public void buttonClick(ClickEvent event) {
				// Place to call business logic.
				Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
				gridView.getGrid().select(null);
				// чистим
				clearComponents();
				setVisible(false);
			}
		};
	}

	private List<Valve> getFreeGpioPin() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Valve> criteriaQuery = builder.createQuery(Valve.class);
		Root<Valve> s = criteriaQuery.from(Valve.class);
		criteriaQuery.select(s).where(s.get(Valve_.owner).isNull());
		TypedQuery<Valve> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}

	/**
	 * Возвращает список свободных датчиков.
	 * 
	 * @return
	 */
	private List<String> getFreeSensorsIds() {
		List<String> rv = service.getIdsDS18B();
		rv.removeAll(getNoFreeSensorsIds());
		return rv;
	}

	@Override
	protected Collection<? extends Component> getInputs() {
		sensorId = new ComboBox("Идентификатор");
		name = new TextField("Помещение");
		low = new TextField("Минимальная температура (C)");
		top = new TextField("Максимальная температура (C)");
		gpioPin = new ListSelect("Контура");
		gpioPin.setNullSelectionAllowed(false);
		// Show 5 items and a scrollbar if there are more
		gpioPin.setRows(5);

		gpioBox = new ComboBox("Свободные контура");

		Button addGpio = new Button("Добавить контур", new Button.ClickListener() {

			private static final long serialVersionUID = 4489078023605774389L;

			@Override
			public void buttonClick(ClickEvent event) {
				Valve value = (Valve) gpioBox.getValue();
				// Add some items (here by the item ID as the caption)
				gpioPin.addItems(value);
			}
		});

		Button removeGpio = new Button("Удалить контур");

		VerticalLayout gridButtons = new VerticalLayout(addGpio, removeGpio);
		HorizontalLayout mainLayout = new HorizontalLayout(gpioBox, gridButtons);
		mainLayout.setSpacing(true);

		Collection<Component> rv = new ArrayList<>();
		rv.add(sensorId);
		rv.add(name);
		rv.add(low);
		rv.add(top);
		rv.add(mainLayout);
		rv.add(gpioPin);
		return rv;
	}

	/**
	 * Возвращает список закрепленных датчиков.
	 * 
	 * @return
	 */
	private List<String> getNoFreeSensorsIds() {
		List<String> rv = new ArrayList<>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteria = builder.createTupleQuery();
		Root<Sensor> personRoot = criteria.from(Sensor.class);
		Path<String> idPath = personRoot.get(Sensor_.sensorId);
		criteria.multiselect(idPath);
		List<Tuple> tuples = em.createQuery(criteria).getResultList();
		for (Tuple tuple : tuples) {
			rv.add(tuple.get(idPath));
		}
		return rv;
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
					HashSet<Valve> rr = new HashSet<>();
					for (Valve component : (Collection<Valve>) gpioPin.getItemIds()) {
						component.setOwner(entity);
						rr.add(component);
					}
					entity.setGpioPin(rr);
					// Save DAO to backend with direct synchronous service API
					gridView.getJpaContainer().addEntity(entity);
					String msg = String.format("Saved '%s'.", entity.toString());
					Notification.show(msg, Type.TRAY_NOTIFICATION);
					gridView.refreshData();
					// чистим
					clearComponents();
					// обновляем сканнер
					scanner.setDeviceConfig(entity);
				} catch (FieldGroup.CommitException e) {
					// Validation exceptions could be shown here
					e.printStackTrace();
				}
			}
		};
	}

	@Override
	protected void setFocus() {
		sensorId.focus();
	}

	private void setUpComponents() {
		if (this.entity != null) {
			gpioPin.addItems(this.entity.getGpioPin());
		}
		gpioBox.addItems(getFreeGpioPin());
		comboboxReadOnly(gpioBox);
		sensorId.addItems(getFreeSensorsIds());
		comboboxReadOnlyAndSelectFirst(sensorId);
	}

}
