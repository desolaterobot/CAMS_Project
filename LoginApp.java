import java.util.Scanner;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginApp{

    //hashes the input string and returns the hash using SHA-256 hash algorithm
    public static String hash(String data){
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

    //check if a given username and password pair is valid.
    public static User validateUser(String userID, String password) throws IOException{
        User[] userList = UserManager.getStaffStudents();
        User foundUser = null;
        for(User user : userList){
            if(userID.equals(user.userID)){
                foundUser = user;
            }
        }
        if(foundUser == null){
            System.out.println("User not found. Try again.");
            return null;
        }
        if(!foundUser.passHash.equals(hash(password))){
            System.out.println("Incorrect password. Try again");
            return null;
        }
        return foundUser;
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

        User userLoggedIn = null; //this variable stores the User object that is currently logged in.

        while(true){
            System.out.printf("UserID: ");
            String userInput = sc.nextLine();
            System.out.printf("Password: ");
            String passwInput = sc.nextLine();
            userLoggedIn = validateUser(userInput, passwInput);
            if(userLoggedIn != null) break;
        }

        System.out.println("Login successful.");
        System.out.printf("Welcome, %s! Logged in as: %s\n", userLoggedIn.name, userLoggedIn.status == accountType.Staff ? "Staff Member" : "Student");

        //more functions to be added here depending on whether the user is a Student, Staff, or Camp Commitee Member but i think this is ok for now.
        while(true){
            System.out.println("What would you like to do?");
            System.out.println("1: Change password");
            System.out.println("0: Logout"); 

            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.printf("Type your new password: ");
                    String newPassword = sc.nextLine();
                    UserManager.changePasswordHash(userLoggedIn, hash(newPassword));
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