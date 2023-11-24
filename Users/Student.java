package Users;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Camp.Camp;
import Camp.CampManager;
import Enquiry.Enquiry;
import Enquiry.EnquiryManager;
import Enquiry.EnquiryReply;

import java.util.Date;
import java.util.LinkedList;
import java.time.LocalDate;

public class Student extends User {
	private List<String> registeredCamps;
	private boolean isCommitteeMember;
	private String committeeMemberOf;
//	private Camp[] camp_db;
	
	//FOR TESTING
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
	
	public Student(String name, String email, String faculty, String password, Boolean isCommitteeMember, String committeeMemberOf) {
		super(name, email, faculty, password);
		this.isCommitteeMember = isCommitteeMember;
		this.committeeMemberOf = committeeMemberOf;
		Camp[] camps = CampManager.getCampsForStudents(this);
		this.registeredCamps = new ArrayList<>();
		for(Camp c : camps) {
			for(String s : c.getAttendees()) {
				if(s.equals(this.userID)) {
					if(!registeredCamps.contains(c.getCampName()))
						registeredCamps.add(c.getCampName());
				}
			}
			for(String s : c.getCommitteeList()) {
				if(s.equals(this.userID)) {
					if(!registeredCamps.contains(c.getCampName()))
						registeredCamps.add(c.getCampName());
				}
			}
		}
	}
		
	public void registerCamp(String campName, boolean committeeMember) {
		Camp camp = CampManager.getCamp(campName);
		if(!registeredCamps.contains(campName)) {
			if(!committeeMember) {
				if(isEligible(camp)) {
					registeredCamps.add(campName);
					camp.consumeSlot();
					CampManager.addAttendee(camp, userID);
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
						CampManager.addCommittee(camp, userID);
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
			//need to update committee status in students.csv
		}
		else {
			System.out.println("You are already registered for " + campName + "!");
			System.out.println();
		}
	}
	
	private boolean isEligible(Camp camp) {
		//add checker for camps withdrew and registration deadline
		LocalDate currentDate = LocalDate.now();
		LocalDate registrationDeadline = camp.getRegistrationDeadline().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
//		System.out.println(currentDate);
//		System.out.println(registrationDeadline);
		if(currentDate.isAfter(registrationDeadline)) {
			System.out.println("Registration failed. Registration is closed for this camp.");
			return false;
		}
		
		if(camp.getTotalSlots() <= 0) { 
			System.out.println("No more available slots in " + camp.getCampName());
			return false;
		}
		
		for(String userId : camp.getWithdrawals()) {
			if(userId.equals(userID)) {
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
	
	public void withdrawCamp(String campName) {
		Camp camp = CampManager.getCamp(campName);
		if(!registeredCamps.contains(campName)) {
			System.out.println("You are not registered in this camp!");
			return;
		}
		
		if(camp.getCampName().equals(committeeMemberOf)) {
//			camp.committeeSlots++;
//			camp.totalSlots++;
//			isCommitteeMember = false;
//			committeeMemberOf = null;
//			CampManager.removeCommittee(camp, userID);
			System.out.println("You are a committee member of this camp. You can't withdraw from this camp!");
			return;
		}
		else {
			camp.releaseSlot();
			CampManager.removeAttendee(camp, userID);
		}
		CampManager.addWithdrawal(camp, userID);
		System.out.println("You have successfully withdrew from camp: "+camp.getCampName());
	}
	
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
		//for testing
//		System.out.println();
//		for(Camp c : db) {
//			System.out.println(c.campName + " , " + c.faculty + " , " + Boolean.toString(c.visible) + " , " +Boolean.toString(c.visible));
//		}
	}
	
	public void submitEnquiry(Camp camp, String enquiry) {
		EnquiryManager.addEnquiry(this, camp, enquiry);
		System.out.println("Enquiry Submitted! You may view/edit/delete your enquiry before it is processed!");
	}
	
	private boolean isEnquiryProcessed(Enquiry enq) {
		EnquiryReply[] enqr = enq.getReplies();
		return enqr.length > 0;
	}
	
	public void viewEnquiries() {
		//A student can view, edit, and delete their enquiries before it is processed
		Enquiry[] enqs = EnquiryManager.getStudentEnquiries(this);
		System.out.println("ENQUIRIES MADE BY YOU:");
		for(Enquiry enq : enqs) {
			if(!isEnquiryProcessed(enq)) {
				System.out.print("[Unreplied] ");
				System.out.println("Enquiry for : " + enq.getCamp().getCampName()); 
				System.out.println(enq.getStudent().name + ": " + enq.getMessage());
				System.out.println();
			}
			else {
				viewEnquiryReplies(enq);
				System.out.println("");
			}
		}
	}
	
	public void editEnquiry(Enquiry enq) {
		if(isEnquiryProcessed(enq)) {
			System.out.println("Cannot edit processed enquiry!");
			return;
		}
		//editCamp Menu (Testing)
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
			sc.close();
			return;
		}
		
		EnquiryManager.editEnquiry(enq);
		System.out.println("You have successfully edited your enquiry!");
	}
	
	public void deleteEnquiry(Enquiry enq) {
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
	
	public void viewEnquiryReplies(Enquiry enquiry) {
		EnquiryReply[] enqr = enquiry.getReplies();
		System.out.print("[Replied] ");
		System.out.println("Enquiry for: " + enquiry.getCamp().getCampName());
		System.out.println(enquiry.getStudent().name + ": " + enquiry.getMessage());
		for(EnquiryReply enqReply : enqr) {
			System.out.print("\u21B3 ");
			System.out.println(enqReply.getUser().name + ": " + enqReply.getReply());
		}
	}
	
	public void viewRegisteredCamps(){
		if(!registeredCamps.isEmpty()) {
			System.out.println("YOUR REGISTERED CAMPS:");
			System.out.println("------------------------------------------------------------------");
			for(String campName : registeredCamps) {
//				System.out.println("debug: " + campName);
				Camp c = CampManager.getCamp(campName);
				Boolean cc = false;
				System.out.println("Camp Name : " + c.getCampName());
				for(String s : c.getCommitteeList()) {
					if (s.equals(userID)) {
						cc = true;
//						System.out.println(Boolean.toString(cc));
						break;
					}
				}
//				System.out.println(Boolean.toString(cc));
				System.out.println("Your Role : " + (cc?"Committee Member":"Attendee"));
				System.out.println("Start Date : " + c.getStartDate());
				System.out.println("End Date : " + c.getEndDate());
				System.out.println("Camp Description : " + c.getDescription());
				System.out.println("------------------------------------------------------------------");
			}
			System.out.println();
		}
		else
			System.out.println("You are not registered to any camps.");
	}
	
	public boolean isCommitteeMember() {
		return isCommitteeMember;
	}
	
	public void setCommitteeMember(Boolean b) {
		isCommitteeMember = b;
	}
	
	public String getCommitteeCamp() {
		return committeeMemberOf;
	}
}
