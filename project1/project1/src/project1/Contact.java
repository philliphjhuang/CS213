package project1;

/**
 * @author Yucong Liu, Phillip Huang
 * @create 2023/9/19 8:11
 */
public class Contact {
    private Department department;
    private String email;

    /**
     * @param depName constructor for Department's names
     * @param email constructor for email
     */
    public Contact(String depName, String email) {
        for (Department department1 : Department.values()) {
            if (department1.name().equalsIgnoreCase(depName)) {
                this.department = department1;
                break;
            }
        }
        this.email = email;
    }

    /**
     * Check if department is not null and email matches a format
     * @return true if both department and email are valid, false otherwise
     */
    public boolean isValid() {
        boolean departmentIsValid = (department != null);
        boolean emailIsValid = email.matches("[a-zA-Z0-9._%+-]+@[rutgers]+\\.[edu]{2,}");

        return departmentIsValid && emailIsValid;
    }

    /**
     * Change Contact to a string
     * @return Contact with department and email
     */
    @Override
    public String toString() {
        return "Contact{" +
                "department=" + department +
                ", email='" + email + '\'' +
                '}';
    }

    /**
     * Getter for department
     * @return department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Setter for department
     * @param department
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * Getter for email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
