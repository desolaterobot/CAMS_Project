import java.util.*;


public class Staff extends User {
    List<Camp> ownCamps = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Testing...");
        Staff s = new Staff("Alexei","OURIN@ntu.edu.sg","ADM","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
//        s.viewOwnCamp();
//        s.viewAllCamp();
//        s.viewEnquiries();
        s.acceptSuggestion();
//        s.viewSuggestions();
    }

    public Staff(String name, String email, String faculty, String passHash) {
        super(name, email, faculty, passHash);
    }

    public boolean createCamp() {
        CampManager.createCamp(this);
        return true;
    }

    public boolean editCamp(Camp camp) {
        CampManager.editCamp(camp);
        return true;
    }

    public boolean deleteCamp(Camp camp) {
        CampManager.deleteCamp(camp);
        return true;
    }

    public void viewStudentList(Camp camp) {
        System.out.println("======Student List======");
        for (String student : camp.attendees) {
            System.out.println(student);
        }
        System.out.println("========================");
    }

    public void viewSuggestions() {
        for (Camp camp:
        CampManager.getCampsByStaffID(userID)) {
            System.out.println("Suggestions for " + camp.campName + ":");
            int num = 1;
            for (Suggestion suggestion:
            SuggestionManager.getSuggestionsForCamp(camp)) {
                System.out.println(num + ") " +  suggestion.committeeMember.name +": " + suggestion.message + ". Is Approved: " + suggestion.approved);
                num++;
            }
        }

    }

    public void acceptSuggestion() {
        System.out.println("Enter Number to accept the suggestion");
        List<Suggestion> allSuggestions = new ArrayList<>();
        int num = 1;
        for (Camp camp:
                CampManager.getCampsByStaffID(userID)) {
            System.out.println("Suggestions for " + camp.campName + ":");
            for (Suggestion suggestion:
                    SuggestionManager.getSuggestionsForCamp(camp)) {
                if (!suggestion.approved) {
                    System.out.println(num + ") " +  suggestion.committeeMember.name +": " + suggestion.message + ". Is Approved: " + suggestion.approved);
                    allSuggestions.add(suggestion);
                    num++;
                }

            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int choice = Integer.parseInt(sc.nextLine());
        SuggestionManager.approveSuggestion(allSuggestions.get(choice-1),this);
    }

//    public String generateCampReport(Camp camp) {
//
//    }

    public boolean viewEnquiries() {
        Camp[] camps = CampManager.getCampsByStaffID(userID);
        System.out.println("Select Camp to view Enquiries");
        CampManager.printCamps(camps,false);
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        for (Enquiry enquiry :
                EnquiryManager.getCampEnquiries(camps[choice - 1])) {
            System.out.println(enquiry.student.name + ": " +  enquiry.message);
            EnquiryReply[] replies = enquiry.getReplies();
            if (replies.length != 0) {
                for (EnquiryReply reply :
                        replies) {
                    System.out.println("\u21B3 " + reply.user.name + ": " + reply.reply);
                }
            } else {
                System.out.println("[No Replies]");
            }


        }
        return true;
    }

    public boolean replyEnquiries(Enquiry enquiry) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Reply: ");
        enquiry.reply(this,sc.nextLine());
        return true;
    }

    /**
     * Toggle the visibility of given Camp.
     * @param camp camp object of visibility to change
     * @return result of the change
     */
    public boolean toggleVisibility(Camp camp) {
        camp.visible = !camp.visible;
        return camp.visible;
    }

    public boolean viewAllCamp() {
        CampManager.printCamps(CampManager.getCampDatabase(),false);
        return true;
    }

    public boolean viewOwnCamp() {
        CampManager.printCamps(CampManager.getCampsByStaffID(this.userID), false);
        return true;
    }
}
