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
     * Approves the suggestion in database, only works if it is not approved.
     *
     * @param staff the User object representing the staff who approved this suggestion.
     * @return status of success
     */
    public boolean approve(User staff){
        if(this.approved){
            System.out.printf("This suggestion has already been approved by %s\n", approvedBy.getName());
            return false;
        }
        this.approved = true;
        this.approvedBy = staff;
        String modifiedLine = String.format("%s,%s,%s,%s,%s", this.suggestionID, this.committeeMember.getUserId(), this.camp.campName, CSVReader.removeCommas(this.message), staff.getUserId());
        CSVReader.modifyLine("data/suggestions.csv", suggestionID, modifiedLine);
        return true;
    }

    /**
     * Edits a suggestion in database, only works if it is not approved.
     *
     * @param newMessage The new suggestion message
     * @return status of success
     */
    public boolean edit(String newMessage){
        if(this.approved){
            System.out.println("Cannot edit suggestions that are already approved.");
            return false;
        }
        this.message = newMessage;
        String modifiedLine = String.format("%s,%s,%s,%s,None", this.suggestionID, this.committeeMember.getUserId(), this.camp.campName, CSVReader.removeCommas(newMessage));
        CSVReader.modifyLine("data/suggestions.csv", suggestionID, modifiedLine);
        return true;
    }

    /**
     * Deletes a suggestion in database, only works if it is not approved.
     *
     * @return status of success
     */
    public boolean delete(){
        if(this.approved){
            System.out.println("Cannot delete approved suggestions.");
            return false;
        }
        CSVReader.deleteLine("data/suggestions.csv", suggestionID);
        return true;
    }
    
    //Getters and Setters
    public String getSuggestionID() {
    	return suggestionID;
    }
    
    public void setSuggestionID(String suggestionID) {
    	this.suggestionID = suggestionID;
    }
    
    public User getCommitteeMember() {
    	return committeeMember;
    }
    
    public Camp getCamp() {
    	return camp;
    }
    
    public String getMessage() {
    	return message;
    }
    public void setMessage(String message) {
    	this.message = message;
    }
    
    public Boolean getApprovedStatus() {
    	return approved;
    }
    
    public void setApprovedStatus(Boolean approved) {
    	this.approved = approved;
    }
    
    public User getApprovedBy() {
    	return approvedBy;
    }
}
