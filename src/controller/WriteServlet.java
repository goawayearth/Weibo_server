package controller;

import com.alibaba.fastjson.JSON;
import dao.BlogDAO;
import model.Microblog;
import service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/write")
public class WriteServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        ServletInputStream is = request.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = br.readLine();
        System.out.println("客户端json = "+line);

        Microblog microblog = JSON.parseObject(line,Microblog.class);

        new BlogDAO().add_blog(microblog);

        //将结果返回给客户端
        out.write("success");
    }
}
