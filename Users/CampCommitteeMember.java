package Users;
import java.util.Scanner;

import Camp.*;
import Suggestion.*;
import Enquiry.*;
import Utility.ReportGenerator;

/**
 * Represents a committee member extending the Student class with additional committee-related functionalities.
 */
public class CampCommitteeMember extends Student implements EnquiryReplyInterface, SuggestionInterface{
	private Camp myCamp;
	private Suggestion[] ownSuggestions;
	private int noOfPendingSuggestions;

	/**
     * Constructor for the CampCommitteeMember class.
     *
     * @param userId           The user ID of the committee member.
     * @param email            The email of the committee member.
     * @param faculty          The faculty of the committee member.
     * @param password         The password of the committee member.
     * @param isCommitteeMember A boolean indicating if the user is a committee member.
     * @param committeeMemberOf The camp the committee member is part of.
     */
	public CampCommitteeMember(String userId, String email, String faculty, String password, Boolean isCommitteeMember,
			String committeeMemberOf) {
		super(userId, email, faculty, password, isCommitteeMember, committeeMemberOf);
		// TODO Auto-generated constructor stub
		this.myCamp = CampManager.getCampsByCommiteeID(this.getUserId())[0];
		this.ownSuggestions = SuggestionManager.getSuggestionsByUser(this);
		this.noOfPendingSuggestions = 0;
	}

	/**
     * Submits a suggestion for the camp committee member.
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
     * Views the enquiries for the camp committee member.
     */
	public void viewCampEnquiries() {
		Enquiry[] enquiries = EnquiryManager.getCampEnquiries(myCamp);
		if (enquiries.length > 0) {
			System.out.printf("Enquiries of %s: \n", myCamp.getCampName());
			for (int i = 0; i < enquiries.length; i++) {
	    		System.out.println("[" + i + "]: " + enquiries[i].getMessage());
	    	}
		} else {
			System.out.println("There is no enquiry!");
		} 	
	}

	/**
     * Replies to an enquiry for the camp committee member.
     */
    public void replyToEnquiry() {
    	Scanner input = new Scanner(System.in);
    	Enquiry[] enquiries = EnquiryManager.getCampEnquiries(myCamp);
    	this.viewCampEnquiries();
    	if (enquiries.length > 0) {	
        	System.out.print("Please select the enquiry to reply to: ");
    		int enquiryIndex = input.nextInt();
        	input.nextLine();
        	if (!enquiries[enquiryIndex].getStudent().getName().equals(this.getName())) {
        		System.out.print("Please input the reply to the enquiry: ");
        		String reply = input.nextLine();
        		EnquiryManager.replyEnquiry(this, reply,enquiries[enquiryIndex]);
        		PointsSystem.addPoint(this);
        		System.out.println("Enquiry replied.\n");
        	} else {
        		System.out.println("You cannot reply to your own enquiry!");
        	}
    	} 
    }

	/**
     * Views the committee member's own suggestions.
     */
    public void viewOwnSuggestions() {
    	noOfPendingSuggestions = 0;
    	if (ownSuggestions.length > 0) {
    		System.out.println("Your suggestion(s): ");
    		for (int i = 0; i < ownSuggestions.length; i++) {
        		if (ownSuggestions[i].getApprovedStatus() == false) {
        			System.out.println("[" + i + "]: " + ownSuggestions[i].getMessage());
        			noOfPendingSuggestions++;
        		}
        	}	
    		System.out.print("\n");
    	} else {
    		System.out.println("You have made no suggestions!\n");
    	}
    }

	/**
     * Edits the committee member's own suggestion.
     */
    public void editSuggestion() {
		Scanner input = new Scanner(System.in);
		viewOwnSuggestions();
    	if (noOfPendingSuggestions > 0) {
    		System.out.print("Please select the suggestion to edit: ");
    		int suggestionIndex = input.nextInt();
    		input.nextLine();
    		System.out.print("Please input the new suggestion: ");
    		String newSuggestion = input.nextLine();
    		SuggestionManager.editSuggestion(ownSuggestions[suggestionIndex], newSuggestion);
    		System.out.println("Suggestion edited.\n");
    	} else {
    		System.out.println("No pending suggestion available for editing.\n");
    	}
		//input.close();
    }

	/**
     * Deletes the committee member's own suggestion.
     */
    public void deleteSuggestion() {
    	Scanner input = new Scanner(System.in);
		viewOwnSuggestions();
    	if (noOfPendingSuggestions > 0) {
			System.out.print("Please select the suggestion to delete: ");
			int suggestionIndex = input.nextInt();
			SuggestionManager.deleteSuggestion(ownSuggestions[suggestionIndex]);
			System.out.println("Suggestion deleted.\n");
    	} else {
    		System.out.println("No suggestion available to delete.\n");
    	}
    	
		//input.close();
	}

	/**
     * Withdraws the committee member from the camp.
     */
    public void withdrawCamp() {
    	System.out.println("You cannot withdraw from \"" + myCamp.getCampName() + "\" as you are a Committee Member.\n");
    }

	/**
     * Generates a report for the committee member.
     */
    public void commGenerateReport() {
    	ReportGenerator.commMemGenerateReport(myCamp);
    }

	/**
     * Main method for testing the committee member functionalities.
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

        //committeeMember.viewCampEnquiries(committeeMember.myCamp);
        
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
