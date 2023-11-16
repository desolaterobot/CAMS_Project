package Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an enquiry made by a student for a specific camp, along with the associated replies.
 */
public class Enquiry{
    /** The unique identifier for the enquiry. */
    String enquiryID;

    /** The user (student) making the enquiry. */
    User student;

    /** The camp associated with the enquiry. */
    Camp camp;

    /** The content of the enquiry message. */
    String message;

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
        int replyID = EnquiryManager.getEnquiryReplyDatabase().length;
        String line = String.format("%d,%s,%s", replyID, CSVReader.removeCommas(reply), replier.userID);
        CSVReader.addLine("data/replies.csv", line); //done with appending a new reply into database
        //but still need to update the enquiry database as well:
        List<Integer> intList = Arrays.stream(replies).boxed().collect(Collectors.toList());
        intList.add(replyID);
        String[] newIDStrings = intList.stream().map(Object::toString).toArray(String[]::new);
        String enqLine = String.format("%s,%s,%s,%s,%s", enquiryID, student.userID, CSVReader.removeCommas(camp.campName), CSVReader.removeCommas(message), CSVReader.listToString(newIDStrings));
        CSVReader.modifyLine("data/enquiry.csv", enquiryID, enqLine);
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
}
