import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

enum accountType{Staff, Student}

/**
 * The User class represents a user in the Camp Application and Management System.
 * It contains information about the user's account type, user ID, name, email, password hash, and faculty.
 */
public class User {
    /** The account type of the user (Staff or Student or Committee). */
    public accountType Status;

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

    /** Indicates whether the user is a camp committee member. */
    public boolean isCommitteeMember;

    /** The camp for which the user is a committee member (if applicable). */
    public String committeeCamp;
    
    /**
     * Constructs a new User with the given parameters.
     *
     * @param name            The name of the user.
     * @param email           The email address of the user.
     * @param faculty         The faculty to which the user belongs.
     * @param passHash        The hashed password of the user.
     * @param status          The account type of the user (Staff or Student).
     * @param isCommitteeMember Indicates whether the user is a camp committee member.
     * @param committeeCamp   The camp for which the user is a committee member (if applicable).
     */
     
    public User(String name, String email, String faculty, String passHash) {
    	this.name = name;
		this.email = email;
		this.faculty = faculty;
		this.passHash = passHash;
		this.userID = email.split("@")[0]; //userID is the characters before the '@' in the email address.
	}
    
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userID;
	}


	public String getPassword() {
		// TODO Auto-generated method stub
		return this.passHash;
	}
    
}
