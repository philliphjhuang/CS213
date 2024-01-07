package project1;

/**
 * @author Yucong Liu, Phillip Huang
 * @create 2023/9/19 8:51
 */
public enum Timeslot {
    MORNING(10, 30),
    AFTERNOON(14, 00),
    EVENING(18, 30);

    private int hour;
    private int minute;

    /**
     * Constructor for Timeslot
     * @param hour
     * @param minute
     */
    Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Getter for hour
     * @return hour
     */
    public int getHour() {
        return hour;
    }

    /**
     * Getter for minute
     * @return minute
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Getter for time
     * @return String of hour and minute in 00:00 format
     */
    public String getTime() {
        return String.format("%02d:%02d", hour, minute);
    }

    /**
     * Re-calculate the hour and minute after adding an int of minute
     * @param minutesToAdd to hour and minute
     * @return String of newHour and newMinute in 00:00 format
     */
    public String addMinutes(int minutesToAdd) {
        int totalMinutes = hour * 60 + minute + minutesToAdd;
        int newHour = (totalMinutes / 60) % 24; // wrap around after 24 hours
        int newMinute = totalMinutes % 60;
        return String.format("%02d:%02d", newHour, newMinute);
    }
}
