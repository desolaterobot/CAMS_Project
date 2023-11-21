package Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Staff extends User {

    private static Date strToDate(String str){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try{
            date = format.parse(str);
            return date;
        }catch(ParseException e){
            e.printStackTrace();
        }
        return date;
    }
    public Staff(String name, String email, String faculty, String passHash) {
        super(name, email, faculty, passHash);

    }

    public boolean createCamp() {
        CampManager.createCamp(this);
        return true;
    }

    public boolean editCamp(Camp camp) {
        /**Things staff can edit:
         Start Date
         End Date
         Registration Deadline
         Faculty only visibility
         Location
         Description
         Total Slots
         Committee Slots
         **/
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter number of what you want to edit:");
            System.out.println("1. Start Date");
            System.out.println("2. End Date");
            System.out.println("3. Registration Deadline");
            System.out.println("4. Toggle Faculty only visibility");
            System.out.println("5. Location");
            System.out.println("6. Total Slots");
            System.out.println("7. Camp Committee Slots (Max 10)");
            System.out.println("8. Description");
            System.out.println("9. View Current Changes");
            System.out.println("10. Confirm Changes");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Enter the start date of the camp, in the format: dd/mm/yyyy");
                    Date startDate = strToDate(sc.nextLine());
                    camp.startDate = startDate;
                    break;
                case 2:
                    System.out.println("Enter the end date of the camp, in the format: dd/mm/yyyy");
                    Date endDate = strToDate(sc.nextLine());
                    camp.endDate = endDate;
                    break;
                case 3:
                    System.out.println("Enter the registration deadline, in the format: dd/mm/yyyy");
                    Date registrationDeadline = strToDate(sc.nextLine());
                    camp.registrationDeadline = registrationDeadline;
                    break;
                case 4:

                    if (camp.onlyFaculty) camp.onlyFaculty = false;
                    else camp.onlyFaculty = true;
                    System.out.println("Toggling to " + camp.onlyFaculty);
                    break;
                case 5:
                    System.out.println("Enter the location of the camp");
                    String location = sc.nextLine();
                    camp.location = location;
                    break;
                case 6:
                    System.out.println("Enter the total number of attendee slots.");
                    int totalSlots = Integer.parseInt(sc.nextLine());
                    camp.totalSlots = totalSlots;
                    break;
                case 7:
                    System.out.println("Enter the total number of committee slots. (Max 10)");
                    int committeeSlots = Integer.parseInt(sc.nextLine());
                    if (committeeSlots > 10 || committeeSlots < 0) {
                        break;
                    } else {
                        camp.committeeSlots = committeeSlots;
                    }
                    break;
                case 8:
                    System.out.println("Enter a brief description for this camp.");
                    String description = sc.nextLine();
                    camp.description = description;
                    break;
                case 9:
                    System.out.println("Camp Name: " + camp.campName);
                    System.out.println("Start Date: "+ camp.startDate);
                    System.out.println("End Date: " + camp.endDate);
                    System.out.println("Registration Deadline: " + camp.registrationDeadline);
                    System.out.println("Faculty only visibility: " + camp.onlyFaculty);
                    System.out.println("Location: " + camp.location);
                    System.out.println("Description: " + camp.description);
                    System.out.println("Total Slots: " + camp.totalSlots);
                    System.out.println("Committee Slots: " + camp.committeeSlots );
                    break;
                case 10:
                    CampManager.editCamp(camp);
                    return true;
                default:
                    System.out.println("Invalid input.");

            }
        }



    }

    public boolean deleteCamp(Camp camp) {
        CampManager.deleteCamp(camp);
        return true;
    }

    public void viewStudentList(Camp camp) {
        System.out.println("======Student List======");
        for (String student : camp.attendees) {
            System.out.println(student);
        }
        System.out.println("========================");
    }

//    public void viewSuggestion(Suggestion suggestion) {
//
//    }

//    public void acceptSuggestion(Suggestion suggestion) {
//
//    }

//    public String generateCampReport(Camp camp) {
//
//    }

    public  boolean toggleVisibility(Camp camp) {
        if (camp.visible) camp.visible = false;
        else camp.visible = true;
        return camp.visible;
    }
}
