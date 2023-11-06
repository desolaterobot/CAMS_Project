public class Camp {
    String name;
    String startDate;
    String endDate;
    String registrationDeadline;
    boolean onlyFaculty;
    String location;
    int totalSlots;
    int committeeSlots;
    String description;
    Staff staffInCharge;

    public Camp(String name, String startDate, String endDate, String registrationDeadline, boolean onlyFaculty, String location, int totalSlots, int committeeSlots, String description, Staff staffInCharge){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationDeadline = registrationDeadline;
        this.onlyFaculty = onlyFaculty;
        this.location = location;
        this.totalSlots = totalSlots;
        this.committeeSlots = committeeSlots;
        this.description = description;
        this.staffInCharge = staffInCharge;
    }
}
