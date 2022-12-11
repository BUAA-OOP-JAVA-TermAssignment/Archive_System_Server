package dao.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 连接数据库工具
 */
public class DBUtil {

    private static DataSource ds;

    /**
     * 静态代码块初始化链接
     */
    static{
        try {
            Properties pro =new Properties();
            //加载属性文件
            pro.load(DBUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
            //获得连接池对象
            ds = DruidDataSourceFactory.createDataSource(pro);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL Initialize Failed!");
        }
    }


    /**
     * 获取连接
     * @return connection
     */
    public static Connection getConnection(){
        try {
            return  ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Get SQL connection failed!");
        }
        return null;
    }

    /**
     * 关闭连接
     * @param rs
     * @param ps
     * @param cn
     */
    public static void close(ResultSet rs,PreparedStatement ps,Connection cn){
        if(rs!=null){
            try {
                rs.close();
                ps.close();
                cn.close();  //  此时的关闭，是归还给连接池对象
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Close SQL connection failed!");
            }
        }
    }

    /**
     * 获得连接池的方法
     * @return 返回连接池对象
     */
    public static  DataSource getDataSource(){
        return ds;
    }

}
