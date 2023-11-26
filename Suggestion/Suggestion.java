package Suggestion;
import Camp.Camp;
import Users.Staff;
import Users.User;
import Users.UserManager;
import Utility.CSVReader;

/**
 * Represents an Suggestion made by a commitee member, and its data.
 */
public class Suggestion {
    /** The unique identifier for the suggestion. */
    private String suggestionID;
    /** The commitee member, in the form of User class, who made the suggestion. */
    private User committeeMember; 
    /** The camp that this suggestion belongs to. */
    private Camp camp;
    /** Content of suggestion. */
    private String message;
    /** Indicator of approval. */
    private boolean approved;
    /** The Staff member, in the form of User class, who approved this suggestion. */
    private User approvedBy;

    /**
     * Constructs an Suggestion object with the specified parameters.
     *
     * @param suggestionID The unique identifier for the suggestion.
     * @param committeeMember   The commitee member making the suggestion.
     * @param camp      The camp associated with the suggestion.
     * @param message   The content of the suggestion.
     * @param approvedBy   A string that represents the Staff who approved the suggestion.
     */
    public Suggestion(String suggestionID, User committeeMember, Camp camp, String message, String approvedBy) {
        this.suggestionID = suggestionID;
        this.committeeMember = committeeMember;
        this.camp = camp;
        this.message = message;

        if(approvedBy.equals("None")){
            this.approvedBy = null;
            this.approved = false; 
        }else{
            this.approvedBy = UserManager.getUser(approvedBy);
            this.approved = true;
        }
    }
    
    /**
     * Gets the unique identifier for the suggestion.
     *
     * @return The suggestion ID.
     */
    public String getSuggestionID() {
    	return suggestionID;
    }

    /**
     * Sets the suggestion ID.
     *
     * @param suggestionID The new suggestion ID.
     */
    public void setSuggestionID(String suggestionID) {
    	this.suggestionID = suggestionID;
    }

    /**
     * Gets the committee member making the suggestion.
     *
     * @return The committee member User object.
     */
    public User getCommitteeMember() {
    	return committeeMember;
    }

    /**
     * Gets the camp associated with the suggestion.
     *
     * @return The camp object.
     */
    public Camp getCamp() {
    	return camp;
    }

    /**
     * Gets the content of the suggestion.
     *
     * @return The suggestion message.
     */
    public String getMessage() {
    	return message;
    }

    /**
     * Sets the content of the suggestion.
     *
     * @param message The new suggestion message.
     */
    public void setMessage(String message) {
    	this.message = message;
    }

    /**
     * Gets the approval status of the suggestion.
     *
     * @return {@code true} if approved, {@code false} otherwise.
     */
    public Boolean getApprovedStatus() {
    	return approved;
    }

    /**
     * Sets the approval status of the suggestion.
     *
     * @param approved The new approval status.
     */
    public void setApprovedStatus(Boolean approved) {
    	this.approved = approved;
    }

    /**
     * Sets the staff member who approved the suggestion.
     *
     * @param approvedBy The staff member User object who approved the suggestion.
     */
    public void setApprovedBy(Staff approvedBy){
        this.approvedBy = approvedBy;
    }

    /**
     * Gets the staff member who approved the suggestion.
     *
     * @return The staff member User object, or {@code null} if not approved.
     */
    public User getApprovedBy() {
    	return approvedBy;
    }
}
