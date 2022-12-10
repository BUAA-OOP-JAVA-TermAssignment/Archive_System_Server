package dao.impl;

import dao.UserDao;
import dao.domain.Admin;
import dao.domain.User;
import dao.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static PreparedStatement ps = null;
    private static Connection cn = null;
    private static ResultSet rs = null;

    private static final String MATCH_USER_SQL = "select id,password from user where username=? and password=?";
    private static final String ADD_USER_SQL = "insert into user values(?,?,?,?,?,?)";
    private static final String EDIT_USER_SQL = "update admin set username=?,password=?,email=?where id=?";
    private static final String DELETE_USER_SQL = "delete from admin where id=?";
    private static final String LIST_USER_SQL = "select * from user";
    @Override
    public boolean addUser(User user) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(ADD_USER_SQL);
            ps.setString(1, user.getId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setInt(5, user.getDownloadCnt());
            ps.setString(6, user.getTime().toString());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean editUser(User user) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(EDIT_USER_SQL);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getId());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean deleteUser(String id) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(DELETE_USER_SQL);
            ps.setString(1, id);
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<User> getAllUser() {
        List<User> list = new ArrayList<>();
        cn = DBUtil.getConnection();
        try{
            assert cn != null;
            ps = cn.prepareStatement(LIST_USER_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getString(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
                //放到集合中
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public User getMatchUser(String id, String password) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(MATCH_USER_SQL);
            ps.setString(1, id);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteUsers(List<String> userIdList) {
        for(String id : userIdList){
            if(!deleteUser(id)){
                return false;
            }
        }
        return true;
    }
}
