package dao.impl;

import commons.JDBCUtil;
import dao.DepartmentDAO;
import pojo.Departments;
import pojo.Dept;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the implementation of the DAO interface.
 */
public class DepartmentDAOImpl extends BaseDAOImpl implements DepartmentDAO {
    @Override
    public List<Departments> selectDeptByName(String name) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        List<Departments> list = new ArrayList<>();
        try{
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement("select * from departments where department_name = ?");
            ps.setString(1,name);
            set = ps.executeQuery();
            while(set.next()){
                Departments d = new Departments();
                d.setDept_id(set.getInt("department_id"));
                d.setDept_name(set.getString("department_name"));
                d.setLocation_id(set.getInt("location_id"));
                list.add(d);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JDBCUtil.closeUtil(conn,ps);
        }
        return list;
    }



    @Override
    public int insertDept(Departments dept) {
        String sql = "insert into departments values(0, ?, ?)";
        Object[] args = new Object[]{dept.getDept_name(), dept.getLocation_id()};
        return executeUpd(sql,args);
    }

    @Override
    public int updateDept(Departments dept) {
        String sql = "update departments set department_name=?, location_id=? where department_id=?";
        Object[] args = new Object[]{dept.getDept_name(), dept.getLocation_id(), dept.getDept_id()};
        return executeUpd(sql,args);
    }

    @Override
    public int deleteDeptByID(int id) {
        String sql = "delete from departments where department_id = ?";
        Object[] args = new Object[]{id};
        return executeUpd(sql, args);
    }

    @Override
    public List<Dept> selectDeptByNameLike(String name) {
        String sql = "select * from departments where department_name like ?";
        Object[] args = new Object[] {"%"+name+"%"};
        return select(sql, args, Dept.class);
    }
}
