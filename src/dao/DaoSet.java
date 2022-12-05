package dao;

import controller.LoginController;
import dao.impl.AdminDaoImpl;
import dao.impl.DocDaoImpl;
import service.LoginService;

public class DaoSet {

    public static AdminDao adminDao = new AdminDaoImpl();
    public static DocDaoImpl docDao = new DocDaoImpl();

}
