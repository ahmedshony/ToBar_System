package DB_Manager.Main_Data_Manager;

import DB_Manager.Connect;
import Model.Enums.QueryState;
import Model.Main_Data.Company;
import Model.Main_Data.Item_Move;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class ItemMove_DataManger {

    DecimalFormat df=new DecimalFormat("#.##");
    DecimalFormat df2=new DecimalFormat("#.###");

    // TODO : For Item Move Controller Purpose

    // For Load All Items Move From Items DataBase
    public ObservableList<Item_Move> load_items_Move_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Item_Move> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `item_move` " +
                "ORDER BY `move_date` DESC , `move_code` DESC ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Item_Move item_move = new Item_Move();

                item_move.setCode(rs.getInt("move_code"));
                item_move.setItem_code(rs.getInt("move_item_code"));
                item_move.setItem_name(rs.getString("move_item_name"));
                item_move.setBill_code(rs.getInt("move_bill_code"));
                item_move.setKind(rs.getString("move_kind"));
                item_move.setItem_kind(rs.getString("move_item_kind"));
                item_move.setQuantity(Double.parseDouble(df2.format(rs.getDouble("move_quantity"))));
                item_move.setBuy_price(Double.parseDouble(df.format(rs.getDouble("move_buy_price"))));
                item_move.setPrice(Double.parseDouble(df.format(rs.getDouble("move_price"))));
                item_move.setEnvoy_name(rs.getString("move_envoy_name"));
                item_move.setDate(rs.getDate("move_date"));
                item_move.setExpire_date(rs.getDate("move_expire_date"));
                item_move.setClient_name(rs.getString("move_client_name"));
                item_move.getIsSelected_checkBox().setSelected(true);


                op_list.add(item_move);
            }
            System.out.println("ItemMove_DataManger.load_items_Move_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("ItemMove_DataManger.load_items_Move_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("ItemMove_DataManger.load_items_Move_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemMove_DataManger.load_items_Move_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Load All Envoy Names From Items DataBase
    public ObservableList<String> load_envoy_names_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<String> op_list =  FXCollections.observableArrayList();

        String query = "SELECT DISTINCT `move_envoy_name` " +
                "FROM `item_move` " +
                "ORDER BY `move_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                String name ="";
                name = rs.getString("move_envoy_name") ;


                op_list.add(name);
            }
            System.out.println("ItemMove_DataManger.load_envoy_names_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("ItemMove_DataManger.load_envoy_names_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("ItemMove_DataManger.load_envoy_names_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemMove_DataManger.load_envoy_names_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }


    // For Load All Client Names From Items DataBase
    public ObservableList<String> load_client_names_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<String> op_list =  FXCollections.observableArrayList();

        String query = "SELECT DISTINCT `move_client_name` " +
                "FROM `item_move` " +
                "ORDER BY `move_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                String name ="";
                name = rs.getString("move_client_name") ;


                op_list.add(name);
            }
            System.out.println("ItemMove_DataManger.load_cleint_names_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("ItemMove_DataManger.load_cleint_names_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("ItemMove_DataManger.load_cleint_names_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemMove_DataManger.load_cleint_names_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }


    // TODO : For Import And Export Controllers Purpose

    // For Add New Item To Items Move DataBase
    public QueryState add_item_Move(Item_Move item_move) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;



        String query =
                "INSERT INTO `item_move`(`move_code`, `move_item_code`, `move_item_name`, `move_bill_code`, " +
                        " `move_kind`, `move_item_kind`, `move_quantity`," +
                        "  `move_buy_price`,`move_price`, `move_envoy_name`, `move_date`," +
                        " `move_client_name` , `move_expire_date`  ) " ;

        query = query + "VALUES ( "+
                "'" + item_move.getCode() + "', "+
                "'" + item_move.getItem_code() + "', "+
                "'" + item_move.getItem_name() + "', "+
                "'" + item_move.getBill_code() + "', "+
                "'" + item_move.getKind() + "', "+
                "'" + item_move.getItem_kind() + "', "+
                "'" + item_move.getQuantity() + "', "+
                "'" + item_move.getBuy_price() + "', "+
                "'" + item_move.getPrice() + "', "+
                "'" + item_move.getEnvoy_name() + "', "+
                "'" + item_move.getDate() + "', "+
                "'" + item_move.getClient_name() + "', "+
                "'" + item_move.getExpire_date() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("ItemMove_DataManger.add_item_Move() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("ItemMove_DataManger.add_item_Move() | Success");
            }
            try {
                // close connection
                System.out.println("ItemMove_DataManger.add_item_Move()| Connection | Close");
            }catch (Exception e){
                System.out.println("ItemMove_DataManger.add_item_Move() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Delete Item Move
    public QueryState delete_Item_Moves(int bill_code , String kind) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `item_move` WHERE  `move_bill_code` = '" + bill_code
                        + "' AND  `move_kind` LIKE '"+ kind+"' ";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("ItemMove_DataManger.delete_Item_Moves() | Success | " );
        }catch (SQLException e) {
            System.out.println("ItemMove_DataManger.delete_Item_Moves() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("ItemMove_DataManger.delete_Item_Moves() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("ItemMove_DataManger.delete_Item_Moves() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }


}
