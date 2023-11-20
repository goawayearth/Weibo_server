package service;

import dao.UserDAO;
import model.User;

/*
登录时候验证账号密码是否正确
 */
public class LoginService {

    public static String userLogin(String id, String password) {
        //调用Dao层查询方法，通过学号查询用户对象
        User user = new User();
        UserDAO dao = new UserDAO();
        user = dao.check_id(id);

        //判断用户对象是否为空
        if(user == null){
            return "用户不存在";
        }

        //判断密码是否正确
        if(!password.equals(user.getUserPwd())){
            return "用户密码不正确";
        }
        //连接成功
        return "success";
    }
}
