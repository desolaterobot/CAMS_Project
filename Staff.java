import java.util.*;


public class Staff extends User {
    /**
     * A list of camps current Staff is in-charged of. To prevent circular referencing
     * ownCamps is only loaded when getOwnCamps is called.
     */
    private List<Camp> ownCamps = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Testing...");
        Staff s = new Staff("Alexei","OURIN@ntu.edu.sg","ADM","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
//        s.viewOwnCamp();
//        s.viewAllCamp();
//        s.viewEnquiries();
        // s.replyEnquiries();
//        s.acceptSuggestion();
//        s.viewSuggestions();
        s.editCamp();
    }

    public Staff(String name, String email, String faculty, String passHash) {
        super(name, email, faculty, passHash);
    }

    /**
     * Returns the list of camps that current staff is in charged of. To prevent circular referencing
     * ownCamps is only loaded when getOwnCamps is called.
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
        ownCamps.addAll(List.of(CampManager.getCampsByStaffID(this.userID)));
    }

    /**
     * Create a New Camp and assign current staff as staff-in-charged.
     * @return
     */
    public boolean createCamp() {
        CampManager.createCamp(this);
        return true;
    }

    /**
     * Given Camp, edit the particulars of this camp.
     * @param camp
     * @return
     */
    public boolean editCamp(Camp camp) {
        CampManager.editCamp(camp);
        return true;
    }
    public boolean editCamp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to edit Camp");
        int campCounter = 1;
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        for (Camp camp : campList) {
            System.out.printf("%d) %s\n",campCounter,camp.campName);
            campCounter++;
        }
        int choice = Integer.parseInt(sc.nextLine());
        editCamp(campList[choice-1]);
        return true;
    }

    /**
     * Delete the given Camp.
     * @param camp
     * @return
     */
    public boolean deleteCamp(Camp camp) {
        CampManager.deleteCamp(camp);
        return true;
    }

    public boolean deleteCamp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to delete Camp");
        int campCounter = 1;
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        for (Camp camp : campList) {
            System.out.printf("%d) %s\n",campCounter,camp.campName);
            campCounter++;
        }
        int choice = Integer.parseInt(sc.nextLine());
        deleteCamp(campList[choice-1]);
        return true;

    }

    /**
     * View the list of Students in the current Camp.
     * @param camp
     */
    public void viewStudentList(Camp camp) {
        System.out.println("======Student List======");
        for (String student : camp.attendees) {
            System.out.println(student);
        }
        System.out.println("======Camp Commitee=====");
        for (String campCom : camp.committeeList) {
            System.out.println(campCom);
        }
    }
    public void viewStudentList() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to edit Camp");
        int campCounter = 1;
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        for (Camp camp : campList) {
            System.out.printf("%d) %s\n",campCounter,camp.campName);
            campCounter++;
        }
        int choice = Integer.parseInt(sc.nextLine());
        viewStudentList(campList[choice-1]);
    }

    /**
     * View All suggestion form the camps current staff is in charged of.
     */
    public void viewSuggestions() {
        for (Camp camp:
        getOwnCamps()) {
            System.out.println("Suggestions for " + camp.campName + ":");
            int num = 1;
            if (ownCamps.size() == 0) {
                System.out.println("[No Suggestion]");
                continue;
            }
            for (Suggestion suggestion:
            SuggestionManager.getSuggestionsForCamp(camp)) {
                System.out.println(num + ") " +  suggestion.committeeMember.name +": " + suggestion.message + ". Is Approved: " + suggestion.approved);
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
            System.out.println("Suggestions for " + camp.campName + ":");
            if (ownCamps.size() == 0) {
                System.out.println("[No Suggestion]");
                continue;
            }
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
        System.out.println("Suggestion is approved");
    }

    public Boolean generateCampReport(Camp camp) {
        ReportGenerator.staffGenerateReport(camp);
        return true;
    }

    public void generatePerformanceReport() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to edit Camp");
        int campCounter = 1;
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        for (Camp camp : campList) {
            System.out.printf("%d) %s\n",campCounter,camp.campName);
            campCounter++;
        }
        int choice = Integer.parseInt(sc.nextLine());
        generatePerformanceReport(campList[choice-1]);
    }

    public void generatePerformanceReport(Camp camp) {
        ReportGenerator.staffGeneratePerformanceReport(camp);
    }

    public Boolean generateCampReport() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to generate report of that Camp");
        int campCounter = 1;
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        for (Camp camp : campList) {
            System.out.printf("%d) %s\n",campCounter,camp.campName);
            campCounter++;
        }
        int choice = Integer.parseInt(sc.nextLine());
        generateCampReport(campList[choice-1]);
        return true;
    }

    /**
     * Display a list of all the Enquiries and their replies.
     * @return
     */
    public boolean viewEnquiries() {
        System.out.println("Select Camp to view Enquiries");
        CampManager.printCamps(getOwnCamps().toArray(new Camp[1]),false);
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int choice = Integer.parseInt(sc.nextLine());

        for (Enquiry enquiry :
                EnquiryManager.getCampEnquiries(getOwnCamps().toArray(new Camp[1])[choice - 1])) {
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

    /**
     * Given Enquiry reply to that enquiry.
     * @param enquiry Enquiry that is replying to.
     * @return
     */
    public boolean replyEnquiry(Enquiry enquiry) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Reply: ");
        enquiry.reply(this,sc.nextLine());
        return true;
    }

    /**
     * View all Enquiries and select the one that you want to reply.
     * @return
     */
    public boolean replyEnquiries() {
        List<Enquiry> allEnquiries = new ArrayList<>();
        System.out.println("Enter the number of the Enquiry you want to reply");
        int enquiryChoiceCounter = 1;
        for (Camp camp :
                getOwnCamps()) {
            System.out.println("------------------------------");
            System.out.println(camp.campName);
            System.out.println("------------------------------");
            Enquiry[] enquiries = EnquiryManager.getCampEnquiries(camp);
            if (enquiries.length == 0) {
                System.out.println("[No Reply]");
                continue;
            }
            for (Enquiry enquiry :
                    enquiries) {
                System.out.println(enquiryChoiceCounter + ")" + enquiry.student.name + ": " +  enquiry.message);
                allEnquiries.add(enquiry);
                enquiryChoiceCounter++;
            }

        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Choice: ");
        int enquiryChoice = Integer.parseInt(sc.nextLine());
        replyEnquiry(allEnquiries.get(enquiryChoice-1));
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

    public boolean toggleVisibility() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number to toggle visibility of Camp");
        int campCounter = 1;
        Camp[] campList = getOwnCamps().toArray(new Camp[1]);
        for (Camp camp : campList) {
            System.out.printf("%d) %s\n",campCounter,camp.campName);
        }
        int choice = Integer.parseInt(sc.nextLine());
        toggleVisibility(campList[choice-1]);
        return true;
    }

    /**
     * View All Camps created in the database.
     * @return
     */
    public boolean viewAllCamp() {
        CampManager.printCamps(CampManager.getCampDatabase(),false);
        return true;
    }

    /**
     * View Camps created by current staff.
     * @return
     */
    public boolean viewOwnCamp() {
        CampManager.printCamps(getOwnCamps().toArray(new Camp[1]), false);
        return true;
    }
}
