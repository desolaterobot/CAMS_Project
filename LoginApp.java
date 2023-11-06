import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginApp{

    //hashes the input string and returns the hash using SHA-256 hash algorithm
    public static String sha256(String data){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

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

        User userLoggedIn = null; //this variable stores the User that is currently logged in.

        while(true){
            System.out.printf("UserID: ");
            String userInput = sc.nextLine();
            System.out.printf("Password: ");
            String passwInput = sc.nextLine();

            User[] userList = UserManager.getStaffStudents();
            for(User user : userList){
                if(userInput.equals(user.userID)){
                    userLoggedIn = user;
                    break;
                }
            }

            if(userLoggedIn == null){
                System.out.println("User not found. Try again.");
                continue;
            }

            if(!userLoggedIn.passHash.equals(sha256(passwInput))){
                System.out.println("Incorrect password. Try again");
                continue;
            }
            break;
        }

        System.out.println("Login successful.");
        System.out.printf("Welcome, %s! Logged in as: %s\n", userLoggedIn.name, userLoggedIn.status == accountType.Staff ? "Staff Member" : "Student");
        
        //more functions to be added here depending on whether the user is a Student or Staff but i think this is ok for now.
        while(true){
            System.out.println("What would you like to do?");
            System.out.println("1: Change password");
            System.out.println("0: Exit Program"); 

            int choice = sc.nextInt();
            sc.nextLine(); //to remove the newline character from the carraige
            switch (choice) {
                case 1:
                    System.out.printf("Type your new password: ");
                    String newPassword = sc.nextLine();
                    UserManager.changePassword(userLoggedIn, sha256(newPassword));
                    System.out.println("Password changed!");
                    continue;
                case 0:
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid response. Try again.");
                    continue;
            }
        }
    }  
}