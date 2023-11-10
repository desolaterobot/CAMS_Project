import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//this user manager class converts CSV data to User objects
class UserManager extends CSVreader{
    
    private static User[] getUsers(String userfilepath){
        String[] staffList = null;
        try{
            staffList = getLines(userfilepath);
        }catch(IOException e){
            e.printStackTrace();
        }
        List<User> userList = new ArrayList<User>();
        User[] userarray = new User[staffList.length];
        for(String s : staffList){
            String[] stringValues = s.split(",");
            User newUser = new User(stringValues[0], stringValues[1], stringValues[2], stringValues[3]);
            userList.add(newUser);
        }
        return userList.toArray(userarray);
    }

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

    //DATA READING///////////////////////////////////////////////////////////////////////////////////////

    //returns a User list of staff
    public static User[] getStaff(){
        return getUsers("data/staff.csv");
    }

    //returns a User list of students
    public static User[] getStudents(){
        return getUsers("data/student.csv");
    }

    //returns a User list of staff and students
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

    //check if a given username and password pair is valid, returns a User object of the specified userID if passw is correct
    //returns null if wrong or user cannot be found.
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
        if(user.status == accountType.Staff){
            file = "data/staff.csv";
        }else{
            file = "data/student.csv";
        }
        String newLine = String.format("%s,%s,%s,%s", user.name, user.email, user.faculty, newPassHash);
        try{
            modifyLine(file, user.name, newLine);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}