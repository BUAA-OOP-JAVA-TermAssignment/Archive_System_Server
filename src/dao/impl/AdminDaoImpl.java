package dao.impl;

import dao.AdminDao;
import dao.domain.Admin;
import dao.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDao {

    private static PreparedStatement ps = null;
    private static Connection cn = null;
    private static ResultSet rs = null;

    private static final String MATCH_ADMIN_SQL = "select username,password from admin where username=? and password=?";
    private static final String ADD_ADMIN_SQL = "insert into admin values(null,?,?,?)";
    private static String DELETE_ADMIN_SQL = "delete from admin where id=?";
    private static String EDIT_ADMIN_SQL = "update admin set username=?,password=?,phone=?where id=?";
    private static String LIST_ADMIN_SQL = "select * from admin";

    @Override
    public boolean addAdmin(Admin admin) {
        return AddEdit(admin, ADD_ADMIN_SQL);
    }

    @Override
    public boolean editAdmin(Admin admin) {
        return AddEdit(admin, EDIT_ADMIN_SQL);
    }

    private boolean AddEdit(Admin admin, String updateAdminSql) {
        cn = DBUtil.getConnection();
        try{
            assert cn != null;
            ps = cn.prepareStatement(updateAdminSql);
            ps.setString(1, admin.getUserName());
            ps.setString(2, admin.getPassword());
            ps.setString(3, admin.getPhone());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteAdmin(int id) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(DELETE_ADMIN_SQL);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Admin> getAllAdmin() {
        List<Admin> list = new ArrayList<>();
        cn = DBUtil.getConnection();
        try{
            assert cn != null;
            ps = cn.prepareStatement(LIST_ADMIN_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Admin(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4)));
                //放到集合中
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public boolean getMatchAdmin(String username, String password) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(MATCH_ADMIN_SQL);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                DBUtil.close(null, ps, cn);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



}
