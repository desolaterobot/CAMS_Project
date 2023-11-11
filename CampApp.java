import java.util.Scanner;
import java.io.IOException;

public class CampApp{

    public static User userLoggedIn = null; //this variable stores the User object that is currently logged in.

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println(
        " ██████╗ █████╗ ███╗   ███╗███████╗\r\n" + 
        "██╔════╝██╔══██╗████╗ ████║██╔════╝\r\n" + 
        "██║     ███████║██╔████╔██║███████╗\r\n" + 
        "██║     ██╔══██║██║╚██╔╝██║╚════██║\r\n" + 
        "╚██████╗██║  ██║██║ ╚═╝ ██║███████║\r\n" + 
        " ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝\r");
        System.out.println("Camp Application And Management System");
        System.out.println("Login to get started.");

        while(true){
            System.out.printf("UserID: ");
            String userInput = sc.nextLine();
            System.out.printf("Password: ");
            String passwInput = sc.nextLine();
            userLoggedIn = UserManager.validateUser(userInput, passwInput);
            if(userLoggedIn != null) break;
        }

        System.out.println("Login successful.");
        System.out.printf("Welcome, %s! Logged in as: %s\n", userLoggedIn.name, userLoggedIn.status == accountType.Staff ? "Staff Member" : "Student");
        
        printStaffLoginMenu();
        sc.close();
    }

    public static void printStaffLoginMenu(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("What would you like to do?");
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
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid response. Try again.");
                    continue;
            }
        }
    }
}