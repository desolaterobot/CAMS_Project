package Test;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The UserManager class manages user-related operations in the Camp Application and Management System.
 * It includes methods for reading user data from CSV files, hashing passwords, and modifying user information.
 */
//this user manager class converts CSV data to User objects
class UserManager extends CSVReader{

    public static void main(String[] a){
        System.out.println("test");
    }

    /**
     * Retrieves an array of User objects from the specified CSV file.
     *
     * @param userfilepath The path of the CSV file containing user data.
     * @return An array of User objects.
     */
    private static User[] getUsers(String userfilepath){
        String[] staffList = null;
        staffList = getLines(userfilepath);
        List<User> userList = new ArrayList<User>();
        User[] userarray = new User[staffList.length];
        for(String s : staffList){
            String[] stringValues = s.split(",");
            boolean isCommitteeMember = Boolean.parseBoolean(stringValues[4]);
            User newUser = new User(stringValues[0], stringValues[1], stringValues[2], stringValues[3], isCommitteeMember ,stringValues[5]);
            userList.add(newUser);
        }
        return userList.toArray(userarray);
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

    //DATA READING///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Finds a single user by the given userID.
     *
     * @param userID The ID of the user to find.
     * @return The User object if found, otherwise null.
     */
    public static User getUser(String userID){
        for(User u : getStaffStudents()){
            if(u.userID.equals(userID)){
                return u;
            }
        }
        System.out.printf("User %s not found in database.\n", userID);
        return null;
    }

    /**
     * Returns an array of User objects representing staff members.
     *
     * @return An array of User objects.
     */
    public static User[] getStaff(){
        return getUsers("data/staff.csv");
    }

    /**
     * Returns an array of User objects representing students.
     *
     * @return An array of User objects.
     */
    public static User[] getStudents(){
        return getUsers("data/student.csv");
    }

    /**
     * Returns an array of User objects representing both staff and students.
     *
     * @return An array of User objects.
     */
    public static User[] getStaffStudents(){
        List<User> userList = new ArrayList<User>();
        User[] arr1 = null;
        User[] arr2 = null;
        arr1 = getUsers("data/staff.csv");
        arr2 = getUsers("data/student.csv");
        for(User u : arr1){
            userList.add(u);
        }
        for(User u : arr2){
            userList.add(u);
        }
        User[] combinedArray = new User[arr1.length + arr2.length];
        return userList.toArray(combinedArray);
    }

    /**
     * Checks if a given username and password pair is valid.
     * Returns a User object of the specified userID if the password is correct.
     *
     * @param userID The user ID.
     * @param password The user password.
     * @return The User object if authentication is successful, otherwise null.
     */
    public static User validateUser(String userID, String password){
        User[] userList = getStaffStudents();
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

    //DATA MODIFICATION//////////////////////////////////////////////////////////////////////////////////

    //changes the password hash entry of a user in the database
    public static void changePassword(User user, String newPassword){
        String newPassHash = hash(newPassword);
        String file;
        if(user.Status == accountType.Staff){
            file = "data/staff.csv";
        }else{
            file = "data/student.csv";
        }
        String newLine = String.format("%s,%s,%s,%s", user.name, user.email, user.faculty, newPassHash);
        modifyLine(file, user.name, newLine);
    }
}
