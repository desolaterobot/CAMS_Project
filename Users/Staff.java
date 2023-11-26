package Users;
import java.util.*;

import Camp.*;
import Enquiry.*;
import Suggestion.*;
import Utility.ReportGenerator;

/**
 * Represents a staff member with various functionalities related to camp management.
 */
public class Staff extends User implements EnquiryReplyInterface, ApproveSuggestionInterface {
    /**
     * A list of camps current Staff is in-charged of. To prevent circular referencing,
     * ownCamps is only loaded when getOwnCamps is called.
     */
    private List<Camp> ownCamps = new ArrayList<>();

    
    public static void main(String[] args) {
        System.out.println("Testing...");
        Staff s = new Staff("Alexei","OURIN@ntu.edu.sg","ADM","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
    //    s.viewOwnCamp();
    //    s.viewAllCamp();
    //    s.viewEnquiries();
    //    s.replyEnquiries();
    //    s.acceptSuggestion();
    //    s.viewSuggestions();
    //    s.editCamp();
    }

    /**
     * Constructs a Staff object with the specified attributes.
     *
     * @param name      The name of the staff member.
     * @param email     The email of the staff member.
     * @param faculty   The faculty of the staff member.
     * @param passHash  The password hash of the staff member.
     */
    public Staff(String name, String email, String faculty, String passHash) {
        super(name, email, faculty, passHash);
    }

    /**
     * Returns the list of camps that current staff is in charged of. To prevent circular referencing,
     * ownCamps is only loaded when getOwnCamps is called.
     *
     * @return List<Camp> Lists of camps that current staff is in charged of.
     */
    public List<Camp> getOwnCamps() {
        if (ownCamps.isEmpty()) {
            loadOwnCamp();
        }
        return ownCamps;
    }

    /**
     * Load Camp from CampManger into current stuff's ownCamps attribute.
     */
    private void loadOwnCamp() {
        ownCamps = new ArrayList<>();
        ownCamps.addAll(List.of(CampManager.getCampsByStaffID(this.getUserId())));
    }

    /**
     * Create a New Camp and assign current staff as staff-in-charged.
     */
    public void createCamp() {
        CampManager.createCamp(this);
    }

    /**
     * Given Camp, edit the particulars of this camp.
     *
     * @param camp The camp to be edited.
     */

    public void editCamp(Camp camp) {
        CampManager.editCamp(camp);
    }
    /**
     * Display list to edit Camps.
     */
    public void editCamp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to edit Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        CampPrinter.print(campList,false);
        int choice = Integer.parseInt(sc.nextLine());
        editCamp(campList[choice-1]);
    }

    /**
     * Delete the given Camp.
     *
     * @param camp The camp to be deleted.
     */
    public void deleteCamp(Camp camp) {
        CampManager.deleteCamp(camp);
    }

