package dao;

public interface AdminDao {

    /**
     * 查找是否存在一个账号与输入的账号密码匹配
     * @return 真代表存在 假代表不存在
     */
    public boolean getMatchAdmin(String account, String password);
}
