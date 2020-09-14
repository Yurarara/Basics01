package TestJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestAdvancedJDBC {
    /*
    Dynamic selecting: commit different selecting operations according to user-specifying conditions
    In this instance, a Department object is used to store these conditions.
    Therefore, the sql command is formed referring to the information within the object, rather than via a String.
     */
    public List<Departments> selectByProperty(Departments d){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        List<Departments> list = new ArrayList<>();
        try {
            String sql = createSQL(d);
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            set = ps.executeQuery();
            while(set.next()){
                Departments departments = new Departments();
                departments.setDept_id(set.getInt("department_id"));
                departments.setDept_name(set.getString("department_name"));
                departments.setLocation_id(set.getInt("location_id"));
                list.add(departments);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(ps, conn, set);
        }
        return list;
    }
    private String createSQL(Departments d){
        StringBuffer sb = new StringBuffer("select * from departments where 1=1");
        if(d.getDept_id()>0){
            sb.append(" and department_id=").append(d.getDept_id());
        }
        if(d.getDept_name()!=null && d.getDept_name().length()>0){
            sb.append(" and department_name='").append(d.getDept_name()).append("'");
        }
        if(d.getLocation_id()>0){
            sb.append("  and location_id=").append(d.getLocation_id());
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        TestAdvancedJDBC adv = new TestAdvancedJDBC();
        Departments dept = new Departments(0,"Training",0);
        List<Departments> list = adv.selectByProperty(dept);
        for(Departments d:list){
            System.out.println(d.getDept_id()+"\t"+d.getDept_name()+"\t"+d.getLocation_id());
        }

    }
}
