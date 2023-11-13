import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Enquiry{
    String enquiryID;
    User student;
    Camp camp;
    String message;
    private int[] replies; //will contain the reply ID's not the actual reply.

    public EnquiryReply[] getReplies(){
        List<EnquiryReply> replyList = new LinkedList<>();
        EnquiryReply[] database = EnquiryManager.getEnquiryReplyDatabase();
        for(int x : replies){
            replyList.add(database[x]);
        }
        return replyList.toArray(new EnquiryReply[replyList.size()]);
    }

    public void reply(User replier, String reply){
        int replyID = EnquiryManager.getEnquiryReplyDatabase().length;
        String line = String.format("%d,%s,%s", replyID, CSVreader.removeCommas(reply), replier.userID);
        CSVreader.addLine("data/replies.csv", line); //done with appending a new reply into database
        //but still need to update the enquiry database as well:
        List<Integer> intList = Arrays.stream(replies).boxed().collect(Collectors.toList());
        intList.add(replyID);
        String[] newIDStrings = intList.stream().map(Object::toString).toArray(String[]::new);
        String enqLine = String.format("%s,%s,%s,%s,%s", enquiryID, student.userID, CSVreader.removeCommas(camp.campName), CSVreader.removeCommas(message), CSVreader.listToString(newIDStrings));
        CSVreader.modifyLine("data/enquiry.csv", enquiryID, enqLine);
    }
    
    public Enquiry(String enquiryID, User student, Camp camp, String message, String[] replies) {
        this.enquiryID = enquiryID;
        this.student = student;
        this.camp = camp;
        this.message = message;
        this.replies = Arrays.stream(replies).mapToInt(Integer::parseInt).toArray();
    }
}