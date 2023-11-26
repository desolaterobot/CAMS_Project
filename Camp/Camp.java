package Camp;
import java.util.Date;

import Users.UserManager;

/**
 * Represents a camp with various attributes such as name, dates, location, and attendees.
 */
public class Camp {
    
    /** The name of the camp. */
    protected String campName;
    
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
    
    /**
     * The faculty of the staff member in charge of the camp.
     * It is automatically derived from the faculty of the staff creating this camp.
     */
    protected String faculty;
    
    /** An array of names associated with the withdrawal of camp. */
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

    // Getters for the various attributes of the camp

    /**
     * Returns the name of the camp.
     *
     * @return The name of the camp.
     */
    public String getCampName() {
        return campName;
    }

    /**
     * Returns an array of user IDs representing attendees of the camp.
     *
     * @return An array of user IDs representing attendees of the camp.
     */
    public String[] getAttendees() {
        return attendees;
    }

    /**
     * Returns an array of committee names associated with the camp.
     *
     * @return An array of committee names associated with the camp.
     */
    public String[] getCommitteeList() {
        return committeeList;
    }
    
    
    /**
 * Retrieves the faculty restriction status for the staff member in charge of the camp.
 *
 * @return true if the staff member is restricted to their own faculty, false otherwise.
 */
    public boolean getOnlyFaculty() {
    	return this.onlyFaculty;
    }

    /**
     * Returns the number of slots reserved for committees.
     *
     * @return The number of slots reserved for committees.
     */
    public int getCommitteeSlots() {
        return committeeSlots;
    }

    /**
     * Returns the description of the camp.
     *
     * @return The description of the camp.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the end date of the camp.
     *
     * @return The end date of the camp.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Returns the faculty of the staff member in charge of the camp.
     *
     * @return The faculty of the staff member in charge of the camp.
     */
    public String getFaculty() {
        return faculty;
    }

    /**
     * Returns the location of the camp.
     *
     * @return The location of the camp.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the registration deadline for the camp.
     *
     * @return The registration deadline for the camp.
     */
    public Date getRegistrationDeadline() {
        return registrationDeadline;
    }

    /**
     * Returns the staff member in charge of the camp.
     *
     * @return The staff member in charge of the camp.
     */
    public String getStaffInCharge() {
        return staffInCharge;
    }

    /**
     * Returns the start date of the camp.
     *
     * @return The start date of the camp.
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Returns the total number of slots available for the camp.
     *
     * @return The total number of slots available for the camp.
     */
    public int getTotalSlots() {
        return totalSlots;
    }

    /**
     * Returns an array of user IDs representing withdrawals from the camp.
     *
     * @return An array of user IDs representing withdrawals from the camp.
     */
    public String[] getWithdrawals() {
        return withdrawals;
    }

    /**
     * Returns a boolean indicating whether the camp is visible.
     *
     * @return A boolean indicating whether the camp is visible.
     */
    public boolean getVisible() {
        return visible;
    }

    // Methods for modifying camp attributes

    /**
     * Consumes a slot from the total available slots.
     */
    public void consumeSlot() {
        totalSlots--;
    }

    /**
     * Adds a slot from the total available slots.
     */
    public void releaseSlot() {
        totalSlots++;
    }

     /**
     * Toggles the visibility of the camp.
     */
    public void toggleVisibility() {
        visible = !visible;
    }

    /**
     * Consumes a slot from the Committee member available slots.
     */
    public void consumeCommitteeSlots() {
        committeeSlots--;
    }
    
    /**
     * Adds a slot from the Committee member available slots.
     */
    public void releaseCommitteeSlots() {
        committeeSlots++;
    }
    /**
     * Set the Committee List to a new value.
     * @param committeeList
     */
    public void setCommitteeList(String[] committeeList) {
        this.committeeList = committeeList;
    }
}
