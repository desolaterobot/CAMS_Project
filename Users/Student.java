package Users;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Camp.Camp;
import Camp.CampManager;
import Enquiry.*;

import java.util.Date;
import java.util.LinkedList;
import java.time.LocalDate;

/**
 * Represents a Student with functionalities related to camp registration, enquiries, and viewing registered camps.
 */
public class Student extends User implements EnquiryInterface{
	private List<String> registeredCamps;
	private boolean isCommitteeMember;
	private String committeeMemberOf;
	
	/**
     * Constructs a Student object with the specified attributes.
     *
     * @param name             The name of the student.
     * @param email            The email of the student.
     * @param faculty          The faculty of the student.
     * @param password         The password hash of the student.
     * @param isCommitteeMember Whether the student is a camp committee member.
     * @param committeeMemberOf The camp for which the student is a committee member.
     */
	public Student(String name, String email, String faculty, String password, Boolean isCommitteeMember, String committeeMemberOf) {
		super(name, email, faculty, password);
		this.isCommitteeMember = isCommitteeMember;
		this.committeeMemberOf = committeeMemberOf;
		Camp[] camps = CampManager.getCampsForStudents(this);
		this.registeredCamps = new ArrayList<>();
		for(Camp c : camps) {
			for(String s : c.getAttendees()) {
				if(s.equals(getUserId())) {
					if(!registeredCamps.contains(c.getCampName()))
						registeredCamps.add(c.getCampName());
				}
			}
			for(String s : c.getCommitteeList()) {
				if(s.equals(getUserId())) {
					if(!registeredCamps.contains(c.getCampName()))
						registeredCamps.add(c.getCampName());
				}
			}
		}
	}
	
	/**
     * Checks if the student is eligible to register for the given camp.
     *
     * @param camp The camp to check eligibility for.
     * @return True if eligible, false otherwise.
     */
	private boolean isEligible(Camp camp) {
		LocalDate currentDate = LocalDate.now();
		LocalDate registrationDeadline = camp.getRegistrationDeadline().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
		if(currentDate.isAfter(registrationDeadline)) {
			System.out.println("Registration is closed for this camp.");
			return false;
		}
		
		if(camp.getTotalSlots() <= 0) { 
			System.out.println("No more available slots in " + camp.getCampName());
			return false;
		}
		
		for(String userId : camp.getWithdrawals()) {
			if(userId.equals(this.getUserId())) {
				System.out.println("You cannot register for a Camp which you withdrew from!");
				return false;
			}
		}
		for(String s: registeredCamps) {
			Camp c = CampManager.getCamp(s);
			if(camp.getStartDate().before(c.getStartDate()) && camp.getEndDate().after(c.getStartDate()) ||
					camp.getStartDate().before(c.getEndDate()) && camp.getEndDate().after(c.getEndDate()) ||
					camp.getStartDate().before(c.getStartDate()) && camp.getEndDate().after(c.getEndDate()) ||
					camp.getStartDate().after(c.getEndDate()) && camp.getEndDate().before(c.getEndDate())) {
				System.out.println("Dates clashed with " + c.getCampName() + "!");
				return false;
			}
		}
		
		return true;
	}

