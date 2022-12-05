package dao.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

// 针对数据库的连接作用
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
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接池
     * @param rs
     * @param ps
     * @param cn
     */
    public static void close(ResultSet rs,PreparedStatement ps,Connection cn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(cn!=null){
            try {
                cn.close();  //  此时的关闭，是归还给连接池对象
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
