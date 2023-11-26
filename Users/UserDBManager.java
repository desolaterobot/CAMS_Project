package Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Users.CampCommitteeMember;
import Users.PointsSystem;
import Users.Staff;
import Users.Student;
import Users.User;
import Utility.CSVReader;

public class UserDBManager extends CSVReader {
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
        System.out.printf("Staff %s not found in database.\n", userID);
        return null;
    }
    
	public static List<Student> loadStudents() {
		List<Student> users = new ArrayList<>();
		String file = "data/students.csv";
		
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
		String file = "data/staff.csv";
		
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
				
				if(file.toLowerCase().contains("student")) {
					boolean isCommitteeMember = Boolean.parseBoolean(part[4]);
					String whichCampCommittee = part[5];
					
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

    /**
     * Changes the password of the User. Requires a child User object, such as Student, CommiteeMember, or Staff
     *
     * @param user The User-inherited object.
     */

    //changes the password hash entry of a user in the database
    public static void changePassword(User user){
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your new password.");
		String newPassword = sc.nextLine();
        String newPassHash = UserManager.hash(newPassword);
        String file = null;
		String newLine = null;
        if(user instanceof Staff){
			System.out.println("Changing staff password...");
            file = "data/staff.csv";
			newLine = String.format("%s,%s,%s,%s", user.getName(), user.getEmail(), user.getFaculty(), newPassHash);
        }else if(user instanceof Student){
			file = "data/students.csv";
			System.out.println("Changing student password...");
			Student s = (Student) user;
			newLine = String.format("%s,%s,%s,%s,%b,%s,0", s.getName(), s.getEmail(), s.getFaculty(), newPassHash,s.isCommitteeMember(),s.getCommitteeCamp());
		}else{
			System.out.println("You did not pass in an inherited User object.");
			System.out.println("Unable to change password.");
			return;
		}
        modifyLine(file, user.getName(), newLine);
		System.out.printf("Successfully changed password to %s for %s.\n", newPassword, user.getName());
    }
}
