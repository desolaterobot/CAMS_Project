import java.util.Scanner;

/**
 * Represents a Camp Committee Member, extending the functionality of a Student.
 */
public class CampCommitteeMember extends Student {
	private Camp myCamp;
	private Suggestion[] ownSuggestions;
	private int noOfPendingSuggestions;

	/**
     * Constructs a CampCommitteeMember with the specified parameters.
     *
     * @param userId             The user ID of the committee member.
     * @param email              The email of the committee member.
     * @param faculty            The faculty of the committee member.
     * @param password           The hashed password of the committee member.
     * @param isCommitteeMember  Indicates whether the user is a committee member.
     * @param committeeMemberOf  The camp for which the user is a committee member.
     */
	public CampCommitteeMember(String userId, String email, String faculty, String password, Boolean isCommitteeMember,
			String committeeMemberOf) {
		super(userId, email, faculty, password, isCommitteeMember, committeeMemberOf);
		// TODO Auto-generated constructor stub
		this.myCamp = CampManager.getCampsByCommiteeID(this.userID)[0];
		this.ownSuggestions = SuggestionManager.getSuggestionsByUser(this);
		this.noOfPendingSuggestions = 0;
	}

	/**
     * Allows the committee member to submit a suggestion for the camp.
     */
	public void submitSuggestion() {
    	//get camp and suggestion
    	Scanner input = new Scanner(System.in);
    	System.out.println("Please input suggestion: ");
		String suggestion = input.nextLine();
    	SuggestionManager.addSuggestion(this.myCamp, this, suggestion);
    	PointsSystem.addPoint(this);
    	System.out.println("Suggestion submitted.\n");
    	//input.close();
    }
	
	/**
     * Allows the committee member to reply to an enquiry related to the camp.
     */
    public void replyToEnquiry() {
    	Scanner input = new Scanner(System.in);
    	Enquiry[] enquiries = EnquiryManager.getCampEnquiries(myCamp);
    	for (int i = 0; i < enquiries.length; i++) {
    		System.out.println("[" + i + "]: " + enquiries[i].message);
    	}
    	
    	System.out.print("Please select the enquiry to reply to: ");
		int enquiryIndex = input.nextInt();
    	input.nextLine();
    	System.out.print("Please input the reply to the enquiry: ");
		String reply = input.nextLine();
		enquiries[enquiryIndex].reply(this, reply);
		PointsSystem.addPoint(this);
		System.out.println("Enquiry replied.\n");
		//input.close();
    }

	/**
     * Displays the pending suggestions submitted by the committee member.
     */
    public void viewOwnSuggestions() {
    	noOfPendingSuggestions = 0;
    	if (ownSuggestions.length > 0) {
    		System.out.println("Your suggestion(s): ");
    		for (int i = 0; i < ownSuggestions.length; i++) {
        		if (ownSuggestions[i].approved == false) {
        			System.out.println("[" + i + "]: " + ownSuggestions[i].message);
        			noOfPendingSuggestions++;
        		}
        	}	
    		System.out.print("\n");
    	} else {
    		System.out.println("You have made no suggestions!\n");
    	}
    }

	/**
     * Allows the committee member to edit their own suggestion.
     */
    public void editOwnSuggestion() {
		Scanner input = new Scanner(System.in);
		viewOwnSuggestions();
    	if (noOfPendingSuggestions > 0) {
    		System.out.print("Please select the suggestion to edit: ");
    		int suggestionIndex = input.nextInt();
    		input.nextLine();
    		System.out.print("Please input the new suggestion: ");
    		String newSuggestion = input.nextLine();
    		ownSuggestions[suggestionIndex].edit(newSuggestion);
    		System.out.println("Suggestion edited.\n");
    	} else {
    		System.out.println("No pending suggestion available for editing.\n");
    	}
		//input.close();
    }

	/**
     * Allows the committee member to delete their own suggestion.
     */
    public void deleteOwnSuggestion() {
    	Scanner input = new Scanner(System.in);
		viewOwnSuggestions();
    	if (noOfPendingSuggestions > 0) {
			System.out.print("Please select the suggestion to delete: ");
			int suggestionIndex = input.nextInt();
			ownSuggestions[suggestionIndex].delete();
			System.out.println("Suggestion deleted.\n");
    	} else {
    		System.out.println("No suggestion available to delete.\n");
    	}
    	
		//input.close();
	}

	/**
     * Prevents the committee member from withdrawing from the camp.
     */
    public void withdrawCamp() {
    	System.out.println("You cannot withdraw from \"" + myCamp.campName + "\" as you are a Committee Member.\n");
    }

	/**
     * Generates a report for the committee member related to the camp.
     */
    public void commGenerateReport() {
    	ReportGenerator.commMemGenerateReport(myCamp);
    }

	/**
     * Main method for testing the functionality of the CampCommitteeMember class.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a test committee member
    	CampCommitteeMember committeeMember = new CampCommitteeMember("BRANDON","BR015@e.ntu.edu.sg","EEE","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", true, "yet another camp");

        // Test submitSuggestion (working)
        //committeeMember.submitSuggestion();
        //System.out.println("Suggestion submitted.");

        // Test replyToEnquiry (working)
        //committeeMember.replyToEnquiry();
        //System.out.println(PointsSystem.getCurrentPoints(committeeMember));
        //System.out.println("Enquiry replied.");
        
        // Test viewOwnSuggestions (working)
        //System.out.println("Own Suggestions:");
        //committeeMember.viewOwnSuggestions();

        // Test editOwnSuggestion (working)
        //committeeMember.editOwnSuggestion();
        //System.out.println("Suggestion edited.");

        // Test deleteOwnSuggestion (working)
        //committeeMember.deleteOwnSuggestion();
        //System.out.println("Suggestion deleted.");
        
        //committeeMember.commGenerateReport();
        //committeeMember.withdrawCamp();
        
    }

}
