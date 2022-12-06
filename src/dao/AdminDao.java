package dao;

public interface AdminDao {

    /**
     * 查找是否存在一个管理员账号与输入的账号密码匹配
     *
     * @param username 账号
     * @param password 密码
     * @return true 存在 false 不存在
     */
    public boolean getMatchAdmin(String username, String password);
}
