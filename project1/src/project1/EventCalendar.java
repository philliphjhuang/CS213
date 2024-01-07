package project1;

/**
 * @author Yucong Liu, Phillip Huang
 * @create 2023/9/19 8:03
 */
public class EventCalendar {
    private Event[] events = new Event[4];
    private int numEvents = 0;

    /**
     * Search for a specific event from the events array
     * @param event to be found in the events array
     * @return index of the event in array if found, -1 otherwise
     */
    private int find(Event event) {
        for (int i = 0; i < numEvents; i++) {
            if (events[i].equals(event) && events[i] != null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Making the events array have a larger length through cloning
     */
    private void grow() {
        Event[] b = new Event[numEvents + 4];
        for (int i = 0; i < numEvents; i++) {
            b[i] = events[i];
        }
        events = b; // Setting the original array to the new one
    }

    /**
     * Add an event to the events array. Make events array larger
     * if not enough length
     * @param event to be added
     * @return true after the event is added
     */
    public boolean add(Event event) {
        if (numEvents % 4 == 0 && numEvents != 0) {
            grow();
        }

        events[numEvents++] = event;
        return true;
    }

    /**
     * Remove a specific event from events array
     * @param event to be removed
     * @return false if number of events is 0 or event not found in events array.
     * True if found and shifts all elements after the removed element to the left.
     */
    public boolean remove(Event event) {
        if (numEvents == 0) {
            return false;
        }
        int index = find(event);
        if (index == -1) {
            return false;
        }
        events[index] = null;
        for (int i = index; i < numEvents; i++) {
            if (index + 1 < numEvents) {
                events[index] = events[index + 1];
                index++;
            }
        }
        numEvents--;
        return true;
    }

    /**
     * Check if events array contain a specific event
     * @param event to look for in events array
     * @return true if found, false if not found
     */
    public boolean contains(Event event) {
        int i = find(event);
        if (i != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * P command: Print the events array
     */
    public void print() {
        if (numEvents == 0) {
            System.out.println("Event calendar is empty!");
            return;
        }else { System.out.println("* Event calendar *");
            for (int i = 0; i < numEvents; i++) {
                if (events[i] != null) {
                    System.out.println(events[i].toString());
                }
            }
            System.out.println("* end of event calendar *");
        }
    }

    /**
     * PE command: Print events array in order of event date and timeslot
     */
    public void printByDate() {
        int n = numEvents;
        if (n == 0) {
            System.out.println("Event calendar is empty!");
            return;
        }else {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (events[j].compareTo(events[j + 1]) > 0) {
                        // Swap elements in events array
                        Event temp = events[j];
                        events[j] = events[j + 1];
                        events[j + 1] = temp;
                    }
                }
            }
            System.out.println("* Event calendar by event date and start time *");
            for (int i = 0; i < numEvents; i++) {
                if (events[i] != null) {
                    System.out.println(events[i].toString());
                }
            }
            System.out.println("* end of event calendar *");
        }
    }

    /**
     * PC command: Print events array in order of campus and building/room
     */
    public void printByCampus() {
        int n = numEvents;
        if (n == 0) {
            System.out.println("Event calendar is empty!");
            return;
        }else {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    String currentCampus = events[j].getLocation().getCampus();
                    String nextCampus = events[j + 1].getLocation().getCampus();

                    String currentBuilding = events[j].getLocation().getName();
                    String nextBuilding = events[j + 1].getLocation().getName();

                    // Compare by campus
                    if (currentCampus.compareTo(nextCampus) > 0) {
                        // Directly swap elements in events array
                        Event temp = events[j];
                        events[j] = events[j + 1];
                        events[j + 1] = temp;
                    }
                    // If campuses are the same, then compare by building
                    else if (currentCampus.equals(nextCampus) && currentBuilding.compareTo(nextBuilding) > 0) {
                        // Directly swap elements in events array
                        Event temp = events[j];
                        events[j] = events[j + 1];
                        events[j + 1] = temp;
                    }
                }
            }
            System.out.println("* Event calendar by campus and building *");
            for (int i = 0; i < numEvents; i++) {
                if (events[i] != null) {
                    System.out.println(events[i].toString());
                }
            }
            System.out.println("* end of event calendar *");
        }

    }

    /**
     * PD command: Print events array in order of department name
     */
    public void printBytDepartment() {
        int n = numEvents;
        if (n == 0) {
            System.out.println("Event calendar is empty!");
            return;
        }else {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    if (events[j].getContact().getDepartment().getName()
                            .compareTo(events[j + 1].getContact().getDepartment().getName()) > 0) {
                        // Swap elements in events array
                        Event temp = events[j];
                        events[j] = events[j + 1];
                        events[j + 1] = temp;
                    }
                }
            }
            System.out.println("* Event calendar by department *");
            for (int i = 0; i < numEvents; i++) {
                if (events[i] != null) {
                    System.out.println(events[i].toString());
                }
            }
            System.out.println("* end of event calendar *");
        }
    }
}
