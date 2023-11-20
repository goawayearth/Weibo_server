package controller;

import com.alibaba.fastjson.JSON;
import dao.CommentDAO;
import model.Comment;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/get_comment")
public class CommentServlet extends HttpServlet {
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");

        System.out.println("id is :"+id);

        List<Comment> comments = new CommentDAO().comment_all(id);

        String jsonArray = JSON.toJSONString(comments);

        System.out.println(jsonArray);

        out.write(jsonArray);
    }
}
