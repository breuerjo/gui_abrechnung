package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConversion {

	// Date conversion because PreparedStatement#setDate(..) expects a java.sql.Date
	// argument
	public static java.sql.Date dateConversion(String date) {
		java.sql.Date sql_date = null;
		try {
			Date date_date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			sql_date = new java.sql.Date(date_date.getTime());
		} catch (ParseException e1) {
		}

		return sql_date;
	}

	public static String fillUpValue(int value) {
		String return_value = "" + value;
		if (return_value.length() == 1) {
			return_value = "0" + value;
		}
		return return_value;
	}
	
	public static String dateFormating(String date_string) throws ParseException {
		
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(date_string);
		
		return (new SimpleDateFormat("dd.MM.yyyy").format(date));
	}
	
	
	public static String dateFormatingBack(String date_string) throws ParseException {
		
		Date date = new SimpleDateFormat("dd.MM.yyyy").parse(date_string);
		
		return (new SimpleDateFormat("yyyy-MM-dd").format(date));
	}

}
