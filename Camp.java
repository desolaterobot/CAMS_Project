import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camp {
    //all the variables listed here are NOT private as of now
    //i want them to be accessible without the user of accessors for simplicity
    String campName;
    //all of these are Date objects, not strings!
    Date startDate; 
    Date endDate;
    Date registrationDeadline;
    //false would mean that the camp is available to the whole of NTU
    boolean onlyFaculty;
    String location;
    String description;
    //as of now, the staff in charge, commitees and atendees are stored as STRINGS OF THEIR USERID'S because Staff or Student classes are not created yet.
    String staffInCharge;
    String[] commiteeList;
    String[] attendees;
    boolean visible;
    int totalSlots;
    int committeeSlots;
    //faculty is automatically derived from the faculty of the staff creating this camp, see line 42.
    String faculty;

    public Camp(String name, Date startDate, Date endDate, Date registrationDeadline, String[] commiteeList, boolean onlyFaculty, String location, String description, String staffInCharge, String[] attendees, boolean visible, int totalSlots, int committeeSlots){
        this.campName = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationDeadline = registrationDeadline;
        this.onlyFaculty = onlyFaculty;
        this.location = location;
        this.totalSlots = totalSlots;
        this.committeeSlots = committeeSlots; //maximum 10.
        this.description = description;
        this.staffInCharge = staffInCharge;
        this.visible = visible;
        this.commiteeList = commiteeList;
        this.attendees = attendees;
        this.faculty = UserManager.getUser(staffInCharge).faculty;
    }
}
