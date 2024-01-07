package project1;

/**
 * @author Yucong Liu, Phillip Huang
 * @create 2023/9/19 8:50
 */
public enum Location {
    HLL114("Hill Center", "Busch"),
    ARC103("Allison Road Classroom", "Busch"),
    BE_AUD("Beck Hall", "Livingston"),
    TIL232("Tillett Hall", "Livingston"),
    AB2225("Academic Building", "College Avenue"),
    MU302("Murray Hall", "College Avenue"),
    ;
    private String buildName;
    private String campus;

    /**
     * Constructor for Location
     * @param buildName
     * @param campus
     */
    Location(String buildName, String campus) {
        this.buildName = buildName;
        this.campus = campus;
    }

    /**
     * Get the name of building and campus
     * @return buildName and campus
     */
    public String getBuildName() {
        return buildName;
    }

    /**
     * Getter for campus
     * @return campus
     */
    public String getCampus() {
        return campus;
    }

    /**
     * Getter for building name and campus
     * @return buildName and campus
     */
    public String getName(){
        return buildName + ", " + campus;
    }


}
