package controller;

import dao.domain.User;
import message.FailMsg;
import message.SuccessMsg;
import request.AdminLoginRequst;
import request.UserLoginRequst;
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
    public void userLoginCheck(UserLoginRequst um) {
        String username = um.getUsername();
        String password = um.getPassword();
        if (username != null && password != null) {
            boolean haveMatch = loginService.hasMatchUser(username, password);
            if (haveMatch) {
                um.getThread().sendMsgBack(new SuccessMsg());
            } else {
                um.getThread().sendMsgBack(new FailMsg());
            }
        } else {
            um.getThread().sendMsgBack(new FailMsg());
        }

    }

    /**
     * 管理员登录检查
     *
     * @param am
     */
    public void adminLoginCheck(AdminLoginRequst am) {

        String username = am.getUsername();
        String password = am.getPassword();
        if (username != null && password != null) {
            boolean haveMatch = loginService.hasMatchAdmin(username, password);
            if (haveMatch) {
                am.getThread().sendMsgBack(new SuccessMsg());
            } else {
                am.getThread().sendMsgBack(new FailMsg());
            }
        } else {
            am.getThread().sendMsgBack(new FailMsg());
        }
    }

}
