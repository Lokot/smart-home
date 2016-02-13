package smarthome.gpio;

import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@RequestScoped
public class GpioStatesBean {
	private Logger logger = LoggerFactory.getLogger("GpioStatesBean");
	private GpioPinLayout[] gpios;

	public GpioStatesBean() {
		ArrayList<GpioPinLayout> layouts = new ArrayList();
		for (GpioPins gpioPin : GpioPins.values()) {
			GpioPinLayout gpioPinLayout = new GpioPinLayout(gpioPin.getName());
			String img = "close";
			String alt = "Закрыт";
			try {
				if (gpioPin.isNormaliClosed()) {
					if (gpioPin.getState()) {
						img = "open";
						alt = "Открыт";
					}
				} else if (!gpioPin.getState()) {
					img = "open";
					alt = "Открыт";
				}
			} catch (IOException ex) {
				this.logger.error("Ошибка определения состояния пина.", ex);
			}
			gpioPinLayout.setImg(img);
			gpioPinLayout.setAlt(alt);
			layouts.add(gpioPinLayout);
		}
		this.gpios = new GpioPinLayout[layouts.size()];
		this.gpios = ((GpioPinLayout[]) layouts.toArray(this.gpios));
	}

	public GpioPinLayout[] getGpios() {
		return this.gpios;
	}

	public class GpioPinLayout {
		private String name;
		private String img;
		private String alt;

		public GpioPinLayout(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public String getImg() {
			return this.img;
		}

		public void setImg(String img) {
			this.img = img;
		}

		public String getAlt() {
			return this.alt;
		}

		public void setAlt(String alt) {
			this.alt = alt;
		}
	}
}