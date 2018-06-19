package in.bananaa.pass.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

	public static String formatDate(Calendar unFormattedDate, DateFormatType format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format.getValue());
		return dateFormat.format(unFormattedDate.getTime());
	}

	public static String formatDate(Date unFormattedDate, DateFormatType format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format.getValue());
		return dateFormat.format(unFormattedDate.getTime());
	}

	public static Date parseDate(String dateStr, DateFormatType format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format.getValue());
		return dateFormat.parse(dateStr);
	}

	public static Calendar parseCalendar(String dateStr, DateFormatType format) throws ParseException {
		Date date = parseDate(dateStr, format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
}
