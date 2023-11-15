import java.util.Scanner;
import java.io.IOException;

 /**
 * The main class for the Camp Application and Management System.
 * Provides a text-based user interface for logging in, managing camps, and other functionalities.
 */
public class CampApp{

    /** The User object that is currently logged in. */
    public static User userLoggedIn = null; 

    /**
     * The main method to start the Camp Application and Management System.
     *
     * @param args The command-line arguments (not used in this application).
     * @throws Exception If an exception occurs during program execution.
     */
    public static void main(String[] args) throws Exception {
        while(true){
        Scanner sc = new Scanner(System.in);
            System.out.println(
            " ██████╗ █████╗ ███╗   ███╗███████╗\r\n" + 
            "██╔════╝██╔══██╗████╗ ████║██╔════╝\r\n" + 
            "██║     ███████║██╔████╔██║███████╗\r\n" + 
            "██║     ██╔══██║██║╚██╔╝██║╚════██║\r\n" + 
            "╚██████╗██║  ██║██║ ╚═╝ ██║███████║\r\n" + 
            " ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝\r");
            System.out.println("Camp Application And Management System");
            System.out.println("Login to get started. Type 'e' to exit.");

            while(true){
                System.out.printf("UserID: ");
                String userInput = sc.nextLine();
                if(userInput.equals("e")){
                    sc.close();
                    return;
                }
                System.out.printf("Password: ");
                String passwInput = sc.nextLine();
                userLoggedIn = UserManager.validateUser(userInput, passwInput);
                if(userLoggedIn != null) {
                    System.out.println("Login successful.");
                    System.out.printf("Welcome, %s! Logged in as: %s\n", userLoggedIn.name, userLoggedIn.status == accountType.Staff ? "Staff Member" : "Student");

                    if(userLoggedIn.Status == accountType.Staff){
                        //if staff. if student/commitee then print a seperate login menu function
                        printStaffLoginMenu();
                        sc.close();
                        }
                    else if(userLoggedIn.Status == accountType.Student){
                        printStudentLoginMenu();
                        sc.close();
                        }
                    /*else if(userLoggedIn.Status == accountType.CCM){
                        printCCMLoginMenu();
                        sc.close();
                        }*/
                    
                }
                else{
                    System.out.println("Login unsuccessful.");
                }
    }

    /**
     * Prints the staff login menu and handles staff-specific actions.
     */
    public static void printStaffLoginMenu(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("\nWhat would you like to do?");
            System.out.println("1: Change password");
            System.out.println("2: Show all visible camps");
            System.out.println("3: Show all camps created by you (bypasses visibility)");
            System.out.println("4: Create a new camp.");
            System.out.println("0: Logout");

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.printf("Type your new password: ");
                    String newPassword = sc.nextLine();
                    UserManager.changePassword(userLoggedIn, newPassword);
                    System.out.println("Password changed!");
                    continue;
                case 2:
                    CampManager.printCamps(CampManager.getCampDatabase(), true);
                    break;
                case 3:
                    CampManager.printCamps(CampManager.getCampsByStaffID(userLoggedIn.userID), false);
                    break;
                case 4:
                    CampManager.createCamp(userLoggedIn);
                    break;
                case 0:
                    System.out.println("Logging out. Goodbye!");
                    userLoggedIn = null;
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid response. Try again.");
                    continue;
            }
        }
    }

    public static void printStudentLoginMenu(){} //need to implment

    public static void printCCMLoginMenu(){} //need to implment
}
