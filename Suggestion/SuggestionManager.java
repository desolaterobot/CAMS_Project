package Suggestion;
import java.util.LinkedList;
import java.util.List;

import Camp.Camp;
import Camp.CampManager;
import DataManager.SuggestionDBManager;
import Users.*;
import Utility.CSVReader;

/**
 * The SuggestionManager class manages suggestions from commitee members, interacting with CSV data.
 * It provides methods to retrieve, add, and manipulate suggestions.
 */
public class SuggestionManager extends SuggestionDBManager{

    /**
     * Retrieves the array of all suggestions for a specific camp.
     *
     * @param camp The Camp object that corresponds to the target camp.
     * @return An array of Suggestion objects containing suggestion data for this camp.
     */
    public static Suggestion[] getSuggestionsForCamp(Camp camp){
        List<Suggestion> suggList = new LinkedList<>();
        for(Suggestion s : getSuggestionDatabase()){
            if(s.getCamp().getCampName().equals(camp.getCampName())){
                suggList.add(s);
            }
        }
        return suggList.toArray(new Suggestion[suggList.size()]);
    }

    /**
     * Retrieves the array of all suggestions by a specific user.
     *
     * @param user The User object that corresponds to the user making the suggestion.
     * @return An array of Suggestion objects containing suggestion data for this camp.
     */
    public static Suggestion[] getSuggestionsByUser(User user){
        List<Suggestion> suggList = new LinkedList<>();
        for(Suggestion s : getSuggestionDatabase()){
            if(s.getCommitteeMember().getUserId().equals(user.getUserId())){
                suggList.add(s);
            }
        }
        return suggList.toArray(new Suggestion[suggList.size()]);
    }

    /**
     * Creates a new suggestion into the CSV file.
     *
     * @param camp The Camp object that correspods to the target camp.
     * @param committeeMember The User object that corresponds to the committee member creating this suggestion.
     * @param message The contents of the suggestion
     */
    public static void addSuggestion(Camp camp, User committeeMember, String message){
        Suggestion[] suggDB = getSuggestionDatabase();
        int suggID = CSVReader.toInt(suggDB[suggDB.length-1].getSuggestionID())+1;
        Suggestion newSugg = new Suggestion(Integer.toString(suggID), committeeMember, camp, message, "None");
        addSuggDB(newSugg);
    }

    /**
     * Approves the suggestion and writes the data to the CSV file.
     *
     * @param suggestion The Suggestion object to be approved.
     * @return State of success
     */
    public static boolean approveSuggestion(Suggestion suggestion, Staff staff) {
        if(suggestion.getApprovedStatus()){
            System.out.println("Suggestion has already been approved.");
            return false;
        }
        suggestion.setApprovedBy(staff);
        suggestion.setApprovedStatus(true);
        PointsSystem.addPoint((Student) suggestion.getCommitteeMember());
        updateSuggDB(suggestion);
        return true;
    }

    /**
     * Edits a suggestion in database, only works if it is not approved.
     *
     * @param suggestion The Suggestion object to be edited.
     * @param newMessage The new suggestion message
     * @return status of success
     */
    public static boolean editSuggestion(Suggestion suggestion, String newMessage){
        if(suggestion.getApprovedStatus()){
            System.out.println("Cannot edit suggestions that are already approved.");
            return false;
        }
        suggestion.setMessage(newMessage);
        updateSuggDB(suggestion);
        return true;
    }

     /**
     * Deletes a suggestion in database, only works if it is not approved.
     *
     * @param suggestion The Suggestion object to be deleted.
     * @return status of success
     */
    public static boolean deleteSuggestion(Suggestion suggestion){
        if(suggestion.getApprovedStatus()){
            System.out.println("Cannot delete approved suggestions.");
            return false;
        }
        deleteSuggDB(suggestion);
        return true;
    }
}
