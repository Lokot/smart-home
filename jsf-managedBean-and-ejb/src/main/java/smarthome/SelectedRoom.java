package smarthome;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import smarthome.entitys.MaxTemp;

@ManagedBean
@SessionScoped
public class SelectedRoom implements Serializable {
	private int room;
	private MaxTemp maxTemp;

	public MaxTemp getMaxTemp() {
		return this.maxTemp;
	}

	public int getRoom() {
		return this.room;
	}

	public void setRoom(int newRoom) {
		this.room = newRoom;
		this.maxTemp = new MaxTemp();
		for (Sensors sensor : Sensors.values()) {
			if (sensor.getIndex() == this.room) {
				this.maxTemp.setSensor(sensor);
				MaxTemp mTemp = (MaxTemp) HibernateUtil.getEm().find(MaxTemp.class, sensor);
				if (mTemp != null) {
					this.maxTemp = mTemp;
				}
			}
		}
	}

	public String getRoomName() {
		return Rooms.getRoomsNames()[this.room];
	}

	public void saveTemp(ActionEvent e) {
		EntityManager em = HibernateUtil.getEm();
		em.getTransaction().begin();
		try {
			em.merge(this.maxTemp);
			em.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
		}
		em.close();
	}
}