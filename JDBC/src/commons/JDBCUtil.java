package commons;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JDBCUtil {
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PW;
    /*
    static initialisation, including:
    1. register driver
    2. load url, uname & pw (by extracting info from a property file)
     */
    static{
        ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
        DRIVER = bundle.getString("DRIVER");
        URL = bundle.getString("URL");
        USER = bundle.getString("USERNAME");
        PW = bundle.getString("PASSWORD");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //establish connection
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL,USER, PW);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static void closeUtil(AutoCloseable... c){
        for(AutoCloseable clo:c){
            if(clo!=null){
                    try {
                        clo.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public static void rollback(Connection conn){
        try {
            if(conn!=null){
                conn.rollback();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
