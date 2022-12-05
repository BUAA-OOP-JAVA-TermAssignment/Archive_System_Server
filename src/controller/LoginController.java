package controller;

import service.LoginService;

/**
 * @author pcpas
 */
public class LoginController {

    private static LoginController lc;

    private LoginService loginService;

    private LoginController() {
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

    public void setLoginService(LoginService ls) {
        loginService = ls;
    }

    public void adminLoginCheck(String account, String password) {
        //实验用：
        loginService = new LoginService();

        boolean haveMatch = loginService.hasMatchAdmin(account, password);
        if (haveMatch) {
            System.out.println("Welcome!");
        } else {
            System.out.println("Login Denied!");
        }
    }

}
