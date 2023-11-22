public class ReportGenerator extends CSVReader{

    //example usage
    public static void main(String[] args) {
        generateCampReport(CampManager.getCamp("hyper camp"));
        generateCampReport(CampManager.getCamp("stupid camp"));
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
        String filepath = String.format("reports/%sReport.txt", camp.campName.replace(" ", "_"));
        //writeLine() used here is to clear any existing lines on the file, if it exists.
        //if it doesn't exist, it will auto-generate the file.
        writeLine(filepath, "", false);
        //use the addLine function to add a string line onto the text: format is addLine(String filepath, String line)
        //note that the newline character is automatically added at the end of each string, so no need "\n"
        //String.format() is used inside addLine() just to format the line before adding into the CSV file.
        addLine(filepath, String.format("%s Performance Report", camp.campName));
        addLine(filepath, String.format("Staff-In-Charge: %s", UserManager.getUser(camp.staffInCharge).name));
        addLine(filepath, String.format("Description: %s", camp.description));
        addLine(filepath, String.format("Location: %s", camp.location));
        addLine(filepath, String.format("Faculty: %s", camp.faculty));
        addLine(filepath, String.format("Start Date: %s, End Date: %s, Registration Deadline: %s", DateStr.dateToStr(camp.startDate), DateStr.dateToStr(camp.endDate),DateStr.dateToStr(camp.registrationDeadline)));
        //if i want to put a space, must put \n like this:
        addLine(filepath, String.format("\nComittee Members: (%d/%d)", camp.committeeList.length, camp.committeeSlots));
        int x = 1;
        for(String s : camp.committeeList){
            User u = UserManager.getUser(s);
            addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
            x++;
        }
        x = 1;
        addLine(filepath, String.format("\nAttendees: (%d/%d)", camp.attendees.length, camp.totalSlots));
        for(String s : camp.attendees){
            User u = UserManager.getUser(s);
            addLine(filepath, String.format("%d. %s - %s", x, u.name, u.email));
            x++;
        }
        System.out.printf("Report sucessfully generated with the name %s in the reports folder.\n", filepath.split("/")[1]);
    }
}