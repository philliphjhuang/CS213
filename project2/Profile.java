
/**
 * @author Yucong Liu
 */

public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor for Profile
     * @param fname
     * @param lname
     * @param dob
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Compares a Profile's first/last name and date of birth to another Profile
     * @param other the object to be compared.
     * @return 0 if two Profiles are the exact same, otherwise the difference between last name, first name, or date
     */
    @Override
    public int compareTo(Profile other) {
        int lastNameComparison = lname.toLowerCase().compareTo(other.lname.toLowerCase());
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }
        int firstNameComparison = fname.toLowerCase().compareTo(other.fname.toLowerCase());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }
        return this.dob.compareTo(other.dob);
    }

    /**
     * Get a Profile's first name, last name, and date of birth and make them into a String
     * @return String
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob;
    }

    /**
     * Compares Profile with Object o
     * @param o
     * @return true if first name, last name, and date of birth are the same, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Profile)) return false;
        Profile profile = (Profile) o;
        return fname.toLowerCase().equals(profile.fname.toLowerCase()) && lname.toLowerCase().
                equals(profile.lname.toLowerCase()) && dob.equals(profile.dob);
    }

    /**
     * Getter for first name
     * @return fname
     */
    public String getFname() {
        return fname;
    }

    /**
     * Setter for first name
     * @param fname
     */
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * Getter for last name
     * @return lname
     */
    public String getLname() {
        return lname;
    }

    /**
     * Setter for last name
     * @param lname
     */
    public void setLname(String lname) {
        this.lname = lname;
    }

    /**
     * Getter for date of birth
     * @return dob
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Setter for date of birth
     * @param dob
     */
    public void setDob(Date dob) {
        this.dob = dob;
    }
}
