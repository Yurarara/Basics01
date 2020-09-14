package TestJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestPrepStatement2 {

    /*
    The following delete method aims to test the transfer operations with JDBC.
    Therefore, the auto commission must be disabled to allow for save points and rollbacks.
     */
    public void deleteDept(String name){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false); //disable auto-commission
            ps = conn.prepareStatement("delete from departments where department_name like ?");
            ps.setString(1, "%"+name+"%");
            ps.execute();
            //the strings are solely for setting off an exception to test the rollback function.
            String str = null;
            str.length();
            conn.commit(); //commit. only needed when auto-commission is disabled.
        } catch (Exception e) {
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        } finally {
            JDBCUtil.closeUtil(ps, conn);
        }
    }

    public static void main(String[] args) {
        TestPrepStatement2 tps = new TestPrepStatement2();
        tps.deleteDept("Test");
    }
}
