import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.LinkedList;
import java.time.LocalDate;

public class Student extends User {
	private List<Camp> registeredCamps;
	private List<Enquiry> enquiries;
	private boolean isCommitteeMember;
	private String committeeMemberOf;
//	private Camp[] camp_db;
	
	//FOR TESTING
	public static void main(String[] args) { 
		System.out.println("testing Student.java");
		Student s = new Student("CHAN", "YCN019@e.ntu.edu.sg", "NBS", 
				"5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8",
				false, "null");
		Camp camp = CampManager.getCamp("stupid camp");
		System.out.println(s.userID);
		s.viewCamps();
		
		System.out.println(Arrays.toString(camp.attendees) +" , "+ Arrays.toString(camp.committeeList)+" , "+ Arrays.toString(camp.withdrawals));
		System.out.println(Integer.toString(camp.totalSlots) +" , "+ Integer.toString(camp.committeeSlots));
		s.registerCamp(camp, true);
		System.out.println(Arrays.toString(camp.attendees) +" , "+ Arrays.toString(camp.committeeList)+" , "+ Arrays.toString(camp.withdrawals));
		System.out.println(Integer.toString(camp.totalSlots) +" , "+ Integer.toString(camp.committeeSlots));
		s.withdrawCamp(camp);
		System.out.println(Arrays.toString(camp.attendees) +" , "+ Arrays.toString(camp.committeeList)+" , "+ Arrays.toString(camp.withdrawals));
		System.out.println(Integer.toString(camp.totalSlots) +" , "+ Integer.toString(camp.committeeSlots));
		
		
		s.viewEnquiries();
		System.out.println("======== " + EnquiryManager.getStudentEnquiries(s)[1].enquiryID);
		s.editEnquiry(EnquiryManager.getStudentEnquiries(s)[1]);
//		s.editEnquiry(EnquiryManager.getStudentEnquiries(s)[0]);
//		s.submitEnquiry(camp, "delete this");
//		s.submitEnquiry(camp, "don't deleted");
		s.deleteEnquiry(EnquiryManager.getStudentEnquiries(s)[3]);
	}
	
	public Student(String userId, String email, String faculty, String password, Boolean isCommitteeMember, String committeeMemberOf) {
		super(userId, email, faculty, password);
		this.isCommitteeMember = isCommitteeMember;
		this.committeeMemberOf = committeeMemberOf;
		Camp[] camps = isCommitteeMember ? CampManager.getCampsByCommiteeID(userID) : CampManager.getCampsByAttendeeID(userID);
		this.registeredCamps = new ArrayList<>();
		for(Camp c : camps) {
			registeredCamps.add(c);
		}
	}
		
//		camp_db = CampManager.getCampDatabase();
//		for(Camp c : camp_db) {
//			if(c.visible && (!c.onlyFaculty || c.faculty.equals(faculty))) {
//				for(int i=0; i<c.attendees.length; i++) {
//					if(c.attendees[i].equals(userId)) registeredCamps.add(c);
//				}
//				for(int i=0; i<c.commiteeList.length; i++) {
//					if(userId.equals(c.commiteeList[i])) {
//						registeredCamps.add(c);
//						this.isCommitteeMember = true;
//						this.committeeMemberOf = c;
//					}
//				}
//			}
//		}
	
	public void registerCamp(Camp camp, boolean committeeMember) {
		if(!registeredCamps.contains(camp)) {
			if(!committeeMember) {
				if(isEligible(camp)) {
					registeredCamps.add(camp);
					camp.totalSlots--;
					CampManager.addAttendee(camp, userID);
					System.out.println("You have successfully registered to " + camp.campName + " as an attendee!");
				}
				else {
					System.out.println("Camp Registration Failed!");
				}
			}
			else {
				if(isCommitteeMember) {
					System.out.println("You are already a camp committee member of " + committeeMemberOf);
				}
				else if(camp.committeeSlots > 0) {
					if(isEligible(camp)) {
						registeredCamps.add(camp);
						camp.committeeSlots--;
						committeeMemberOf = camp.campName;
						isCommitteeMember = true;
						CampManager.addCommittee(camp, userID);
						//call CampCommittee class? Relogin?
						System.out.println("You have successfully registered as a Camp Committee Member of " + camp.campName);
					}
					else {
						System.out.println("Camp Registration Failed!");
					}
				}
			}
		}
	}
	
