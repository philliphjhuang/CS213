package project1;

import java.util.Calendar;
import java.util.Scanner;

/**
 * @author Yucong Liu
 * @create 2023/9/20 8:49
 */
public class EventOrganizer {

    /**
     * Run Scanner, get EventCalendar, and read commands from input.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        project1.EventCalendar eventCalender = new project1.EventCalendar();
        System.out.println("Event Organizer running..."); // Skip this iteration
        // and move on to the next one
        while (true) {
            String add = scanner.nextLine();
            String[] str = add.split(" ");
            String[] st = removeEmptyStrings(str);

            // Check if the line is empty
            if (add.isEmpty()) {
                continue; // Skip this iteration and move on to the next one
            }
            String command = st[0];
            if (command.equals("A")) {
                add(eventCalender, st);
            } else if (command.equals("R")) {
                remove(eventCalender, st);
            } else if (command.equals("P")) {
                eventCalender.print();
            } else if (command.equals("PE")) {
                eventCalender.printByDate();
            } else if (command.equals("PC")) {
                eventCalender.printByCampus();
            } else if (command.equals("PD")) {
                eventCalender.printBytDepartment();
            } else if (add.equals("Q")) {
                System.out.println("Event Organizer terminated.");
                break;
            }else {
                System.out.println(command + " is an invalid comment!");
            }
        }
    }

    /**
     * Remove an event from eventCalendar
     * @param eventCalender
     * @param str of the event with date, location, and contact
     */
    private static void remove(project1.EventCalendar eventCalender, String[] str) {
        if (str.length != 4) {
            System.out.println("Four parameters are required");
            return; // return early to avoid further processing
        }
        project1.Date date = getDate(str);
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.set(date.getYear(), date.getMonth() - 1, date.getDay());
        calendarDate.set(Calendar.HOUR_OF_DAY, 0);
        calendarDate.set(Calendar.MINUTE, 0);
        calendarDate.set(Calendar.SECOND, 0);
        calendarDate.set(Calendar.MILLISECOND, 0);
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        if (date == null || !date.isValid()) {
            System.out.println(str[1] + ": Invalid calendar date!");
            return;
        }
        if (!isFutureDate(calendarDate, today)) {
            System.out.println(str[1] + ": Event date must be a future date!");
            return;
        }
        if (!isWithinSixMonths(calendarDate, today)) {
            System.out.println(str[1] + ": Event date must be within 6 months!");
            return;
        }
        project1.Timeslot timeslot = getTimeslot(str);
        project1.Location location = getLocation(str);
        if (timeslot == null || location == null) {
            return; // if either timeslot or location is invalid, stop processing
        }
        project1.Event event = new project1.Event(date, timeslot, location, null, 0);
        if (eventCalender.contains(event)) {
            eventCalender.remove(event);
            System.out.println("Event has been removed from the calendar!");
        } else {
            System.out.println("Cannot remove; event is not in the calendar!");
        }
    }

