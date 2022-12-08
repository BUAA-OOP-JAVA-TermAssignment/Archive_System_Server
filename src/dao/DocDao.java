package dao;

import dao.domain.Document;

import java.util.List;

public interface DocDao {
    boolean save(Document bean);

    boolean update(Document bean);

    boolean updateRemain(Document bean);

    boolean delete(Document bean);

    List<Document> find(Document bean);

    Document findById(Document bean);

    boolean deleteDocuments(List<Document> documentList);
}
