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

    private static final String MATCH_USER_SQL = "select * from user where id=? and password=?";
    private static final String ADD_USER_SQL = "insert into user values(?,?,?,?,?,?)";
    private static final String EDIT_USER_SQL = "update user set password=?,email=?,downloadCnt=? where id=?";
    private static final String DELETE_USER_SQL = "delete from user where id=?";
    private static final String LIST_USER_SQL = "select * from user";
    private static final String ADMIN_CHANGE_SQL = "update user set userName=?,password=?,email=?,time=?,downloadCnt=? where id=?";

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return true 成功 false 失败
     */
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
            ps.setString(6, user.getTime());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add user failed!");
            return false;
        }
        return true;
    }

    /**
     * 编辑用户信息
     *
     * @param id
     * @param password
     * @param email
     * @param downloadCnt
     * @return true 成功 false 失败
     */
    @Override
    public boolean editUser(String id, String password, String email, int downloadCnt) {
        System.out.println(id);
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(EDIT_USER_SQL);
            ps.setString(1, password);
            ps.setString(2, email);
            ps.setInt(3, downloadCnt);
            ps.setString(4, id);
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Edit user failed!");
            return false;
        }
        return true;
    }

    /**
     * 通过id删除用户
     *
     * @param id 用户id
     * @return true 成功 false 失败
     */
    @Override
    public boolean deleteUser(String id) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(DELETE_USER_SQL);
            ps.setString(1, id);
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Delete user failed");
            return false;
        }
        return true;
    }

    /**
     * 获取全部用户
     *
     * @return 成功返回全部用户的列表，失败返回null
     */
    @Override
    public List<User> getAllUser() {
        List<User> list = new ArrayList<>();
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(LIST_USER_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Get all user failed!");
            return null;
        }
        return list;
    }

    /**
     * 获取匹配的用户
     *
     * @param id       账号
     * @param password 密码
     * @return 成功返回用户，失败返回null
     */
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
            System.out.println("Get match user failed");
            return null;
        }
        return null;
    }

    /**
     * 通过ID批量删除用户
     *
     * @param userIdList Id列表
     * @return true 成功 false失败
     */
    @Override
    public boolean deleteUsers(List<String> userIdList) {
        for (String id : userIdList) {
            if (!deleteUser(id)) {
                System.out.println("Delete Users failed!");
                return false;
            }
        }
        return true;
    }

    /**
     * 管理员修改用户资料
     *
     * @param id          学号
     * @param userName    姓名
     * @param password    密码
     * @param email       邮箱
     * @param time        登录时间
     * @param downloadCnt 下载次数
     * @return true 成功 false 失败
     */
    public boolean adminChangeUser(String id, String userName, String password, String email, String time, int downloadCnt) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(ADMIN_CHANGE_SQL);
            ps.setString(1, userName);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, time);
            ps.setInt(5, downloadCnt);
            ps.setString(6, id);
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Admin edit user fail!");
            return false;
        }
        return true;
    }
}