    /**
     * Add an event in String to eventCalendar
     * @param eventCalender
     * @param str
     * @return false if successfully added the event to eventCalendar, false otherwise
     */
    private static boolean add(project1.EventCalendar eventCalender, String[] str) {
        if (str.length != 7) {
            System.out.println("Event added to the calendar.");
            return true;
        }
        project1.Date date1 = getDate(str);
        project1.Timeslot timeslot = getTimeslot(str);
        project1.Location local = getLocation(str);

        if (local != null && timeslot != null) {
            String dep = str[4];
            String email = str[5];
            Integer duration = Integer.valueOf(str[6]);

            if (duration < 30 || duration > 120) {
                System.out.println("Event duration must be at least 30 minutes and at most 120 minute");
                return true;
            }

            project1.Contact contact = new project1.Contact(dep, email);
            if (!contact.isValid()) {
                System.out.println("Invalid contact information!");
                return true;
            }
            project1.Event event = new project1.Event(date1, timeslot, local, contact, duration);

            if (eventCalender.contains(event)) {
                // 存在
                System.out.println("The event is already on the calendar.");
                return true;
            }
            if (!event.getDate().isValid()) {
                System.out.println(str[1] + ":Invalid calendar date!");
                return true;
            }

            Calendar calendarDate = Calendar.getInstance();
            calendarDate.set(date1.getYear(), date1.getMonth() - 1, date1.getDay()); // Months
            // in
            // Calendar
            // are
            // 0-based
            calendarDate.set(Calendar.HOUR_OF_DAY, 0); // Resetting hour,
            // minute, second, and
            // millisecond to make
            // sure
            calendarDate.set(Calendar.MINUTE, 0); // we're only comparing the
            // date part
            calendarDate.set(Calendar.SECOND, 0);
            calendarDate.set(Calendar.MILLISECOND, 0);

            Calendar today = Calendar.getInstance();
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);

            if (!isFutureDate(calendarDate, today)) {
                System.out.println(formatCalendarDate(calendarDate) + ": Event date must be a future date!");
                return true;
            }
            if (!isWithinSixMonths(calendarDate, today)) {
                System.out.println(formatCalendarDate(calendarDate) + ": Event date must be within 6 months!");
                return true;
            }
            eventCalender.add(event);
            System.out.println("Event added to the calendar.");
            return false;
        }
        return true;
    }

    /**
     * Check if date is after current date
     * @param date
     * @param current
     * @return true if date is after current, false otherwise
     */
    private static boolean isFutureDate(Calendar date, Calendar current) {
        return date.after(current);
    }

    /**
     * Check if date is within 6 months of current date
     * @param date
     * @param current
     * @return true if date is within 6 months of current, false otherwise
     */
    private static boolean isWithinSixMonths(Calendar date, Calendar current) {
        current.add(Calendar.MONTH, 6);
        return !date.after(current);
    }

    /**
     * Convert calendar date to a string
     * @param calendar
     * @return String of day, month, year in a format
     */
    private static String formatCalendarDate(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.YEAR);
    }

    /**
     * Convert the String of location into a Location variable
     * @param str of location
     * @return local if valid, null otherwise
     */
    private static project1.Location getLocation(String[] str) {
        String location = str[3];
        project1.Location local = null;
        try {
            local = project1.Location.valueOf(location.toUpperCase());
        } catch (Exception e) {
            System.out.println("Invalid location!");
            return null;
        }
        return local;
    }

    /**
     * Convert the String of timeslot into a Timeslot variable
     * @param str of timeslot
     * @return timeslow if valid, null otherwise
     */
    private static project1.Timeslot getTimeslot(String[] str) {
        String time = str[2];
        project1.Timeslot timeslot = null;
        try {
            timeslot = project1.Timeslot.valueOf(time.toUpperCase());
        } catch (Exception e) {
            System.out.println("Invalid time slot!");
            return null;
        }
        return timeslot;
    }

    /**
     * Convert the String of date into a Date variable
     * @param str of date
     * @return date1
     */
    private static project1.Date getDate(String[] str) {
        String[] date = str[1].split("/");
        if (date.length != 3) {
            System.out.println("Time parameter error");
        }
        project1.Date date1 = new project1.Date(Integer.valueOf(date[2]), Integer.valueOf(date[0]),
                Integer.valueOf(date[1]));
        return date1;
    }

    /**
     * Remove empty Strings and shifting the elemnts
     * @param str
     * @return st with no empty Strings
     */
    public static String[] removeEmptyStrings(String[] str) {
        int idx = 0;
        for (int i = 0; i < str.length; i++) {
            if (!str[i].equals("")) {
                str[idx++] = str[i];
            }
        }
        String[] st = new String[idx];
        for (int i = 0; i < idx; i++) {
            st[i] = str[i];
        }
        return st;
    }
}
