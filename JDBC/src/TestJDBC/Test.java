package TestJDBC;

import java.rmi.activation.ActivationGroup_Stub;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Test {
    public void insertDepartments(String name, int location_id) {
        Connection conn = null;
        Statement stt = null;
        try {
            conn = JDBCUtil.getConnection();
            stt = conn.createStatement();
            String insert = "insert into departments values(0,'"+name+"','"+location_id+"')";
            System.out.println(stt.executeUpdate(insert));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(stt, conn);
        }
    }
    public void updateDepartments(String name, int dpt_id, int location_id) {
        Connection conn = null;
        Statement stt = null;
        try {
            conn = JDBCUtil.getConnection();
            stt = conn.createStatement();
            String update = "update departments set department_name='" + name + "', location_id=" + location_id + " where department_id=" + dpt_id;
            System.out.println(stt.executeUpdate(update));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(stt, conn);
        }
    }

    public void selectByID(int dept_id){
        Connection conn = null;
        Statement stt = null;
        ResultSet set = null;
        try {
            conn = JDBCUtil.getConnection();
            stt = conn.createStatement();
            String select = "select * from departments where department_id = "+dept_id;
            //get a set of query results
            set = stt.executeQuery(select);
            while(set.next()){
                System.out.println(set.getInt("department_id")+"\t"+set.getString("department_name")+"\t"+set.getInt("location_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(stt, conn, set);
        }
    }

    //a method for page selection, taking args including current page and # of rows in every page
    public void selectPage(int cur, int row){
        Connection conn = null;
        Statement stt = null;
        ResultSet set = null;
        try {
            conn = JDBCUtil.getConnection();
            stt = conn.createStatement();
            String sql = "select * from departments";
            set = stt.executeQuery(sql);
            //starting and ending point
            int begin = (cur - 1) * row; //first row after previous page
            int end = cur * row; // last row of current page
            /*
            Pointer indicating the # of current selected row.
            It's position is in fact in accordance to the next() pointer.
             */
            int pointer = 0;
            while(set.next()){
                //define a condition under which the info is extracted
                if(pointer>=begin && pointer < end){
                    System.out.println(set.getInt("department_id")+"\t"+set.getString("department_name"));
                    /*
                    Condition for terminating iteration.
                    This block is necessary because the above code indicates that the iteration is executed throughout
                    the whole table, regardless whether the pointer condition is met.
                    Hence, adding the terminating condition helps save system resource and time.
                     */
                    if(pointer == end-1){
                        break;
                    }
                }
                pointer++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeUtil(stt, conn, set);
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        /*
        System.out.println(JDBCUtil.DRIVER+"\t"+JDBCUtil.URL+"\t"+JDBCUtil.USER+"\t"+JDBCUtil.PW);
        updateDepartments("Training", 6,6);
        insertDepartments("Test2",7);
         */
        test.selectPage(3,2);
    }

}
