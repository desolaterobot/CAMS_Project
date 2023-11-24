package Users;

public class PointsSystem extends CSVReader {

	public PointsSystem() {
		// TODO Auto-generated constructor stub
	}
	
	public static int getCurrentPoints(User user) {
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
        
	public static void addPoint(User user) {
		String file = "data/students.csv";
		if (getCurrentPoints(user) != -1) {
			String newPoint = Integer.toString(getCurrentPoints(user) + 1);
	        String newLine = String.format("%s,%s,%s,%s,%s,%s,%s", user.name, user.email, user.faculty, user.passHash, true, user.committeeCamp, newPoint);
	        modifyLine(file, user.name, newLine);
	        //System.out.println(user.isCommitteeMember);
		}
	}
	
	//////////////////////FOR TESTING////////////////////
	/*
	 public static void main(String[] args) {
	        // Create a test user
	        User testUser = new User("DENISE","BR015@e.ntu.edu.sg","EEE","5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8");
	
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
	 */  
		
}
