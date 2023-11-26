

import java.util.*;

import Users.Staff;
import Users.Student;
import Users.User;
import Users.UserManager;
import Utility.MenuHandler;

/**
 * The main class for the Camp Application And Management System.
 */
public class CampApp {
	/** Scanner for user input. */
	Scanner sc = new Scanner(System.in);

	/**
     * The main entry point for the Camp Application And Management System.
     *
     * @param args Command-line arguments (not used).
     */
	public static void main(String[] args0) {
		CampApp app = new CampApp();
		app.run();
	}	

	/**
     * Runs the Camp Application And Management System.
     */
	public void run() {
		System.out.println(
			" ██████╗ █████╗ ███╗   ███╗███████╗\r\n" +
			"██╔════╝██╔══██╗████╗ ████║██╔════╝\r\n" +
			"██║     ███████║██╔████╔██║███████╗\r\n" +
			"██║     ██╔══██║██║╚██╔╝██║╚════██║\r\n" +
			"╚██████╗██║  ██║██║ ╚═╝ ██║███████║\r\n" +
			" ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝\r");
		System.out.println("Camp Application And Management System");
		UserManager UserManager = new UserManager();
		while(true) {
			System.out.println("1.login");
			System.out.println("2.exit");
			int choice = -1;
			
			try {
				choice = Integer.parseInt(sc.nextLine());
			} catch (Exception e) {
				//let switch case handle error
			}
			
			switch(choice) {
				case 1:
					User authenticatedUser = UserManager.login();
					if(authenticatedUser !=null) {
						handleUserMenu(authenticatedUser);
					}
					else {
						System.out.println("Incorrect Username or Password");
					}
					break;
				case 2:
					sc.close();
					return;
				default:
					System.out.println("Invalid choice. Please try again");
					break;
			}
		}
	}

	/**
     * Handles the user menu based on the user type.
     *
     * @param user The authenticated user.
     */
	private void handleUserMenu(User User) {
		if(User instanceof Student) {
			MenuHandler.showStudentMenu((Student) User);
		}
		else if(User instanceof Staff) {
			MenuHandler.showStaffMenu((Staff) User);
		}
	}	
}
