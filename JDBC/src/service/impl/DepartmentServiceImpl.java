package service.impl;

import dao.DepartmentDAO;
import dao.impl.DepartmentDAOImpl;
import pojo.Departments;
import pojo.Dept;
import service.DepartmentService;

import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public void addDepartment(Departments dept) {
        DepartmentDAO dept_dao = new DepartmentDAOImpl();
        dept_dao.insertDept(dept);
    }

    @Override
    public int modifyDepartment(Departments dept) {
        DepartmentDAO dept_dao = new DepartmentDAOImpl();
        return dept_dao.updateDept(dept);
    }

    public int dropDepartment(int id){
        DepartmentDAO dept_dao = new DepartmentDAOImpl();
        return dept_dao.deleteDeptByID(id);
    }

    @Override
    public List<Dept> findDept(String name) {
        DepartmentDAO dept_dao = new DepartmentDAOImpl();
        return dept_dao.selectDeptByNameLike(name);
    }
}
