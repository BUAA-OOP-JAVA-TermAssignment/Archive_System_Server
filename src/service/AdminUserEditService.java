package service;

import dao.DaoSet;
import dao.domain.User;

import java.util.List;

public class AdminUserEditService {
    public boolean hasAdminAdd(String id,String userName, String password, String email, String time, int downloadCnt){
        System.out.println("hasAdminAdd");
        return DaoSet.userDao.addUser(new User(id, userName, password, email, downloadCnt, time));
    }

    public boolean hasAdminDelete(String id){
        System.out.println("hasAdminDelete");
        return DaoSet.userDao.deleteUser(id);
    }

    public boolean hasAdminChange(String id,String userName, String password, String email, String time, int downloadCnt){
        System.out.println("hasAdminChange");
        return DaoSet.userDao.adminChangeUser(id, userName, password, email, time, downloadCnt);
    }

    public List<User> hasUserList(){
        System.out.println("hasUserList");
        return DaoSet.userDao.getAllUser();
    }
}
