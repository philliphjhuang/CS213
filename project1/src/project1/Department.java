package project1;

/**
 * @author Yucong Liu, Phillip Huang
 * @create 2023/9/19 8:43
 */
public enum Department {
    CS("Computer Science"),
    EE("Electrical Engineering"),
    ITI("Information Technology and Informatics"),
    MATH("Mathematics"),
    BAIT("Business Analytics and Information Technology"),
    ;


    private final String name;

    /**
     * Constructor for department
     * @param name
     */
    Department(String name) {
        this.name = name;
    }

    /**
     * Get name from department's enum
     * @return name
     */
    public String getName() {
        return name;
    }
}
