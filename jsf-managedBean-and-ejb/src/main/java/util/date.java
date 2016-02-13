package util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;

public class date extends UIInput implements NamingContainer {
	public String getFamily() {
		return "javax.faces.NamingContainer";
	}

	public void encodeBegin(FacesContext context) throws IOException {
		Date date = (Date) getValue();
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		UIInput dayComponent = (UIInput) findComponent("day");
		UIInput monthComponent = (UIInput) findComponent("month");
		UIInput yearComponent = (UIInput) findComponent("year");
		dayComponent.setValue(Integer.valueOf(cal.get(5)));
		monthComponent.setValue(Integer.valueOf(cal.get(2) + 1));
		yearComponent.setValue(Integer.valueOf(cal.get(1)));
		super.encodeBegin(context);
	}

	public Object getSubmittedValue() {
		return this;
	}

	protected Object getConvertedValue(FacesContext context,
			Object newSubmittedValue) throws ConverterException {
		UIInput dayComponent = (UIInput) findComponent("day");
		UIInput monthComponent = (UIInput) findComponent("month");
		UIInput yearComponent = (UIInput) findComponent("year");

		int day = ((Integer) dayComponent.getValue()).intValue();
		int month = ((Integer) monthComponent.getValue()).intValue();
		int year = ((Integer) yearComponent.getValue()).intValue();
		if (isValidDate(day, month, year)) {
			return new GregorianCalendar(year, month - 1, day).getTime();
		}
		FacesMessage message = Messages.getMessage("util.messages",
				"invalidDate", null);

		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ConverterException(message);
	}

	private static boolean isValidDate(int d, int m, int y) {
		if ((d < 1) || (m < 1) || (m > 12)) {
			return false;
		}
		if (m == 2) {
			if (isLeapYear(y)) {
				return d <= 29;
			}
			return d <= 28;
		}
		if ((m == 4) || (m == 6) || (m == 9) || (m == 11)) {
			return d <= 30;
		}
		return d <= 31;
	}

	private static boolean isLeapYear(int y) {
		return (y % 4 == 0) && ((y % 400 == 0) || (y % 100 != 0));
	}
}

/*
 * Location: E:\classes\
 * 
 * Qualified Name: util.date
 * 
 * JD-Core Version: 0.7.0.1
 */