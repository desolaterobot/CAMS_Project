package Camp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a camp with various attributes such as name, dates, location, and attendees.
 */
public class Camp {
    //all the variables listed here are NOT private as of now
    //i want them to be accessible without the user of accessors for simplicity
    
    /** The name of the camp. */
    String campName;
    
    //all of these are Date objects, not strings!
    /** The start date of the camp. */
    Date startDate; 

    /** The end date of the camp. */
    Date endDate;

    /** The registration deadline for the camp. */
    Date registrationDeadline;
    
    /**
     * A boolean indicating whether the camp is available only to faculty.
     * If true, the camp is available only to faculty; otherwise, it is available to the entire NTU.
     */
    boolean onlyFaculty;

    /** The location of the camp. */
    String location;

    /** A description of the camp. */
    String description;

    
    //as of now, the staff in charge, commitees and atendees are stored as STRINGS OF THEIR USERID'S because Staff or Student classes are not created yet.
    /** The staff member in charge of the camp (stored as a string of their user ID). */
    String staffInCharge;

    /** An array of committee names associated with the camp. */
    String[] committeeList;

    /** An array of user IDs representing attendees of the camp. */
    String[] attendees;

    /** A boolean indicating whether the camp is visible. */
    boolean visible;

    /** The total number of slots available for the camp. */
    int totalSlots;

    /** The number of slots reserved for committees (maximum 10). */
    int committeeSlots;
    
    //faculty is automatically derived from the faculty of the staff creating this camp, see line 97.
    /**
     * The faculty of the staff member in charge of the camp.
     * It is automatically derived from the faculty of the staff creating this camp.
     */
    String faculty;
    
    String[] withdrawals;

    /**
     * Constructs a new Camp object with the specified parameters.
     *
     * @param name               The name of the camp.
     * @param startDate          The start date of the camp.
     * @param endDate            The end date of the camp.
     * @param registrationDeadline The registration deadline for the camp.
     * @param commiteeList       An array of committee names associated with the camp.
     * @param onlyFaculty        A boolean indicating whether the camp is available only to faculty.
     * @param location           The location of the camp.
     * @param description        The description of the camp.
     * @param staffInCharge      The staff member in charge of the camp (stored as a string of their user ID).
     * @param attendees          An array of user IDs representing attendees of the camp.
     * @param visible            A boolean indicating whether the camp is visible.
     * @param totalSlots         The total number of slots available for the camp.
     * @param committeeSlots     The number of slots reserved for committees (maximum 10).
     */
    public Camp(String name, Date startDate, Date endDate, Date registrationDeadline, String[] committeeList, boolean onlyFaculty, String location, 
                String description, String staffInCharge, String[] attendees, boolean visible, int totalSlots, int committeeSlots, String[] withdrawals){
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
        this.committeeList = committeeList;
        this.attendees = attendees;
        this.faculty = UserManager.getStaff(staffInCharge).faculty;
        this.withdrawals = withdrawals;
    }
}
