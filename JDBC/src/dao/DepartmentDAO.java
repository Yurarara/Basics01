package dao;

import pojo.Departments;
import pojo.Dept;

import java.util.List;

/**
 * An interface regulating methods to be realised via implementation.
 * The DAO layer should be at the bottom of the application (programme), and its functions should be designed for
 * multiple (potential) reuse.
 * Therefore, the method naming within this layer should focus on actual database operation instead of the action
 * where the method is to be activated.
 * e.g. insertDept() is better than addDepartment() under such circumstance.
 */
public interface DepartmentDAO extends BaseDAO {
    public int insertDept(Departments dept);
    public int updateDept(Departments dept);
    public int deleteDeptByID(int id);
    public List<Dept> selectDeptByNameLike(String name);
}
