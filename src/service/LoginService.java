package service;

import dao.DaoSet;
import dao.domain.Admin;
import dao.domain.User;

public class LoginService {

    /**
     * �����Ƿ���ƥ��Ĺ���Ա
     *
     * @param id
     * @param password
     * @return
     */
    public Admin hasMatchAdmin(String id, String password) {
        return DaoSet.adminDao.getMatchAdmin(id, password);
    }

    /**
     * �û�ע��
     *
     * @param user
     * @return
     */
    public boolean userRegister(User user) {
        return DaoSet.userDao.addUser(user);
    }

    /**
     * �����Ƿ���ƥ����û�
     *
     * @param id
     * @param password
     * @return
     */
    public User hasMatchUser(String id, String password) {
        return DaoSet.userDao.getMatchUser(id, password);
    }
}
