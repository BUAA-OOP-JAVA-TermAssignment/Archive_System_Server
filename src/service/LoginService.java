package service;

import dao.DaoSet;
import dao.domain.Admin;
import dao.domain.User;

public class LoginService {

    /**
     * 查找是否有匹配的管理员
     *
     * @param id
     * @param password
     * @return
     */
    public Admin hasMatchAdmin(String id, String password) {
        return DaoSet.adminDao.getMatchAdmin(id, password);
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    public boolean userRegister(User user) {
        return DaoSet.userDao.addUser(user);
    }

    /**
     * 查找是否有匹配的用户
     *
     * @param id
     * @param password
     * @return
     */
    public User hasMatchUser(String id, String password) {
        return DaoSet.userDao.getMatchUser(id, password);
    }
}
