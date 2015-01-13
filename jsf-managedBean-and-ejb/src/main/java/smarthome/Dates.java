package smarthome;

import java.text.DateFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class Dates {
	private int[] days;
	private int[] years;
	private Map<String, Integer> months;

	private static int[] intArray(int from, int to) {
		int[] result = new int[to - from + 1];
		for (int i = from; i <= to; i++) {
			result[(i - from)] = i;
		}
		return result;
	}

	public Dates() {
		this.days = intArray(1, 31);
		this.years = intArray(2014, 2100);
		this.months = new LinkedHashMap();
		String[] names = new DateFormatSymbols().getMonths();
		for (int i = 0; i < 12; i++) {
			this.months.put(names[i], Integer.valueOf(i + 1));
		}
	}

	public int[] getDays() {
		return this.days;
	}

	public int[] getYears() {
		return this.years;
	}

	public Map<String, Integer> getMonths() {
		return this.months;
	}
}