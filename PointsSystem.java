import java.util.ArrayList;
import java.util.List;


/**
 * The PointsSystem class provides methods for managing and retrieving points for users in the system.
 */
public class PointsSystem extends CSVReader {

	/**
     * Default constructor for the PointsSystem class.
     */
	public PointsSystem() {
		
	}

	/**
     * Retrieves the current points for a given user.
     *
     * @param user The user for whom to retrieve points.
     * @return The current points for the user, or -1 if the user is not found or does not have points.
     */
	public static int getCurrentPoints(Student user) {
		String file = "data/students.csv";
        
		String[] lines = CSVReader.getLinesWithHeader(file);
        int index = 0;
        int indexToModify = -1;
        for(String line : lines){
            if(line.startsWith(user.name, 0)){
                indexToModify = index;
            }
            index++;
        }
        if(indexToModify == -1){
            System.out.println("Cannot find item.");
            return 0;
        }
        String[] part = lines[indexToModify].split(",");
        
        int points = 0; 
        if(Boolean.parseBoolean(part[4]) == true) {
			if(part.length > 5) {
				//System.out.println(user.name);
				points = Integer.parseInt(part[6]);
				//System.out.println(Boolean.parseBoolean(part[4]));
				return points;
			}
        }
		return -1;
	}

	/**
     * Adds a point to the current points of the user.
     *
     * @param user The user for whom to add a point.
     */
	public static void addPoint(Student user) {
		String file = "data/students.csv";
		if (getCurrentPoints(user) != -1) {
			String newPoint = Integer.toString(getCurrentPoints(user) + 1);
	        String newLine = String.format("%s,%s,%s,%s,%s,%s,%s", user.name, user.email, user.faculty, user.passHash, true, user.getCommitteeCamp(), newPoint);
	        modifyLine(file, user.name, newLine);
	        //System.out.println(user.isCommitteeMember);
		}
	}
	
	//////////////////////FOR TESTING////////////////////
	/**
     * Main method for testing and demonstrating the functionality of the PointsSystem class.
     *
     * @param args Command-line arguments (not used).
     */
	 public static void main(String[] args) {
	        // Create a test user
	        Student testUser = new Student("BRANDON","BR015@e.ntu.edu.sg","EEE","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8",true, "yet anthoner camp");
	
	        // Test getCurrentPoints
	        int currentPoints = PointsSystem.getCurrentPoints(testUser);
	        System.out.println("Current Points: " + currentPoints);
	
	        // Test addPoint
	        PointsSystem.addPoint(testUser);
	        System.out.println("Points added.");
	
	        // Verify the updated points
	        int updatedPoints = PointsSystem.getCurrentPoints(testUser);
	        System.out.println("Updated Points: " + updatedPoints);
	    }		
}
