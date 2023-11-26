package Enquiry;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import Camp.Camp;
import Users.User;
import Utility.CSVReader;

/**
 * Represents an enquiry made by a student for a specific camp, along with the associated replies.
 */
public class Enquiry{
    /** The unique identifier for the enquiry. */
    protected String enquiryID;

    /** The user (student) making the enquiry. */
    protected User student;

    /** The camp associated with the enquiry. */
    protected Camp camp;

    /** The content of the enquiry message. */
    protected String message;

    /** An array of reply IDs associated with the enquiry. */
    private int[] replies;

    /**
     * Gets an array of EnquiryReply objects corresponding to the replies associated with this enquiry.
     *
     * @return An array of EnquiryReply objects.
     */
    public EnquiryReply[] getReplies(){
        List<EnquiryReply> replyList = new LinkedList<>();
        EnquiryReply[] database = EnquiryManager.getEnquiryReplyDatabase();
        for(int x : replies){
            replyList.add(database[x]);
        }
        return replyList.toArray(new EnquiryReply[replyList.size()]);
    }

    /**
     * Adds a reply to the enquiry.
     *
     * @param replier The user providing the reply.
     * @param reply   The content of the reply.
     */
    public void reply(User replier, String reply){
        EnquiryReply[] enqRDB = EnquiryManager.getEnquiryReplyDatabase();
        int replyID = CSVReader.toInt(enqRDB[enqRDB.length-1].getEnquiryReplyID())+1;
        String line = String.format("%d,%s,%s", replyID, CSVReader.removeCommas(reply), replier.getUserId());
        CSVReader.addLine("data/replies.csv", line); //done with appending a new reply into database
        //but still need to update the enquiry database as well:
        List<Integer> intList = Arrays.stream(replies).boxed().collect(Collectors.toList());
        intList.add(replyID);
        String[] newIDStrings = intList.stream().map(Object::toString).toArray(String[]::new);
        String enqLine = String.format("%s,%s,%s,%s,%s", enquiryID, student.getUserId(), CSVReader.removeCommas(camp.getCampName()), CSVReader.removeCommas(message), CSVReader.listToString(newIDStrings));
        CSVReader.modifyLine("data/enquiry.csv", enquiryID, enqLine);
    }

    /**
     * Edits an enquiry in database, only works if it has not been replied to.
     *
     * @param newEnquiryMessage The new enquiry message
     * @return status of success
     */
    public boolean edit(String newMessage){
        if(replies.length > 0){
            System.out.println("Cannot edit replied enquiries.");
            return false;
        }
        this.message = newMessage;
        String enqLine = String.format("%s,%s,%s,%s,[]", enquiryID, student.getUserId(), CSVReader.removeCommas(camp.getCampName()), CSVReader.removeCommas(newMessage));
        CSVReader.modifyLine("data/enquiry.csv", newMessage, enqLine);
        return true;
    }
    public boolean edit(Camp newCamp){
        if(replies.length > 0){
            System.out.println("Cannot edit replied enquiries.");
            return false;
        }
        this.camp = newCamp;
        String enqLine = String.format("%s,%s,%s,%s,[]", enquiryID, student.getUserId(), CSVReader.removeCommas(camp.getCampName()), CSVReader.removeCommas(message));
        CSVReader.modifyLine("data/enquiry.csv", enquiryID, enqLine);
        return true;
    }

    /**
     * Deletes an enquiry from database, only works if it has not been replied to.
     *
     * @return status of success
     */
    public boolean delete(){
        if(replies.length > 0){
            System.out.println("Cannot delete replied enquiries.");
            return false;
        }
        CSVReader.deleteLine("data/enquiry.csv", enquiryID);
        return true;
    }

    /**
     * Constructs an Enquiry object with the specified parameters.
     *
     * @param enquiryID The unique identifier for the enquiry.
     * @param student   The user (student) making the enquiry.
     * @param camp      The camp associated with the enquiry.
     * @param message   The content of the enquiry message.
     * @param replies   An array of reply IDs associated with the enquiry.
     */
    public Enquiry(String enquiryID, User student, Camp camp, String message, String[] replies) {
        this.enquiryID = enquiryID;
        this.student = student;
        this.camp = camp;
        this.message = message;
        this.replies = Arrays.stream(replies).mapToInt(Integer::parseInt).toArray();
    }


    public Camp getCamp() {
        return camp;
    }

    public String getEnquiryID() {
        return enquiryID;
    }

    public String getMessage() {
        return message;
    }

    public User getStudent() {
        return student;
    }
}
