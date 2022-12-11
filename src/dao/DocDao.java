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

    boolean addDownloadCnt(String docID);

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
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 批量查找文档
     *
     * @param idList
     * @return
     */
    List<Document> findById(List<String> idList);

    /**
     * 根据ID查找文档
     *
     * @param id
     * @return
     */
    Document findById(String id);

    /**
     * 根据ID批量删除文档
     *
     * @param idList
     * @return
     */
    boolean deleteDocuments(List<String> idList);
}
