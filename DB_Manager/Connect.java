package DB_Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Connect {

    //  Using Thread Safety Singleton Design Pattern Concept .


    final static String Driver = "com.mysql.jdbc.Driver";
    final static String Url = "jdbc:mysql://localhost/tobar_system";
    final static String User = "root";
    final static String Password = "root1234";

    private  static    Connect connect_Instance = null;


    public Connection con;
    public Statement stmt;



    private Connect() {
        try {


            Class.forName(Driver);
            con = DriverManager.getConnection(Url, User, Password);
            stmt = con.createStatement();
            if (con != null) {
                System.out.println("Connection...");
            }

        } catch (Exception e) {

            System.out.println("Error in connect DB : " + e.getMessage());
        }
    }
    public static  Connect start_Connection(){
        if (connect_Instance == null){
            synchronized (Connect.class){
                if (connect_Instance == null){

                    connect_Instance = new Connect();
                }
            }

            }

            return  connect_Instance ;
    }

    public static void main(String[] args) {
        new Connect();
    }

}
