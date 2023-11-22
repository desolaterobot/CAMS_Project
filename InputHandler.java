import java.util.Scanner;

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
			//viewegisteredCamp(student);
			break;
		case 10:
			System.out.println("Student 10");
			return;
		}
	}


	public static void handleStaffChoice(Staff Staff,int choice) {
		switch(choice) {
		case 1:
			System.out.println("Staff 1");
			//CreateCamp(staff);
			break;
		case 2:
			System.out.println("Staff 2");
			//EditCamp(staff);
			break;
		case 3:
			System.out.println("Staff 3");
			//DeleteCamp(staff);
			break;
		case 4:
			System.out.println("Staff 4");
			//ToggleVisibility(staff);
			break;
		case 5:
			System.out.println("Staff 5");
			//ViewAllCamps(staff);
			break;
		case 6:
			System.out.println("Staff 6");
			//ViewMyCamps(staff);
			break;
		case 7:
			System.out.println("Staff 7");
			//ViewEnquiries(staff);
			break;
		case 8:
			System.out.println("Staff 8");
			//ReplyEnquiry(staff);
			break;
		case 9:
			System.out.println("Staff 9");
			//ViewSuggestions(staff);
			break;
		case 10:
			System.out.println("Staff 10");
			//ApproveSuggestion(staff);
			break;
		case 11:
			System.out.println("Staff 11");
			//GenerateReport(staff);
			break;
		case 12:
			return;
		
	}
	}
}
