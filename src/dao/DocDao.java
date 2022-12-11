package dao;

import dao.domain.Document;

import java.util.List;

public interface DocDao {
    /**
     * ����ĵ�
     *
     * @param bean
     * @return
     */
    boolean add(Document bean);

    boolean addDownloadCnt(String docID);

    /**
     * �����ĵ�
     *
     * @param bean
     * @return
     */
    boolean update(Document bean);

    /**
     * ɾ���ĵ�
     *
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * ���������ĵ�
     *
     * @param idList
     * @return
     */
    List<Document> findById(List<String> idList);

    /**
     * ����ID�����ĵ�
     *
     * @param id
     * @return
     */
    Document findById(String id);

    /**
     * ����ID����ɾ���ĵ�
     *
     * @param idList
     * @return
     */
    boolean deleteDocuments(List<String> idList);
}
