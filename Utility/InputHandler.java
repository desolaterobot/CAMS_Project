package Utility;

import java.util.Scanner;

import Users.*;

/**
 * Utility class for handling user input and directing choices based on user roles.
 */
public class InputHandler {
	
	/**
     * Handle choices for a committee member, directing to relevant methods based on the choice.
     *
     * @param student The Student object representing the committee member.
     * @param choice  The user's choice.
     */
	public static void handleCommitteeMemberChoice(Student student, int choice) {
		CampCommitteeMember commMem = new CampCommitteeMember(student.getName(), student.getEmail(), student.getFaculty(), student.getPassword(), student.isCommitteeMember(), student.getCommitteeCamp());
		switch(choice) {
		case 11:
			commMem.replyToEnquiry();	
			break;
		case 12:
			commMem.viewOwnSuggestions();
			break;
		case 13:
			commMem.editSuggestion();
			break;
		case 14:
			commMem.submitSuggestion();
			break;
		case 15:
			commMem.deleteSuggestion();
			break;
		case 16:
			commMem.commGenerateReport();
			break;
		default:
			handleStudentChoice(student,choice);
		}
	}

	/**
     * Handle choices for a regular student, directing to relevant methods based on the choice.
     *
     * @param student The Student object representing the regular student.
     * @param choice  The user's choice.
     */
	public static void handleStudentChoice(Student student, int choice) {
		Scanner sc = new Scanner(System.in);
		switch(choice) {
		case 1:
			System.out.println("Change Password for " + student.getUserId());
			UserManager.changePassword((User) student);
			break;
		case 2:
			System.out.println("Viewing Available Camps for: " + student.getName());
			System.out.println();
			student.viewCamps();
			break;
		case 3:
			student.viewCamps();
			System.out.print("Please Enter the Camp Name that you wish to register for: ");
			String campName = sc.nextLine();
			System.out.print("Are you registering for Camp Committee?(Y/N): ");
			Boolean committee = sc.next().toLowerCase().equals("y");
			student.registerCamp(campName, committee);
			System.out.println();
			break;
		case 4:
			System.out.println("Viewing Camps Registered for UserID: " + student.getUserId() + "...");
			System.out.println();
			student.viewRegisteredCamps();
			break;
		case 5:
			student.viewRegisteredCamps();
			System.out.print("Please Enter the Camp Name that you wish to withraw from: ");
			String wCampName = sc.nextLine();
			student.withdrawCamp(wCampName);
			break;
		case 6:
			System.out.print("To which camp would you like to submit your enquiry to?: ");
			String enqCamp = sc.nextLine();
			System.out.print("Please Enter your Enquiry: ");
			String msg = sc.nextLine();
			student.submitEnquiry(enqCamp, msg);
			break;	
		case 7:
			System.out.println("Viewing Enquiries for UserID: " + student.getUserId());
			student.viewEnquiries();
			break;
			
		case 8:
			student.viewEnquiries();
			System.out.println("Enter the Enquiry ID which you wish to view replies for: ");
			String reEnqID = sc.nextLine();
			student.viewEnquiry(reEnqID);
			System.out.println();
			break;
			
		case 9:
			student.viewEnquiries();
			System.out.print("Enter the Enquiry ID which you wish to edit: ");
			String editEnqID = sc.nextLine();
			student.editEnquiry(editEnqID);
			break;
			
		case 10:
			student.viewEnquiries();
			System.out.print("Enter the Enquiry ID which you wish to delete: ");
			String deleteEnqID = sc.nextLine();
			student.deleteEnquiry(deleteEnqID);
		}
	}

	/**
     * Handle choices for a staff member, directing to relevant methods based on the choice.
     *
     * @param staff  The Staff object representing the staff member.
     * @param choice The user's choice.
     */
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
			staff.viewAllCamp();
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
			staff.viewCampSuggestions();
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
			//change password function
			UserManager.changePassword(staff);
			break;
		default:
			return;
		
	}
	}
}