package Enquiry;
import java.util.LinkedList;
import java.util.List;

import Camp.Camp;
import Camp.CampManager;
import Users.CSVReader;
import Users.Student;
import Users.User;
import Users.UserManager;

/**
 * The EnquiryManager class manages user inquiries and replies, interacting with CSV data.
 * It provides methods to retrieve, add, and manipulate enquiries and their replies.
 */
public class EnquiryManager extends CSVReader{
    /**
     * Main method for testing and demonstrating the functionality of the EnquiryManager class.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] a){
        Enquiry[] allEnquiries = getEnquiryDatabase();
        for(Enquiry e : allEnquiries){
            System.out.println(e.message);
        }
        addEnquiry(UserManager.getStudent("DIMAS001"), CampManager.getCamp("hyper camp"), "is this camp hyper enough for me?");
        Enquiry[] allEnquiries2 = getEnquiryDatabase();
        for(Enquiry e : allEnquiries2){
            System.out.println(e.message);
        }
    }

    /**
     * Retrieves the array of enquiry replies from the CSV file.
     *
     * @return An array of EnquiryReply objects containing the replies.
     */
    public static EnquiryReply[] getEnquiryReplyDatabase(){
        String[] replies = getLines("data/replies.csv");
        List<EnquiryReply> replyList = new LinkedList<>();
        for(String s : replies){
            String[] item = s.split(",");
            replyList.add(new EnquiryReply(item[0], getCommas(item[1]), UserManager.getUser(item[2])));
        }
        return replyList.toArray(new EnquiryReply[replyList.size()]);
    }

    /**
     * Retrieves the array of enquiries from the CSV file.
     *
     * @return An array of Enquiry objects containing the enquiries.
     */
    public static Enquiry[] getEnquiryDatabase(){
        String[] enqs = getLines("data/enquiry.csv");
        List<Enquiry> enqlist = new LinkedList<>();
        for(String s : enqs){
            String[] items = s.split(",");
            enqlist.add(new Enquiry(items[0], UserManager.getStudent(items[1]), CampManager.getCamp(items[2]), getCommas(items[3]), stringToList(items[4])));
        }
        return enqlist.toArray(new Enquiry[enqlist.size()]);
    }

	/**
     * Retrieves enquiries associated with a specific student.
     *
     * @param s The Student object for which enquiries are retrieved.
     * @return An array of Enquiry objects associated with the specified student.
     */
    public static Enquiry[] getStudentEnquiries(Student s) {
    	Enquiry[] enqs = getEnquiryDatabase();
    	List<Enquiry> enqlist = new LinkedList<>();
    	for(Enquiry enq : enqs)
    		if(enq.student.userID.equals(s.userID))
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
    Enquiry[] enqs = getEnquiryDatabase();
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
	Enquiry[] enqs = getEnquiryDatabase();
	for(Enquiry enq : enqs) {
		if(enq.enquiryID.equals(id))
			return enq;
	}
	return null;
}

    /**
     * Adds a new enquiry to the CSV file.
     *
     * @param sender  The user sending the enquiry.
     * @param camp    The camp related to the enquiry.
     * @param message The message content of the enquiry.
     */
    public static void addEnquiry(User sender, Camp camp, String message){
        int enquiryID = getEnquiryDatabase().length;
        String line = String.format("%d,%s,%s,%s,%s", enquiryID, sender.userID, removeCommas(camp.getCampName()), removeCommas(message), listToString(new String[0]));
        addLine("data/enquiry.csv", line);
    }
    
    /**
     * Edits an existing enquiry in the CSV file.
     *
     * @param toBeUpdated The Enquiry object to be updated.
     */
    public static void editEnquiry(Enquiry toBeUpdated) {
    	EnquiryReply[] enqrs = toBeUpdated.getReplies();
    	String[] strEnquiryReplyIDs = new String[enqrs.length];
    	for(int i=0; i<enqrs.length; i++) {
    		strEnquiryReplyIDs[i] = enqrs[i].EnquiryReplyID;
    	}
		String line =  String.format("%s,%s,%s,%s,%s", toBeUpdated.enquiryID, toBeUpdated.student.userID, removeCommas(toBeUpdated.camp.getCampName()), removeCommas(toBeUpdated.message), listToString(strEnquiryReplyIDs));
		modifyLine("data/enquiry.csv", toBeUpdated.enquiryID, line);
    }

	/**
     * Deletes an enquiry from the CSV file.
     *
     * @param toBeDeleted The Enquiry object to be deleted.
     */
    public static void deleteEnquiry(Enquiry toBeDeleted) {
    	deleteLine("data/enquiry.csv", toBeDeleted.enquiryID);
    }
}
