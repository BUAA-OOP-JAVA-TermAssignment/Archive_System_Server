package dao;

import dao.domain.Admin;
import dao.domain.User;

import java.util.List;

public interface AdminDao {
    /**
     * 添加一个管理员
     *
     * @param admin 管理员信息
     * @return true 添加成功 false 失败
     */
    public boolean addAdmin(Admin admin);

    /**
     * 根据用户的id删除一个管理员
     *
     * @param id 管理员id
     * @return true 成功 false 失败
     */
    public boolean deleteAdmin(int id);

    /**
     * 编辑管理员信息
     *
     * @param admin 管理员信息
     * @return true 成功 false 失败
     */
    public boolean editAdmin(Admin admin);

    /**
     * 获取所有管理员信息
     *
     * @return 所有管理员信息
     */
    public List<Admin> getAllAdmin();
    /**
     * 查找是否存在一个管理员账号与输入的账号密码匹配
     *
     * @param username 账号
     * @param password 密码
     * @return true 存在 false 不存在
     */
    public boolean getMatchAdmin(String username, String password);
}
