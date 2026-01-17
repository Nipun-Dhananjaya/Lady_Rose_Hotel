package com.lady_rose.model;

import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    private static String UserName;
    private static String Password;
    private static UserModel user;

    public UserModel(String UserName, String Password) {
        this.UserName = UserName;
        this.Password = Password;
    }

    public static boolean IsCorrect(String userName, String password) {
        ResultSet result = null;
        try {
            result = CrudUtil.execute("SELECT username,password FROM user WHERE username=? AND password=?;", userName, password);
            if (result.next()) {
                user = new UserModel(result.getString(1), result.getString(2));
            }else {
                return false;
            }
            if (user.UserName.equals(userName) & user.Password.equals(password)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean changePwd(String oldUserName, String oldPassword, String newUserName, String newPassword, String comfirmationPwd, String jobroll) {
        ResultSet result=null;
        String userId=null;
        if (newPassword.equals(comfirmationPwd)) {
            try {
                result = CrudUtil.execute("SELECT user_id FROM user WHERE user_name=? AND password=? AND job_role=?;", oldUserName, oldPassword, jobroll);
                if (result.next()) {
                    userId = result.getString(1);
                }
                boolean IsAffected=CrudUtil.execute("UPDATE user SET user_name=?, password=? WHERE user_id=?", newUserName, newPassword, userId);
                if(IsAffected){
                    return true;
                }
                else{
                    return false;
                }
            } catch (SQLException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
