package Users;
import java.util.*;

import Camp.*;
import Enquiry.*;
import Suggestion.*;
import Utility.ReportGenerator;
import DataManager.CampDBManager;

/**
 * The class represents a staff member involved in managing camps. It extends the {@code User} class
 * and implements interfaces for handling enquiries and approving suggestions.
 * The class includes methods for managing camps, handling enquiries, approving suggestions, and generating reports.
 */
public class Staff extends User implements EnquiryReplyInterface, ApproveSuggestionInterface {
    /**
     * A list of camps that the current staff is in charge of. To prevent circular referencing,
     * ownCamps is only loaded when getOwnCamps is called.
     */
    private List<Camp> ownCamps = new ArrayList<>();

    /**
     * Main method for testing and demonstrating the functionality of the Staff class.
     *
     * @param args Command-line arguments (not used).
     */
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
        // s.deleteCamp();
        s.createCamp();
    }

    /**
     * Constructs a new Staff object with the given parameters.
     *
     * @param name     The name of the staff member.
     * @param email    The email of the staff member.
     * @param faculty  The faculty of the staff member.
     * @param passHash The password hash of the staff member.
     */
    public Staff(String name, String email, String faculty, String passHash) {
        super(name, email, faculty, passHash);
    }

    /**
     * Returns the list of camps that current staff is in charged of. To prevent circular referencing
     * ownCamps is only loaded when getOwnCamps is called.
     * @return ownCamps Lists of camps that current staff is in charged of.
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
        // ownCamps.addAll(List.of());
        for (Camp camp : CampManager.getCampsByStaffID(this.getUserId())) {
            ownCamps.add(camp);
        }
    }

    /**
     * Create a New Camp and assign current staff as staff-in-charged.
     */
    public void createCamp() {
        CampManager.createCamp(this);
        loadOwnCamp();
    }

    /**
     * Given a Camp, edit the particulars of this camp.
     *
     * @param camp The camp to edit.
     */
    public void editCamp(Camp camp) {
        CampManager.editCamp(camp);
        loadOwnCamp();
    }
    /**
     * Display list to edit Camps.
     */
    public void editCamp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to edit Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[0]);

        CampPrinter.print(campList,false);

        while (true) {
            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                //Let if else below handle
            }
             
            if (choice > campList.length || choice < 1)  {
                System.out.println("Please enter a valid number");
            } else {
                editCamp(campList[choice-1]);
                System.out.printf("%s has been successfully edited.\n",campList[choice-1].getCampName());
                break;
            }
        }
    }

    /**
     * Delete the given Camp.
     *
     * @param camp The camp to delete.
     */
    public void deleteCamp(Camp camp) {
        CampManager.deleteCamp(camp);
        loadOwnCamp();
    }

    /**
     * Display List to delete Camps.
     */
    public void deleteCamp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to delete Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[0]);

        CampPrinter.print(campList, false);

        while (true) {
            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                //Let if else below handle
            }
             
            if (choice > campList.length || choice < 1)  {
                System.out.println("Please enter a valid number");
            } else {
                String tempCampName = campList[choice-1].getCampName();
                deleteCamp(campList[choice-1]);
                System.out.printf("%s has been successfully deleted.\n",tempCampName);
                break;
            }
        }
    }

    /**
     * View the list of students in the current Camp.
     *
     * @param camp The camp to view the student list.
     */
    public void viewStudentList(Camp camp) {
        System.out.println("======Student List======");
        for (String student : camp.getAttendees()) {
            System.out.println(UserManager.getUser(student).getName());
            
        }
        System.out.println("======Camp Commitee=====");
        for (String campCom : camp.getCommitteeList()) {
            System.out.println(UserManager.getUser(campCom).getName());
        }
    }

    /**
     * Display list of camp to view students
     */
    public void viewStudentList() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to View Student List");
        Camp[] campList = getOwnCamps().toArray(new Camp[0]);
        
        CampPrinter.print(campList, false);

        while (true) {
            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                //Let if else below handle
            }
             
            if (choice > campList.length || choice < 1)  {
                System.out.println("Please enter a valid number");
            } else {
                viewStudentList(campList[choice-1]);
                break;
            }
        }
    }

    /**
     * View All suggestion form the camps current staff is in charged of.
     */
    public void viewCampSuggestions() {
        getOwnCamps();
        if (ownCamps.size() == 0) {
                System.out.println("[No Suggestions]");
                return;
            }
        for (Camp camp:
        getOwnCamps()) {
            System.out.println("Suggestions for " + camp.getCampName() + ":");
            int num = 1;
            
            Suggestion[] suggestionList = SuggestionManager.getSuggestionsForCamp(camp);
            if (suggestionList.length == 0) {
                System.out.println("[No Suggestion]");
                continue;
            }

            for (Suggestion suggestion:
            suggestionList) {
                System.out.println(num + ") " +  suggestion.getCommitteeMember().getName() +": " + suggestion.getMessage() + ". Is Approved: " + suggestion.getApprovedStatus());
                num++;
            }
        }
    }

    /**
     * Accept the suggestion of the Camps that current staff is in charged of.
     */
    public void approveSuggestion() {
        getOwnCamps();
        if (ownCamps.size() == 0) {
                System.out.println("[No Suggestions]");
                return;
            }
        System.out.println("Enter Number to accept the suggestion");
        List<Suggestion> allSuggestions = new ArrayList<>();
        int num = 1;
        for (Camp camp:
                getOwnCamps()) {
            System.out.println("Suggestions for " + camp.getCampName() + ":");

            Suggestion[] suggestionList = SuggestionManager.getSuggestionsForCamp(camp);
            if (suggestionList.length == 0) {
                System.out.println("[No Suggestion]");
                continue;
            }
            for (Suggestion suggestion:
                    suggestionList) {
                if (!suggestion.getApprovedStatus()) {
                    System.out.println(num + ") " +  suggestion.getCommitteeMember().getName() +": " + suggestion.getMessage() + ". Is Approved: " + suggestion.getApprovedStatus());
                    allSuggestions.add(suggestion);
                    num++;
                }

            }
        }
        if (allSuggestions.size() == 0) {
            System.out.println("Nothing to approve");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int choice = Integer.parseInt(sc.nextLine());
        SuggestionManager.approveSuggestion(allSuggestions.get(choice-1),this);
        System.out.println("Suggestion is approved");
    }
    
    /**
     * Display List of camps to generate performance report
     */
    
    public void generatePerformanceReport() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to genereate Performance Report for that camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[0]);
        CampPrinter.print(campList, false);
        
        while (true) {
            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                //Let if else below handle
            }
             
            if (choice > campList.length || choice < 1)  {
                System.out.println("Please enter a valid number");
            } else {
                generatePerformanceReport(campList[choice-1]);
                break;
            }
        }
    }

    /**
 * Generate a performance report for the specified camp.
 *
 * @param camp The camp for which to generate the performance report.
 */
    public void generatePerformanceReport(Camp camp) {
        ReportGenerator.staffGeneratePerformanceReport(camp);
    }

    /**
 * Generate a report for the specified camp.
 *
 * @param camp The camp for which to generate the report.
 */
    public void generateCampReport(Camp camp) {
        ReportGenerator.staffGenerateReport(camp);
    }
    
    /**
     * Display list of camps to generate camp report
     */
    public void generateCampReport() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to generate report for that Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[0]);
        CampPrinter.print(campList, false);
        int choice = Integer.parseInt(sc.nextLine());
        generateCampReport(campList[choice-1]);
    }

    /**
     * Display a list of all the Enquiries and their replies.
     */
    public void viewCampEnquiries() {
        System.out.println("Select Camp to view Enquiries");
        CampPrinter.printCamps(getOwnCamps().toArray(new Camp[0]),false);
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        for (Enquiry enquiry :
                EnquiryManager.getCampEnquiries(getOwnCamps().toArray(new Camp[0])[choice - 1])) {
            System.out.println(enquiry.getStudent().getName() + ": " +  enquiry.getMessage());
            EnquiryReply[] replies = EnquiryManager.getReplies(enquiry);
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
     * Given Enquiry reply to that enquiry.
     * @param enquiry Enquiry that is replying to.
     */
    public void replyToEnquiry(Enquiry enquiry) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Reply: ");
        EnquiryManager.replyEnquiry(this,sc.nextLine(),enquiry);
    }

    /**
     * View all Enquiries and select the one that you want to reply.
     */
    public void replyToEnquiry() {
        getOwnCamps();
        if (ownCamps.size() == 0) {
                System.out.println("[No Suggestions]");
                return;
            }
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
        if (allEnquiries.size() == 0) {
            System.out.println("Nothing to reply to.");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int enquiryChoice = Integer.parseInt(sc.nextLine());
        replyToEnquiry(allEnquiries.get(enquiryChoice-1));
    }
    /**
     * Toggle the visibility of given Camp.
     * @param camp camp object of visibility to change
     * @return result of the change
     */
    public boolean toggleVisibility(Camp camp) {
        camp.toggleVisibility();
        CampDBManager.saveUpdatedCamp(camp);
        loadOwnCamp();
        return camp.getVisible();
    }

    /**
     * View List to toggle visibility of Camp.
     */
    public void toggleVisibility() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to toggle visibility of Camp");
        Camp[] campList = getOwnCamps().toArray(new Camp[0]);
        CampPrinter.print(campList,false);
        
        while (true) {
            int choice = -1;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                // TODO: handle exception
            }
             
            if (choice > campList.length || choice < 1)  {
                System.out.println("Please enter a valid number");
            } else {
                System.out.printf("%s visibility has been toggled to: %b\n",campList[choice-1].getCampName(),toggleVisibility(campList[choice-1]));
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
     * 
     * @return true indicating successful display of camp information.
     */
    public boolean viewOwnCamp() {
        CampPrinter.printCamps(getOwnCamps().toArray(new Camp[0]), false);
        return true;
    }
}
