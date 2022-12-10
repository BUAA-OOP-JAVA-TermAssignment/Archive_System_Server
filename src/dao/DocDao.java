package dao;

import dao.domain.Document;

import java.util.List;

public interface DocDao {
    /**
     * 添加文档
     *
     * @param bean
     * @return
     */
    boolean add(Document bean);

    /**
     * 更新文档
     *
     * @param bean
     * @return
     */
    boolean update(Document bean);

    /**
     * 删除文档
     *
     * @param bean
     * @return
     */
    boolean delete(Document bean);

    /**
     * 批量查找文档
     *
     * @param bean
     * @return
     */
    List<Document> find(Document bean);

    /**
     * 根据ID查找文档
     *
     * @param bean
     * @return
     */
    Document findById(Document bean);

    /**
     * 批量删除文档
     *
     * @param documentList
     * @return
     */
    boolean deleteDocuments(List<Document> documentList);
}
