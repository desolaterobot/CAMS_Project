package Utility;
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
/**
 * Checks if a given string represents a valid date in the format "dd/MM/yyyy".
 *
 * @param str The string to be checked for a valid date.
 * @return True if the string represents a valid date; false otherwise.
 */
    public static boolean isValidDate(String str) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false); // Set to false to strictly validate dates
        try {
            format.parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
