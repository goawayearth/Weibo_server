package controller;

import com.alibaba.fastjson.JSON;
import dao.BlogDAO;
import dao.CommentDAO;
import jakarta.servlet.ServletInputStream;
import model.Comment;
import model.Microblog;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/write_comment")
public class WriteCommentServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        ServletInputStream is = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        System.out.println("json = "+line);

        Comment comment = JSON.parseObject(line, Comment.class);

        new CommentDAO().add_comment(comment);

        //将结果返回给客户端
        out.write("success");
    }
}
