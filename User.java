import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

enum accountType{Staff, Student}

public class User {
    public accountType status;
    public String userID; 
    public String name;
    public String email;
    public String passHash;
    public String faculty;

    public User(String name, String email, String faculty, String passHash){
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.passHash = passHash;
        this.userID = email.split("@")[0]; //userID is the characters before the '@' in the email address.
        this.status = email.split("@")[1].startsWith("e") ? accountType.Student : accountType.Staff; //if the character after the '@' is 'e', then it is a student.
    }
}
