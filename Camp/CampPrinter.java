package Camp;

import Utility.DateStr;

/**
 * Provides methods for printing information about camps, including their names, descriptions, locations, dates, and availability.
 */
public class CampPrinter {
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

        for(Camp c : campArray){
            if (onlyVisible) {
                if(c.visible){
                System.out.println("---------------------------------------------------------------------------");
                System.out.printf("%d) %s created by %s\n", x, c.campName, c.staffInCharge);
                System.out.printf("%s\n", c.description);
                System.out.printf("Visiblity of Camp: %b\n", c.visible);
                System.out.printf("Location: %s\n", c.location);
                System.out.printf("From %s to %s\n", DateStr.dateToStr(c.startDate), DateStr.dateToStr(c.endDate));
                System.out.printf("Registration Deadline: %s\n", DateStr.dateToStr(c.registrationDeadline));
                System.out.printf("Total slots left: %d/%d\n", (c.totalSlots-c.attendees.length),c.totalSlots);
                System.out.printf("Total commitee slots left: %d/%d\n", (c.committeeSlots-c.committeeList.length),c.committeeSlots);
                x++;
                }
            } else {
                System.out.println("---------------------------------------------------------------------------");
                System.out.printf("%d) %s created by %s\n", x, c.campName, c.staffInCharge);
                System.out.printf("%s\n", c.description);
                System.out.printf("Visiblity of Camp: %b\n", c.visible);
                System.out.printf("Location: %s\n", c.location);
                System.out.printf("From %s to %s\n", DateStr.dateToStr(c.startDate), DateStr.dateToStr(c.endDate));
                System.out.printf("Registration Deadline: %s\n", DateStr.dateToStr(c.registrationDeadline));
                System.out.printf("Total slots left: %d/%d\n", (c.totalSlots-c.attendees.length),c.totalSlots);
                System.out.printf("Total commitee slots left: %d/%d\n", (c.committeeSlots-c.committeeList.length),c.committeeSlots);
                x++;
            }
            
        }
        System.out.println("---------------------------------------------------------------------------");
    }

    /**
     * Prints a simple list of camp names.
     *
     * @param campArray    The array of Camp objects to be printed.
     * @param onlyVisible  If true, only visible camps will be printed; if false, all camps will be printed.
     */
    public static void print(Camp[] campArray, boolean onlyVisible) {
        if(campArray.length == 0){
            System.out.println("No camps to show.");
            return;
        } 
        int x = 1;
        for(Camp c : campArray){
            if (onlyVisible) {
                if(c.visible){
                System.out.printf("%d) %s\n", x, c.campName);
                x++;
                }
            } else {
                System.out.printf("%d) %s\n", x, c.campName);
                x++;
            }
            
        }
    }

}
