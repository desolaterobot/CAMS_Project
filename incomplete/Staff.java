import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Staff extends User{

    public Camp createCamp(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the name of the camp.");
        String name = sc.nextLine();

        System.out.println("Enter the start date of the camp, in the format: dd-MM-yyyy");
        String startDate = sc.nextLine();

        System.out.println("Enter the end date of the camp, in the format: dd-MM-yyyy");
        String endDate = sc.nextLine();

        System.out.println("Enter the registration deadline, in the format: dd-MM-yyyy");
        String registrationDeadline = sc.nextLine();

        System.out.println("Should this camp be open to the whole NTU (type 1) or only for your faculty? (type 0)");
        int choice = sc.nextInt();
        boolean onlyFaculty = choice == 0 ? true : false;
        
        System.out.println("Enter the location of the camp");
        String location = sc.nextLine();

        System.out.println("Enter the total number of slots ");
        int totalSlots = sc.nextInt();

        System.out.println("Enter the total number of committee slots.");
        int commiteeSlots = sc.nextInt();

        System.out.println("Enter a brief description for this camp.");
        String description = sc.nextLine();

        Camp createdCamp = new Camp(name, startDate, endDate, registrationDeadline, onlyFaculty, location, totalSlots, commiteeSlots, description, this);
        
        sc.close();

        return createdCamp;
    }
}
