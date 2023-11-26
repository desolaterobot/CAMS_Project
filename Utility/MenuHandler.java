package Utility;
import java.util.*;

import Users.Staff;
import Users.Student;

/**
 * The MenuHandler class provides methods for displaying and handling user menus based on their roles (Student, Staff).
 */
public class MenuHandler {

	/**
     * Main method for testing and demonstrating the functionality of the MenuHandler class.
     *
     * @param args Command-line arguments (not used).
     */
	public static void main(String[] args) { 
		String a = "hello";
		String b = "f@e";
		String c = "df";
		String d = "l";
		
		Staff staff = new Staff(a,b,c,d);
		System.out.println("here");
		showStaffMenu(staff);
	}

	 /**
     * Prints the menu options for a Student.
     *
     * @param student The Student user.
     */
	public static void printStudentMenu(Student student) {
			System.out.println("0. Logout");
			System.out.println("1. Change Password");
			System.out.println("2. View Camps");
			System.out.println("3. Register Camp");
			System.out.println("4. View Registered Camps");
			System.out.println("5. Withdraw Camp");
			System.out.println("6. Submit Enquiry");
			System.out.println("7. View Enquiries");
			System.out.println("8. View Enquiry Replies");
			System.out.println("9. Edit Enquiry");
			System.out.println("10. Delete Enquiry");
			if(student.isCommitteeMember()) {
				System.out.println("11. Reply Enquiry");
				System.out.println("12. View Suggestions");
				System.out.println("13. Edit Suggestions");
				System.out.println("14. Submit Suggestions");
				System.out.println("15. Delete Suggestions");
				System.out.println("16. View Camp Details");	
			}
		}

	/**
     * Prints the menu options for a Staff member.
     */
	public static void printStaffMenu() {
			System.out.println("0. Logout");
			System.out.println("1. Create Camp");
			System.out.println("2. Edit Camp");
			System.out.println("3. Delete Camp");
			System.out.println("4. Toggle Visibility");
			System.out.println("5. View All Camps");
			System.out.println("6. View My Camps");
			System.out.println("7. View Student List");
			System.out.println("8. View Enquieries");
			System.out.println("9. Reply Enquiries");
			System.out.println("10. View Suggestions");
			System.out.println("11. Approve Suggestion");
			System.out.println("12. Generate Camp Report");
			System.out.println("13. Generate Performance Report");
			System.out.println("14. Change Password");
	}

	/**
     * Displays the Student menu and handles user input based on their choices.
     *
     * @param student The Student user.
     */
	public static void showStudentMenu(Student student) {

		Scanner sc = new Scanner(System.in);
		
		while(true) {
			printStudentMenu(student);
			
			int choice = sc.nextInt();
			
			sc.nextLine();
			
			if(choice==0) return;
			
			if(student.isCommitteeMember()) {
				if (choice != 1) {
					InputHandler.handleCommitteeMemberChoice(student,choice);
				} else {
					InputHandler.handleCommitteeMemberChoice(student,choice);
					System.out.println("Please login again...");
					return;
				}
			}
			
			else {
				if (choice != 1) {
					InputHandler.handleStudentChoice(student,choice);
				} else {
					InputHandler.handleStudentChoice(student,choice);
					System.out.println("Please login again...");
					return;
				}
			}
				
				
			}
		}
	
	/**
     * Displays the Staff menu and handles user input based on their choices.
     *
     * @param staff The Staff user.
     */
	public static void showStaffMenu(Staff staff) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			printStaffMenu();
			
			int choice = sc.nextInt();
			
			sc.nextLine();
			
			if(choice==0) return;
			
			if (choice != 1) {
				InputHandler.handleStaffChoice(staff,choice);
			} else {
				InputHandler.handleStaffChoice(staff,choice);
				System.out.println("Please login again...");
				return;
			}
			}
		}	
	}

