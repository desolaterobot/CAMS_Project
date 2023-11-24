import java.util.Scanner;

public class CampCommitteMember extends Student {
	private Camp myCamp;
	
	public CampCommitteMember(String userId, String email, String faculty, String password, Boolean isCommitteeMember,
			String committeeMemberOf) {
		super(userId, email, faculty, password, isCommitteeMember, committeeMemberOf);
		// TODO Auto-generated constructor stub
		this.myCamp = CampManager.getCampsByCommiteeID(this.userID)[0];
	}
	
	public void submitSuggestion() {
    	//get camp and suggestion
    	Scanner input = new Scanner(System.in);
    	System.out.println("Please input suggestion: ");
		String suggestion = input.nextLine();
    	SuggestionManager.addSuggestion(this.myCamp, this, suggestion);
    	PointsSystem.addPoint(this);
    	input.close();
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
    	input.nextLine();
    	System.out.println("Please input the reply to the enquiry: ");
		String reply = input.nextLine();
		enquiries[enquiryIndex].reply(this, reply);
		PointsSystem.addPoint(this); 
		input.close();
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
		input.nextLine();
		System.out.print("Please input the new suggestion: ");
		String newSuggestion = input.nextLine();
		input.nextLine();
		ownSuggestions[suggestionIndex].edit(newSuggestion);
		input.close();
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
		input.close();
	}
    
    public static void main(String[] args) {
        // Create a test committee member
        CampCommitteeMember committeeMember = new CampCommitteeMember("BRANDON","BR015@e.ntu.edu.sg","EEE","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");

        // Test submitSuggestion (working)
        //committeeMember.submitSuggestion();
        //System.out.println("Suggestion submitted.");

        // Test replyToEnquiry
        //committeeMember.replyToEnquiry();
        //System.out.println("Enquiry replied.");

        // Test viewOwnSuggestions (working)
        //System.out.println("Own Suggestions:");
        //committeeMember.viewOwnSuggestions();

        // Test editOwnSuggestion
        committeeMember.editOwnSuggestion();
        System.out.println("Suggestion edited.");
        
        //System.out.println("Own Suggestions:");
        //committeeMember.viewOwnSuggestions();

        // Test deleteOwnSuggestion (working)
        //committeeMember.deleteOwnSuggestion();
        //System.out.println("Suggestion deleted.");
        
        //System.out.println("Own Suggestions:");
        //committeeMember.viewOwnSuggestions();
    }

}
