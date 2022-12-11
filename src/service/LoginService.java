package service;

import dao.DaoSet;
import dao.domain.Admin;
import dao.domain.User;

public class LoginService {

    /**
     * 查找是否有匹配的管理员
     *
     * @param id       管理员id
     * @param password 管理员密码
     * @return 返回匹配的管理员，若不存在返回null
     */
    public Admin hasMatchAdmin(String id, String password) {
        return DaoSet.adminDao.getMatchAdmin(id, password);
    }

    /**
     * 用户注册
     *
     * @param user 用户结构体，包含用户信息
     * @return true 成功 false 失败
     */
    public boolean userRegister(User user) {
        return DaoSet.userDao.addUser(user);
    }

    /**
     * 查找是否有匹配的用户
     *
     * @param id       用户id
     * @param password 用户密码
     * @return 返回匹配的用户，若不存在返回null
     */
    public User hasMatchUser(String id, String password) {
        return DaoSet.userDao.getMatchUser(id, password);
    }
}
