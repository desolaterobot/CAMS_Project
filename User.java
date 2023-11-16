import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

enum accountType{Staff, Student, Committee}

/**
 * The User class represents a user in the Camp Application and Management System.
 * It contains information about the user's account type, user ID, name, email, password hash, and faculty.
 */
public class User {
    /** The account type of the user (Staff or Student or Committee). */
    public accountType status;

    /** The user ID generated from the email address (characters before '@'). */
    public String userID; 

    /** The name of the user. */
    public String name;

    /** The email address of the user. */
    public String email;

    /** The hashed password of the user. */
    public String passHash;

    /** The faculty to which the user belongs. */
    public String faculty;

    // Determine the status based on the domain
    public String domain;
    /**
     * Constructs a new User with the given parameters.
     *
     * @param name     The name of the user.
     * @param email    The email address of the user.
     * @param faculty  The faculty to which the user belongs.
     * @param passHash The hashed password of the user.
     */
    public User(String name, String email, String faculty, String passHash){
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.passHash = passHash;
        this.userID = email.split("@")[0]; //userID is the characters before the '@' in the email address.
        //this.status = email.split("@")[1].startsWith("e") ? accountType.Student : accountType.Staff; //if the character after the '@' is 'e', then it is a student.

        // Extract the part after '@' and convert it to lowercase for case-insensitive comparison
        this.domain = email.split("@")[1].toLowerCase();
    
        // Determine the status based on the domain
        if (domain.startsWith("e")) {
            this.status = accountType.Student; // if the character after the '@' is 'e', then it is a student.
        } else if (domain.startsWith("c")) {
            this.status = accountType.Committee; // if the character after the '@' is 'c', then it is a committee member.
        } else {
            this.status = accountType.Staff; // default to Staff if it doesn't match 'e' or 'c'
        }
    }
}
