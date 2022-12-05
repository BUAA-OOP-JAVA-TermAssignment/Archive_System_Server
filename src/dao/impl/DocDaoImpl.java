package dao.impl;

import dao.DocDao;
import dao.domain.Document;

import java.util.List;

public class DocDaoImpl implements DocDao {
    @Override
    public boolean save(Document bean) {
        return true;
    }

    @Override
    public boolean update(Document bean) {
        return true;
    }

    @Override
    public boolean updateRemain(Document bean) {
        return true;
    }

    @Override
    public boolean delete(Document bean) {
        return true;
    }

    @Override
    public List<Document> find(Document bean) {
        return null;
    }

    @Override
    public Document findById(Document bean) {
        return null;
    }

    @Override
    public Document findByBno(Document bean) {
        return null;
    }
}
