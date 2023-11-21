import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Staff extends User {

    public static void main(String[] args) {
        System.out.println("Testing...");
        Staff s = new Staff("Alexei","OURIN@ntu.edu.sg","ADM","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
        s.viewOwnCamp();
        s.viewAllCamp();
        s.viewEnquiries();
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

//    public void viewSuggestion(Suggestion suggestion) {
//
//    }

//    public void acceptSuggestion(Suggestion suggestion) {
//
//    }

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
            System.out.println("\t" + enquiry.getReplies());
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
