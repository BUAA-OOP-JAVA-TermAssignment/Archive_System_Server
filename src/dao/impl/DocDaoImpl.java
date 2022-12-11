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

    private static final String MATCH_DOC_SQL = "select * from document where id = ?";
    private static final String ADD_DOC_SQL = "insert into document values(?, ?, ?, ? ,0)";
    private static final String DELETE_DOC_SQL = "delete from document where id=?";
    private static final String EDIT_DOC_SQL = "update document set name=?, author=? where id=?";

    /**
     * 添加文档
     *
     * @param bean 文档对象
     * @return true 成功 false 失败
     */
    @Override
    public boolean add(Document bean) {
        if (bean.getContent() == null) {
            System.out.println("Try to illegally add a document null");
            return false;
        }

        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(ADD_DOC_SQL);
            ps.setString(1, bean.getId());
            ps.setString(2, bean.getName());
            ps.setString(3, bean.getAuthor());
            ps.setString(4, bean.getContent());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Add document failed!");
            return false;
        }
        return true;
    }

    /**
     * 更新文档信息
     * 注意：不会修改文档的正文
     *
     * @param bean 文档对象
     * @return true 成功 false 失败
     */
    @Override
    public boolean update(Document bean) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(EDIT_DOC_SQL);
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getAuthor());
            ps.setString(3, bean.getId());
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Update document failed!");
            return false;
        }
        return true;
    }


    /**
     * 删除文档
     *
     * @param id 文档ID
     * @return true 成功 false 失败
     */
    @Override
    public boolean delete(String id) {
        cn = DBUtil.getConnection();
        try {
            assert cn != null;
            ps = cn.prepareStatement(DELETE_DOC_SQL);
            ps.setString(1, id);
            int result = ps.executeUpdate();
            DBUtil.close(null, ps, cn);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Delete file failed!");
            return false;
        }
        return true;
    }

    /**
     * 通过Id寻找特定的文档
     *
     * @param idList 文档ID的列表
     * @return 成功返回文档列表，失败返回null
     */
    @Override
    public List<Document> findById(List<String> idList) {

        return null;
    }


    /**
     * 通过Id寻找特定的文档
     *
     * @param id 文档ID
     * @return 成功返回文档，失败返回null
     */
    @Override
    public Document findById(String id) {
        return null;
    }

    /**
     * 删除所有的文档
     *
     * @param idList 文档ID列表
     * @return true 成功 false 失败
     */
    @Override
    public boolean deleteDocuments(List<String> idList) {
        return false;
    }

}