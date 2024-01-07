package project1;

import java.util.Objects;

/**
 * @author Yucong Liu, Phillip Huang
 * @create 2023/9/19 7:30
 */
public class Event implements Comparable<Event> {
    private Date date;
    private Timeslot startTime;
    private Location location;
    private Contact contact; //Includes department name and email
    private int duration;

    /**
     * Constructor for Event
     * @param date
     * @param startTime
     * @param location
     * @param contact
     * @param duration
     */
    public Event(Date date, Timeslot startTime, Location location, Contact contact, int duration) {
        this.date = date;
        this.startTime = startTime;
        this.location = location;
        this.contact = contact;
        this.duration = duration;
    }


    /**
     * Compare Timeslot of two events
     * @param event to be compared
     * @return 0 if there is no difference between their dates and timeslot,
     * the difference between their hours or minutes
     */
    @Override
    public int compareTo(Event o) {
        int i = this.date.compareTo(o.date);
        if (i == 0) {
            if(this.startTime.getHour() != o.startTime.getHour()){
                return this.startTime.getHour() - o.startTime.getHour();
            }else if(this.startTime.getMinute() != o.startTime.getMinute()){
                return this.startTime.getMinute() - o.startTime.getMinute();
            }else {
                return 0;
            }
        }
        return i;
    }

    /**
     * Compares the Date and Timeslot of two events
     * @param obj
     * @return true if Date and Timeslot are the in same location, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Event event = (Event) obj;
        int i = this.date.compareTo(event.date); //Dates are the same
        boolean locationEquals = Objects.equals(this.location.getName(),event.location.getName());
        boolean timeEq = Objects.equals(this.startTime.getTime(), event.startTime.getTime());
        return i == 0 && locationEquals && timeEq;
    }

    /**
     * Turn an event into a string and adjust its time with AM/PM
     * @return String of event's date, start time, end time, location, and contact
     */
    @Override
    public String toString() {
        String startTimeAMPM = startTime.getTime() + "am] ";
        String endTimeAMPM = startTime.addMinutes(duration) + "am] ";
        if(Integer.valueOf(startTime.getTime().substring(0,2))>=12){
            if(Integer.valueOf(startTime.getTime().substring(0,2))>=13) {
                startTimeAMPM = startTime.addMinutes(-720) + "pm] ";
            } else{
                startTimeAMPM = startTime.getTime() + "pm] ";
            }
        }
        if(startTimeAMPM.charAt(0)=='0') {
            startTimeAMPM = startTimeAMPM.substring(1);
        }
        if(Integer.valueOf(startTime.addMinutes(duration).substring(0,2))>=12){
            if(Integer.valueOf(startTime.getTime().substring(0,2))>=13) {
                endTimeAMPM = startTime.addMinutes(duration - 720)  + "pm] ";
            } else{
                endTimeAMPM = startTime.getTime() + "pm] ";
            }
        }
        if(endTimeAMPM.charAt(0)=='0') {
            endTimeAMPM = endTimeAMPM.substring(1);
        }
        return "[Event Date: " + date + "] " +
                "[Start: " + startTimeAMPM +
                "[End: " + endTimeAMPM +
                "@" + location + " (" + location.getName() + ")" +
                " [Contact: " + contact.getDepartment().getName() + ", " + contact.getEmail() + "]";
    }

    /**
     * Getter for Date
     * @return date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter for Date
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Getter for start time of Timeslot
     * @return startTime
     */
    public Timeslot getStartTime() {
        return startTime;
    }

    /**
     * Setter for start time of Timeslot
     * @param startTime
     */
    public void setStartTime(Timeslot startTime) {
        this.startTime = startTime;
    }

    /**
     * Getter for end time
     * @return startTime after adding duration, which is the end time
     */
    public String getEndTime() {
        return startTime.addMinutes(duration);
    }

    /**
     * Getter for Location
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Setter for Location
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Getter for Contact
     * @return contact
     */
    public Contact getContact() {
        return contact;
    }

    /**
     * Setter for Contact
     * @param contact
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Getter for Duration
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter for Duration
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Compares two events
     * @param event1
     * @param event2
     * @param expectedOutput
     * @param actualOutput
     * @return true if expectedOutput and actualOutput are the same, false otherwise
     */
    public static boolean testResult(Event event1, Event event2, boolean expectedOutput,
                                     boolean actualOutput) {
        System.out.println(expectedOutput == actualOutput);
        return expectedOutput == actualOutput;
    }

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        identicalEvents_1();
        identicalEvents_2();
        identicalEvents_3();
        identicalEvents_4();
    }

    /**
     * Test case #1
     */
    public static void identicalEvents_1() {
        Event event1 = new Event(new Date("09/28/2023"), Timeslot.MORNING, Location.AB2225,
                new Contact(Department.CS.getName(), "ph390@scarletmail.rutgers.edu"), 90);
        Event event2 = new Event(new Date("09/28/2023"), Timeslot.MORNING, Location.AB2225,
                new Contact(Department.CS.getName(), "ph390@scarletmail.rutgers.edu"), 90);
        boolean expectedOutput = true;
        boolean actualOutput = event1.equals(event2);
        System.out.println("**Test case #1: check if event1 and event2 are identical");
        testResult(event1, event2, expectedOutput, actualOutput);
    }

    /**
     * Test case #2
     */
    public static void identicalEvents_2() {
        Event event1 = new Event(new Date("09/28/2023"), Timeslot.MORNING, Location.AB2225,
                new Contact(Department.CS.getName(), "ph390@scarletmail.rutgers.edu"), 90);
        Event event2 = new Event(new Date("09/28/2021"), Timeslot.MORNING, Location.AB2225,
                new Contact(Department.CS.getName(), "ph390@scarletmail.rutgers.edu"), 90);
        boolean expectedOutput = false;
        boolean actualOutput = event1.equals(event2);
        System.out.println("**Test case #2: check if event1 and event2 have the same date");
        testResult(event1, event2, expectedOutput, actualOutput);
    }

    /**
     * Test case #3
     */
    public static void identicalEvents_3() {
        Event event1 = new Event(new Date("09/28/2023"), Timeslot.MORNING, Location.AB2225,
                new Contact(Department.CS.getName(), "ph390@scarletmail.rutgers.edu"), 90);
        Event event2 = new Event(new Date("09/28/2023"), Timeslot.EVENING, Location.AB2225,
                new Contact(Department.CS.getName(), "ph390@scarletmail.rutgers.edu"), 90);
        boolean expectedOutput = false;
        boolean actualOutput = event1.equals(event2);
        System.out.println("**Test case #3: check if event1 and event2 have the same timeslot");
        testResult(event1, event2, expectedOutput, actualOutput);
    }

    /**
     * Test case #4
     */
    public static void identicalEvents_4() {
        Event event1 = new Event(new Date("09/28/2023"), Timeslot.EVENING, Location.AB2225,
                new Contact(Department.CS.getName(), "ph390@scarletmail.rutgers.edu"), 90);
        Event event2 = new Event(new Date("09/28/2023"), Timeslot.EVENING, Location.MU302,
                new Contact(Department.CS.getName(), "ph390@scarletmail.rutgers.edu"), 90);
        boolean expectedOutput = true;
        boolean actualOutput = event1.equals(event2);
        System.out.println("**Test case #4: check if event1 and event2 will still be identical " +
                "with different location");
        testResult(event1, event2, expectedOutput, actualOutput);
    }
}
