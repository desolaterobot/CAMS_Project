package Enquiry;

public interface EnquiryInterface {
	public void viewEnquiries();
	public void viewEnquiry(String enquiryID);
	public void submitEnquiry(String name, String msg);
	public void editEnquiry(String enquiryID);
	public void deleteEnquiry(String enquiryID);
}
