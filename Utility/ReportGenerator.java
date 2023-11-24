package Utility;
import java.util.Scanner;

import Camp.Camp;
import Camp.CampManager;
import Users.CSVReader;
import Users.PointsSystem;
import Users.User;
import Users.UserManager;

public class ReportGenerator extends CSVReader{

    //example usage
    public static void main(String[] args) {
    	commMemGenerateReport(CampManager.getCamp("hyper camp"));
        //generateCampReport(CampManager.getCamp("stupid camp"));
    	//for(String s : CampManager.getCamp("hyper camp").withdrawals) {
    		//System.out.println(s);
    	//}
    }

    /**
     * Generates a .txt file into the reports folder regarding a selected camp.
     * The report shows all the details of the selected camp.
     *
     * @param camp The selected camp object for report generation.
     */
    public static void generateCampReport(Camp camp){
        //firstly, specify the filepath: for this one it is "reports/{camp_name}Report.txt"
        //please use the reports folder to store reports for neatness.
        String filepath = String.format("reports/%sReport.txt", camp.getCampName().replace(" ", "_"));
        //writeLine() used here is to clear any existing lines on the file, if it exists.
        //if it doesn't exist, it will auto-generate the file.
        writeLine(filepath, "", false);
        //use the addLine function to add a string line onto the text: format is addLine(String filepath, String line)
        //note that the newline character is automatically added at the end of each string, so no need "\n"
        //String.format() is used inside addLine() just to format the line before adding into the CSV file.
        addLine(filepath, String.format("%s Performance Report", camp.getCampName()));
        addLine(filepath, String.format("Staff-In-Charge: %s", UserManager.getUser(camp.getStaffInCharge()).name));
        addLine(filepath, String.format("Description: %s", camp.getDescription()));
        addLine(filepath, String.format("Location: %s", camp.getLocation()));
        addLine(filepath, String.format("Faculty: %s", camp.getFaculty()));
        addLine(filepath, String.format("Start Date: %s, End Date: %s, Registration Deadline: %s", DateStr.dateToStr(camp.getStartDate()), DateStr.dateToStr(camp.getEndDate()),DateStr.dateToStr(camp.getRegistrationDeadline())));
        //if i want to put a space, must put \n like this:
        addLine(filepath, String.format("\nComittee Members: (%d/%d)", camp.getCommitteeList().length, camp.getCommitteeSlots()));
        int x = 1;
        for(String s : camp.getCommitteeList()){
            User u = UserManager.getUser(s);
            addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
            x++;
        }
        x = 1;
        addLine(filepath, String.format("\nAttendees: (%d/%d)", camp.getAttendees().length, camp.getTotalSlots()));
        for(String s : camp.getAttendees()){
            User u = UserManager.getUser(s);
            addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
            x++;
        }
        System.out.printf("Report sucessfully generated with the name %s in the reports folder.\n", filepath.split("/")[1]);
    }
    
    public static void commMemGenerateReport(Camp camp) {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Please type your filename: ");
    	String userFileName = input.nextLine();
    	String filepath = String.format("reports/%s_%sReport.txt", camp.getCampName().replace(" ", "_"), userFileName.replace(" ", "_"));
    	writeLine(filepath, "", false);
    	
    	addLine(filepath, String.format("%s Performance Report", camp.getCampName()));
        addLine(filepath, String.format("Staff-In-Charge: %s", UserManager.getUser(camp.getStaffInCharge()).name));
        addLine(filepath, String.format("Description: %s", camp.getDescription()));
        addLine(filepath, String.format("Location: %s", camp.getLocation()));
        addLine(filepath, String.format("Faculty: %s", camp.getFaculty()));
        addLine(filepath, String.format("Start Date: %s, End Date: %s, Registration Deadline: %s", DateStr.dateToStr(camp.getStartDate()), DateStr.dateToStr(camp.getEndDate()),DateStr.dateToStr(camp.getRegistrationDeadline())));
        
    	
    	System.out.println("Would you like to include attendees? Y/N: ");
    	String choice = input.nextLine();
    	int x = 1;
    	if (choice.contains("Y")||choice.contains("y")) {
    		addLine(filepath, String.format("\nAttendees: (%d/%d)", camp.getAttendees().length, camp.getTotalSlots()));
    		for(String s : camp.getAttendees()){
                User u = UserManager.getUser(s);
                addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
                x++;
            }
    	}
    	
    	System.out.println("Would you like to include camp committees? Y/N: ");
    	choice = input.nextLine();
    	
    	if (choice.contains("Y")||choice.contains("y")) {
    		addLine(filepath, String.format("\nComittee Members: (%d/%d)", camp.getCommitteeList(), camp.getCommitteeSlots()));
    		x = 1;
            for(String s : camp.getCommitteeList()){
                User u = UserManager.getUser(s);
                addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
                x++;
            }
    	}
    	
    	System.out.println("Would you like to include withdrawals? Y/N: ");
    	choice = input.nextLine();
    	if (choice.contains("Y")||choice.contains("y")) {
    		addLine(filepath, String.format("\nWithdrawals: (%d)", camp.getWithdrawals().length));
    		x = 1;
            for(String s : camp.getWithdrawals()){
                User u = UserManager.getUser(s);
                addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
                x++;
            }
    	}
        System.out.printf("Report sucessfully generated with the name %s in the reports folder.\n", filepath.split("/")[1]);
        input.close();
    }

