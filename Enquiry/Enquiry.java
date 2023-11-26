package Enquiry;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import Camp.Camp;
import Users.User;
import Utility.CSVReader;

/**
 * Represents an enquiry made by a student for a specific camp, along with the associated replies.
 */
public class Enquiry{
    /** The unique identifier for the enquiry. */
    protected String enquiryID;

    /** The user (student) making the enquiry. */
    protected User student;

    /** The camp associated with the enquiry. */
    protected Camp camp;

    /** The content of the enquiry message. */
    protected String message;

    /** An array of reply IDs associated with the enquiry. */
    private int[] replies;

    /**
     * Constructs an Enquiry object with the specified parameters.
     *
     * @param enquiryID The unique identifier for the enquiry.
     * @param student   The user (student) making the enquiry.
     * @param camp      The camp associated with the enquiry.
     * @param message   The content of the enquiry message.
     * @param replies   An array of reply IDs associated with the enquiry.
     */
    public Enquiry(String enquiryID, User student, Camp camp, String message, String[] replies) {
        this.enquiryID = enquiryID;
        this.student = student;
        this.camp = camp;
        this.message = message;
        this.replies = Arrays.stream(replies).mapToInt(Integer::parseInt).toArray();
    }

/**
     * Gets the camp associated with the enquiry.
     *
     * @return The camp associated with the enquiry.
     */
    public Camp getCamp() {
        return camp;
    }

    /**
     * Gets the unique identifier for the enquiry.
     *
     * @return The unique identifier for the enquiry.
     */
    public String getEnquiryID() {
        return enquiryID;
    }

    /**
     * Gets the content of the enquiry message.
     *
     * @return The content of the enquiry message.
     */

    public String getMessage() {
        return message;
    }

    /**
     * Gets an array of reply IDs associated with the enquiry.
     *
     * @return An array of reply IDs associated with the enquiry.
     */
    public int[] getReplies() {
    	return replies;
    }

    /**
     * Sets the content of the enquiry message.
     *
     * @param message The new content of the enquiry message.
     */
    public void setMessage(String message) {
    	this.message = message;
    }

    /**
     * Gets the user (student) making the enquiry.
     *
     * @return The user (student) making the enquiry.
     */
    public User getStudent() {
        return student;
    }

    /**
     * Sets the camp associated with the enquiry.
     *
     * @param camp The new camp associated with the enquiry.
     */
    public void setCamp(Camp camp) {
    	this.camp = camp;
    }
}
