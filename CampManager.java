import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages camps, including reading from and writing to CSV files, creating, editing, and deleting camps.
 */
public class CampManager {

    /**
     * Main method for testing the functionality of the CampManager class.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] a){
        System.out.println("test");
    }

    public static Camp[] getAllCamps() {
        return CampDBManager.getCampDatabase();
    }

    /**
     * Retrieves camps created by a staff member based on their user ID.
     *
     * @param staffUserID The user ID of the staff member.
     * @return An array of Camp objects created by the specified staff member.
     */
    public static Camp[] getCampsByStaffID(String staffUserID){
        List<Camp> campList = new LinkedList<>(); 
        for(Camp c : CampDBManager.getCampDatabase()){
            if(c.staffInCharge.equals(staffUserID)){
                campList.add(c);
            }
        }
        return campList.toArray(new Camp[campList.size()]);
    }

    /**
     * Retrieves camps attended by a participant based on their user ID.
     *
     * @param attendeeID The user ID of the participant.
     * @return An array of Camp objects attended by the specified participant.
     */
    public static Camp[] getCampsByAttendeeID(String attendeeID){
        List<Camp> campList = new LinkedList<>(); 
        for(Camp c : CampDBManager.getCampDatabase()){
            boolean includeThis = false;
            for(String attendee : c.attendees){
                if(attendee.equals(attendeeID)){
                    includeThis = true;
                    break;
                }
            }
            if(includeThis){
                campList.add(c);
            }
        }
        return campList.toArray(new Camp[campList.size()]);
    }

    /**
     * Retrieves camps associated with a committee based on its ID.
     *
     * @param commiteeID The ID of the committee.
     * @return An array of Camp objects associated with the specified committee.
     */
    public static Camp[] getCampsByCommiteeID(String commiteeID){
        List<Camp> campList = new LinkedList<>(); 
        for(Camp c : CampDBManager.getCampDatabase()){
            boolean includeThis = false;
            for(String attendee : c.committeeList){
                if(attendee.equals(commiteeID)){
                    includeThis = true;
                    break;
                }
            }
            if(includeThis){
                campList.add(c);
            }
        }
        return campList.toArray(new Camp[campList.size()]);
    }

    /**
     * Retrieves camps visible to students, optionally filtered by faculty.
     *
     * @param s The Student object for filtering by faculty.
     * @return An array of Camp objects visible to students, considering faculty criteria.
     */
    public static Camp[] getCampsForStudents(Student s) {
    	List<Camp> campList = new LinkedList<>();
    	Camp[] camp = CampDBManager.getCampDatabase();
    	for(Camp c : camp) {
    		if(c.visible && (!c.onlyFaculty || c.faculty.equals(s.getFaculty()))) {
    			if(!campList.contains(c))
    				campList.add(c);
    		}
    	}
    	return campList.toArray(new Camp[campList.size()]);
    }

    /**
     * Retrieves a specific camp based on its name.
     *
     * @param campName The name of the camp.
     * @return The Camp object with the specified name.
     */
    public static Camp getCamp(String campName){
        for(Camp c : CampDBManager.getCampDatabase()){
            if(c.campName.equals(campName)){
                return c;
            }
        }
        System.out.println("Camp not found.");
        return null;
    }

    // DATA MODIFICATION METHODS ////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a new camp based on user input and adds it to the camp database.
     *
     * @param staffInCharge The staff member creating the camp.
     */
    public static void createCamp(User staffInCharge){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the name of the camp.");
        String name = sc.nextLine();

        System.out.println("Enter the start date of the camp, in the format: dd/mm/yyyy");
        Date startDate = DateStr.strToDate(sc.nextLine());

        System.out.println("Enter the end date of the camp, in the format: dd/mm/yyyy");
        Date endDate = DateStr.strToDate(sc.nextLine());

        System.out.println("Enter the registration deadline, in the format: dd/mm/yyyy");
        Date registrationDeadline = DateStr.strToDate(sc.nextLine());

        System.out.println("Enter the location of the camp");
        String location = sc.nextLine();

        System.out.println("Enter a brief description for this camp.");
        String description = sc.nextLine();

        System.out.println("Enter the total number of attendee slots.");
        int totalSlots = sc.nextInt();

        System.out.println("Enter the total number of committee slots.");
        int commiteeSlots = sc.nextInt();

        System.out.println("Should this camp be open to the whole NTU (type 1) or only for your faculty? (type 0)");
        int choice = sc.nextInt();
        boolean onlyFaculty = choice == 1 ? false : true;

        System.out.println("Initial visibility? Visible (type 1) or Invisible? (type 0)");
        int choice2 = sc.nextInt();
        boolean visible = choice2 == 1 ? true : false;
        sc.nextLine();

        String[] emptyArray = new String[0];

        Camp createdCamp = new Camp(name, startDate, endDate, registrationDeadline, emptyArray, onlyFaculty, location, description, staffInCharge.getUserId(), emptyArray, visible, totalSlots, commiteeSlots, emptyArray);
        
        CampDBManager.createCamp(createdCamp);
    }

