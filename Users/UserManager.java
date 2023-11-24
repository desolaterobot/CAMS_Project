package Users;
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
public //this user manager class converts CSV data to User objects
class UserManager extends CSVReader{

    public static void main(String[] a){
        System.out.println("test");
        loadUsers();
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
		User User = getUser(userId);
		
		if(User.getUserId().equals(userId) && User.getPassword().equals(hash(password))) {
			return User;
		}
		
		return null;
	}

	public Student authStudent(String userId, String password) {
		List<Student> Students = loadStudents("data/student.csv");
		
		for(Student s : Students) {
			if(s.getUserId().equals(userId) && s.getPassword().equals(hash(password))) {
			return s;
			}
		}
		return null;
	}
	
	public Staff authSaff(String userId, String password) {
		List<Staff> Staffs = loadStaff("data/staff.csv");
		
		for(Staff s : Staffs) {
			if(s.getUserId().equals(userId) && s.getPassword().equals(hash(password))) {
			return s;
			}
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

    //DATA READING///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Finds a single user by the given userID.
     *
     * @param userID The ID of the user to find.
     * @return The User object if found, otherwise null.
     */
    public static Student getStudent(String userID){
    	List<Student> Students = loadStudents("data/students.csv");
    	
        for(Student u : Students){
            if(u.userID.equals(userID)){
                return u;
            }
        }
        System.out.printf("Student %s not found in database.\n", userID);
        return null;
    }
    
    public static Staff getStaff(String userID) {
    	List<Staff> Staffs = loadStaff("data/staff.csv");
    	
    	for(Staff u : Staffs){
            if(u.userID.equals(userID)){
                return u;
            }
        }
        System.out.printf("Staff %s not found in database.\n", userID);
        return null;
    }
    
    public static User getUser(String userID) {
    	List<User> Users = loadUsers();
    	
    	for(User u : Users){
            if(u.userID.equals(userID)){
                return u;
            }
        }
        System.out.printf("Staff %s not found in database.\n", userID);
        return null;
    }
    
	public static List<Student> loadStudents(String file) {
		List<Student> users = new ArrayList<>();
		
		String[] lines = CSVReader.getLines(file);
		
		for(String line : lines) {
			String[] part = line.split(",");
			
			String userId = part[0];
			String email = part[1];
			String faculty = part[2];
			String password = part[3];
			boolean committeeMember  = Boolean.parseBoolean(part[4]);
			String whichCampCommittee = null;
			
			if(committeeMember) {
				whichCampCommittee = part[5];
				Student student = new Student(userId, email, faculty, password, committeeMember, whichCampCommittee);
				users.add(student);
			}
			else {
			Student student = new Student(userId, email, faculty, password, committeeMember, whichCampCommittee);
			users.add(student);
			}
		}
		return users;
	}
	
	public static List<Staff> loadStaff(String file) {
		List<Staff> users = new ArrayList<>();
		
		String[] lines = CSVReader.getLines(file);
		
		for(String line : lines) {
			String[] part = line.split(",");
			
			String userId = part[0];
			String email = part[1];
			String faculty = part[2];
			String password = part[3];
			
			Staff staff = new Staff(userId, email, faculty, password);
			users.add(staff);
		}
		return users;
	}
	
	
	
	public static List<User> loadUsers(){
		List<User> Users = new ArrayList<>();
		String[] files = {"data/students.csv","data/staff.csv"};
		
		for(String file : files) {
			String[] lines = CSVReader.getLines(file);
			
			for(String line: lines) {
				String[] part = line.split(",");
				
				String name = part[0];
				String email = part[1];
				String faculty = part[2];
				String password = part[3];
				
				boolean isCommitteeMember = false;
				String whichCampCommittee = null;
				
				if(file.toLowerCase().contains("student")) {
					isCommitteeMember = Boolean.parseBoolean(part[4]);
					whichCampCommittee = part[5];
					
					Student student = new Student(name, email, faculty, password, isCommitteeMember, whichCampCommittee);
					Users.add(student);
				}
				else if(file.toLowerCase().contains("staff")) {
					Staff staff = new Staff(name, email, faculty, password);
					Users.add(staff);
				}
			}
		}
		return Users;		
	}
	
	public static void updateStudentDB(Student student) {
		String line = String.format("%s,%s,%s,%s,%s,%s", 
        removeCommas(student.name), removeCommas(student.email), removeCommas(student.faculty), removeCommas(student.passHash), 
        removeCommas(Boolean.toString(student.isCommitteeMember()).toUpperCase()), removeCommas(student.getCommitteeCamp()));
		modifyLine("data/students.csv", student.name, line);
	}
	
    /**
     * Returns an array of User objects representing staff members.
     *
     * @return An array of User objects.
     */
//    public static User[] getStaff(){
//        return getUsers("data/staff.csv");
//    }

    /**
     * Returns an array of User objects representing students.
     *
     * @return An array of User objects.
     */
//    public static User[] getStudents(){
//        return getUsers("data/student.csv");
//    }

    /**
     * Returns an array of User objects representing both staff and students.
     *
     * @return An array of User objects.
     */
//    public static User[] getStaff(){
//        List<User> userList = new ArrayList<User>();
//        User[] arr1 = null;
//        arr1 = getUsers("data/staff.csv");
//        arr2 = getUsers("data/student.csv");
//        for(User u : arr1){
//            userList.add(u);
//        }
//        return userList;
//    }
    
//    public static User[] getStudents() {
//    	List<User> userList = new ArrayList<User>();
//    	
//    	User[] arr1 = null;
//        arr1 = getUsers("data/student.csv");
//        
//        for(User u : arr1){
//            userList.add(u);
//        }
//        
//        return userList
//        
//    }

    /**
     * Checks if a given username and password pair is valid.
     * Returns a User object of the specified userID if the password is correct.
     *
     * @param userID The user ID.
     * @param password The user password.
     * @return The User object if authentication is successful, otherwise null.
     */
//    public static User validateUser(String userID, String password){
//        User[] userList = getStaffStudents();
//        User foundUser = null;
//        for(User user : userList){
//            if(userID.equals(user.userID)){
//                foundUser = user;
//            }
//        }
//        if(foundUser == null){
//            System.out.println("User not found. Try again.");
//            return null;
//        }
//        if(!foundUser.passHash.equals(hash(password))){
//            System.out.println("Incorrect password. Try again");
//            return null;
//        }
//        return foundUser;
//    }

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
