package Users;
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
class UserManager extends CSVReader{

    public static void main(String[] a){
        System.out.println("test");
        loadUsers();
    }

	
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
		User User = authUser(userId,password);
		return User;
		}

	public User authUser(String userId, String password) {
		User user = getUser(userId);

		if (user ==  null) {
			return null;
		}

		if(user.getUserId().equals(userId) && user.getPassword().equals(hash(password))) {
				if(user.getPassword().equals(hash("password"))) {
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

    //DATA READING///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Finds a single user by the given userID.
     *
     * @param userID The ID of the user to find.
     * @return The User object if found, otherwise null.
     */
    public static Student getStudent(String userID){
    	List<Student> Students = loadStudents();
    	
        for(Student u : Students){
            if(u.getUserId().equals(userID)){
                return u;
            }
        }
        System.out.printf("Student %s not found in database.\n", userID);
        return null;
    }
    
    public static Staff getStaff(String userID) {
    	List<Staff> Staffs = loadStaff();
    	
    	for(Staff u : Staffs){
            if(u.getUserId().equals(userID)){
                return u;
            }
        }
        System.out.printf("Staff %s not found in database.\n", userID);
        return null;
    }
    
    public static User getUser(String userID) {
    	List<User> Users = loadUsers();
    	
    	for(User u : Users){
            if(u.getUserId().equals(userID)){
                return u;
            }
        }
        System.out.printf("User %s not found in database.\n", userID);
        return null;
    }
    
	public static List<Student> loadStudents() {
		List<Student> users = new ArrayList<>();
		String[] files = {"data/students.csv"};
		
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
	
	public static List<Staff> loadStaff() {
		List<Staff> users = new ArrayList<>();
		String[] files = {"data/staff.csv"};
		
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
        removeCommas(student.getName()), removeCommas(student.getEmail()), removeCommas(student.getFaculty()), removeCommas(student.getPassword()), 
        removeCommas(Boolean.toString(student.isCommitteeMember()).toUpperCase()), removeCommas(student.getCommitteeCamp()));
		modifyLine("data/students.csv", student.getName(), line);
	}
	

    //DATA MODIFICATION//////////////////////////////////////////////////////////////////////////////////

    /**
     * Changes the password of the User. Requires a child User object, such as Student, CommiteeMember, or Staff
     *
     * @param user The User-inherited object.
     */
    public static void changePassword(User user){
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your new password.");
		String newPassword = sc.nextLine();
        String newPassHash = hash(newPassword);
        String file = null;
		String newLine = null;
        if(user instanceof Staff){
			System.out.println("Changing staff password...");
            file = "data/staff.csv";
			newLine = String.format("%s,%s,%s,%s", user.getName(), user.getEmail(), user.getFaculty(), newPassHash);
        }
        else if(user instanceof Student){
			file = "data/students.csv";
			System.out.println("Changing student password...");
			newLine = String.format("%s,%s,%s,%s,false,null,0", user.getName(), user.getEmail(), user.getFaculty(), newPassHash);
		}else{
			System.out.println("You did not pass in an inherited User object.");
			System.out.println("Unable to change password.");
			return;
		}
        modifyLine(file, user.getName(), newLine);
		System.out.printf("Successfully changed password to %s for %s.\n", newPassword, user.getName());
    }


}
