package dao;
/*
check_id 通过用户ID查询用户信息
create用于向数据库插入新的用户记录

*/
import model.User;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    //通过id查询用户
    public User check_id(String id){
        User u=null;
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet resultSet=null;

        try{
            connection = JdbcUtil.getUserConnection();
            if(connection==null){
                System.out.println("connect is null");
            }
            String sql = "select * from user where userId=?";
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            pstmt.setString(1,id);
            resultSet = pstmt.executeQuery();
            if(resultSet.next()){
                u=new User();
                u.setUserId(resultSet.getString("userId"));
                u.setUserPwd(resultSet.getString("userPwd"));
                u.setUserPhone(resultSet.getString("userPhone"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(resultSet);
            JdbcUtil.close(connection);
            JdbcUtil.close(pstmt);
        }
        return u;
    }

    //创建新用户
    public void create(String userId,String userPwd,String userPhone){
        System.out.println("new password is :"+userPwd);
        System.out.println("aim id is :"+userId);
        System.out.println("phone is :"+userPhone);
        String sql = "insert into user values(?,?,?)";
        //该语句为每个 IN 参数保留一个问号（“？”）作为占位符
        Connection conn = null;				//和数据库取得连接
        PreparedStatement pstmt = null;		//创建statement
        try{
            conn = JdbcUtil.getUserConnection();
            pstmt = (PreparedStatement) conn.prepareStatement(sql);
            pstmt.setString(1, userId); //给占位符赋值
            pstmt.setString(2, userPwd); //给占位符赋值
            pstmt.setString(3, userPhone); //给占位符赋值
            pstmt.executeUpdate();			//执行
        }catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            JdbcUtil.close(pstmt);
            JdbcUtil.close(conn);		//必须关闭
        }
    }
}
