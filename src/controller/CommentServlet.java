package controller;

import com.alibaba.fastjson.JSON;
import dao.CommentDAO;
import model.Comment;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
