
public interface CampUserInterface extends UserInterface {
	public Student authStudent(String userId, String password);
	public Staff authStaff(String UserId, String password);

}
