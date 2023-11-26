package DataManager;

import java.util.LinkedList;
import java.util.List;

import Camp.CampManager;
import Enquiry.Enquiry;
import Enquiry.EnquiryReply;
import Users.UserManager;
import Utility.CSVReader;

/**
 * Manages the storage and retrieval of enquiries and their replies in the CSV file.
 */
public class EnquiryDBManager extends CSVReader {
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
     * Edits an existing enquiry in the CSV file.
     *
     * @param toBeUpdated The Enquiry object to be updated.
     * @param line        The updated CSV-formatted string representation of the Enquiry.
     */
    public static void updateEnquiryDB(Enquiry toBeUpdated, String line) {
		modifyLine("data/enquiry.csv", toBeUpdated.getEnquiryID(), line);
    }

	/**
     * Deletes an enquiry from the CSV file.
     *
     * @param toBeDeleted The Enquiry object to be deleted.
     */
    public static void deleteEnquiryFromDB(Enquiry toBeDeleted) {
    	deleteLine("data/enquiry.csv", toBeDeleted.getEnquiryID());
    }
    
    /**
     * Adds a new enquiry to the CSV file.
     *
     * @param line The CSV-formatted string representation of the new enquiry.
     */
    public static void addEnquiryToDB(String line){
        addLine("data/enquiry.csv", line);
    }

	/**
     * Adds a new reply to the CSV file.
     *
     * @param line The CSV-formatted string representation of the new reply.
     */
    public static void addReplyToDB(String line){
    	addLine("data/replies.csv", line);
    }

}
