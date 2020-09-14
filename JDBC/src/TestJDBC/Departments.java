package TestJDBC;

/**
 * Objects of this class stores information extracted from table Departments in the database.
 */
public class Departments {
    private int dept_id;
    private String dept_name;
    private int location_id;

    public Departments(int dept_id, String dept_name, int location_id) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        this.location_id = location_id;
    }

    public Departments() {
    }

    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(int dept_id) {
        this.dept_id = dept_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
}
