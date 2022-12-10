package service;

import dao.DaoSet;
import dao.domain.Admin;
import dao.domain.User;

public class LoginService {

    public Admin hasMatchAdmin(String id, String password) {
        return DaoSet.adminDao.getMatchAdmin(id, password);
    }

    public boolean userRegister(User user){
        return DaoSet.userDao.addUser(user);
    }

    public User hasMatchUser(String id, String password){
        return DaoSet.userDao.getMatchUser(id, password);
    }
}