	//camp 
	/**
     * Registers the student for a camp, either as an attendee or a committee member.
     *
     * @param campName        The name of the camp to register for.
     * @param committeeMember Whether the student is registering as a committee member.
     */
	public void registerCamp(String campName, boolean committeeMember) {
		Camp camp = CampManager.getCamp(campName);
		if(!registeredCamps.contains(campName)) {
			if(!committeeMember) {
				if(isEligible(camp)) {
					registeredCamps.add(campName);
					camp.consumeSlot();
					CampManager.addAttendee(camp, getUserId());
					System.out.println("You have successfully registered to " + camp.getCampName() + " as an attendee!");
				}
				else {
					System.out.println("Camp Registration Failed!");
				}
			}
			else {
				if(isCommitteeMember) {
					System.out.println("You are already a camp committee member of " + committeeMemberOf);
				}
				else if(camp.getCommitteeSlots() > 0) {
					if(isEligible(camp)) {
						registeredCamps.add(campName);
						camp.consumeSlot();
						camp.consumeCommitteeSlots();
						committeeMemberOf = camp.getCampName();
						isCommitteeMember = true;
						CampManager.addCommittee(camp, getUserId());
						UserManager.updateStudentDB(this);
						System.out.println("You have successfully registered as a Camp Committee Member of " + camp.getCampName());
					}
					else {
						System.out.println("Camp Registration Failed!");
					}
				}
				else if(camp.getCommitteeSlots() <= 0){
					System.out.println("There are no available slots for camp committee for this camp!");
				}
			}
		}
		else {
			if(committeeMember) {
				if(isCommitteeMember) {
					System.out.println("You are already a camp committee member of " + committeeMemberOf);
				}
				else if(camp.getCommitteeSlots() > 0) {
					if(isEligible(camp)) {
						registeredCamps.add(campName);
						camp.consumeSlot();
						camp.consumeCommitteeSlots();
						committeeMemberOf = camp.getCampName();
						isCommitteeMember = true;
						CampManager.addCommittee(camp, getUserId());
						UserManager.updateStudentDB(this);
						System.out.println("You have successfully registered as a Camp Committee Member of " + camp.getCampName());
					}
					else {
						System.out.println("Camp Registration Failed!");
					}
				}
				else if(camp.getCommitteeSlots() <= 0){
					System.out.println("There are no available slots for camp committee for this camp!");
				}
			}
			else {
				System.out.println("You are already registered for " + campName + "!");
				System.out.println();
			}
		}
	}

	/**
     * Withdraws the student from a registered camp.
     *
     * @param campName The name of the camp to withdraw from.
     */
	public void withdrawCamp(String campName) {
		Camp camp = CampManager.getCamp(campName);
		if(!registeredCamps.contains(campName)) {
			System.out.println("You are not registered for this camp!");
			return;
		}
		
		if(camp.getCampName().equals(committeeMemberOf)) {
			System.out.println("You are a committee member of this camp. You can't withdraw from this camp!");
			return;
		}
		else {
			camp.releaseSlot();
			registeredCamps.remove(campName);
			CampManager.removeAttendee(camp, getUserId());
		}
		CampManager.addWithdrawal(camp, getUserId());
		System.out.println("You have successfully withdrew from camp: "+camp.getCampName());
	}

	/**
     * Views the available camps for the student to register.
     */
	public void viewCamps() {
		System.out.println("CAMPS AVAILABLE TO YOU:");
		System.out.println("------------------------------------------------------------------");
		for(Camp c : CampManager.getCampsForStudents(this)) {
			System.out.println("Camp Name : " + c.getCampName());
			System.out.println("Start Date : " + c.getStartDate());
			System.out.println("End Date : " + c.getEndDate());
			System.out.println("Camp Description : " + c.getDescription());
			System.out.println("Remaining slots for Attendees : " + Integer.toString(c.getTotalSlots()));
			System.out.println("Remaining slots for Camp Committee : " + Integer.toString(c.getCommitteeSlots()));
			System.out.println("REGISTRATION CLOSES ON : " + c.getRegistrationDeadline());
			System.out.println("------------------------------------------------------------------");
		}
		System.out.println("\n");
	}
	
	
	//Enquiry Methods

	/**
     * Submits an enquiry for a specific camp.
     *
     * @param campName The name of the camp for the enquiry.
     * @param enquiry  The enquiry message.
     */
	public void submitEnquiry(String campName, String enquiry) {
		Camp camp = CampManager.getCamp(campName);
		EnquiryManager.addEnquiry(this, camp, enquiry);
		System.out.println("Enquiry Submitted! You may view/edit/delete your enquiry before it is processed!");
	}
	
	/**
     * Checks if the enquiry has been processed (replied to).
     *
     * @param enq The enquiry to check.
     * @return True if processed, false otherwise.
     */
	private boolean isEnquiryProcessed(Enquiry enq) {
		EnquiryReply[] enqr = enq.getReplies();
		return enqr.length>0;
	}

