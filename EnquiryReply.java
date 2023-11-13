public class EnquiryReply {
    String EnquiryReplyID;
    String reply;
    User commiteeMember;

    public EnquiryReply(String EnquiryReplyID, String reply, User commiteeMember){
        this.EnquiryReplyID = EnquiryReplyID;
        this.reply = reply;
        this.commiteeMember = commiteeMember;
    }
}
