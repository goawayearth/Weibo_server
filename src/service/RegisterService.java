package service;

import dao.UserDAO;
import model.User;

/*
注册功能
 */

public class RegisterService {

    public static String CreateUser(String id, String password,String phone) {
        //调用Dao层查询方法，通过学号查询用户对象
        User user = new User();
        UserDAO dao = new UserDAO();
        //检查账号是否已经存在
        user = dao.check_id(id);
        if(user!=null){
            return "该账号已经存在！";
        }
        else{
            dao.create(id,password,phone);
            return "success";
        }
    }
}
