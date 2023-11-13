import java.util.LinkedList;
import java.util.List;

public class EnquiryManager extends CSVreader{
    public static void main(String[] a){
        Enquiry[] allEnquiries = getEnquiryDatabase();
        for(Enquiry e : allEnquiries){
            System.out.println(e.message);
        }
        addEnquiry(UserManager.getUser("DIMAS001"), CampManager.getCamp("hyper camp"), "is this camp hyper enough for me?");
        Enquiry[] allEnquiries2 = getEnquiryDatabase();
        for(Enquiry e : allEnquiries2){
            System.out.println(e.message);
        }
    }

    public static EnquiryReply[] getEnquiryReplyDatabase(){
        String[] replies = getLines("data/replies.csv");
        List<EnquiryReply> replyList = new LinkedList<>();
        for(String s : replies){
            String[] item = s.split(",");
            replyList.add(new EnquiryReply(item[0], getCommas(item[1]), UserManager.getUser(item[2])));
        }
        return replyList.toArray(new EnquiryReply[replyList.size()]);
    }

    public static Enquiry[] getEnquiryDatabase(){
        String[] enqs = getLines("data/enquiry.csv");
        List<Enquiry> enqlist = new LinkedList<>();
        for(String s : enqs){
            String[] items = s.split(",");
            enqlist.add(new Enquiry(items[0], UserManager.getUser(items[1]), CampManager.getCamp(items[2]), getCommas(items[3]), stringToList(items[4])));
        }
        return enqlist.toArray(new Enquiry[enqlist.size()]);
    }

    public static void addEnquiry(User sender, Camp camp, String message){
        int enquiryID = getEnquiryDatabase().length;
        String line = String.format("%d,%s,%s,%s,%s", enquiryID, sender.userID, removeCommas(camp.campName), removeCommas(message), listToString(new String[0]));
        addLine("data/enquiry.csv", line);
    }
}