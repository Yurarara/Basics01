package TestJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TestPrepStatement {
    public void insertDept(String name, int location_id){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConnection();
            /*
            Within prepared statements, a symbol '?' can be used as a dull character representing the
            (yet-to-fill) arguments.
             */
            ps = conn.prepareStatement("insert into departments values(0,?,?)");
            /*
            The set methods take 2 arguments.
            The first is an integer denoting the index of '?' to be set, while the second is the value of
            argument.
             */
            ps.setString(1, name);
            ps.setInt(2, location_id);
            System.out.println(ps.executeUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(ps, conn);
        }
    }

    /*
    select single result
     */
    public Departments selectDeptByID(int dept_id){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        Departments dept = null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement("select * from departments where department_id = ?");
            ps.setInt(1,dept_id);
            set = ps.executeQuery();
            while(set.next()){
                dept = new Departments();
                dept.setDept_id(set.getInt("department_id"));
                dept.setDept_name(set.getString("department_name"));
                dept.setLocation_id(set.getInt("location_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(ps, conn, set);
            return dept;
        }
    }

    /*
    select multiple result
     */
    public List<Departments> selectDeptByLike(String dept_name){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        List<Departments> list = new ArrayList<>();
        Departments dept = null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement("select * from departments where department_name like ?");
            ps.setString(1,"%"+dept_name+"%");
            set = ps.executeQuery();
            while(set.next()){
                dept = new Departments();
                dept.setDept_id(set.getInt("department_id"));
                dept.setDept_name(set.getString("department_name"));
                dept.setLocation_id(set.getInt("location_id"));
                list.add(dept);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(ps, conn, set);
            return list;
        }
    }


    /*
    batch operations
     */
    public void insertBatch(List<Departments> list){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement("insert into departments values(0,?,?)");
            //iterate for every entry in the list
            for(int i=0; i<list.size(); i++){
                ps.setString(1,list.get(i).getDept_name()+i);
                ps.setInt(2, list.get(i).getLocation_id());
                /*
                adding batch: this step adds the sql command line specified within the above iteration to a sql
                batch.
                After this line is added, the iteration continues and a new line is specified (and added).
                 */
                ps.addBatch();
            }
            int[] arr = ps.executeBatch(); //# of lines affected by each line in the batch
            for(int i:arr){
                System.out.print(i+"\t");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(ps, conn);
        }
    }

    public static void main(String[] args) {
        TestPrepStatement tps = new TestPrepStatement();
        /*
        Departments dept = tps.selectDeptByID(4);
        if(dept!=null){
            System.out.println(dept.getDept_id()+"\t"+dept.getDept_name()+"\t"+dept.getLocation_id());
        }

        List<Departments> list = tps.selectDeptByLike("test");
        for(Departments dept:list){
            System.out.println(dept.getDept_id()+"\t"+dept.getDept_name()+"\t"+dept.getLocation_id());
        }
         */
        List<Departments> list = new ArrayList<>();
        for(int i = 1;i<=5;i++){
            Departments dept = new Departments();
            dept.setDept_name("Test"+i+10);
            dept.setLocation_id(20+i);
            list.add(dept);
        }
        tps.insertBatch(list);
    }
}
