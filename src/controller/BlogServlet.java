package controller;

import   com.alibaba.fastjson.JSON;
import dao.BlogDAO;
import model.Microblog;

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

@WebServlet("/get_blog")
public class BlogServlet extends HttpServlet {
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("id");
        String search = request.getParameter("search");
        System.out.println("id is "+id);
        System.out.println("search is "+search);
        List<Microblog> microblogs;
        if(id!=null){
            microblogs = new BlogDAO().blog_user(id);
        }else if(search!=null){
            microblogs = new BlogDAO().blog_content(search);
            if(microblogs ==null || microblogs.size()==0){  //关键词查询为空，则查询用户id
                microblogs = new BlogDAO().blog_user(search);
            }
        }else{
            microblogs = new BlogDAO().blog_all();
        }

        String jsonArray = JSON.toJSONString(microblogs);
        System.out.println("blogServlet " + jsonArray);

        out.write(jsonArray);
    }
}
