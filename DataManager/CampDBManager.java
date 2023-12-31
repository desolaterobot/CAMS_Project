package DataManager;
import java.util.LinkedList;
import java.util.List;

import Camp.Camp;
import Utility.CSVReader;
import Utility.DateStr;

/**
 * Manages the camp database by providing methods to convert, retrieve, create, delete, and save camp information.
 */
public class CampDBManager extends CSVReader {
    
    /**
     * Converts a Camp object to a CSV-formatted string.
     *
     * @param c The Camp object to be converted.
     * @return The CSV-formatted string representation of the Camp.
     */
    public static String campToLine(Camp c){
        String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", 
        removeCommas(c.getCampName()), DateStr.dateToStr(c.getStartDate()), DateStr.dateToStr(c.getEndDate()), DateStr.dateToStr(c.getRegistrationDeadline()), 
        listToString(c.getCommitteeList()), boolToStr(c.getOnlyFaculty()), removeCommas(c.getLocation()), 
        removeCommas(c.getDescription()), c.getStaffInCharge(), listToString(c.getAttendees()), boolToStr(c.getVisible()), intToStr(c.getTotalSlots()), intToStr(c.getCommitteeSlots()), listToString(c.getWithdrawals()));
        return line;
    }

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
            campList.add(new Camp(getCommas(item[0]), DateStr.strToDate(item[1]),DateStr.strToDate(item[2]), DateStr.strToDate(item[3]), stringToList(item[4]), toBool(item[5]), getCommas(item[6]), getCommas(item[7]), item[8], stringToList(item[9]), toBool(item[10]), toInt(item[11]), toInt(item[12]), stringToList(item[13])));
        }
        return campList.toArray(new Camp[campList.size()]);
    }

    /**
     * Create a camp into the camp database.
     *
     * @param newCamp The Camp object to be added to the database.
     */
    public static void createCamp(Camp newCamp) {
        addLine("data/camps.csv", campToLine(newCamp));
    }

    /**
     * Deletes a camp from the camp database.
     *
     * @param toBeDeleted The Camp object to be deleted.
     */
    public static void deleteCamp(Camp toBeDeleted){
        deleteLine("data/camps.csv", toBeDeleted.getCampName());
    }

    /**
     * Commits the information of an existing camp to the database.
     *
     * @param updatedCamp The updated Camp object.
     */
    public static void saveUpdatedCamp(Camp updatedCamp){
        modifyLine("data/camps.csv", updatedCamp.getCampName(), campToLine(updatedCamp));
    }
}
