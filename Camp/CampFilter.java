package Camp;

import java.util.LinkedList;
import java.util.List;
import Camp.CampPrinter;

/**
 * Provides methods for filtering and sorting camps based on various criteria.
 */
public class CampFilter{

    /**
     * Main method to demonstrate filtering and printing visible camps.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        filterAndPrintVisibleCamps();
    }

    /**
     * Sorts an array of camps according to multiple parameters.
     *
     * @param campList The list of camps to be sorted.
     * @param method   A number indicating the parameter used to sort the camps.
     *                 0: alphabetical, based on camp name.
     *                 1: alphabetical, based on location.
     *                 2: alphabetical, based on description.
     *                 3: alphabetical, based on faculty.
     *                 4: by increasing number of attendees.
     *                 5: by increasing number of committee members.
     *                 6: by increasing camp dates.
     *                 7: by increasing camp registration deadlines.
     * @return An array of camps sorted by the user's preference.
     */

    public static Camp[] sort(List<Camp> campList, int method){
        switch (method) {
            case 0:
                campList.sort( (c1, c2) -> c1.campName.compareTo(c2.campName) );
                break;
            case 1:
                campList.sort( (c1, c2) -> c1.location.compareTo(c2.location) );
                break;
            case 2:
                campList.sort( (c1, c2) -> c1.description.compareTo(c2.description) );
                break;
            case 3:
                campList.sort( (c1, c2) -> c1.faculty.compareTo(c2.faculty) );
                break;
            case 4:
                campList.sort( (c1, c2) -> Integer.compare(c1.attendees.length, c2.attendees.length));
                break;
            case 5:
                campList.sort( (c1, c2) -> Integer.compare(c1.committeeList.length, c2.committeeList.length));
                break;
            case 6:
                campList.sort( (c1, c2) -> c1.startDate.compareTo(c2.startDate));
                break;
            case 7:
                campList.sort( (c1, c2) -> c1.registrationDeadline.compareTo(c2.registrationDeadline));
                break;
            default:
                System.out.println("Invalid method!");
                return null;
        }

        return campList.toArray(new Camp[campList.size()]);
    }

    /**
     * Prints all the possible choices of filters.
     */
    public static void printFilterChoices(){
        System.out.println("Select the way you want to filter the camps:");
        System.out.println(
        "0: alphabetical, based on camp name."+"\n"+
        "1: alphabetical, based on location."+"\n"+
        "2: alphabetical, based on description."+"\n"+
        "3: alphabetical, based on faculty."+"\n"+
        "4: by increasing number of attendees."+"\n"+
        "5: by increasing number of commitee members."+"\n"+
        "6: by increasing camp dates."+"\n"+
        "7: by increasing camp registration deadlines"+"\n"+
        "8: by increasing camp creation date."
        );
    }

    /**
     * Filters and prints all visible camps, allowing the user to choose the sorting criteria.
     */
    public static void filterAndPrintVisibleCamps(int choice){
        Scanner sc = new Scanner(System.in);
        List<Camp> campList = new LinkedList<>();
        for(Camp c : CampManager.getAllCamps()){
            if(c.visible){
                campList.add(c);
            }   
        }

        CampFilter.sort(campList, choice);
        CampPrinter.printCamps(campList.toArray(new Camp[campList.size()]), true);
    }

    /**
     * Filters and prints all camps being passed in, allowing the user to choose the sorting criteria.
     *
     * @param camps The array of camps being passed in.
     */
    public static void filterAndPrintCamps(Camp[] camps, int choice){
        Scanner sc = new Scanner(System.in);
        List<Camp> campList = new LinkedList<>();
        for(Camp c : camps){
            if(c.visible){
                campList.add(c);
            }   
        }

        CampFilter.sort(campList, choice);
        CampPrinter.printCamps(campList.toArray(new Camp[campList.size()]), true);
    }
}