	/**
     * Views the enquiries made by the student.
     */
	public void viewEnquiries() {
		//A student can view, edit, and delete their enquiries before it is processed
		Enquiry[] enqs = EnquiryManager.getStudentEnquiries(this);
		if(enqs.length <= 0) {
			System.out.println("No enquries made yet. You can make an enquiry with option 5.");
			return;
		}
		System.out.println("ENQUIRIES MADE BY YOU:");
		System.out.println("-----------------------------------------------------------------------");
		for (Enquiry enq : enqs) {
			System.out.print("EnquiryID[" + enq.getEnquiryID() + "]. ");
			System.out.print(isEnquiryProcessed(enq)?"[Replied]":"[Unreplied]");
			System.out.print(" Enquiry for " + enq.getCamp().getCampName().toUpperCase() + ": ");
			System.out.print((enq.getMessage().length()>=20?(enq.getMessage().substring(0,20)+"..."):enq.getMessage()));
			System.out.println();
		}
		System.out.println("-----------------------------------------------------------------------");
	}

	/**
     * Edits an existing enquiry made by the student.
     *
     * @param enqID The ID of the enquiry to edit.
     */
	public void editEnquiry(String enqID) {
		Enquiry enq = EnquiryManager.getEnquiryByID(enqID);
		
		if(enq.equals(null)) return;
		
		if(isEnquiryProcessed(enq)) {
			System.out.println("Cannot edit processed enquiry!");
			return;
		}
		System.out.println("1. Edit Message");
		System.out.println("2. Change Camp");
		Scanner sc = new Scanner(System.in);
		int c = sc.nextInt();
		sc.nextLine();
		switch(c) {
		case 1:
			System.out.println("Original Message: " + enq.getMessage());
			System.out.print("Enter New Message: ");
			String newMsg = sc.nextLine();
			enq.edit(newMsg);
			break;
			
		case 2:
			System.out.println("Original Camp: " + enq.getCamp().getCampName());
			System.out.print("Enter New Camp name: ");
			String sCamp = sc.nextLine();
			System.out.println(sCamp);
			Camp nCamp = CampManager.getCamp(sCamp);
			if(nCamp != null)
				enq.edit(nCamp);
			else {
				System.out.println("Change camp unsuccessful!");
				return;
			}
			break;
		default:
			System.out.println("Not a valid choice");
			return;
		}
		
		EnquiryManager.editEnquiry(enq);
		System.out.println("You have successfully edited your enquiry!");
	}

	/**
     * Deletes an existing enquiry made by the student.
     *
     * @param enqID The ID of the enquiry to delete.
     */
	public void deleteEnquiry(String enqID) {
		Enquiry enq = EnquiryManager.getEnquiryByID(enqID);
		if(enq.equals(null));
		if(isEnquiryProcessed(enq)) {
			System.out.println("Cannot delete processed enquiry!");
			return;
		}
		
		Enquiry[] enqs = EnquiryManager.getStudentEnquiries(this);
		for(Enquiry e : enqs) {
			if(e.getEnquiryID().equals(enq.getEnquiryID())) {
				EnquiryManager.deleteEnquiry(enq);
				System.out.println("You have successfully deleted your enquiry!");
				return;
			}
		}
		System.out.println("Invalid Enquiry ID!");
	}

	/**
     * Views details of a specific enquiry made by the student.
     *
     * @param enquiryID The ID of the enquiry to view.
     */
	public void viewEnquiry(String enquiryID) {
		Enquiry enquiry = EnquiryManager.getEnquiryByID(enquiryID);
		if(enquiry.equals(null)) return;
		EnquiryReply[] enqr = enquiry.getReplies();
		if(enqr.length <= 0) {
			System.out.println("There are no replies yet for this enquiry.\n");
		}
		System.out.print("[Replied] ");
		System.out.println("Enquiry for: " + enquiry.getCamp().getCampName());
		System.out.println(enquiry.getStudent().getName() + ": " + enquiry.getMessage());
		for(EnquiryReply enqReply : enqr) {
			System.out.print("\u21B3 ");
			System.out.println(enqReply.getUser().getName() + ": " + enqReply.getReplyMessage());
		}
	}

