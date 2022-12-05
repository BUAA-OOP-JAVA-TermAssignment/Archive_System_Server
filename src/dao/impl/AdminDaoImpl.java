package dao.impl;

import dao.AdminDao;
import dao.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDaoImpl implements AdminDao {

    private static PreparedStatement ps = null;
    private static Connection cn = null;
    private static ResultSet rs = null;

    private static final String MATCH_ADMIN_SQL = "select username,password from admin where username=? and password=?";

    @Override
    public boolean getMatchAdmin(String account, String password) {
        cn = DBUtil.getConnection();
        try {
            ps = cn.prepareStatement(MATCH_ADMIN_SQL);
            ps.setString(1, account);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
