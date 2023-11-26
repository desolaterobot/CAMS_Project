package Utility;

import java.util.Scanner;

import Camp.*;
import Users.*;

/**
 * The InputHandler class provides methods for handling user choices based on their roles in the Camp Application and Management System.
 * It includes methods for processing choices of students, camp committee members, and staff members.
 */
public class InputHandler {
	/**
     * Handles choices specific to camp committee members.
     *
     * @param student The camp committee member.
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
		case 17:
			System.out.printf("Your current point(s): %d\n", PointsSystem.getCurrentPoints(commMem));
		case 18:
			commMem.viewStudentList();
		default:
			System.out.println("Please input a value between 0-18!");
			handleStudentChoice(student,choice);
		}
	}

	/**
     * Handles choices specific to students.
     *
     * @param student The student.
     * @param choice  The user's choice.
     */
	public static void handleStudentChoice(Student student, int choice) {
		Scanner sc = new Scanner(System.in);
		switch(choice) {
		case 1:
			System.out.println("Change Password for " + student.getUserId());
			if (student.isCommitteeMember()) {
				CampCommitteeMember commMem = new CampCommitteeMember(student.getName(), student.getEmail(), student.getFaculty(), student.getPassword(), student.isCommitteeMember(), student.getCommitteeCamp());
				UserManager.changePassword(commMem);
			} else {
				UserManager.changePassword((User) student);
			}
			break;
		case 2:
			System.out.println("Viewing Available Camps for: " + student.getName());
			CampFilter.printFilterChoices();
			int filterChoice = Integer.parseInt(sc.nextLine());
			CampFilter.filterAndPrintCamps(CampManager.getCampsForStudents(student), filterChoice); //dimas edited this!!!
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
			student.viewCamps();
			System.out.print("To which camp would you like to submit your enquiry to? ");
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
			break;
		default:
			System.out.println("Please input a value between 0-10!");
			break;
		}
	}

	/**
     * Handles choices specific to staff members.
     *
     * @param staff  The staff member.
     * @param choice The user's choice.
     */
	public static void handleStaffChoice(Staff staff,int choice) {
		Scanner sc = new Scanner(System.in);
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
			System.out.println("Camp deleted!");
			break;
		case 4:
			System.out.println("Toggling Visibility...");
			staff.toggleVisibility();
			break;
		case 5:
			System.out.println("View All Camps...");
			CampFilter.printFilterChoices();
			int filterChoice = Integer.parseInt(sc.nextLine());
			CampFilter.filterAndPrintCamps(filterChoice); //dimas edited this!!!!
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
			System.out.println("Please input a value between 0-14!");
			return;
		
	}
	}
}
