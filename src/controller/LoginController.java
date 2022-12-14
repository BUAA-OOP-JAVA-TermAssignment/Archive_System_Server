package controller;

import dao.domain.Admin;
import dao.domain.User;
import message.BaseMsg;
import message.LoginReturnMsg;
import request.UserLoginRequst;
import request.UserRegisterRequest;
import service.LoginService;

import java.util.Date;

/**
 * @author pcpas
 */
public class LoginController {

    private static LoginController lc;

    private LoginService loginService;

    private LoginController() {
        setLoginService(new LoginService());
    }

    /**
     * 线程安全的单例模式
     *
     * @return LoginController实例
     */
    public static LoginController getInstance() {
        if (lc == null) {
            synchronized (LoginController.class) {
                if (lc == null) {
                    lc = new LoginController();
                }
            }
        }
        return lc;
    }

    /**
     * 设定LoginService
     *
     * @param ls login-service
     */
    public void setLoginService(LoginService ls) {
        loginService = ls;
    }

    /**
     * 用户注册
     *
     * @param um 来自客户端的注册消息
     */
    public void userRegister(UserRegisterRequest um) {
        User user = new User(um.getId(), um.getName(), um.getPassword(), um.getEmail(), 0, new Date().toString());
        boolean isReg = loginService.userRegister(user);
        if (isReg) {
            System.out.println("Register Success");
            um.getThread().sendMsgBack(new BaseMsg(BaseMsg.SUCCESS));
        } else {
            System.out.println("Register Failed");
            um.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
        }
    }

    /**
     * 用户和管理员登录检查
     *
     * @param um 来自客户端的登入消息
     */
    public void userLoginCheck(UserLoginRequst um) {
        System.out.println("Receive login request");
        int userType = um.getUserType();
        String id = um.getId();
        String password = um.getPassword();
        if (id != null && password != null) {
            if (userType == 1) {
                User user = loginService.hasMatchUser(id, password);
                if (user != null) {
                    System.out.println("Match user success");
                    um.getThread().sendMsgBack(LoginReturnMsg.createLoginReturnMsg(user.getUserName(), user.getId(), user.getEmail(), user.getPassword(), user.getDownloadCnt(), user.getTime()));
                } else {
                    System.out.println("No matched user!");
                    um.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
                }
            } else if (userType == 2) {
                Admin admin = loginService.hasMatchAdmin(id, password);
                if (admin != null) {
                    System.out.println("Match admin success");
                    um.getThread().sendMsgBack(LoginReturnMsg.createLoginReturnMsg(admin.getUserName(), admin.getId(), admin.getEmail(), admin.getPassword(), 0, admin.getTime()));
                } else {
                    System.out.println("No matched admin!");
                    um.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
                }
            } else {
                um.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
            }
        } else {
            um.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
        }
    }

}
