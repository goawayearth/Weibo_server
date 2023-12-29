package util;

/*
这是一个简单的Java代码，用于提供JDBC（Java Database Connectivity）工具类。
这个工具类提供了四个静态方法，分别用于获取用户数据库连接、关闭预处理语句、
关闭数据库连接和关闭结果集。
这个工具类可以被其他Java程序使用，以提供JDBC相关的功能。
 */

import java.sql.*;

public class JdbcUtil {
    //获取用户数据库链接
    public static Connection getUserConnection(){
        Connection conn = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 用户名和密码都是root
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project?" +
                    "characterEncoding=UTF-8" +
                    "&serverTimezone=Asia/Shanghai&useSSL=false" +
                    "&allowPublicKeyRetrieval=true","root", "root");
            System.out.println("database access success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("fail to access database");
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("fail to access database");
        }
        return conn;
    }

    //关闭的方法
    public static void close(PreparedStatement pstmt){
        if(pstmt != null){						//避免出现空指针异常
            try{
                pstmt.close();
            }catch(SQLException e){
                e.printStackTrace();
            }

        }
    }

    public static void close(Connection conn){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
