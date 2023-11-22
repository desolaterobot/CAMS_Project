package Camp;

import java.util.Scanner;

public class CampCommitteeMem extends User {
	private Camp myCamp;
	
	public CampCommitteeMem(String name, String email, String faculty, String passHash) {
		super(name, email, faculty, passHash);
		// TODO Auto-generated constructor stub
		this.myCamp = CampManager.getCampsByCommiteeID(this.userID)[0];
        super.isCommitteeMember = true;
        //add the comm mem to commlist in camp class???
	}
	
	public void submitSuggestion() {
    	//get camp and suggestion
    	Scanner input = new Scanner(System.in);
    	System.out.println("Please input suggestion: ");
		String suggestion = input.nextLine();
    	SuggestionManager.addSuggestion(this.myCamp, this, suggestion);
    	//pointsSystem.addPoint()????????
    }
	
	// Method to reply to enquiries
    public void replyToEnquiry() {
    	Scanner input = new Scanner(System.in);
    	Enquiry[] enquiries = EnquiryManager.getCampEnquiries(myCamp);
    	for (int i = 0; i < enquiries.length; i++) {
    		System.out.println("[" + i + "]: " + enquiries[i].message);
    	}
    	
    	System.out.println("Please select the enquiry to reply to: ");
		int enquiryIndex = input.nextInt();
    	
    	System.out.println("Please input the reply to the enquiry: ");
		String reply = input.nextLine();
		enquiries[enquiryIndex].reply(this, reply);
		//pointsSystem.addPoint(); ???????????
    }
    
    public void viewOwnSuggestions() {
    	Suggestion[] ownSuggestions = SuggestionManager.getSuggestionsByUser(this);
    	for (int i = 0; i < ownSuggestions.length; i++) {
    		if (ownSuggestions[i].approved == false) {
    			System.out.println("[" + i + "]: " + ownSuggestions[i].message);
    		}
    	}
    }
    
	public void editOwnSuggestion() {
		Scanner input = new Scanner(System.in);
		Suggestion[] ownSuggestions = SuggestionManager.getSuggestionsByUser(this);
    	for (int i = 0; i < ownSuggestions.length; i++) {
    		if (ownSuggestions[i].approved == false) {
    			System.out.println("[" + i + "]: " + ownSuggestions[i].message);
    		}	
    	}
    	System.out.println("Please select the suggestion to edit: ");
		int suggestionIndex = input.nextInt();
		System.out.println("Please input the new suggestion: ");
		String newSuggestion = input.nextLine();
		ownSuggestions[suggestionIndex].edit(newSuggestion);
    }
	
    public void deleteOwnSuggestion() {
    	Scanner input = new Scanner(System.in);
		Suggestion[] ownSuggestions = SuggestionManager.getSuggestionsByUser(this);
    	for (int i = 0; i < ownSuggestions.length; i++) {
    		if (ownSuggestions[i].approved == false) {
    			System.out.println("[" + i + "]: " + ownSuggestions[i].message);
    		}	
    	}
    	System.out.println("Please select the suggestion to delete: ");
		int suggestionIndex = input.nextInt();
		ownSuggestions[suggestionIndex].delete();
	}
    
    //prevent quitting
}

