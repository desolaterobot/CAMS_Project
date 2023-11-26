package DataManager;

import java.util.LinkedList;
import java.util.List;

import Camp.CampManager;
import Suggestion.Suggestion;
import Users.UserManager;
import Utility.CSVReader;

/**
 * Manages the storage and retrieval of suggestions in the CSV file.
 */
public class SuggestionDBManager extends CSVReader{

    /**
     * Retrieves the array of all suggestions from the CSV file.
     *
     * @return An array of Suggestion objects containing suggestion data.
     */
    public static Suggestion[] getSuggestionDatabase(){
        List<Suggestion> suggList = new LinkedList<>();
        for(String s : getLines("data/suggestions.csv")){
            String[] items = s.split(",");
            suggList.add(new Suggestion(items[0], UserManager.getUser(items[1]), CampManager.getCamp(items[2]), getCommas(items[3]), items[4]));
        }
        return suggList.toArray(new Suggestion[suggList.size()]); 
    }

    /**
     * Converts a suggestion object into String format.
     *
     * @param s The suggestion object to be converted.
     * @return A CSV-formatted string representation of the suggestion.
     */
    private static String suggToLine(Suggestion s){
    	String approvedByUserId = (s.getApprovedBy() != null) ? s.getApprovedBy().getUserId() : "None";
    	
        return String.format("%s,%s,%s,%s,%s", s.getSuggestionID(), s.getCommitteeMember().getUserId(), s.getCamp().getCampName(), removeCommas(s.getMessage()), approvedByUserId);
    }

    /**
     * Adds a line to the Suggestion CSV file.
     *
     * @param s The suggestion object to be added to the CSV file.
     */
    public static void addSuggDB(Suggestion s){
        addLine("data/suggestions.csv", suggToLine(s));
    }

     /**
     * Updates a line in the Suggestion CSV file.
     *
     * @param s The suggestion object to be updated in the CSV file.
     */
    public static void updateSuggDB(Suggestion s){
        modifyLine("data/suggestions.csv", s.getSuggestionID(), suggToLine(s));
    }

     /**
     * Deletes a line from the Suggestion CSV file.
     *
     * @param s The suggestion object to be deleted from the CSV file.
     */
    public static void deleteSuggDB(Suggestion s){
        deleteLine("data/suggestions.csv", s.getSuggestionID());
    }
    
}
