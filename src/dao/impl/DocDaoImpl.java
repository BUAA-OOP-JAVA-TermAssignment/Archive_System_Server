package dao.impl;

import dao.DocDao;
import dao.domain.Document;
import dao.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocDaoImpl implements DocDao {

    private static PreparedStatement ps = null;
    private static Connection cn = null;
    private static ResultSet rs = null;

    private static final String MATCH_DOC_SQL = "select Id from Document where Id=?";
    private static final String ADD_DOC_SQL = "insert into Document values(?, ?, ?, ?, ?, ?, ?, ?,0)";
    private static final String DELETE_DOC_SQL = "delete from Document where Id=?";
    private static final String EDIT_DOC_SQL = "update Document set name=?, author=?, publish=?, introduction=?, language=?";
    private static final String LIST_DOC_SQL = "select * from Document where name like ? or introduction like ?";

    @Override
    public boolean add(Document bean) {
        if (bean.getContent() == null) {
            System.out.println("尝试非法添加空文档");
            return false;
        }

        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(ADD_DOC_SQL);
            ps.setString(1, bean.getId());
            ps.setString(2, bean.getName());
            ps.setString(3, bean.getAuthor());
            ps.setString(4, bean.getPublish());
            ps.setString(5, bean.getIntroduction());
            ps.setString(6, bean.getLanguage());
            ps.setString(7, bean.getUploadDate().toString());
            ps.setString(8, bean.getContent());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Document bean) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(EDIT_DOC_SQL);
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getAuthor());
            ps.setString(3, bean.getPublish());
            ps.setString(4, bean.getIntroduction());
            ps.setString(5, bean.getLanguage());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean delete(Document bean) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(DELETE_DOC_SQL);
            ps.setString(1, bean.getId());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Document> find(Document bean) {
        List<Document> list = new ArrayList<>();
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(LIST_DOC_SQL);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Document(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    @Override
    public Document findById(Document bean) {
        cn = DBUtil.getConnection();
        Document result = null;
        try {
            assert cn != null;
            ps = cn.prepareStatement(MATCH_DOC_SQL);
            ps.setString(1, bean.getId());
            rs = ps.executeQuery();
            while (rs.next()) {
                result = new Document();
                result.setId(rs.getString("Id"));
                result.setName(rs.getString("name"));
                result.setAuthor(rs.getString("author"));
                result.setPublish(rs.getString("publish"));
                result.setIntroduction(rs.getString("introduction"));
                result.setLanguage(rs.getString("language"));
                result.setUploadDate(rs.getString("uploadDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public boolean deleteDocuments(List<Document> documentList) {
        for (Document bean : documentList) {
            if (!delete(bean)) {
                return false;
            }
        }
        return true;
    }
}
