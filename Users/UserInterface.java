package Users;

/**
 * The UserInterface represents the interface for user-related operations in the system.
 * Classes implementing this interface are responsible for handling user login and authentication.
 */
public interface UserInterface {
	/**
     * Prompts the user to log in and returns the corresponding user object upon successful login.
     *
     * @return The User object representing the logged-in user.
     */
	public User login();

	/**
     * Authenticates a user based on the provided user ID and password.
     * Returns the authenticated User object if successful, or null if authentication fails.
     *
     * @param userId   The user ID for authentication.
     * @param password The password for authentication.
     * @return The authenticated User object, or null if authentication fails.
     */
	public User authUser(String userId, String password);
}
