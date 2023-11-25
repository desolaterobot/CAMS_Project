package Users;

/**
 * Represents the interface for user-related functionalities.
 */
public interface UserInterface {

	/**
     * Performs user login.
     *
     * @return The User object representing the logged-in user.
     */
	public User login();

	/**
     * Authenticates a user based on user ID and password.
     *
     * @param userId   The user ID to authenticate.
     * @param password The password to authenticate.
     * @return The User object if authentication is successful, null otherwise.
     */
	public User authUser(String userId, String password);
}
