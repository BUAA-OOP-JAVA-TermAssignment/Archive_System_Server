package service;

import dao.DaoSet;
import dao.domain.User;

import java.util.List;

/**
 * 本类提供管理员对用户信息的编辑服务
 */
public class AdminUserEditService {
    /**
     * 添加用户
     *
     * @param id          用户id
     * @param userName    用户名
     * @param password    密码
     * @param email       邮箱
     * @param time        注册时间
     * @param downloadCnt 用户下载量
     * @return true 成功 false 失败
     */
    public boolean hasAdminAdd(String id, String userName, String password, String email, String time, int downloadCnt) {
        System.out.println("hasAdminAdd");
        return DaoSet.userDao.addUser(new User(id, userName, password, email, downloadCnt, time));
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return true 成功 false 失败
     */
    public boolean hasAdminDelete(String id) {
        System.out.println("hasAdminDelete");
        return DaoSet.userDao.deleteUser(id);
    }

    /**
     * 改变用户信息
     *
     * @param id          用户id
     * @param userName    用户名
     * @param password    密码
     * @param email       邮箱
     * @param time        注册时间
     * @param downloadCnt 用户下载量
     * @return true 成功 false 失败
     */
    public boolean hasAdminChange(String id, String userName, String password, String email, String time, int downloadCnt) {
        System.out.println("hasAdminChange");
        return DaoSet.userDao.adminChangeUser(id, userName, password, email, time, downloadCnt);
    }

    /**
     * 获取所有用户
     *
     * @return List<>User</>
     */
    public List<User> hasUserList() {
        System.out.println("hasUserList");
        return DaoSet.userDao.getAllUser();
    }
}
