package service;

import dao.DaoSet;

public class ModifyUserInfoService {
    /**
     * 用户修改自身信息
     *
     * @param id          id
     * @param password    密码
     * @param email       邮箱
     * @param downloadCnt 下载量
     * @return true 成功 false 失败
     */
    public boolean hasModify(String id, String password, String email, int downloadCnt) {
        System.out.println("hasModify");
        return DaoSet.userDao.editUser(id, password, email, downloadCnt);
    }
}
