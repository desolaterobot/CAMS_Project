import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camp {
    String campName;

    Date startDate; 
    Date endDate;
    Date registrationDeadline;

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
    }
}
