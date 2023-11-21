import java.util.*;

public class CampApp {
	Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args0) {
		CampApp app = new CampApp();
		app.run();
	}	
	
	public void run() {
		System.out.println(
			" ██████╗ █████╗ ███╗   ███╗███████╗\r\n" +
			"██╔════╝██╔══██╗████╗ ████║██╔════╝\r\n" +
			"██║     ███████║██╔████╔██║███████╗\r\n" +
			"██║     ██╔══██║██║╚██╔╝██║╚════██║\r\n" +
			"╚██████╗██║  ██║██║ ╚═╝ ██║███████║\r\n" +
			" ╚═════╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝\r");
		System.out.println("Camp Application And Management System");
		UserManager UserManager = new UserManager();
		while(true) {
			System.out.println("1.login");
			System.out.println("2.exit");
			
			int choice = sc.nextInt();
			switch(choice) {
				case 1:
					UserManager.login();
					break;
				case 2:
					sc.close();
					return;
				default:
					System.out.println("Invalud choice. Please try again");
					break;
			}
		}
	}
	
//	public List<Camp> loadCamps(String file) {
//		List<Camp> Camps = new ArrayList<>();
//		
//		String[] lines = CSVReader.getLines(file);
//		
//		for (String line: lines) {
//			String[] part = line.split(",");
//			
//			String name = part[0];
//			Date startDate = DateStr.strToDate(part[1]);
//			Date endDate= DateStr.strToDate(part[2]);
//			Date registrationDeadline= DateStr.strToDate(part[3]);
//			String location= part[4];
//			int totalSlots= Integer.parseInt(part[5]);
//			int campCommitteeSlot= Integer.parseInt(part[6]);
//			String description= part[7];
//			boolean visible = Boolean.parseBoolean(part[9]);
//			boolean onlyFaculty = Boolean.parseBoolean(part[10]);
//			String staffInCharge= part[8];
//			Staff inCharge = findStaffById(staffInCharge);
//			
//			Camp camp = new Camp(name,startDate,endDate,registrationDeadline,location,
//					totalSlots,campCommitteeSlot,description,inCharge,visible,onlyFaculty);
//			
//			Camps.add(camp);
//		}
//		return Camps;
//	}
	
	public static void printStudentMenu(Student student) {
		System.out.println("1. Register Camp");
		System.out.println("2. View Camps");
		System.out.println("3. Submit Enquiry");
		System.out.println("4. Withdraw Camp");
		System.out.println("5.");
		System.out.println("11. Logout");
		if(student.isCommitteeMember()) {
			System.out.println("6. Reply Enquiry");
			System.out.println("7. View Suggestions");
			System.out.println("8. Edit Suggestions");
			System.out.println("9. Submit Suggestions");
			System.out.println("10. View Camp Details");
		}
		
	}
	
	public static void showStudentMenu(Student student) {
		//student actions:
		// - view open camps
		// - register/withdraw from camp
		// - submit enquiry for the camp they registered
		// - view registered camp
		
		//if they are committee member actions:
			//

		Scanner sc = new Scanner(System.in);
		
		while(true) {
			printStudentMenu(student);
			
			int choice = sc.nextInt();
			
			sc.nextLine();
			
			if(choice==11) return;
			
			if(student.isCommitteeMember()) {
				handleCommitteeMemberChoice(student,choice);
			}
			
			else {
				handleStudentChoice(student,choice);
			}
				
				
			}
		}
	
	public static void handleCommitteeMemberChoice(Student student, int choice) {
		// TODO Auto-generated method stub
		switch(choice) {
		case 6:
			System.out.println("Student 6");
			//replyEnquiry(student);
			
			break;
		case 7:
			System.out.println("Student 7");
			//viewSuggestion(student);
			break;
		case 8:
			System.out.println("Student 8");
			//editSuggestion(student);
			break;
		case 9:
			System.out.println("Student 9");
			//submitSuggestion(student);
			break;
		case 10:
			System.out.println("Student 10");
			//viewCommitteeCampDetails(student);
			
			break;
		default:
			handleStudentChoice(student,choice);
		}
	}
	
	public static void handleStudentChoice(Student student, int choice) {
		// TODO Auto-generated method stub
		switch(choice) {
		case 1:
			System.out.println("Student 1");
			//viewAailableCamps(student);
			break;
		case 2:
			System.out.println("Student 2");
			//registerCamp(student);
			break;
		case 3:
			System.out.println("Student 3");
			//withdrawCamp(student);
			break;
		case 4:
			System.out.println("Student 4");
			//submitEnquiry(student);
			break;
		case 5:
			System.out.println("Student 5");
			//viewegisteredCamp(student);
			break;
		case 10:
			System.out.println("Student 10");
			return;
		}
	}


	public static void printStaffMenu() {
		System.out.println("1. Create Camp");
		System.out.println("2. Edit Camp");
		System.out.println("3. Delete Camp");
		System.out.println("4. Toggle Visibility");
		System.out.println("5. View All Camps");
		System.out.println("6. View My Camps");
		System.out.println("7. View Enquiries");
		System.out.println("8. Reply Enquiry");
		System.out.println("9. View Suggestions");
		System.out.println("10. Approve Suggestions");
		System.out.println("11. Generate Report");
		System.out.println("12. Exit");
	}
		
	public static void showStaffMenu(Staff staff) {
		//staff actions:
		// - create/edit/delete camp
		// - toggle camp visibility
		// - view all camps
		// - view camps created
		// - view/reply to enquiries
		// - approve suggestions
		// - generate reports
		Scanner sc = new Scanner(System.in);
		
		while(true) {
			printStaffMenu();

			int choice = Integer.parseInt(sc.nextLine());
			
			switch(choice) {
			case 1:
				System.out.println("Creating Camp.");
				staff.createCamp();
				break;
			case 2:
				System.out.println("Editing Camp.");
				List<Camp> camps = Arrays.asList(CampManager.getCampsByStaffID(staff.userID));
				int i = 1;
				for (Camp camp :
						camps) {
					System.out.println(i + ". "+ camp.campName);
					i++;
				}
				System.out.println("Select Camp to edit.");
				int campChoice;
				while (true) {
					campChoice = Integer.parseInt(sc.nextLine());
					if (campChoice > i-1 || campChoice < 1) continue;
					break;
				}

				staff.editCamp(camps.get(campChoice-1));
				//EditCamp(staff);
				break;
			case 3:
				System.out.println("Deleting Camp.");
				//DeleteCamp(staff);
				break;
			case 4:
				System.out.println("Toggling Visibility");
				//ToggleVisibility(staff);
				break;
			case 5:
				System.out.println("View All Camps");
				//ViewAllCamps(staff);
				break;
			case 6:
				System.out.println("View My Camps");
				//ViewMyCamps(staff);
				break;
			case 7:
				System.out.println("View Enquiries.");
				//ViewEnquiries(staff);
				break;
			case 8:
				System.out.println("Reply Enquiries");
				//ReplyEnquiry(staff);
				break;
			case 9:
				System.out.println("View Suggestions");
				//ViewSuggestions(staff);
				break;
			case 10:
				System.out.println("Approve Suggestions");
				//ApproveSuggestion(staff);
				break;
			case 11:
				System.out.println("Generate Report");
				//GenerateReport(staff);
				break;
			case 12:
				return;
			}
		}	
	}
}