    public static void staffGenerateReport(Camp camp) {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Please type your filename: ");
    	String userFileName = input.nextLine();
    	String filepath = String.format("reports/%s_%sReport.txt", camp.getCampName().replace(" ", "_"), userFileName.replace(" ", "_"));
    	writeLine(filepath, "", false);
    	
    	addLine(filepath, String.format("%s Performance Report", camp.getCampName()));
        addLine(filepath, String.format("Staff-In-Charge: %s", UserManager.getUser(camp.getStaffInCharge()).name));
        addLine(filepath, String.format("Description: %s", camp.getDescription()));
        addLine(filepath, String.format("Location: %s", camp.getLocation()));
        addLine(filepath, String.format("Faculty: %s", camp.getFaculty()));
        addLine(filepath, String.format("Start Date: %s, End Date: %s, Registration Deadline: %s", DateStr.dateToStr(camp.getStartDate()), DateStr.dateToStr(camp.getEndDate()),DateStr.dateToStr(camp.getRegistrationDeadline())));
        
    	
    	System.out.println("Would you like to include attendees? Y/N: ");
    	String choice = input.nextLine();
    	int x = 1;
    	if (choice.contains("Y")||choice.contains("y")) {
    		addLine(filepath, String.format("\nAttendees: (%d/%d)", camp.getAttendees().length, camp.getTotalSlots()));
    		for(String s : camp.getAttendees()){
                User u = UserManager.getUser(s);
                addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
                x++;
            }
    	}
    	
    	System.out.println("Would you like to include camp committees? Y/N: ");
    	choice = input.nextLine();
    	
    	if (choice.contains("Y")||choice.contains("y")) {
    		addLine(filepath, String.format("\nComittee Members: (%d/%d)", camp.getCommitteeList(), camp.getCommitteeSlots()));
    		x = 1;
            for(String s : camp.getCommitteeList()){
                User u = UserManager.getUser(s);
                addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
                x++;
            }
    	}
    	
    	System.out.println("Would you like to include withdrawals? Y/N: ");
    	choice = input.nextLine();
    	if (choice.contains("Y")||choice.contains("y")) {
    		addLine(filepath, String.format("\nWithdrawals: (%d)", camp.getWithdrawals().length));
    		x = 1;
            for(String s : camp.getWithdrawals()){
                User u = UserManager.getUser(s);
                addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
                x++;
            }
    	}
        System.out.printf("Report sucessfully generated with the name %s in the reports folder.\n", filepath.split("/")[1]);
        input.close();
    }
    
    public static void staffGeneratePerformanceReport(Camp camp) {
    	Scanner input = new Scanner(System.in);
    	System.out.println("Please type your filename: ");
    	String userFileName = input.nextLine();
    	String filepath = String.format("reports/%s_%sReport.txt", camp.getCampName().replace(" ", "_"), userFileName.replace(" ", "_"));
    	writeLine(filepath, "", false);
    	
    	addLine(filepath, String.format("%s Performance Report", camp.getCampName()));
        addLine(filepath, String.format("Staff-In-Charge: %s", UserManager.getUser(camp.getStaffInCharge()).name));
        addLine(filepath, String.format("Description: %s", camp.getDescription()));
        addLine(filepath, String.format("Location: %s", camp.getLocation()));
        addLine(filepath, String.format("Faculty: %s", camp.getFaculty()));
        addLine(filepath, String.format("Start Date: %s, End Date: %s, Registration Deadline: %s", DateStr.dateToStr(camp.getStartDate()), DateStr.dateToStr(camp.getEndDate()),DateStr.dateToStr(camp.getRegistrationDeadline())));
        	
        int x = 1;
        for(String s : camp.getCommitteeList()){
            User u = UserManager.getUser(s);
            int userPoint = PointsSystem.getCurrentPoints(u);
            addLine(filepath, String.format("%d. %s - %d pts", x, u.name, userPoint));
            x++;
        }

        System.out.printf("Performance report sucessfully generated with the name %s in the reports folder.\n", filepath.split("/")[1]);
    }
}