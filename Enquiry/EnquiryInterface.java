package Enquiry;

/**
 * An interface defining methods for managing enquiries.
 */
public interface EnquiryInterface {
	
	/**
     * Displays a list of all enquiries.
     */
	public void viewEnquiries();

	/**
     * Displays details of a specific enquiry based on the provided enquiryID.
     *
     * @param enquiryID The unique identifier of the enquiry to view.
     */
	public void viewEnquiry(String enquiryID);

	/**
     * Submits a new enquiry with the provided name and message.
     *
     * @param name The name associated with the enquiry.
     * @param msg  The message content of the enquiry.
     */
	public void submitEnquiry(String name, String msg);

	/**
     * Edits the content of a specific enquiry based on the provided enquiryID.
     *
     * @param enquiryID The unique identifier of the enquiry to edit.
     */
	public void editEnquiry(String enquiryID);

	/**
     * Deletes a specific enquiry based on the provided enquiryID.
     *
     * @param enquiryID The unique identifier of the enquiry to delete.
     */
	public void deleteEnquiry(String enquiryID);
}
