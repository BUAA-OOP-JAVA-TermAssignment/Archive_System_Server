package service;

import dao.DaoSet;
import dao.domain.User;

public class LoginService {

    public boolean hasMatchAdmin(String username, String password) {
        return DaoSet.adminDao.getMatchAdmin(username, password);
    }

    public boolean userRegister(User user){
        return DaoSet.userDao.addUser(user);
    }

    public boolean hasMatchUser(String username, String password){
        return DaoSet.userDao.getMatchUser(username, password);
    }
}
