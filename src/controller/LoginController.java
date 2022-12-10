package controller;

import dao.domain.User;
import message.BaseMsg;
import message.LoginReturnMsg;
import message.AdminArchiveEditMsg;
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
     * 用户和管理员登录检查
     *
     * @param um 来自客户端的登入消息
     */
    public void userLoginCheck(UserLoginRequst um) {
        //TODO:这里返回值需要修改！
        String id = um.getId();
        String password = um.getPassword();
        if (id != null && password != null) {
            boolean haveMatch = loginService.hasMatchUser(id, password);
            System.out.println("成功查找到此人！");
            if (haveMatch) {
                um.getThread().sendMsgBack(new BaseMsg(BaseMsg.SUCCESS));
            } else {
                um.getThread().sendMsgBack(new BaseMsg(BaseMsg.UNDEFINED_FAILED));
            }
        } else {
            um.getThread().sendMsgBack(new BaseMsg(BaseMsg.TIME_OUT));
        }

    }

}
