package format;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ClassFormat {
	public static Date convertStringToDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return (Date) sdf.parse(dateStr);
    }

	    public static java.sql.Date convertDateToDateSql(Date date) {
	        return new java.sql.Date(date.getTime());
	    }
}
