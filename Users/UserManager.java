package Users;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Utility.CSVReader;
import DataManager.UserDBManager;

/**
 * The UserManager class manages user-related operations in the Camp Application and Management System.
 * It includes methods for reading user data from CSV files, hashing passwords, and modifying user information.
 */
public class UserManager extends UserDBManager{

	/**
     * Main method for testing purposes.
     *
     * @param a Command-line arguments.
     */
    public static void main(String[] a){
        System.out.println("test");
        loadUsers();
    }

	/**
 * Prompts the user to enter a user ID and password for authentication.
 * Calls the authUser method to authenticate the user.
 *
 * @return The authenticated User object if successful, or null if authentication fails.
 */
	public User login() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("User ID");
		String userId = sc.nextLine();
		
		System.out.println("Password");
		String password = sc.nextLine();
		User User = authUser(userId,password);
		return User;
		}

	/**
     * Authenticates a user based on the provided user ID and password.
     * Returns the authenticated User object if successful, or null if authentication fails.
     *
     * @param userId   The user ID for authentication.
     * @param password The password for authentication.
     * @return The authenticated User object, or null if authentication fails.
     */
	public User authUser(String userId, String password) {
		User user = getUser(userId);
		
		if (user ==  null) {
			return null;
		}

		if(user.getUserId().equals(userId) && user.getPassword().equals(hash(password))) {
				if (user.getPassword().equals(hash("password"))) {
					UserManager.changePassword(user);
				}
			return user;
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
