import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for converting between Date and String representations in the "dd/MM/yyyy" format.
 */
public class DateStr {
    /**
     * Converts a string representation of a date to a Date object.
     *
     * @param str The string representation of the date in "dd/MM/yyyy" format.
     * @return The corresponding Date object.
     */
    public static Date strToDate(String str){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try{
            date = format.parse(str);
            return date;
        }catch(ParseException e){
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Converts a Date object to a string representation.
     *
     * @param date The Date object to be converted.
     * @return The string representation of the date in "dd/MM/yyyy" format.
     */
    public static String dateToStr(Date date){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String str = null;
        try{
            str = format.format(date);
        }catch(Exception e){
            e.printStackTrace();
        }
        return str;
    }
}
