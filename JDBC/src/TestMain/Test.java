package TestMain;

import pojo.Departments;
import pojo.Dept;
import service.DepartmentService;
import service.impl.DepartmentServiceImpl;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        DepartmentService ds = new DepartmentServiceImpl();
        //ds.addDepartment(new Departments(0,"Service",20));
        //ds.modifyDepartment(new Departments(22, "Administration", 11));
        //ds.dropDepartment(18);
        List<Dept> list = ds.findDept("test");
        for(Dept d:list){
            System.out.println(d.getDepartment_id()+"\t"+d.getDepartment_name()+"\t"+d.getLocation_id());
        }
    }
}
