package ru.skysoftlab.smarthome.heating.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;

import ru.skysoftlab.smarthome.heating.entitys.Sensor;

public class SensorService implements Serializable {
	
	private static final long serialVersionUID = -3424302725332979273L;
	
//	// Create dummy data by randomly combining first and last names
//	static String[] fnames = { "Peter", "Alice", "John", "Mike", "Olivia",
//			"Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene", "Lisa",
//			"Linda", "Timothy", "Daniel", "Brian", "George", "Scott",
//			"Jennifer" };
//	static String[] lnames = { "Smith", "Johnson", "Williams", "Jones",
//			"Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor",
//			"Anderson", "Thomas", "Jackson", "White", "Harris", "Martin",
//			"Thompson", "Young", "King", "Robinson" };
//
//	private static SensorService instance;
//
//	public static SensorService createDemoService() {
//		if (instance == null) {
//
//			final SensorService contactService = new SensorService();
//
//			Random r = new Random(0);
//			for (int i = 0; i < 100; i++) {
//				Sensor contact = new Sensor();
//				contact.setId(r.nextLong());
//				contact.setName(fnames[r.nextInt(fnames.length)]);
//                contact.setMaxTemp(r.nextFloat());
//                contact.setSensorId(UUID.randomUUID().toString());
//                contactService.save(contact);
//			}
//			instance = contactService;
//		}
//
//		return instance;
//	}
//
//	private HashMap<Long, Sensor> contacts = new HashMap<>();
//	private long nextId = 0;
//
//	public synchronized List<Sensor> findAll(String stringFilter) {
//		ArrayList arrayList = new ArrayList();
//		for (Sensor contact : contacts.values()) {
//			try {
//				boolean passesFilter = (stringFilter == null || stringFilter
//						.isEmpty())
//						|| contact.toString().toLowerCase()
//								.contains(stringFilter.toLowerCase());
//				if (passesFilter) {
//					arrayList.add(contact.clone());
//				}
//			} catch (CloneNotSupportedException ex) {
//				Logger.getLogger(SensorService.class.getName()).log(
//						Level.SEVERE, null, ex);
//			}
//		}
//		Collections.sort(arrayList, new Comparator<Sensor>() {
//
//			@Override
//			public int compare(Sensor o1, Sensor o2) {
//				return (int) (o2.getId() - o1.getId());
//			}
//		});
//		return arrayList;
//	}
//
//	public synchronized long count() {
//		return contacts.size();
//	}
//
//	public synchronized void delete(Sensor value) {
//		contacts.remove(value.getId());
//	}
//
//	public synchronized void save(Sensor entry) {
//		if (entry.getId() == null) {
//			entry.setId(nextId++);
//		}
//		try {
//			entry = (Sensor) BeanUtils.cloneBean(entry);
//		} catch (Exception ex) {
//			throw new RuntimeException(ex);
//		}
//		contacts.put(entry.getId(), entry);
//	}
}
