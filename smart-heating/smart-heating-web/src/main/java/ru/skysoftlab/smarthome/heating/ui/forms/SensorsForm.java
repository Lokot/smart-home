package ru.skysoftlab.smarthome.heating.ui.forms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.owfs.jowfsclient.OwfsConnection;
import org.owfs.jowfsclient.OwfsConnectionConfig;
import org.owfs.jowfsclient.OwfsConnectionFactory;
import org.owfs.jowfsclient.OwfsException;

import ru.skysoftlab.smarthome.heating.ejb.EmProducer;
import ru.skysoftlab.smarthome.heating.entitys.GpioPin;
import ru.skysoftlab.smarthome.heating.entitys.GpioPin_;
import ru.skysoftlab.smarthome.heating.entitys.Sensor;
import ru.skysoftlab.smarthome.heating.entitys.Sensor_;
import ru.skysoftlab.smarthome.heating.gpio.GpioPinType;
import ru.skysoftlab.smarthome.heating.util.OwsfUtilDS18B;

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

/**
 * Форма датчиков.
 * 
 * @author Loktionov Artem
 *
 */
public class SensorsForm extends AbstractForm<Sensor> {

	private static final long serialVersionUID = 2372643403143137631L;

	private ComboBox sensorId;
	private TextField name;
	private TextField low;
	private TextField top;
	private ListSelect gpioPin;
	private ComboBox gpioBox;

	private void clearComponents() {
		sensorId.removeAllItems();
		gpioBox.removeAllItems();
		gpioPin.removeAllItems();
	}

	private void setUpComponents() {
		if (this.entity != null) {
			gpioPin.addItems(this.entity.getGpioPin());
		}
		try {
			gpioBox.addItems(getFreeGpioPin());
		} catch (NamingException e) {
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		}

		OwfsConnection client = null;
		try {
			OwfsConnectionConfig connectionConfig = new OwfsConnectionConfig(
					"192.168.0.86", 3000);
			client = OwfsConnectionFactory.newOwfsClient(connectionConfig);
			List<String> sensorsIds = OwsfUtilDS18B.getIdsDS18B(client);
			sensorsIds.removeAll(getNoFreeSensorsIds());
			sensorId.addItems(sensorsIds);
		} catch (OwfsException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
				}
			}
		}
	}

	@Override
	protected Collection<? extends Component> getInputs() {

		// OwfsConnectionConfig connectionConfig = new OwfsConnectionConfig(
		// "192.168.0.86", 3000);
		// OwfsConnection client = OwfsConnectionFactory
		// .newOwfsClient(connectionConfig);
		//
		// try {
		// List<String> sensorsIds = OwsfUtilDS18B.getIdsDS18B(client);
		// client.disconnect();
		// sensorsIds.removeAll(getNoFreeSensorsIds());
		// sensorId = new ComboBox("Идентификатор", sensorsIds);
		// } catch (OwfsException | IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		// } catch (NamingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// Notification.show(e.getMessage(), Type.TRAY_NOTIFICATION);
		// }
		sensorId = new ComboBox("Идентификатор");
		name = new TextField("Помещение");
		low = new TextField("Минимальная температура (C)");
		top = new TextField("Максимальная температура (C)");
		gpioPin = new ListSelect("Контура");
		gpioPin.setNullSelectionAllowed(false);
		// Show 5 items and a scrollbar if there are more
		gpioPin.setRows(5);

		gpioBox = new ComboBox("Свободные контура");

		Button addGpio = new Button("Добавить контур",
				new Button.ClickListener() {

					private static final long serialVersionUID = 4489078023605774389L;

					@Override
					public void buttonClick(ClickEvent event) {
						GpioPin value = (GpioPin) gpioBox.getValue();
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

	private List<String> getNoFreeSensorsIds() throws NamingException {
		List<String> rv = new ArrayList<>();
		EntityManager em = lookupBean(EmProducer.class).getEM();
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

	private List<GpioPin> getFreeGpioPin() throws NamingException {
		EntityManager em = lookupBean(EmProducer.class).getEM();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<GpioPin> criteriaQuery = builder
				.createQuery(GpioPin.class);
		Root<GpioPin> s = criteriaQuery.from(GpioPin.class);
		criteriaQuery.select(s).where(
				builder.equal(s.get(GpioPin_.type), GpioPinType.KONTUR),
				s.get(GpioPin_.owner).isNull());
		TypedQuery<GpioPin> query = em.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	protected void setFocus() {
		sensorId.focus();
	}

	@SuppressWarnings("unchecked")
	private <B> B lookupBean(Class<B> beanClass) throws NamingException {
		return (B) new InitialContext().lookup("java:module/"
				+ beanClass.getSimpleName());
	}

	@Override
	public void edit(Sensor sensor) {
		this.entity = sensor;
		clearComponents();
		if (sensor != null) {
			// Bind the properties of the contact POJO to fiels in this form
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(sensor, this);
			setUpComponents();
			// добавляем редактируемый
			sensorId.addItem(entity.getSensorId());
			setFocus();
		}
		setVisible(sensor != null);
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
					HashSet<GpioPin> rr = new HashSet<>();
					for (GpioPin component : (Collection<GpioPin>) gpioPin
							.getItemIds()) {
						component.setOwner(entity);
						rr.add(component);
					}
					entity.setGpioPin(rr);
					// Save DAO to backend with direct synchronous service API
					gridView.getJpaContainer().addEntity(entity);
					String msg = String
							.format("Saved '%s'.", entity.toString());
					Notification.show(msg, Type.TRAY_NOTIFICATION);
					gridView.refreshData();
					// чистим
					clearComponents();
					// gpioPin.removeAllItems();
					// gpioBox.removeAllItems();
				} catch (FieldGroup.CommitException e) {
					// Validation exceptions could be shown here
					e.printStackTrace();
				}
			}
		};
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
				// gpioPin.removeAllItems();
				setVisible(false);
			}
		};
	}

}
