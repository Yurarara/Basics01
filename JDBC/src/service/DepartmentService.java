package service;

import pojo.Departments;
import pojo.Dept;

import java.util.List;

/**
 * This interface regulates the actual function of the programme (i.e. facing user rather than database).
 * Therefore the method here should be named according to the result to be achieved.
 */
public interface DepartmentService {
    public void addDepartment(Departments dept);
    public int modifyDepartment(Departments dept);
    public int dropDepartment(int id);
    public List<Dept> findDept(String name);
}
