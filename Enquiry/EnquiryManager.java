package Enquiry;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import Camp.Camp;
import Camp.CampManager;
import DataManager.EnquiryDBManager;
import Users.Student;
import Users.User;
import Users.UserManager;
import Utility.CSVReader;

/**
 * The EnquiryManager class manages user inquiries and replies, interacting with CSV data.
 * It provides methods to retrieve, add, and manipulate enquiries and their replies.
 */
public class EnquiryManager extends EnquiryDBManager{
    /**
     * Main method for testing and demonstrating the functionality of the EnquiryManager class.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] a){
        Enquiry[] allEnquiries = EnquiryDBManager.getEnquiryDatabase();
        for(Enquiry e : allEnquiries){
            System.out.println(e.message);
        }
        addEnquiry(UserManager.getStudent("DIMAS001"), CampManager.getCamp("hyper camp"), "is this camp hyper enough for me?");
        Enquiry[] allEnquiries2 = EnquiryDBManager.getEnquiryDatabase();
        for(Enquiry e : allEnquiries2){
            System.out.println(e.message);
        }
    }

	/**
     * Retrieves enquiries associated with a specific student.
     *
     * @param s The Student object for which enquiries are retrieved.
     * @return An array of Enquiry objects associated with the specified student.
     */
    public static Enquiry[] getStudentEnquiries(Student s) {
    	Enquiry[] enqs = EnquiryDBManager.getEnquiryDatabase();
    	List<Enquiry> enqlist = new LinkedList<>();
    	for(Enquiry enq : enqs)
    		if(enq.student.getUserId().equals(s.getUserId()))
    			enqlist.add(enq);
    	return enqlist.toArray(new Enquiry[enqlist.size()]);
    }
	
	/**
     * Retrieves enquiries associated with a specific camp.
     *
     * @param camp The Camp object for which enquiries are retrieved.
     * @return An array of Enquiry objects associated with the specified camp.
     */
    public static Enquiry[] getCampEnquiries(Camp camp) {
	    Enquiry[] enqs = EnquiryDBManager.getEnquiryDatabase();
	    List<Enquiry> enqlist = new LinkedList<>();
	    for(Enquiry enq : enqs)
	        if(enq.camp.getCampName().equals(camp.getCampName()))
	            enqlist.add(enq);
	    return enqlist.toArray(new Enquiry[0]);
    }

	/**
     * Retrieves an enquiry based on its ID.
     *
     * @param id The ID of the enquiry.
     * @return The Enquiry object with the specified ID, or null if not found.
     */
    public static Enquiry getEnquiryByID(String id) {
		Enquiry[] enqs = EnquiryDBManager.getEnquiryDatabase();
		for(Enquiry enq : enqs) {
			if(enq.enquiryID.equals(id))
				return enq;
		}
		return null;
    } 
    
    /**
     * Gets an array of EnquiryReply objects corresponding to the replies associated with this enquiry.
     *
     * @return An array of EnquiryReply objects.
     */
	public static EnquiryReply[] getReplies(Enquiry enquiry){
        List<EnquiryReply> replyList = new LinkedList<>();
        EnquiryReply[] database = getEnquiryReplyDatabase();
        for(int x : enquiry.getReplies()){
            replyList.add(database[x]);
        }
        return replyList.toArray(new EnquiryReply[replyList.size()]);
    }
    
    /**
     * Adds a new enquiry to the CSV file.
     *
     * @param sender  The user sending the enquiry.
     * @param camp    The camp related to the enquiry.
     * @param message The message content of the enquiry.
     */
    public static void addEnquiry(User sender, Camp camp, String message){
        int enquiryID = EnquiryDBManager.getEnquiryDatabase().length;
        String line = String.format("%d,%s,%s,%s,%s", enquiryID, sender.getUserId(), removeCommas(camp.getCampName()), removeCommas(message), listToString(new String[0]));
        EnquiryDBManager.addEnquiryToDB(line);
    }
    
	/**
     * Deletes an enquiry from the CSV file.
     *
     * @param toBeDeleted The Enquiry object to be deleted.
     */
    public static void deleteEnquiry(Enquiry toBeDeleted) {
    	EnquiryDBManager.deleteEnquiryFromDB(toBeDeleted);
    }
    
    /**
     * Edits an existing enquiry in the CSV file.
     *
     * @param toBeUpdated The Enquiry object to be updated.
     */
    public static void editEnquiry(Enquiry toBeEdited) {
    	EnquiryReply[] enqrs = getReplies(toBeEdited);
    	String[] strEnquiryReplyIDs = new String[enqrs.length];
    	for(int i=0; i<enqrs.length; i++) {
    		strEnquiryReplyIDs[i] = enqrs[i].getEnquiryReplyID();
    	}
		String line =  String.format("%s,%s,%s,%s,%s", toBeEdited.enquiryID, toBeEdited.student.getUserId(), removeCommas(toBeEdited.camp.getCampName()), removeCommas(toBeEdited.message), listToString(strEnquiryReplyIDs));
		EnquiryDBManager.updateEnquiryDB(toBeEdited, line);
    }

	/**
	 * Adds a reply to the enquiry.
	 *
	 * @param replier The user providing the reply.
	 * @param reply   The content of the reply.
	 */
    public static void replyEnquiry(User replier, String reply, Enquiry enq){
	    EnquiryReply[] enqRDB = EnquiryDBManager.getEnquiryReplyDatabase();
	    int replyID = toInt(enqRDB[enqRDB.length-1].getEnquiryReplyID())+1;
	    String line = String.format("%d,%s,%s", replyID, removeCommas(reply), replier.getUserId());
	    EnquiryDBManager.addReplyToDB(line);//done with appending a new reply into database
	    
	    //but still need to update the enquiry database as well:
	    List<Integer> intList = Arrays.stream(enq.getReplies()).boxed().collect(Collectors.toList());
	    intList.add(replyID);
	    String[] newIDStrings = intList.stream().map(Object::toString).toArray(String[]::new);
	    String enqLine = String.format("%s,%s,%s,%s,%s", enq.getEnquiryID(), replier.getUserId(), removeCommas(enq.camp.getCampName()), removeCommas(enq.message), listToString(newIDStrings));
	    EnquiryDBManager.updateEnquiryDB(enq, enqLine);
    }
}