    /**
 * Edits an existing camp based on user input and updates it in the camp database.
 *
 * @param campToBeEdited The Camp object to be edited.
 */
    public static void editCamp (Camp campToBeEdited) {
        /*Things staff can edit:
         Start Date
         End Date
         Registration Deadline
         Faculty only visibility
         Location
         Description
         Total Slots
         Committee Slots
         */
        //init new camp
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter number of what you want to edit:");
            System.out.println("1. Start Date");
            System.out.println("2. End Date");
            System.out.println("3. Registration Deadline");
            System.out.println("4. Toggle Faculty only visibility");
            System.out.println("5. Location");
            System.out.println("6. Total Slots");
            System.out.println("7. Camp Committee Slots (Max 10)");
            System.out.println("8. Description");
            System.out.println("9. View Current Changes");
            System.out.println("10. Confirm Changes");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter the start date of the camp, in the format: dd/mm/yyyy");
                    campToBeEdited.startDate = DateStr.strToDate(sc.nextLine());
                    break;
                case 2:
                    System.out.println("Enter the end date of the camp, in the format: dd/mm/yyyy");
                    campToBeEdited.endDate = DateStr.strToDate(sc.nextLine());
                    break;
                case 3:
                    System.out.println("Enter the registration deadline, in the format: dd/mm/yyyy");
                    campToBeEdited.registrationDeadline = DateStr.strToDate(sc.nextLine());
                    break;
                case 4:
                    campToBeEdited.onlyFaculty = !campToBeEdited.onlyFaculty;
                    System.out.println("Toggling to " + campToBeEdited.onlyFaculty);
                    break;
                case 5:
                    System.out.println("Enter the location of the camp");
                    campToBeEdited.location = sc.nextLine();
                    break;
                case 6:
                    System.out.println("Enter the total number of attendee slots.");
                    campToBeEdited.totalSlots = Integer.parseInt(sc.nextLine());
                    break;
                case 7:
                    System.out.println("Enter the total number of committee slots. (Max 10)");
                    int committeeSlots = Integer.parseInt(sc.nextLine());
                    if (committeeSlots > 10 || committeeSlots < 0) {
                        break;
                    } else {
                        campToBeEdited.committeeSlots = committeeSlots;
                    }
                    break;
                case 8:
                    System.out.println("Enter a brief description for this camp.");
                    campToBeEdited.description = sc.nextLine();
                    break;
                case 9:
                    System.out.println("Camp Name: " + campToBeEdited.campName);
                    System.out.println("Start Date: "+ campToBeEdited.startDate);
                    System.out.println("End Date: " + campToBeEdited.endDate);
                    System.out.println("Registration Deadline: " + campToBeEdited.registrationDeadline);
                    System.out.println("Faculty only visibility: " + campToBeEdited.onlyFaculty);
                    System.out.println("Location: " + campToBeEdited.location);
                    System.out.println("Description: " + campToBeEdited.description);
                    System.out.println("Total Slots: " + campToBeEdited.totalSlots);
                    System.out.println("Committee Slots: " + campToBeEdited.committeeSlots );
                    break;
                case 10:
                    CampDBManager.saveUpdatedCamp(campToBeEdited);
                    return;
                default:
                    System.out.println("Invalid input.");

            }
        }

    }

    public static void deleteCamp(Camp campToBeDeleted) {
        CampDBManager.deleteCamp(campToBeDeleted);
    }

    /**
     * Adds an attendee to a camp.
     *
     * @param toBeModified The Camp object to which the attendee is added.
     * @param attendeeUserID The user ID of the attendee.
     */
    public static void addAttendee(Camp toBeModified, String attendeeUserID){
        List<String> attendeeList = new ArrayList<>(Arrays.asList(toBeModified.attendees));
        attendeeList.add(attendeeUserID);
        toBeModified.attendees = attendeeList.toArray(new String[0]);
        CampDBManager.saveUpdatedCamp(toBeModified);
    }

    /**
     * Adds a committee member to a camp.
     *
     * @param toBeModified        The Camp object to which the committee member is added.
     * @param committeeUserID    The user ID of the committee member.
     */
    public static void addCommittee(Camp toBeModified, String committeeUserID){
        List<String> committeeList = new ArrayList<>(Arrays.asList(toBeModified.committeeList));
        committeeList.add(committeeUserID);
        toBeModified.committeeList = committeeList.toArray(new String[0]);
        CampDBManager.saveUpdatedCamp(toBeModified);
    }

    /**
     * Removes an attendee from a camp.
     *
     * @param toBeModified      The Camp object from which the attendee is removed.
     * @param attendeeUserID    The user ID of the attendee to be removed.
     */
    public static void removeAttendee(Camp toBeModified, String attendeeUserID){
        List<String> attendeeList = new ArrayList<>(Arrays.asList(toBeModified.attendees));
        attendeeList.remove(attendeeUserID);
        toBeModified.attendees = attendeeList.toArray(new String[0]);
        CampDBManager.saveUpdatedCamp(toBeModified);
    }
    
    /**
     * Removes a committee member from a camp.
     *
     * @param toBeModified        The Camp object from which the committee member is removed.
     * @param committeeUserID    The user ID of the committee member to be removed.
     */
    public static void removeCommittee(Camp toBeModified, String committeeUserID){
        List<String> committeeList = new ArrayList<>(Arrays.asList(toBeModified.committeeList));
        committeeList.remove(committeeUserID);
        toBeModified.committeeList = committeeList.toArray(new String[0]);
        CampDBManager.saveUpdatedCamp(toBeModified);
    }

    /**
     * Adds a withdrawal record for a user in a camp.
     *
     * @param toBeModified The Camp object to which the withdrawal is added.
     * @param userID       The user ID for the withdrawal.
     */
    public static void addWithdrawal(Camp toBeModified, String UserID){
        List<String> withdrawalList = new ArrayList<>(Arrays.asList(toBeModified.withdrawals));
        withdrawalList.add(UserID);
        toBeModified.withdrawals = withdrawalList.toArray(new String[0]);
        CampDBManager.saveUpdatedCamp(toBeModified);
    }
}
