package Test;

/**
 * The EnquiryReply class represents a reply to an enquiry.
 * It contains information about the reply's ID, content, and the committee member who provided the reply.
 */
public class EnquiryReply {
    /** The ID of the enquiry reply. */
    String EnquiryReplyID;

    /** The content of the reply. */
    String reply;

    /** The committee member who provided the reply. */
    User commiteeMember;

    /**
     * Constructs a new EnquiryReply with the given parameters.
     *
     * @param EnquiryReplyID The ID of the enquiry reply.
     * @param reply          The content of the reply.
     * @param commiteeMember The committee member who provided the reply.
     */
    public EnquiryReply(String EnquiryReplyID, String reply, User commiteeMember){
        this.EnquiryReplyID = EnquiryReplyID;
        this.reply = reply;
        this.commiteeMember = commiteeMember;
    }
}
