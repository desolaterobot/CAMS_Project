import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.LinkedList;
import java.time.LocalDate;

public class Student extends User implements EnquiryInterface{
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
			for(String s : c.attendees) {
				if(s.equals(this.userID)) {
					if(!registeredCamps.contains(c.campName))
						registeredCamps.add(c.campName);
				}
			}
			for(String s : c.committeeList) {
				if(s.equals(this.userID)) {
					if(!registeredCamps.contains(c.campName))
						registeredCamps.add(c.campName);
				}
			}
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
	
	
	
	public void registerCamp(String campName, boolean committeeMember) {
		Camp camp = CampManager.getCamp(campName);
		if(!registeredCamps.contains(campName)) {
			if(!committeeMember) {
				if(isEligible(camp)) {
					registeredCamps.add(campName);
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
						registeredCamps.add(campName);
						camp.totalSlots--;
						camp.committeeSlots--;
						committeeMemberOf = camp.campName;
						isCommitteeMember = true;
						CampManager.addCommittee(camp, userID);
						UserManager.updateStudentDB(this);
						System.out.println("You have successfully registered as a Camp Committee Member of " + camp.campName);
					}
					else {
						System.out.println("Camp Registration Failed!");
					}
				}
				else if(camp.committeeSlots <= 0){
					System.out.println("There are no available slots for camp committee for this camp!");
				}
			}
		}
		else {
			if(committeeMember) {
				if(isCommitteeMember) {
					System.out.println("You are already a camp committee member of " + committeeMemberOf);
				}
				else if(camp.committeeSlots > 0) {
					if(isEligible(camp)) {
						registeredCamps.add(campName);
						camp.totalSlots--;
						camp.committeeSlots--;
						committeeMemberOf = camp.campName;
						isCommitteeMember = true;
						CampManager.addCommittee(camp, userID);
						UserManager.updateStudentDB(this);
						System.out.println("You have successfully registered as a Camp Committee Member of " + camp.campName);
					}
					else {
						System.out.println("Camp Registration Failed!");
					}
				}
				else if(camp.committeeSlots <= 0){
					System.out.println("There are no available slots for camp committee for this camp!");
				}
			}
			else {
				System.out.println("You are already registered for " + campName + "!");
				System.out.println();
			}
		}
	}
	
	private boolean isEligible(Camp camp) {
		//add checker for camps withdrew and registration deadline
		LocalDate currentDate = LocalDate.now();
		LocalDate registrationDeadline = camp.registrationDeadline.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
//		System.out.println(currentDate);
//		System.out.println(registrationDeadline);
		if(currentDate.isAfter(registrationDeadline)) {
			System.out.println("Registration is closed for this camp.");
			return false;
		}
		
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
		for(String s: registeredCamps) {
			Camp c = CampManager.getCamp(s);
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
	
	public void withdrawCamp(String campName) {
		Camp camp = CampManager.getCamp(campName);
		if(!registeredCamps.contains(campName)) {
			System.out.println("You are not registered for this camp!");
			return;
		}
		
		if(camp.campName.equals(committeeMemberOf)) {
//			camp.committeeSlots++;
//			camp.totalSlots++;
//			isCommitteeMember = false;
//			committeeMemberOf = null;
//			CampManager.removeCommittee(camp, userID);
			System.out.println("You are a committee member of this camp. You can't withdraw from this camp!");
			return;
		}
		else {
			camp.totalSlots++;
			registeredCamps.remove(campName);
			CampManager.removeAttendee(camp, userID);
		}
		CampManager.addWithdrawal(camp, userID);
		System.out.println("You have successfully withdrew from camp: "+camp.campName);
	}
	
	public void viewCamps() {
		System.out.println("CAMPS AVAILABLE TO YOU:");
		System.out.println("------------------------------------------------------------------");
		for(Camp c : CampManager.getCampsForStudents(this)) {
			System.out.println("Camp Name : " + c.campName);
			System.out.println("Start Date : " + c.startDate);
			System.out.println("End Date : " + c.endDate);
			System.out.println("Camp Description : " + c.description);
			System.out.println("Remaining slots for Attendees : " + Integer.toString(c.totalSlots));
			System.out.println("Remaining slots for Camp Committee : " + Integer.toString(c.committeeSlots));
			System.out.println("REGISTRATION CLOSES ON : " + c.registrationDeadline);
			System.out.println("------------------------------------------------------------------");
		}
		System.out.println("\n");
		//for testing
//		System.out.println();
//		for(Camp c : db) {
//			System.out.println(c.campName + " , " + c.faculty + " , " + Boolean.toString(c.visible) + " , " +Boolean.toString(c.visible));
//		}
	}
	
	public void submitEnquiry(String campName, String enquiry) {
		Camp camp = CampManager.getCamp(campName);
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
		if(enqs.length <= 0) {
			System.out.println("No enquries made yet. You can make an enquiry with option 5.");
			return;
		}
		System.out.println("ENQUIRIES MADE BY YOU:\n");
		for(Enquiry enq : enqs) {
			System.out.print(isEnquiryProcessed(enq)?"[Replied]":"[Unreplied]");
			System.out.println(" Enquiry ID: " + enq.enquiryID);
			System.out.println("------------------------------------------------------------------");
			System.out.println("Enquiry for : " + enq.camp.campName.toUpperCase());
			System.out.println();
			System.out.println(enq.student.name + ": " + enq.message);
			System.out.println();
			System.out.println("------------------------------------------------------------------");
			System.out.println();
		}
	}
	
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
			System.out.println("Original Message: " + enq.message);
			System.out.print("Enter New Message: ");
			String newMsg = sc.nextLine();
			enq.message = newMsg;
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
	
	public void deleteEnquiry(String enqID) {
		Enquiry enq = EnquiryManager.getEnquiryByID(enqID);
		if(enq.equals(null));
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
	
	public void viewEnquiry(String enquiryID) {
		Enquiry enquiry = EnquiryManager.getEnquiryByID(enquiryID);
		if(enquiry.equals(null)) return;
		EnquiryReply[] enqr = enquiry.getReplies();
		if(enqr.length <= 0) {
			System.out.println("There are no replies yet for this enquiry.\n");
		}
		System.out.print("[Replied] ");
		System.out.println("Enquiry for: " + enquiry.camp.campName);
		System.out.println(enquiry.student.name + ": " + enquiry.message);
		for(EnquiryReply enqReply : enqr) {
			System.out.print("\u21B3 ");
			System.out.println(enqReply.user.name + ": " + enqReply.reply);
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
				System.out.println("Camp Name : " + c.campName);
				for(String s : c.committeeList) {
					if (s.equals(userID)) {
						cc = true;
//						System.out.println(Boolean.toString(cc));
						break;
					}
				}
//				System.out.println(Boolean.toString(cc));
				System.out.println("Your Role : " + (cc?"Committee Member":"Attendee"));
				System.out.println("Start Date : " + c.startDate);
				System.out.println("End Date : " + c.endDate);
				System.out.println("Camp Description : " + c.description);
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
