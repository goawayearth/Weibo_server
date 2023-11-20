package dao;

/*
comment_all 获取特定微博的ID的评论列表
add_comment 用于向数据库中插入新的评论记录

*/

import model.Comment;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommentDAO {

    //获取ID微博的评论
    public List<Comment> comment_all(String id){
        List<Comment> comments = new ArrayList<Comment>();
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet resultSet=null;

        try{
            connection = JdbcUtil.getUserConnection();
            if(connection==null){
                System.out.println("connect is null");
            }
            String sql = "select * from comment where blogID = ?";
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            pstmt.setString(1, id);
            resultSet = pstmt.executeQuery();
            while(resultSet.next()){
                Comment comment= new Comment();
                comment.setCommentId(UUID.fromString(resultSet.getString("commentID")));
                comment.setContent(resultSet.getString("content"));
                comment.setWriterName(resultSet.getString("writer"));
                comments.add(comment);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JdbcUtil.close(resultSet);
            JdbcUtil.close(connection);
            JdbcUtil.close(pstmt);
        }
        return comments;
    }

    //新增评论
    public void add_comment(Comment comment){
        Connection connection =null;
        PreparedStatement pstmt=null;
        ResultSet resultSet=null;
        try{
            connection = JdbcUtil.getUserConnection();
            if(connection==null){
                System.out.println("connect is null");
            }
            String sql = "insert into comment value(?,?,?,?)";
            pstmt = (PreparedStatement)connection.prepareStatement(sql);
            pstmt.setString(1,comment.getCommentId().toString());
            pstmt.setString(2,comment.getContent());
            pstmt.setString(3,comment.getWriterName());
            pstmt.setString(4,comment.getMicroblogId().toString());
            pstmt.executeUpdate();
        }catch (Exception e){
            System.out.println("评论发送失败！");
            e.printStackTrace();
        }
    }
}
