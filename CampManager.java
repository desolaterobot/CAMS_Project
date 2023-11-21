package Camp;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages camps, including reading from and writing to CSV files, creating, editing, and deleting camps.
 */
public class CampManager extends CSVReader{

    
    public static void main(String[] a){
        System.out.println("test");
    }

    /**
     * Converts a string representation of a date to a Date object.
     *
     * @param str The string representation of the date.
     * @return The corresponding Date object.
     */
    private static Date strToDate(String str){
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
     * @return The string representation of the date.
     */
    private static String dateToStr(Date date){
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
     * Converts a Camp object to a CSV-formatted string.
     *
     * @param c The Camp object to be converted.
     * @return The CSV-formatted string representation of the Camp.
     */
    private static String campToLine(Camp c){
        String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 
        removeCommas(c.campName), dateToStr(c.startDate), dateToStr(c.endDate), dateToStr(c.registrationDeadline), 
        listToString(c.committeeList), boolToStr(c.onlyFaculty), removeCommas(c.location), 
        removeCommas(c.description), c.staffInCharge, listToString(c.attendees), boolToStr(c.visible), intToStr(c.totalSlots), intToStr(c.committeeSlots), listToString(c.withdrawals));
        return line;
    }

    /**
     * Prints information about camps, including their names, descriptions, locations, dates, and availability.
     *
     * @param campArray    The array of Camp objects to be printed.
     * @param onlyVisible  If true, only visible camps will be printed; if false, all camps will be printed.
     */
    public static void printCamps(Camp[] campArray, boolean onlyVisible){
        if(campArray.length == 0){
            System.out.println("No camps to show.");
            return;
        }
        int x = 1;
        if(!onlyVisible){
            for(Camp c : campArray){
                c.visible = true;
            }
        }
        for(Camp c : campArray){
            if(c.visible){
                System.out.println("---------------------------------------------------------------------------");
                System.out.printf("%d) %s created by %s\n", x, c.campName, c.staffInCharge);
                System.out.printf("%s\n", c.description);
                System.out.printf("Location: %s\n", c.location);
                System.out.printf("From %s to %s\n", dateToStr(c.startDate), dateToStr(c.endDate));
                System.out.printf("Registration Deadline: %s\n", dateToStr(c.registrationDeadline));
                System.out.printf("Total slots left: %d/%d\n", (c.totalSlots-c.attendees.length),c.totalSlots);
                System.out.printf("Total commitee slots left: %d/%d\n", (c.committeeSlots-c.committeeList.length),c.committeeSlots);
                x++;
            }
        }
        System.out.println("---------------------------------------------------------------------------");
    }

    // DATA READING METHODS ////////////////////////////////////////////////////////////////////////////////

    /**
     * Retrieves the camp database from the CSV file and returns an array of Camp objects.
     *
     * @return An array of Camp objects representing the camp database.
     */
    public static Camp[] getCampDatabase(){
        String[] lines = null;
        lines = getLines("data/camps.csv");
        List<Camp> campList = new LinkedList<>(); 
        for(String line : lines){
            String[] item = line.split(",");
            campList.add(new Camp(getCommas(item[0]), strToDate(item[1]), strToDate(item[2]), strToDate(item[3]), stringToList(item[4]), toBool(item[5]), getCommas(item[6]), getCommas(item[7]), item[8], stringToList(item[9]), toBool(item[10]), toInt(item[11]), toInt(item[12]), stringToList(item[13])));
        }
        return campList.toArray(new Camp[campList.size()]);
    }

    /**
     * Retrieves camps created by a staff member based on their user ID.
     *
     * @param staffID The user ID of the staff member.
     * @return An array of Camp objects created by the specified staff member.
     */
    public static Camp[] getCampsByStaffID(String staffUserID){
        List<Camp> campList = new LinkedList<>(); 
        for(Camp c : getCampDatabase()){
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
        for(Camp c : getCampDatabase()){
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
        for(Camp c : getCampDatabase()){
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
    
    public static Camp[] getCampsForStudents(Student s) {
    	List<Camp> campList = new LinkedList<>();
    	Camp[] camp = getCampDatabase();
    	for(Camp c : camp) {
    		if(c.visible && (!c.onlyFaculty || c.faculty.equals(s.faculty))) {
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
        for(Camp c : getCampDatabase()){
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
        Date startDate = strToDate(sc.nextLine());

        System.out.println("Enter the end date of the camp, in the format: dd/mm/yyyy");
        Date endDate = strToDate(sc.nextLine());

        System.out.println("Enter the registration deadline, in the format: dd/mm/yyyy");
        Date registrationDeadline = strToDate(sc.nextLine());

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

        Camp createdCamp = new Camp(name, startDate, endDate, registrationDeadline, emptyArray, onlyFaculty, location, description, staffInCharge.userID, emptyArray, visible, totalSlots, commiteeSlots, emptyArray);
        
        addLine("data/camps.csv", campToLine(createdCamp));
    }

    //not allowed to edit names, since we index by name to filter camps
    /**
     * Edits the information of an existing camp.
     *
     * @param updatedCamp The updated Camp object.
     */
    public static void editCamp(Camp updatedCamp){
        modifyLine("data/camps.csv", updatedCamp.campName, campToLine(updatedCamp));
    }

    /**
     * Deletes a camp from the camp database.
     *
     * @param toBeDeleted The Camp object to be deleted.
     */
    public static void deleteCamp(Camp toBeDeleted){
        deleteLine("data/camps.csv", toBeDeleted.campName);
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
        editCamp(toBeModified);
    }
    
    public static void addCommittee(Camp toBeModified, String committeeUserID){
        List<String> committeeList = new ArrayList<>(Arrays.asList(toBeModified.committeeList));
        committeeList.add(committeeUserID);
        toBeModified.committeeList = committeeList.toArray(new String[0]);
        editCamp(toBeModified);
    }
    
    public static void removeAttendee(Camp toBeModified, String attendeeUserID){
        List<String> attendeeList = new ArrayList<>(Arrays.asList(toBeModified.attendees));
        attendeeList.remove(attendeeUserID);
        toBeModified.attendees = attendeeList.toArray(new String[0]);
        editCamp(toBeModified);
    }
    
    public static void removeCommittee(Camp toBeModified, String committeeUserID){
        List<String> committeeList = new ArrayList<>(Arrays.asList(toBeModified.committeeList));
        committeeList.remove(committeeUserID);
        toBeModified.committeeList = committeeList.toArray(new String[0]);
        editCamp(toBeModified);
    }
    
    public static void addWithdrawal(Camp toBeModified, String UserID){
        List<String> withdrawalList = new ArrayList<>(Arrays.asList(toBeModified.withdrawals));
        withdrawalList.add(UserID);
        toBeModified.withdrawals = withdrawalList.toArray(new String[0]);
        editCamp(toBeModified);
    }
}
