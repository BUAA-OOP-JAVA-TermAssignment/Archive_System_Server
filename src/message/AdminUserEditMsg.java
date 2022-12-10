package message;

import data.UserData;
import view.AdminMainFrm;

import java.util.*;

/**
 * 管理员请求操作用户信息的消息
 *
 * @author : AkashiSensei
 * &#064;date  : 2022/12/8 20:57
 */
public class AdminUserEditMsg extends BaseMsg{
    private static volatile AdminUserEditMsg instance;
    public static final int REQUEST_INFO = 0;

    private int adminUserEditCode;
    private ArrayList<User> userArrayList = new ArrayList<>(){{
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("汉字", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
    }};
    class User {
        private String userName;
        private String id;
        private String password;
        private int downloadCnt;
        private String date;
        private String email;

        /**
         * 要注释掉！
         */
        User(String userName, String id, String password, int downloadCnt, String date,String email){
            this.userName = userName;
            this.id = id;
            this.password = password;
            this.downloadCnt = downloadCnt;
            this.date = date;
            this.email = email;
        }
    }

    /**
     * 要注释
     */


    private AdminUserEditMsg() {
        super(ADMIN_USER_EDIT);
    }

    public static AdminUserEditMsg createAdminUserEditMsg(){
        if(instance == null){
            synchronized (UserData.class){
                if(instance == null){
                    instance = new AdminUserEditMsg();
                }
            }
        }
        return instance;
    }

    /**
     * 在数据库上传时调用
     * @param userArrayList
     *
     */
    public void addUserMsg(ArrayList<User> userArrayList){
        this.userArrayList = userArrayList;
    }

    public void addEditCode(int adminUserEditCode){
        this.adminUserEditCode = adminUserEditCode;
    }

    public void changeDownloadCnt(int i){
        this.userArrayList.get(i).downloadCnt = 0;
    }

    public void changePassword(int i, String password){
        this.userArrayList.get(i).password = password;
    }

    public void changeEmail(int i, String email){
        this.userArrayList.get(i).email = email;
    }

    public int getUserNum(){
        if(userArrayList == null){
            return 0;
        }
        return userArrayList.size();
    }
    public User getUserInfo(int i){
        return userArrayList.get(i);
    }

    public String getUserName(User user){
        return user.userName;
    }

    public String getUserId(User user){
        return user.id;
    }

    public String getUserEmail(User user){
        return user.email;
    }

    public String getUserPassword(User user){
        return user.password;
    }

    public int getUserDownloadCnt(User user){
        return user.downloadCnt;
    }

    public String getUserDate(User user){
        return user.date;
    }
}
