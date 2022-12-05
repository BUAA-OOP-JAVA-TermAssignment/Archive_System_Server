package service;

import dao.DaoSet;

public class LoginService {

    public boolean hasMatchAdmin(String account, String password) {
        return DaoSet.adminDao.getMatchAdmin(account, password);
    }

}
