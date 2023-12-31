package Users;

enum accountType{Staff, Student}

/**
 * The User class represents a user in the Camp Application and Management System.
 * It contains information about the user ID, name, email, password hash, and faculty.
 */
public class User {
    /** The user ID generated from the email address (characters before '@'). */
    private String userID; 

    /** The name of the user. */
    private String name;

    /** The email address of the user. */
    private String email;

    /** The hashed password of the user. */
    private String passHash;

    /** The faculty to which the user belongs. */
    private String faculty;
    
    /**
     * Constructs a new User with the given parameters.
     *
     * @param name            The name of the user.
     * @param email           The email address of the user.
     * @param faculty         The faculty to which the user belongs.
     * @param passHash        The hashed password of the user.
     */
     
    public User(String name, String email, String faculty, String passHash) {
    	this.name = name;
		this.email = email;
		this.faculty = faculty;
		this.passHash = passHash;
		this.userID = email.split("@")[0]; //userID is the characters before the '@' in the email address.
	}

	/**
     * Retrieves the user's ID.
     *
     * @return The user's ID.
     */
	public String getUserId() {
		return this.userID;
	}

	/**
     * Retrieves the user's Name.
     *
     * @return The user's Name.
     */
	public String getName() {
		return name;
	}

	/**
     * Retrieves the user's Email.
     *
     * @return The user's Email.
     */
	public String getEmail() {
		return email;
	}

	/**
     * Retrieves the user's password hash.
     *
     * @return The user's password hash.
     */
	public String getPassword() {
		return this.passHash;
	}

	/**
     * Retrieves the user's Faculty.
     *
     * @return The user's Faculty.
     */
	public String getFaculty() {
		return faculty;
	}
    
}
