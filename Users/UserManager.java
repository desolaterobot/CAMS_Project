package Users;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Utility.CSVReader;

/**
 * The UserManager class manages user-related operations in the Camp Application and Management System.
 * It includes methods for reading user data from CSV files, hashing passwords, and modifying user information.
 */
public //this user manager class converts CSV data to User objects
class UserManager{

    public static void main(String[] a){
        System.out.println("test");
        UserDBManager.loadUsers();
    }

    /**
     * Retrieves an array of User objects from the specified CSV file.
     *
     * @param userfilepath The path of the CSV file containing user data.
     * @return An array of User objects.
     */
//    private static User[] getUsers(String userfilepath){
//        String[] staffList = null;
//        staffList = getLines(userfilepath);
//        List<User> userList = new ArrayList<User>();
//        User[] userarray = new User[staffList.length];
//        for(String s : staffList){
//            String[] stringValues = s.split(",");
//            boolean isCommitteeMember = Boolean.parseBoolean(stringValues[4]);
//            User newUser = new User(stringValues[0], stringValues[1], stringValues[2], stringValues[3], isCommitteeMember ,stringValues[5]);
//            userList.add(newUser);
//        }
//        return userList.toArray(userarray);
//    }
    
	public User login() {
		//prompt for userID and pw
		//authenticate user
		//if student, showStudentMenu()
		//if staff, showStaffMenu()
		//else return to login menu for incorrect auth
		Scanner sc = new Scanner(System.in);
		
		System.out.println("User ID");
		String userId = sc.nextLine();
		
		System.out.println("Password");
		String password = sc.nextLine();
//		Student student = authStudent(userId,password);
//		if(student !=null) {
//			CampApp.showStudentMenu(student);
//			return;
//		}
//		Staff staff = authSaff(userId,password);
//		if(staff !=null) {
//			CampApp.showStaffMenu(staff);
//			return;
		User User = authUser(userId,password);
		return User;
		}

	
	public User authUser(String userId, String password) {
		User User = UserDBManager.getUser(userId);
		
		if(User.getUserId().equals(userId) && User.getPassword().equals(hash(password))) {
				while(User.getPassword().equals(hash("password"))) {
					UserDBManager.changePassword(User);
				}
			
			return User;
		}
		
		
		return null;
	}

    /**
     * Hashes the input string using the SHA-256 hash algorithm.
     *
     * @param data The input string to be hashed.
     * @return The hashed string.
     */
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
}