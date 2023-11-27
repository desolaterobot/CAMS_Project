package DataManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Users.CampCommitteeMember;
import Users.PointsSystem;
import Users.Staff;
import Users.Student;
import Users.User;
import Users.UserManager;
import Utility.CSVReader;

/**
 * Manages the storage and retrieval of user information from CSV files.
 */
public class UserDBManager extends CSVReader {
	//DATA READING///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Finds a single student by the given userID.
     *
     * @param userID The ID of the student to find.
     * @return The Student object if found, otherwise null.
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

	/**
     * Finds a single staff member by the given userID.
     *
     * @param userID The ID of the staff member to find.
     * @return The Staff object if found, otherwise null.
     */
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

	/**
     * Finds a single user by the given userID.
     *
     * @param userID The ID of the user to find.
     * @return The User object if found, otherwise null.
     */
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

	/**
     * Loads a list of students from the CSV file.
     *
     * @return A list of Student objects.
     */
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

	 /**
     * Loads a list of staff members from the CSV file.
     *
     * @return A list of Staff objects.
     */
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
	
	/**
     * Loads a list of all users (both students and staff) from the CSV files.
     *
     * @return A list of User objects.
     */
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

	/**
     * Updates a student's information in the CSV file.
     *
     * @param student The Student object to be updated.
     */
	public static void updateStudentDB(Student student) {
		String line = String.format("%s,%s,%s,%s,%s,%s,%d", 
        removeCommas(student.getName()), removeCommas(student.getEmail()), removeCommas(student.getFaculty()), removeCommas(student.getPassword()), 
        removeCommas(Boolean.toString(student.isCommitteeMember()).toUpperCase()), removeCommas(student.getCommitteeCamp()), PointsSystem.getCurrentPoints(student));
		modifyLine("data/students.csv", student.getName(), line);
	}

    //DATA MODIFICATION//////////////////////////////////////////////////////////////////////////////////

    /**
     * Changes the password of the User. Requires a child User object, such as Student, CommitteeMember, or Staff
     *
     * @param user The User-inherited object.
     */
    public static void changePassword(User user){
		Scanner sc = new Scanner(System.in);
		String newPassword;
		do {
			System.out.println("Please enter your new password.");
			newPassword = sc.nextLine();
			if (newPassword.equals("password")) {
				System.out.println("Please change your password to something that is not the default password! -- Password change failed!");
			} else if (UserManager.hash(newPassword).equals(user.getPassword())){
					System.out.println("You're using your old password! -- Password change failed!");
			}
		} while (newPassword.equals("password")|| UserManager.hash(newPassword).equals(user.getPassword()));
 
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
