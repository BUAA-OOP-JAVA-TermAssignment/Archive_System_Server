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
     * @param bean
     * @return
     */
    boolean delete(Document bean);

    /**
     * ���������ĵ�
     *
     * @param bean
     * @return
     */
    List<Document> find(Document bean);

    /**
     * ����ID�����ĵ�
     *
     * @param bean
     * @return
     */
    Document findById(Document bean);

    /**
     * ����ɾ���ĵ�
     *
     * @param documentList
     * @return
     */
    boolean deleteDocuments(List<Document> documentList);
}
