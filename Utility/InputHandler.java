package Utility;

import Users.Staff;
import Users.Student;

public class InputHandler {
	public static void handleCommitteeMemberChoice(Student student, int choice) {
		// TODO Auto-generated method stub
		switch(choice) {
		case 6:
			System.out.println("Student 6");
			//replyEnquiry(student);
			
			break;
		case 7:
			System.out.println("Student 7");
			//viewSuggestion(student);
			break;
		case 8:
			System.out.println("Student 8");
			//editSuggestion(student);
			break;
		case 9:
			System.out.println("Student 9");
			//submitSuggestion(student);
			break;
		case 10:
			System.out.println("Student 10");
			//viewCommitteeCampDetails(student);
			break;
		default:
			handleStudentChoice(student,choice);
		}
	}
	
	public static void handleStudentChoice(Student student, int choice) {
		// TODO Auto-generated method stub
		switch(choice) {
		case 1:
			System.out.println("Student 1");
			//viewAailableCamps(student);
			break;
		case 2:
			System.out.println("Student 2");
			//registerCamp(student);
			break;
		case 3:
			System.out.println("Student 3");
			//withdrawCamp(student);
			break;
		case 4:
			System.out.println("Student 4");
			//submitEnquiry(student);
			break;
		case 5:
			System.out.println("Student 5");
			//change password function;
			break;
		case 10:
			System.out.println("Student 11");
			return;
			
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
			staff.viewEnquiries();
			break;
		case 9:
			System.out.println("Reply Enquiries...");
			staff.replyEnquiries();
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
			System.out.println("change password");
			//change password function
			break;
		default:
			return;
		
	}
	}
}
