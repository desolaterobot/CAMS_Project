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
    User user;

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
    
    public String getEnquiryReplyID() {
    	return EnquiryReplyID;
    }
    public String getReplyMessage() {
    	return reply;
    }
    public void setReplyMessage(String reply) {
    	this.reply = reply;
    }
}