    /**
     * Display List to delete Camps.
     */
    public void deleteCamp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to delete Camp");
        int campCounter = 1;
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        CampPrinter.print(campList, false);
        int choice = Integer.parseInt(sc.nextLine());
        deleteCamp(campList[choice-1]);
    }

    /**
     * View the list of Students in the current Camp.
     *
     * @param camp The camp for which the student list is to be viewed.
     */
    public void viewStudentList(Camp camp) {
        System.out.println("======Student List======");
        for (String student : camp.getAttendees()) {
            System.out.println(student);
        }
        System.out.println("======Camp Commitee=====");
        for (String campCom : camp.getCommitteeList()) {
            System.out.println(campCom);
        }
    }

    /**
     * Display list of camp to view students
     */
    public void viewStudentList() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to edit Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        CampPrinter.print(campList, false);
        int choice = Integer.parseInt(sc.nextLine());
        viewStudentList(campList[choice-1]);
    }

    /**
     * View All suggestion form the camps current staff is in charged of.
     */
    public void viewCampSuggestions() {
        for (Camp camp:
        getOwnCamps()) {
            System.out.println("Suggestions for " + camp.getCampName() + ":");
            int num = 1;
            if (ownCamps.size() == 0) {
                System.out.println("[No Suggestion]");
                continue;
            }
            for (Suggestion suggestion:
            SuggestionManager.getSuggestionsForCamp(camp)) {
                System.out.println(num + ") " +  suggestion.getCommitteeMember().getName() +": " + suggestion.getMessage() + ". Is Approved: " + suggestion.getApprovedStatus());
                num++;
            }
        }
    }

    /**
     * Accept the suggestion of the Camps that current staff is in charged of.
     */
    public void approveSuggestion() {
        System.out.println("Enter Number to accept the suggestion");
        List<Suggestion> allSuggestions = new ArrayList<>();
        int num = 1;
        for (Camp camp:
                getOwnCamps()) {
            System.out.println("Suggestions for " + camp.getCampName() + ":");
            if (ownCamps.size() == 0) {
                System.out.println("[No Suggestion]");
                continue;
            }
            for (Suggestion suggestion:
                    SuggestionManager.getSuggestionsForCamp(camp)) {
                if (!suggestion.getApprovedStatus()) {
                    System.out.println(num + ") " +  suggestion.getCommitteeMember().getName() +": " + suggestion.getMessage() + ". Is Approved: " + suggestion.getApprovedStatus());
                    allSuggestions.add(suggestion);
                    num++;
                }

            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int choice = Integer.parseInt(sc.nextLine());
        SuggestionManager.approveSuggestion(allSuggestions.get(choice-1),this);
        System.out.println("Suggestion is approved");
    }

    /**
     * Display list of camps to generate camp report.
     */
    public void generatePerformanceReport() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to edit Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        CampPrinter.print(campList, false);
        int choice = Integer.parseInt(sc.nextLine());
        generatePerformanceReport(campList[choice-1]);
    }

    /**
     * Generate Camp Report for the given camp.
     *
     * @param camp The camp for which the report is to be generated.
     */
    public void generatePerformanceReport(Camp camp) {
        ReportGenerator.staffGeneratePerformanceReport(camp);
    }

    /**
     * Generate Camp Report for the given camp.
     *
     * @param camp The camp for which the report is to be generated.
     */
    public void generateCampReport(Camp camp) {
        ReportGenerator.staffGenerateReport(camp);
    }
    
    /**
     * Display list of camps to generate performance report.
     */
    public void generateCampReport() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to generate report of that Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        CampPrinter.print(campList, false);
        int choice = Integer.parseInt(sc.nextLine());
        generateCampReport(campList[choice-1]);
    }

    /**
     * Display a list of all the Enquiries and their replies.
     */
    public void viewCampEnquiries() {
        System.out.println("Select Camp to view Enquiries");
        CampPrinter.printCamps(getOwnCamps().toArray(new Camp[1]),false);
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        for (Enquiry enquiry :
                EnquiryManager.getCampEnquiries(getOwnCamps().toArray(new Camp[1])[choice - 1])) {
            System.out.println(enquiry.getStudent().getName() + ": " +  enquiry.getMessage());
            EnquiryReply[] replies = enquiry.getReplies();
            if (replies.length != 0) {
                for (EnquiryReply reply :
                        replies) {
                    System.out.println("\u21B3 " + reply.getUser().getName() + ": " + reply.getReplyMessage());
                }
            } else {
                System.out.println("[No Replies]");
            }


        }
    }

    /**
     * Given Enquiry, reply to that enquiry.
     *
     * @param enquiry The Enquiry to reply to.
     */
    public void replyToEnquiry(Enquiry enquiry) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Reply: ");
        enquiry.reply(this,sc.nextLine());
    }

    /**
     * View all Enquiries and select the one that you want to reply.
     */
    public void replyToEnquiry() {
        List<Enquiry> allEnquiries = new ArrayList<>();
        System.out.println("Enter the number of the Enquiry you want to reply");
        int enquiryChoiceCounter = 1;
        for (Camp camp :
                getOwnCamps()) {
            System.out.println("------------------------------");
            System.out.println(camp.getCampName());
            System.out.println("------------------------------");
            Enquiry[] enquiries = EnquiryManager.getCampEnquiries(camp);
            if (enquiries.length == 0) {
                System.out.println("[No Reply]");
                continue;
            }
            for (Enquiry enquiry :
                    enquiries) {
                System.out.println(enquiryChoiceCounter + ")" + enquiry.getStudent().getName() + ": " +  enquiry.getMessage());
                allEnquiries.add(enquiry);
                enquiryChoiceCounter++;
            }

        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int enquiryChoice = Integer.parseInt(sc.nextLine());
        replyToEnquiry(allEnquiries.get(enquiryChoice-1));
    }
    /**
     * Toggle the visibility of the given Camp.
     *
     * @param camp The camp object of visibility to change.
     * @return The result of the change.
     */
    public boolean toggleVisibility(Camp camp) {
        camp.toggleVisibility();
        loadOwnCamp();
        CampDBManager.saveUpdatedCamp(camp);
        return camp.getVisible();
    }

    /**
     * View List to toggle visibility of Camp.
     */
    public void toggleVisibility() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to toggle visibility of Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        CampPrinter.print(campList,false);
        int choice = Integer.parseInt(sc.nextLine());
        while (true) {
            if (choice > campList.length || choice < 1)  {
                System.out.println("Please enter a valid number");
                choice = Integer.parseInt(sc.nextLine());
            } else {
                toggleVisibility(campList[choice-1]);
                break;
            }
        }
        
    }

    /**
     * View All Camps created in the database.
     */
    public void viewAllCamp() {
        CampPrinter.printCamps(CampManager.getAllCamps(),false);
    }

    /**
     * View Camps created by current staff.
     */
    public boolean viewOwnCamp() {
        CampPrinter.printCamps(getOwnCamps().toArray(new Camp[1]), false);
        return true;
    }
}
