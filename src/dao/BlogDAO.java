package dao;

import model.Microblog;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 返回各种条件的微博集合
 */
public class BlogDAO {
    private SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    //获取所有人的微博
    public List<Microblog> blog_all(){
        List<Microblog> microblogs = new ArrayList<Microblog>();
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet resultSet=null;

        try{
            //连接数据库
            connection = JdbcUtil.getUserConnection();
            if(connection==null){
                //连接不成功
                System.out.println("connect is null");
            }
            String sql = "select * from blog order by date DESC";
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            // 执行sql语句
            resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                //记录下返回的每一条blog
                Microblog microblog= new Microblog();
                microblog.setCommentSum(resultSet.getInt("commentNum"));
                microblog.setCreateDate(SDF.parse(resultSet.getString("date")));
                microblog.setMicroblogID(UUID.fromString(resultSet.getString("blogID")));
                microblog.setMicroblogWriter(resultSet.getString("writer"));
                microblog.setMicroblogTextContent(resultSet.getString("content"));
                microblogs.add(microblog);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭数据库
            JdbcUtil.close(resultSet);
            JdbcUtil.close(connection);
            JdbcUtil.close(pstmt);
        }
        // 返回所有的blog
        return microblogs;
    }

    //查找某个人的ID微博
    public List<Microblog> blog_user(String id){
        List<Microblog> microblogs = new ArrayList<Microblog>();
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet resultSet=null;

        try{
            connection = JdbcUtil.getUserConnection();
            if(connection==null){
                System.out.println("connect is null");
            }
            String sql = "select * from blog where writer = ? order by date DESC";
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            pstmt.setString(1,id);
            resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                Microblog microblog= new Microblog();
                microblog.setCommentSum(resultSet.getInt("commentNum"));
                microblog.setCreateDate(SDF.parse(resultSet.getString("date")));
                microblog.setMicroblogID(UUID.fromString(resultSet.getString("blogID")));
                microblog.setMicroblogWriter(resultSet.getString("writer"));
                microblog.setMicroblogTextContent(resultSet.getString("content"));
                microblogs.add(microblog);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(resultSet);
            JdbcUtil.close(connection);
            JdbcUtil.close(pstmt);
        }
        return microblogs;
    }

    //新增微博
    public void add_blog(Microblog microblog){
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet resultSet=null;
        try{
            connection = JdbcUtil.getUserConnection();
            if(connection==null){
                System.out.println("connect is null");
            }
            String sql = "insert into blog value(?,?,?,?,?)";
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            pstmt.setString(1,microblog.getMicroblogID().toString());
            pstmt.setString(2,microblog.getMicroblogWriter());
            pstmt.setString(3,microblog.getCreateDate());
            pstmt.setString(4,microblog.getMicroblogTextContent());
            pstmt.setInt(5,microblog.getCommentSum());
            pstmt.executeUpdate();
        }catch (Exception e){
            System.out.println("发送微博失败！");
            e.printStackTrace();
        }
    }

    //删除微博
    public void delete_blog(String id){
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = JdbcUtil.getUserConnection();;
            if(connection ==null){
                System.out.println("connection is null");
            }

            String sql = "delete from comment where blogID = ?";
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            pstmt.setString(1,id);
            pstmt.executeUpdate();
            String sql2 = "delete from blog where blogID = ?";
            pstmt = (PreparedStatement)connection.prepareStatement(sql2);
            pstmt.setString(1,id);
            pstmt.executeUpdate();
        }catch (Exception e){
            System.out.println("删除微博失败");
            e.printStackTrace();
        }
    }

    //搜索包含关键词的微博
    public List<Microblog> blog_content(String search) {
        List<Microblog> microblogs = new ArrayList<Microblog>();
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet resultSet=null;

        try{
            connection = JdbcUtil.getUserConnection();
            if(connection==null){
                System.out.println("connect is null");
            }
            String sql = "select * from blog where content like ? order by date DESC";
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            pstmt.setString(1,"%"+search+"%");
            resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                Microblog microblog= new Microblog();
                microblog.setCommentSum(resultSet.getInt("commentNum"));
                microblog.setCreateDate(SDF.parse(resultSet.getString("date")));
                microblog.setMicroblogID(UUID.fromString(resultSet.getString("blogID")));
                microblog.setMicroblogWriter(resultSet.getString("writer"));
                microblog.setMicroblogTextContent(resultSet.getString("content"));
                microblogs.add(microblog);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(resultSet);
            JdbcUtil.close(connection);
            JdbcUtil.close(pstmt);
        }
        return microblogs;
    }
}
