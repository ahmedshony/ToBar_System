package DB_Manager.Main_Data_Manager;

import DB_Manager.Connect;
import Model.Enums.QueryState;
import Model.Main_Data.Envoy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class Envoy_DataManager {
    DecimalFormat df=new DecimalFormat("#.##");


    // TODO : For envoy Controller Purpose

    // For Add New envoy To envoys DataBase
    public QueryState add_envoy(Envoy envoy) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;



        String query =
                "INSERT INTO `envoys`(`envoy_code`, `envoy_name`," +
                        " `envoy_phone`, `envoy_current_balance`)" ;

        query = query + "VALUES ( "+
                "'" + envoy.getCode() + "', "+
                "'" + envoy.getName() + "', "+
                "'" + envoy.getPhone() + "', "+
                "'" + envoy.getCurrent_balance() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("Envoy_DataManager.add_envoy() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("Envoy_DataManager.add_envoy() | Success");
            }
            try {
                // close connection
                System.out.println("Envoy_DataManager.add_envoy()| Connection | Close");
            }catch (Exception e){
                System.out.println("Envoy_DataManager.add_envoy() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Update envoy's Data From envoys DataBase
    public QueryState update_envoy(Envoy envoy) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "UPDATE `envoys` " +
                        " SET "+
                        " `envoys`.`envoy_name` = '" + envoy.getName() +"' , "+
                        " `envoys`.`envoy_phone` = '" + envoy.getPhone() +"' , "+
                        " `envoys`.`envoy_current_balance` = '" + envoy.getCurrent_balance() +"'";

                        query = query +
                "WHERE " +
                "`envoys`.`envoy_code` = " + "'" + envoy.getCode() + "'";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Envoy_DataManager.update_envoy() | Success | " );
        }catch (SQLException e) {
            System.out.println("Envoy_DataManager.update_envoy() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Envoy_DataManager.update_envoy() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Envoy_DataManager.update_envoy() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Delete envoy's Data From envoys DataBase
    public QueryState delete_envoy(Envoy envoy) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `envoys` WHERE  `envoy_code` = '" + envoy.getCode() + "'";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Envoy_DataManager.delete_envoy() | Success | " );
        }catch (SQLException e) {
            System.out.println("Envoy_DataManager.delete_envoy() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Envoy_DataManager.delete_envoy() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Envoy_DataManager.delete_envoy() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Load All envoys From envoys DataBase
    public ObservableList<Envoy> load_envoys_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Envoy> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `envoys` " +
                "ORDER BY `envoy_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Envoy envoy = new Envoy();

                envoy.setCode(rs.getInt("envoy_code"));
                envoy.setName(rs.getString("envoy_name"));
                envoy.setPhone(rs.getString("envoy_phone"));
                envoy.setCurrent_balance(Double.parseDouble(df.format(rs.getDouble("envoy_current_balance"))));
                envoy.getIsSelected_checkBox().setSelected(true);


                op_list.add(envoy);
            }
            System.out.println("Envoy_DataManager.load_envoys_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Envoy_DataManager.load_envoys_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Envoy_DataManager.load_envoys_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Envoy_DataManager.load_envoys_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Load All envoys Names From envoys DataBase
    public ObservableList<String> load_envoys_names_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<String> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `envoys` " +
                "ORDER BY `envoy_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
              String name ="";
              name = rs.getString("envoy_name") ;


                op_list.add(name);
            }
            System.out.println("Envoy_DataManager.load_envoys_names_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Envoy_DataManager.load_envoys_names_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Envoy_DataManager.load_envoys_names_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Envoy_DataManager.load_envoys_names_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }



    // TODO : For Others Controller Purpose


    // For return  envoy BY his name From envoys DataBase
    public Envoy load_envoy_by_name(String name) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        Envoy  envoy =new Envoy();
        String query = "SELECT * " +
                "FROM `envoys` " +
                "WHERE  `envoy_name` LIKE '" + name +"'";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                 envoy.setCode(rs.getInt("envoy_code"));
                 envoy.setName(rs.getString("envoy_name"));
                 envoy.setPhone(rs.getString("envoy_phone"));
                 envoy.setCurrent_balance(rs.getDouble("envoy_current_balance"));



            }
            System.out.println("Envoy_DataManager.load_envoy_by_name() | Size " + envoy.getCode()) ;

        } catch (Exception e){
            System.out.println("Envoy_DataManager.load_envoy_by_name() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Envoy_DataManager.load_envoy_by_name() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Envoy_DataManager.load_envoy_by_name() | Connection | " + e.getMessage());
            }
            return envoy;
        }
    }


    // For return  envoy BY his code From envoys DataBase
    public Envoy load_envoy_by_code(int code) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        Envoy  envoy =new Envoy();
        String query = "SELECT * " +
                "FROM `envoys` " +
                "WHERE  `envoy_code` =" + code +"";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                envoy.setCode(rs.getInt("envoy_code"));
                envoy.setName(rs.getString("envoy_name"));
                envoy.setPhone(rs.getString("envoy_phone"));
                envoy.setCurrent_balance(rs.getDouble("envoy_current_balance"));



            }
            System.out.println("Envoy_DataManager.load_envoy_by_name() | Size " + envoy.getName()) ;

        } catch (Exception e){
            System.out.println("Envoy_DataManager.load_envoy_by_name() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Envoy_DataManager.load_envoy_by_name() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Envoy_DataManager.load_envoy_by_name() | Connection | " + e.getMessage());
            }
            return envoy;
        }
    }






}
