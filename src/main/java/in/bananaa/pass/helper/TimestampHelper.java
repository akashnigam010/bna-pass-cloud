package in.bananaa.pass.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

public class TimestampHelper {

	public static String getTimeDiffString(Long datestr) {
		String result = "";
		Date startDate = new Date();
		Date endDate = new Date(datestr);

		long duration = startDate.getTime() - endDate.getTime();

		long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
		long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);
		long diffInDays = TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS);

		boolean isToday = DateUtils.isSameDay(startDate, endDate);
		if (!isToday && diffInHours % 24 != 0) {
			diffInDays++;
		}

		long diffInMonth = monthsBetween(startDate, endDate);
		long diffInYear = yearBetween(startDate, endDate);

		if (diffInYear >= 1) {
			result = diffInYear + (diffInYear == 1 ? " year ago" : " years ago");
		} else if (diffInMonth >= 1) {
			result = diffInMonth + (diffInMonth == 1 ? " month ago" : " months ago");
		} else if (diffInDays >= 1) {
			result = diffInDays + (diffInDays == 1 ? " day ago" : " days ago");
		} else if (diffInHours <= 24 && diffInHours >= 1) {
			result = diffInHours + (diffInHours == 1 ? " hour ago" : " hours ago");
		} else if (diffInMinutes <= 59 && diffInMinutes >= 5) {
			result = diffInMinutes + " minutes ago";
		} else {
			result = "just now";
		}

		return result;
	}

	private static int monthsBetween(Date a, Date b) {
		Calendar cal = Calendar.getInstance();
		if (a.before(b)) {
			cal.setTime(a);
		} else {
			cal.setTime(b);
			b = a;
		}
		int c = 0;
		while (cal.getTime().before(b)) {
			cal.add(Calendar.MONTH, 1);
			c++;
		}
		return c - 1;
	}

	private static int yearBetween(Date a, Date b) {
		Calendar cal = Calendar.getInstance();
		if (a.before(b)) {
			cal.setTime(a);
		} else {
			cal.setTime(b);
			b = a;
		}
		int c = 0;
		while (cal.getTime().before(b)) {
			cal.add(Calendar.YEAR, 1);
			c++;
		}
		return c - 1;
	}
}
