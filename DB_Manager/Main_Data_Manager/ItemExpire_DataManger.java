package DB_Manager.Main_Data_Manager;

import DB_Manager.Connect;
import Model.Enums.QueryState;
import Model.Main_Data.Expire_Details;
import Model.Main_Data.Item;
import Model.Main_Data.Item_Move;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

public class ItemExpire_DataManger {

    DecimalFormat df=new DecimalFormat("#.###");

    // TODO : For Item Expire Controller Purpose

    // For Load All Items Expire From Items DataBase
    public ObservableList<Expire_Details> load_items_Expire_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Expire_Details> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `expire_details` " +
                "ORDER BY  `expire_date` ,`item_code`  ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Expire_Details expire_details = new Expire_Details();

                expire_details.setCode(rs.getInt("code"));
                expire_details.setItem_code(rs.getInt("item_code"));
                expire_details.setItem_name(rs.getString("item_name"));
                expire_details.setItem_kind(rs.getString("item_kind"));
                expire_details.setQuantity(Double.parseDouble(df.format(rs.getDouble("quantity"))));
                expire_details.setExpire_date(rs.getDate("expire_date"));


                op_list.add(expire_details);
            }
            System.out.println("ItemExpire_DataManger.load_items_Expire_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("ItemExpire_DataManger.load_items_Expire_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("ItemExpire_DataManger.load_items_Expire_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemExpire_DataManger.load_items_Expire_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }


    // TODO : For Import And Export Controllers Purpose

    // For Add New Item To Items Expire DataBase
    public QueryState add_item_Expire(Expire_Details expire_details) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "INSERT INTO `expire_details`(`code`, `item_code`, `item_name`, `item_kind`, `quantity`, `expire_date`)" ;

        query = query + "VALUES ( "+
                "'" + expire_details.getCode() + "', "+
                "'" + expire_details.getItem_code() + "', "+
                "'" + expire_details.getItem_name() + "', "+
                "'" + expire_details.getItem_kind() + "', "+
                "'" + expire_details.getQuantity() + "', "+
                "'" + expire_details.getExpire_date() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("ItemExpire_DataManger.add_item_Expire() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("ItemExpire_DataManger.add_item_Expire() | Success");
            }
            try {
                // close connection
                System.out.println("ItemExpire_DataManger.add_item_Expire()| Connection | Close");
            }catch (Exception e){
                System.out.println("ItemExpire_DataManger.add_item_Expire() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Update Item's Data From Expire DataBase
    public QueryState update_item_Expire(Expire_Details expire_details) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "UPDATE `expire_details` " +
                        " SET "+
                        " `expire_details`.`item_kind` = '" + expire_details.getItem_kind() +"' , "+
                        " `expire_details`.`quantity` = '" + expire_details.getQuantity()  +"'";

        query = query +
                " WHERE  `item_code` = '" + expire_details.getItem_code()
                + "' AND  `expire_date` LIKE '"+ expire_details.getExpire_date()+"' ";
        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("ItemExpire_DataManger.update_item_Expire() | Success | " );
        }catch (SQLException e) {
            System.out.println("ItemExpire_DataManger.update_item_Expire() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("ItemExpire_DataManger.update_item_Expire() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemExpire_DataManger.update_item_Expire() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Delete Item Expire
    public QueryState delete_Item_Expire(Expire_Details expire_details) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `expire_details` WHERE  `code` = '" + expire_details.getCode() + "'  ";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("ItemExpire_DataManger.delete_Item_Expire() | Success | " );
        }catch (SQLException e) {
            System.out.println("ItemExpire_DataManger.delete_Item_Expire() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("ItemExpire_DataManger.delete_Item_Expire() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemExpire_DataManger.delete_Item_Expire() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Detect item with detected expire date is exist before
    public boolean detect_expire_item_is_exist(int item_code  , Date expire_date) {
        boolean exit_status = false ;

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        String query = "SELECT EXISTS(SELECT * FROM expire_details WHERE item_code = "+ item_code +
                " AND expire_date ='"+ expire_date +"' LIMIT 1 ) AS exit_status" ;

        try {

            rs = c.stmt.executeQuery(query);
            while (rs.next()){
                exit_status = rs.getBoolean("exit_status") ;
            }
            System.out.println("ItemExpire_DataManger.detect_expire_item_is_exist() | Size " + exit_status) ;

        } catch (Exception e){
            System.out.println("ItemExpire_DataManger.detect_expire_item_is_exist() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("ItemExpire_DataManger.detect_expire_item_is_exist() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemExpire_DataManger.detect_expire_item_is_exist() | Connection | " + e.getMessage());
            }
            return exit_status;
        }
    }

    // For Return  Expire Item BY Its Code And Expire Date From Expires DataBase
    public Expire_Details load_expire_item_by_code_and_date(int item_code  , Date expire_date) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        Expire_Details  expire_details =new Expire_Details();
        String query = "SELECT * " +
                "FROM `expire_details` " +
                "WHERE  `item_code` =" + item_code +" AND `expire_date` =' " +expire_date + "' ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                expire_details.setCode(rs.getInt("code"));
                expire_details.setItem_code(rs.getInt("item_code"));
                expire_details.setItem_name(rs.getString("item_name"));
                expire_details.setItem_kind(rs.getString("item_kind"));
                expire_details.setQuantity(Double.parseDouble(df.format(rs.getDouble("quantity"))));
                expire_details.setExpire_date(rs.getDate("expire_date"));


            }
            System.out.println("ItemExpire_DataManger.load_expire_item_by_code_and_date() | Size " + expire_details.getItem_name()) ;

        } catch (Exception e){
            System.out.println("ItemExpire_DataManger.load_expire_item_by_code_and_date() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("ItemExpire_DataManger.load_expire_item_by_code_and_date() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemExpire_DataManger.load_expire_item_by_code_and_date() | Connection | " + e.getMessage());
            }
            return expire_details;
        }
    }


}
