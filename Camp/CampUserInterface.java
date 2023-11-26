package Camp;
import Users.UserInterface;
import Users.Student;
import Users.Staff;

/**
 * Interface for handling user authentication within the camp management system.
 */
public interface CampUserInterface extends UserInterface {

	/**
     * Authenticate a student based on user ID and password.
     *
     * @param userId   The user ID of the student.
     * @param password The password of the student.
     * @return A Student object if authentication is successful; otherwise, null.
     */
	public Student authStudent(String userId, String password);

	/**
     * Authenticate a staff member based on user ID and password.
     *
     * @param userId   The user ID of the staff member.
     * @param password The password of the staff member.
     * @return A Staff object if authentication is successful; otherwise, null.
     */
	public Staff authStaff(String userId, String password);

}
