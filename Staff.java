import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Staff extends User {

    }
    public Staff(String name, String email, String faculty, String passHash) {
        super(name, email, faculty, passHash);

    }

    public boolean createCamp() {
        CampManager.createCamp(this);
        return true;
    }

    public boolean editCamp(Camp camp) {
        CampManager.editCamp(camp);
        return true;
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
