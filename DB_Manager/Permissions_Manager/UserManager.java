package DB_Manager.Permissions_Manager;


import DB_Manager.Connect;
import Model.User_Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;


public class UserManager {




    public ObservableList<User> load_users() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<User> op_list = FXCollections.observableArrayList();

        String query = "SELECT *" +
                "FROM user " +
                "order by user_type desc , priority";
        try{
            rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("user_name"));
                    user.setPassword(rs.getString("password"));
                    user.setUser_type(rs.getString("user_type"));
                    user.setPriority(rs.getInt("priority"));

                    op_list.add(user);
                }
        }catch (Exception e){
            System.out.println("UserManager.load_users() | Error | " + e.getMessage());
            return  null;
        }finally {
            System.out.println("UserManager.load_users() | Size | " + op_list.size());
            try {
                System.out.println("UserManager.load_users() | Connection | Close");
            }catch (Exception e){
                System.out.println("UserManager.load_users() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }


    public User login_user (String given_userName){

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        User user = new User();

        String query = "SELECT *" +
                "FROM user " +
                "WHERE user_name LIKE '" + given_userName + "';";
        try{
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setUser_type(rs.getString("user_type"));
                user.setPriority(rs.getInt("priority"));
            }
        }catch (Exception e){
            System.out.println("UserManager.login_user() | Error | " + e.getMessage());
        }finally {
            System.out.println("UserManager.login_user() | Success | " + user.getPassword());
            System.out.println("UserManager.login_user() | Connection | Close");
            try {
                // close connection
                System.out.println("UserManager.login_user() | Connection | Close");
            }catch (Exception e){
                System.out.println("UserManager.login_user() | Connection | " + e.getMessage());
            }
            return user;
        }
    }


}
