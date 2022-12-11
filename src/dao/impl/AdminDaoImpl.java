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

    private static final String MATCH_ADMIN_SQL = "select id,password from admin where id=? and password=?";
    private static final String ADD_ADMIN_SQL = "insert into admin values(?,?,?,?)";
    private static final String DELETE_ADMIN_SQL = "delete from admin where id=?";
    private static final String EDIT_ADMIN_SQL = "update admin set username=?,password=?,phone=?where id=?";
    private static final String LIST_ADMIN_SQL = "select * from admin";

    /**
     * ��ӹ���Ա
     *
     * @param admin ����Ա��Ϣ
     * @return true �ɹ� false ʧ��
     */
    @Override
    public boolean addAdmin(Admin admin) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(ADD_ADMIN_SQL);
            ps.setString(1, admin.getId());
            ps.setString(2, admin.getUserName());
            ps.setString(3, admin.getPassword());
            ps.setString(4, admin.getEmail());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add admin failed!");
            return false;
        }
        return true;
    }


    /**
     * �༭����Ա��Ϣ
     *
     * @param admin ����Ա��Ϣ
     * @return true �ɹ� false ʧ��
     */
    @Override
    public boolean editAdmin(Admin admin) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(EDIT_ADMIN_SQL);
            ps.setString(1, admin.getUserName());
            ps.setString(2, admin.getPassword());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getId());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Edit admin failed!");
            return false;
        }
        return true;
    }


    /**
     * ɾ������Ա
     *
     * @param id ����Աid
     * @return true �ɹ� false ʧ��
     */
    public boolean deleteAdmin(int id) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(DELETE_ADMIN_SQL);
            ps.setInt(1, id);
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Delete admin failed!");
            return false;
        }
        return true;
    }


    /**
     * ��ȡ���й���Ա
     *
     * @return �ɹ����ع���Ա�б�ʧ�ܷ���null
     */
    @Override
    public List<Admin> getAllAdmin() {
        List<Admin> list = new ArrayList<>();
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(LIST_ADMIN_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Admin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                //�ŵ�������
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Get all admin failed!");
            return null;
        }
        return list;
    }

    /**
     * ����ƥ��Ĺ���Ա
     *
     * @param id       �˺�
     * @param password ����
     * @return ����ƥ��Ĺ���Ա��ʧ�ܷ���null
     */
    @Override
    public Admin getMatchAdmin(String id, String password) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(MATCH_ADMIN_SQL);
            ps.setString(1, id);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Admin(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Get matched admin failed");
            return null;
        }
        return null;
    }

    /**
     * ����ɾ������Ա
     *
     * @param adminIdList ����ɾ������Ա
     * @return true �ɹ� false ʧ��
     */
    @Override
    public boolean deleteAdmins(List<Integer> adminIdList) {
        for (Integer id : adminIdList) {
            if (!deleteAdmin(id)) {
                System.out.println("Delete admins failed");
                return false;
            }
        }
        return true;
    }

}
