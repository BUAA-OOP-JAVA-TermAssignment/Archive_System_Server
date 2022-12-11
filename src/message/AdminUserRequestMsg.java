package message;

import java.util.ArrayList;

public class AdminUserRequestMsg extends BaseMsg {
    public static final int REQUEST_INFO = 0;

    private ArrayList<User> userArrayList = new ArrayList<>() {{
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
        add(new User("ºº×Ö", "12345678", "wefwe", 3, "123//33", "cdcdcd@qwe"));
    }};


    public class User {
        private String userName;
        private String id;
        private String password;
        private int downloadCnt;
        private String date;
        private String email;

        /**
         * Òª×¢ÊÍµô£¡
         */
        User(String userName, String id, String password, int downloadCnt, String date, String email) {
            this.userName = userName;
            this.id = id;
            this.password = password;
            this.downloadCnt = downloadCnt;
            this.date = date;
            this.email = email;
        }
    }

    private AdminUserRequestMsg(ArrayList<User> userArrayList) {
        super(ADMIN_USER_REQUEST);
        this.userArrayList = userArrayList;
    }

    public static AdminUserRequestMsg createAdminUserRequestMsg(ArrayList<User> userArrayList) {
        return new AdminUserRequestMsg(userArrayList);
    }


    public int getUserNum() {
        if (userArrayList == null) {
            return 0;
        }
        return userArrayList.size();
    }

    public User getUserInfo(int i) {
        return userArrayList.get(i);
    }

    public String getUserName(int idx) {
        return userArrayList.get(idx).userName;
    }

    public String getUserId(int idx) {
        return userArrayList.get(idx).id;
    }

    public String getUserEmail(int idx) {
        return userArrayList.get(idx).email;
    }

    public String getUserPassword(int idx) {
        return userArrayList.get(idx).password;
    }

    public int getUserDownloadCnt(int idx) {
        return userArrayList.get(idx).downloadCnt;
    }

    public String getUserDate(int idx) {
        return userArrayList.get(idx).date;
    }
}
