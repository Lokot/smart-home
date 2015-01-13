package smarthome;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.validation.constraints.Past;

@ManagedBean
@RequestScoped
public class TestBean implements Serializable {
	private String anser;
	@Past
	private Date date = new Date();

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void listener(ActionEvent e) {
		System.out.println("1111111111111111111111111111");
		int t = new Random().nextInt();
		this.anser = ("11 + " + t);
		System.out.println(this.anser);
	}

	public String getAnser() {
		return this.anser;
	}
}