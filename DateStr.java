import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//class that allows for conversion of Date to String
//only for dd/mm/yyyy format!!!!
public class DateStr {

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
