package controller;

import dao.domain.User;
import message.AdminLoginMsg;
import message.UserLoginFailMsg;
import message.UserLoginMsg;
import message.UserLoginSuccessMsg;
import service.LoginService;

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
     * @param user 用户信息
     */
    public void userRegister(User user) {
        boolean isReg = loginService.userRegister(user);
        if (isReg) {
            System.out.println("注册成功！");
        } else {
            System.out.println("注册失败~");
        }
    }

    /**
     * 用户登录检查
     *
     * @param um 来自客户端的登入消息
     */
    public void userLoginCheck(UserLoginMsg um) {
        String username = um.getUsername();
        String password = um.getPassword();
        if (username != null && password != null) {
            boolean haveMatch = loginService.hasMatchUser(username, password);
            if (haveMatch) {
                um.getThread().sendMsgBack(new UserLoginSuccessMsg());
            } else {
                um.getThread().sendMsgBack(new UserLoginFailMsg());
            }
        } else {
            um.getThread().sendMsgBack(new UserLoginFailMsg());
        }

    }

    /**
     * 管理员登录检查
     *
     * @param am
     */
    public void adminLoginCheck(AdminLoginMsg am) {

        String username = am.getUsername();
        String password = am.getPassword();
        if (username != null && password != null) {
            boolean haveMatch = loginService.hasMatchAdmin(username, password);
            if (haveMatch) {
                am.getThread().sendMsgBack(new UserLoginSuccessMsg());
            } else {
                am.getThread().sendMsgBack(new UserLoginFailMsg());
            }
        } else {
            am.getThread().sendMsgBack(new UserLoginFailMsg());
        }
    }

}
