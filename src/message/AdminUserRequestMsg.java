package message;

import data.AdminData;

import java.io.Serializable;
import java.util.ArrayList;


public class AdminUserRequestMsg extends BaseMsg {

    private ArrayList<User> userArrayList;


    public class User implements Serializable {
        String userName;
        String id;
        String password;
        int downloadCnt;
        String date;
        String email;

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

    public AdminUserRequestMsg() {
        super(ADMIN_USER_REQUEST);
        this.userArrayList = new ArrayList<>();
    }


    public int getUserNum() {
        if (userArrayList == null) {
            return 0;
        }
        return userArrayList.size();
    }

    public AdminUserRequestMsg.User getUserInfo(int i) {
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
