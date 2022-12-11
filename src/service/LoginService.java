package service;

import dao.DaoSet;
import dao.domain.Admin;
import dao.domain.User;

public class LoginService {

    /**
     * �����Ƿ���ƥ��Ĺ���Ա
     *
     * @param id       ����Աid
     * @param password ����Ա����
     * @return ����ƥ��Ĺ���Ա���������ڷ���null
     */
    public Admin hasMatchAdmin(String id, String password) {
        return DaoSet.adminDao.getMatchAdmin(id, password);
    }

    /**
     * �û�ע��
     *
     * @param user �û��ṹ�壬�����û���Ϣ
     * @return true �ɹ� false ʧ��
     */
    public boolean userRegister(User user) {
        return DaoSet.userDao.addUser(user);
    }

    /**
     * �����Ƿ���ƥ����û�
     *
     * @param id       �û�id
     * @param password �û�����
     * @return ����ƥ����û����������ڷ���null
     */
    public User hasMatchUser(String id, String password) {
        return DaoSet.userDao.getMatchUser(id, password);
    }
}
