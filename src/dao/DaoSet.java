package dao;

import dao.impl.AdminDaoImpl;
import dao.impl.DocDaoImpl;
import dao.impl.UserDaoImpl;


/**
 * ͨ??DaoSet????dao??
 */
public class DaoSet {

    public static AdminDao adminDao = new AdminDaoImpl();
    public static DocDao docDao = new DocDaoImpl();
    public static UserDao userDao = new UserDaoImpl();

}
