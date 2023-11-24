import java.util.Scanner;

public class CampCommitteeMember extends Student {
	private Camp myCamp;
	
	public CampCommitteeMember(String userId, String email, String faculty, String password, Boolean isCommitteeMember,
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
    	System.out.println("Suggestion submitted.");
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
		System.out.println("Enquiry replied.");
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
		ownSuggestions[suggestionIndex].edit(newSuggestion);
		System.out.println("Suggestion edited.");
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
		System.out.println("Suggestion deleted.");
		input.close();
	}
    
    public void withdrawCamp() {
    	System.out.println("You cannot withdraw from \"" + myCamp.campName + "\" as you are a Committee Member.");
    }
    
    public void commGenerateReport() {
    	ReportGenerator.commMemGenerateReport(myCamp);
    }
    
    public static void main(String[] args) {
        // Create a test committee member
    	CampCommitteeMember committeeMember = new CampCommitteeMember("BRANDON","BR015@e.ntu.edu.sg","EEE","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", true, "yet another camp");

        // Test submitSuggestion (working)
        //committeeMember.submitSuggestion();
        //System.out.println("Suggestion submitted.");

        // Test replyToEnquiry (not working)
        committeeMember.replyToEnquiry();
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
