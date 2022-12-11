package service;

import dao.DaoSet;

public class ModifyUserInfoService {
    public boolean hasModify(String id, String password, String email, int downloadCnt){
        System.out.println("hasModify");
        return DaoSet.userDao.editUser(id, password, email, downloadCnt);
    }
}
