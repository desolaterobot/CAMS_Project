/**
 * The SuggestionInterface interface defines methods for managing suggestions.
 */
public interface SuggestionInterface {
	 /**
     * Submits a suggestion.
     */
	public void submitSuggestion();

	/**
     * Views suggestions submitted by the user.
     */
	public void viewOwnSuggestions();

	/**
     * Edits a submitted suggestion.
     */
	public void editSuggestion();

	/**
     * Deletes a submitted suggestion.
     */
	public void deleteSuggestion();
}