	/**
     * Views the list of camps that the student is registered for.
     */
	public void viewRegisteredCamps(){
		if(!registeredCamps.isEmpty()) {
			System.out.println("YOUR REGISTERED CAMPS:");
			System.out.println("------------------------------------------------------------------");
			for(String campName : registeredCamps) {
				Camp c = CampManager.getCamp(campName);
				Boolean cc = false;
				System.out.println("Camp Name : " + c.getCampName());
				for(String s : c.getCommitteeList()) {
					if (s.equals(getUserId())) {
						cc = true;
						break;
					}
				}
				System.out.println("Your Role : " + (cc?"Committee Member":"Attendee"));
				System.out.println("Start Date : " + c.getStartDate());
				System.out.println("End Date : " + c.getEndDate());
				System.out.println("Camp Description : " + c.getDescription());
				System.out.println("location : " + c.getLocation());
				System.out.println("------------------------------------------------------------------");
			}
			System.out.println();
		}
		else
			System.out.println("You are not registered to any camps.");
	}
	
	
	//Getters Setters

	/**
     * Checks if the student is a committee member for any camp.
     *
     * @return True if committee member, false otherwise.
     */
	public boolean isCommitteeMember() {
		return isCommitteeMember;
	}

	/**
     * Sets the committee member status for the student.
     *
     * @param b True if committee member, false otherwise.
     */
	public void setCommitteeMember(Boolean b) {
		isCommitteeMember = b;
	}

	/**
     * Gets the name of the camp for which the student is a committee member.
     *
     * @return The camp name.
     */
	public String getCommitteeCamp() {
		return committeeMemberOf;
	}
	
	// For Testing

    /**
     * Main method for testing the Student class.
     *
     * @param args Command-line arguments.
     */
	public static void main(String[] args) { 
		System.out.println("testing Student.java");
		Student s = UserManager.getStudent("BGOH023");
//		Student s = new Student("BRYAN", "BGOH023@e.ntu.edu.sg", "SCSE", "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8", false, "null");
//		System.out.println(s.userID);
		s.viewCamps();
//		s.registerCamp("scse camp", false);
		s.viewRegisteredCamps();
		s.withdrawCamp("stupid camp");
		
//		System.out.println(Arrays.toString(camp.attendees) +" , "+ Arrays.toString(camp.committeeList)+" , "+ Arrays.toString(camp.withdrawals));
//		System.out.println(Integer.toString(camp.totalSlots) +" , "+ Integer.toString(camp.committeeSlots));
//		s.registerCamp(camp, true);
//		System.out.println(Arrays.toString(camp.attendees) +" , "+ Arrays.toString(camp.committeeList)+" , "+ Arrays.toString(camp.withdrawals));
//		System.out.println(Integer.toString(camp.totalSlots) +" , "+ Integer.toString(camp.committeeSlots));
//		s.withdrawCamp(camp);
//		System.out.println(Arrays.toString(camp.attendees) +" , "+ Arrays.toString(camp.committeeList)+" , "+ Arrays.toString(camp.withdrawals));
//		System.out.println(Integer.toString(camp.totalSlots) +" , "+ Integer.toString(camp.committeeSlots));
//		
//		
//		s.viewEnquiries();
//		System.out.println("======== " + EnquiryManager.getStudentEnquiries(s)[1].enquiryID);
//		s.editEnquiry(EnquiryManager.getStudentEnquiries(s)[1]);
////		s.editEnquiry(EnquiryManager.getStudentEnquiries(s)[0]);
////		s.submitEnquiry(camp, "delete this");
////		s.submitEnquiry(camp, "don't deleted");
//		s.deleteEnquiry(EnquiryManager.getStudentEnquiries(s)[3]);
	}
}
