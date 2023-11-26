package Camp;
import java.util.Date;

import Users.UserManager;

/**
 * Represents a camp with various attributes such as name, dates, location, and attendees.
 */
public class Camp {
    //all the variables listed here are NOT private as of now
    //i want them to be accessible without the user of accessors for simplicity
    
    /** The name of the camp. */
    protected String campName;
    
    //all of these are Date objects, not strings!
    /** The start date of the camp. */
    protected Date startDate; 

    /** The end date of the camp. */
    protected Date endDate;

    /** The registration deadline for the camp. */
    protected Date registrationDeadline;
    
    /**
     * A boolean indicating whether the camp is available only to faculty.
     * If true, the camp is available only to faculty; otherwise, it is available to the entire NTU.
     */
    protected boolean onlyFaculty;

    /** The location of the camp. */
    protected String location;

    /** A description of the camp. */
    protected String description;

    
    //as of now, the staff in charge, commitees and atendees are stored as STRINGS OF THEIR USERID'S because Staff or Student classes are not created yet.
    /** The staff member in charge of the camp (stored as a string of their user ID). */
    protected String staffInCharge;

    /** An array of committee names associated with the camp. */
    protected String[] committeeList;

    /** An array of user IDs representing attendees of the camp. */
    protected String[] attendees;

    /** A boolean indicating whether the camp is visible. */
    protected boolean visible;

    /** The total number of slots available for the camp. */
    protected int totalSlots;

    /** The number of slots reserved for committees (maximum 10). */
    protected int committeeSlots;
    
    //faculty is automatically derived from the faculty of the staff creating this camp, see line 97.
    /**
     * The faculty of the staff member in charge of the camp.
     * It is automatically derived from the faculty of the staff creating this camp.
     */
    protected String faculty;
    
    protected String[] withdrawals;

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
     * @param withdrawals        An array of user IDs representing withdrawls from the camp.
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
        this.faculty = UserManager.getStaff(staffInCharge).getFaculty();
        this.withdrawals = withdrawals;
    }
    
    public String getCampName() {
        return campName;
    }

    public String[] getAttendees() {
        return attendees;
    }

    public String[] getCommitteeList() {
        return committeeList;
    }
    

    public boolean getOnlyFaculty() {
    	return this.onlyFaculty;
    }

    public int getCommitteeSlots() {
        return committeeSlots;
    }
    
    public String getDescription() {
        return description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getLocation() {
        return location;
    }

    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    public String getStaffInCharge() {
        return staffInCharge;
    }

    public Date getStartDate() {
        return startDate;
    }

    public int getTotalSlots() {
        return totalSlots;
    }

    public String[] getWithdrawals() {
        return withdrawals;
    }

    public boolean getVisible() {
        return visible;
    }

    public void consumeSlot() {
        totalSlots--;
    }

    public void releaseSlot() {
        totalSlots++;
    }

    public void toggleVisibility() {
        visible = !visible;
    }

    public void consumeCommitteeSlots() {
        committeeSlots--;
    }

    public void releaseCommitteeSlots() {
        committeeSlots++;
    }
}
