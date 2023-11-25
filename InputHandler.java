import java.util.Scanner;


public class InputHandler {
	public static void handleCommitteeMemberChoice(Student student, int choice) {
		// TODO Auto-generated method stub
		CampCommitteeMember commMem = new CampCommitteeMember(student.name, student.email, student.faculty, student.passHash, student.isCommitteeMember(), student.getCommitteeCamp());
		switch(choice) {
		case 11:
			commMem.replyToEnquiry();	
			break;
		case 12:
			commMem.viewOwnSuggestions();
			break;
		case 13:
			commMem.editOwnSuggestion();
			break;
		case 14:
			commMem.submitSuggestion();
			break;
		case 15:
			commMem.deleteOwnSuggestion();
			break;
		case 16:
			commMem.commGenerateReport();
			break;
		default:
			handleStudentChoice(student,choice);
		}
	}
	
	public static void handleStudentChoice(Student student, int choice) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		switch(choice) {
		case 1:
			System.out.println("Viewing Available Camps for: " + student.name);
			System.out.println();
			CampFilter.filterAndPrintCamps(CampManager.getCampsForStudents(student));
			break;
		case 2:
			System.out.print("Please Enter the Camp Name that you wish to register for: ");
			String campName = sc.nextLine();
			System.out.print("Are you registering for Camp Committee?(Y/N): ");
			Boolean committee = sc.next().toLowerCase().equals("y");
			student.registerCamp(campName, committee);
			System.out.println();
			break;
		case 3:
			System.out.println("Viewing Camps Registered for UserID: " + student.userID + "...");
			System.out.println();
			student.viewRegisteredCamps();
			break;
		case 4:
			System.out.print("Please Enter the Camp Name that you wish to withraw from: ");
			String wCampName = sc.nextLine();
			student.withdrawCamp(wCampName);
			break;
		case 5:
			System.out.print("To which camp would you like to submit your enquiry to?: ");
			String enqCamp = sc.nextLine();
			System.out.print("Please Enter your Enquiry: ");
			String msg = sc.nextLine();
			student.submitEnquiry(enqCamp, msg);
			break;
			
		case 6:
			System.out.println("Viewing Enquiries for UserID: " + student.userID);
			student.viewEnquiries();
			break;
			
		case 7:
			System.out.println("Enter the Enquiry ID which you wish to view replies for: ");
			String reEnqID = sc.nextLine();
			student.viewEnquiryReplies(reEnqID);
			System.out.println();
			break;
			
		case 8:
			System.out.print("Enter the Enquiry ID which you wish to edit: ");
			String editEnqID = sc.nextLine();
			student.editEnquiry(editEnqID);
			break;
			
		case 9:
			System.out.print("Enter the Enquiry ID which you wish to delete: ");
			String deleteEnqID = sc.nextLine();
			student.deleteEnquiry(deleteEnqID);
		case 10:
			UserManager.changePassword(student);
		}
	}


	public static void handleStaffChoice(Staff staff,int choice) {
		switch(choice) {
		case 1:
			System.out.println("Creating Camp...");
			staff.createCamp();
			break;
		case 2:
			System.out.println("Editing Camp...");
			staff.editCamp();
			break;
		case 3:
			System.out.println("Deleting Camp...");
			staff.deleteCamp();
			break;
		case 4:
			System.out.println("Toggling Visibility...");
			staff.toggleVisibility();
			break;
		case 5:
			System.out.println("View All Camps...");
			CampFilter.filterAndPrintVisibleCamps();
			break;
		case 6:
			System.out.println("View My Camps...");
			staff.viewOwnCamp();
			break;
		case 7:
			//view students in camp
			System.out.println("View student list...");
			staff.viewStudentList();
			break;
		case 8:
			System.out.println("View Enquiries...");
			staff.viewCampEnquiries();
			break;
		case 9:
			System.out.println("Reply Enquiries...");
			staff.replyToEnquiry();
			break;
		case 10:
			System.out.println("View Suggestion...");
			staff.viewSuggestions();
			break;
		case 11:
			System.out.println("Approve Suggestion...");
			staff.approveSuggestion();
			break;
		case 12:
			System.out.println("Generate Report...");
			staff.generateCampReport();
			break;
		case 13:
			System.out.println("Generate Performance Report...");
			staff.generatePerformanceReport();
			break;
		case 14:
			UserManager.changePassword(staff);
			break;
		default:
			return;
		
	}
	}
}
