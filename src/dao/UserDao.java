package dao;

import dao.domain.User;

import java.util.List;

public interface UserDao {
    /**
     * 添加一个用户
     *
     * @param user 用户信息
     * @return true 添加成功 false 失败
     */
    public boolean addUser(User user);

    /**
     * 根据用户的id删除一个用户--此接口不单独调用仅实现删除需调用deleteUsers接口
     *
     * @param id 用户id
     * @return true 成功 false 失败
     */
    public boolean deleteUser(String id);

    /**
     * 编辑用户信息
     *
     * @param id,password,email,downloadCnt 用户id，密码，邮箱，下载次数
     * @return true 成功 false 失败
     */
    public boolean editUser(String id, String password, String email, int downloadCnt);

    /**
     * 获取所有用户信息
     *
     * @return 所有用户信息
     */
    public List<User> getAllUser();

    /**
     * 根据账号密码查找该用户是否存在
     *
     * @param username 账号
     * @param password 密码
     * @return true 存在 false 不存在
     */
    public User getMatchUser(String username, String password);

    /**
     * 批量删除用户
     *
     * @param userIdList
     * @return true 成功 false 失败
     */
    public boolean deleteUsers(List<String> userIdList);

    /**
     * 管理员修改用户资料
     *
     * @param id 学号
     * @param userName 姓名
     * @param password 密码
     * @param email 邮箱
     * @param time 登录时间
     * @param downloadCnt 下载次数
     * @return true 成功 false 失败
     */
    public boolean adminChangeUser(String id, String userName, String password, String email, String time, int downloadCnt);
}