	private boolean isEligible(Camp camp) {
		//add checker for camps withdrew and registration deadline
		
		if(camp.totalSlots <= 0) { 
			System.out.println("No more available slots in " + camp.campName);
			return false;
		}
		
		for(String userId : camp.withdrawals) {
			if(userId.equals(userID)) {
				System.out.println("You cannot register for a Camp which you withdrew from!");
				return false;
			}
		}
		for(Camp c: registeredCamps) {
			if(camp.startDate.before(c.startDate) && camp.endDate.after(c.startDate) ||
					camp.startDate.before(c.endDate) && camp.endDate.after(c.endDate) ||
					camp.startDate.before(c.startDate) && camp.endDate.after(c.endDate) ||
					camp.startDate.after(c.endDate) && camp.endDate.before(c.endDate)) {
				System.out.println("Dates clashed with " + c.campName + "!");
				return false;
			}
		}
		
		return true;
	}
	
	public void withdrawCamp(Camp camp) {
		if(!registeredCamps.contains(camp)) return;
		
		if(camp.campName.equals(committeeMemberOf)) {
			camp.committeeSlots++;
			isCommitteeMember = false;
			committeeMemberOf = null;
			CampManager.removeCommittee(camp, userID);
		}
		else {
			camp.totalSlots++;
			CampManager.removeAttendee(camp, userID);
		}
		CampManager.addWithdrawal(camp, userID);
		System.out.println("You have successfully withdrew from camp: "+camp.campName);
	}
	
	public void viewCamps() {
		int i = 1;
		System.out.println("CAMPS AVAILABLE TO YOU:");
		System.out.println("------------------------------------------------------------------");
		for(Camp c : CampManager.getCampsForStudents(this)) {
			System.out.println(Integer.toString(i));
			System.out.println("Camp Name : " + c.campName);
			System.out.println("Start Date : " + c.startDate);
			System.out.println("End Date : " + c.endDate);
			System.out.println("Remaining slots for Attendees : " + Integer.toString(c.totalSlots));
			System.out.println("Remaining slots for Camp Committee : " + Integer.toString(c.committeeSlots));
			System.out.println("REGISTRATION CLOSES ON : " + c.registrationDeadline);
			System.out.println("------------------------------------------------------------------");
			i++;
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
				System.out.println("Enquiry for : " + enq.camp.campName);
				System.out.println(enq.student.name + ": " + enq.message);
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
			System.out.println("Original Message: " + enq.message);
			System.out.print("Enter New Message: ");
			String newMsg = sc.nextLine();
			enq.message = newMsg;
			sc.close();
			break;
			
		case 2:
			System.out.println("Original Camp: " + enq.camp.campName);
			System.out.print("Enter New Camp name: ");
			String sCamp = sc.nextLine();
			System.out.println(sCamp);
			Camp nCamp = CampManager.getCamp(sCamp);
			if(nCamp != null)
				enq.camp = nCamp;
			else {
				System.out.println("Change camp unsuccessful!");
				sc.close();
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
			if(e.enquiryID.equals(enq.enquiryID)) {
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
		System.out.println("Enquiry for: " + enquiry.camp.campName);
		System.out.println(enquiry.student.name + ": " + enquiry.message);
		for(EnquiryReply enqReply : enqr) {
			System.out.print("\u21B3 ");
			System.out.println(enqReply.user.name + ": " + enqReply.reply);
		}
	}
	
	public boolean isCommitteeMember() {
		return isCommitteeMember;
	}
	
	public void setCommitteeMember(Boolean b) {
		isCommitteeMember = b;
	}
}
