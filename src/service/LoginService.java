package service;

import dao.DaoSet;
import dao.domain.User;

public class LoginService {

    public boolean hasMatchAdmin(String id, String password) {
        return DaoSet.adminDao.getMatchAdmin(id, password);
    }

    public boolean userRegister(User user){
        return DaoSet.userDao.addUser(user);
    }

    public boolean hasMatchUser(String id, String password){
        return DaoSet.userDao.getMatchUser(id, password);
    }
}
