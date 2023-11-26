package Enquiry;
import Users.User;

/**
 * The EnquiryReply class represents a reply to an enquiry.
 * It contains information about the reply's ID, content, and the committee member who provided the reply.
 */
public class EnquiryReply {
    /** The ID of the enquiry reply. */
    private String EnquiryReplyID;

    /** The content of the reply. */
    private String reply;

    /** The committee member and staff who provided the reply. */
    private User user;

    /**
     * Constructs a new EnquiryReply with the given parameters.
     *
     * @param EnquiryReplyID The ID of the enquiry reply.
     * @param reply          The content of the reply.
     * @param user The committee member who provided the reply.
     */
    public EnquiryReply(String EnquiryReplyID, String reply, User user){
        this.EnquiryReplyID = EnquiryReplyID;
        this.reply = reply;
        this.user = user;
    }

    /**
     * Gets the user (committee member or staff) who provided the reply.
     *
     * @return The user who provided the reply.
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets the ID of the enquiry reply.
     *
     * @return The ID of the enquiry reply.
     */
    public String getEnquiryReplyID() {
        return EnquiryReplyID;
    }

    /**
     * Gets the content of the reply message.
     *
     * @return The content of the reply message.
     */
    public String getReplyMessage() {
    	return reply;
    }

    /**
     * Sets the content of the reply message.
     *
     * @param reply The new content of the reply message.
     */
    public void setReplyMessage(String reply) {
    	this.reply = reply;
    }
}
