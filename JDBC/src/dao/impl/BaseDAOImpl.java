package dao.impl;

import commons.JDBCUtil;
import dao.BaseDAO;
import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseDAOImpl implements BaseDAO {
    @Override
    public int executeUpd(String sql, Object[] arg) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rows = 0;
        try {
            conn = JDBCUtil.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            //The following code is for parameter specifying via PreparedStatement.
            //1. get info regarding parameters within sql commands
            ParameterMetaData pmd = ps.getParameterMetaData();
            //2. for each parameter i, specify via arg[i]
            for(int i=0;i<pmd.getParameterCount();i++){
                ps.setObject(i+1, arg[i]);
            }
            rows = ps.executeUpdate();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
            JDBCUtil.rollback(conn);
        }finally{
            JDBCUtil.closeUtil(conn,ps);
        }
        return rows;
    }

    @Override
    public <T> List<T> select(String sql, Object[] args, Class<T> clazz) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();
        try{
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ParameterMetaData pmd = ps.getParameterMetaData();
            for(int i=0;i<pmd.getParameterCount();i++){
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            while(rs.next()){
                T t = clazz.getDeclaredConstructor().newInstance();
                for(int i=0;i<rsmd.getColumnCount();i++){ //getColumnCount() returns the # of columns in the table
                    //get column name
                    String columnName = rsmd.getColumnName(i+1);
                    //get column value
                    Object value = rs.getObject(columnName);
                    //IMPORTANT: transfer result to an object for storing via BeanUtils
                    BeanUtils.setProperty(t, columnName, value);
                }
                list.add(t);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtil.closeUtil(conn, ps, rs);
        }
        return list;
    }
}
