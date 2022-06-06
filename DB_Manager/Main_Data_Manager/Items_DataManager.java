package DB_Manager.Main_Data_Manager;

import AppManager.UIManager;
import DB_Manager.Connect;
import Model.Enums.QueryState;
import Model.Main_Data.Envoy;
import Model.Main_Data.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class Items_DataManager {

    DecimalFormat df=new DecimalFormat("#.##");

    DecimalFormat df2=new DecimalFormat("#.###");

    // TODO : For Store Controller Purpose

    // For Add New Item To Items DataBase
    public QueryState add_item(Item item) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;



        String query =
                "INSERT INTO `items`(`item_code`, `item_name`, `item_current_quantity`, " +
                        "`item_kind`, `item_farmer_cash_price`, `item_trade_cash_price`," +
                        " `item_farmer_Installment_price`, `item_trade_Installment_price`, `item_buy_price`, `item_quantity_limit`, `item_notes`)" ;

        query = query + "VALUES ( "+
                "'" + item.getCode() + "', "+
                "'" + item.getName() + "', "+
                "'" + item.getCurrent_quantity() + "', "+
                "'" + item.getKind() + "', "+
                "'" + Double.parseDouble(df.format(item.getFarmer_cash_price()))+ "', "+
                "'" + Double.parseDouble(df.format(item.getTrade_cash_price())) + "', "+
                "'" + Double.parseDouble(df.format(item.getFarmer_Installment_price())) + "', "+
                "'" + Double.parseDouble(df.format(item.getTrade_Installment_price())) + "', "+
                "'" + Double.parseDouble(df.format(item.getBuy_price())) + "', "+
                "'" + item.getQuantity_limit() + "', "+
                "'" + item.getNotes() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("Items_DataManager.add_item() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("Items_DataManager.add_item() | Success");
            }
            try {
                // close connection
                System.out.println("Items_DataManager.add_item()| Connection | Close");
            }catch (Exception e){
                System.out.println("Items_DataManager.add_item() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Update Item's Data From Items DataBase
    public QueryState update_item(Item item) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "UPDATE `items` " +
                        " SET "+
                        " `items`.`item_name` = '" + item.getName() +"' , "+
                        " `items`.`item_kind` = '" + item.getKind() +"' , "+
                        " `items`.`item_farmer_cash_price` = '" + Double.parseDouble(df.format(item.getFarmer_cash_price())) +"' , "+
                        " `items`.`item_trade_cash_price` = '" + Double.parseDouble(df.format(item.getTrade_cash_price())) +"' , "+
                        " `items`.`item_farmer_Installment_price` = '" + Double.parseDouble(df.format(item.getFarmer_Installment_price())) +"' , "+
                        " `items`.`item_trade_Installment_price` = '" + Double.parseDouble(df.format(item.getTrade_Installment_price())) +"' , "+
                        " `items`.`item_buy_price` = '" + Double.parseDouble(df.format(item.getBuy_price())) +"' , "+
                        " `items`.`item_current_quantity` = '" + item.getCurrent_quantity() +"' , "+
                        " `items`.`item_quantity_limit` = '" + item.getQuantity_limit() +"' , "+
                        " `items`.`item_notes` = '" + item.getNotes() +"'";

                        query = query +
                "WHERE " +
                "`items`.`item_code` = " + "'" + item.getCode() + "'";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Items_DataManager.update_item() | Success | " );
        }catch (SQLException e) {
            System.out.println("Items_DataManager.update_item() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Items_DataManager.update_item() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Items_DataManager.update_item() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Delete Item's Data From Items DataBase
    public QueryState delete_item(Item item) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `items` WHERE  `item_code` = '" + item.getCode() + "'";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Items_DataManager.delete_item() | Success | " );
        }catch (SQLException e) {
            System.out.println("Items_DataManager.delete_item() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Items_DataManager.delete_item() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Items_DataManager.delete_item() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Load All Items From Items DataBase
    public ObservableList<Item> load_items_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Item> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `items` " +
                "ORDER BY `item_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Item item = new Item();

                item.setCode(rs.getInt("item_code"));
                item.setName(rs.getString("item_name"));
                item.setCurrent_quantity(Double.parseDouble(df2.format(rs.getDouble("item_current_quantity"))));
                item.setKind(rs.getString("item_kind"));
                item.setFarmer_cash_price(Double.parseDouble(df.format(rs.getDouble("item_farmer_cash_price"))));
                item.setTrade_cash_price(Double.parseDouble(df.format(rs.getDouble("item_trade_cash_price"))));
                item.setFarmer_Installment_price(Double.parseDouble(df.format(rs.getDouble("item_farmer_Installment_price"))));
                item.setTrade_Installment_price(Double.parseDouble(df.format(rs.getDouble("item_trade_Installment_price"))));
                item.setBuy_price(Double.parseDouble(df.format(rs.getDouble("item_buy_price"))));
                item.setQuantity_limit(Double.parseDouble(df2.format(rs.getDouble("item_quantity_limit"))));
                item.setNotes(rs.getString("item_notes"));
                item.getIsSelected_checkBox().setSelected(true);


                op_list.add(item);
            }
            System.out.println("Items_DataManager.load_items_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Items_DataManager.load_items_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Items_DataManager.load_items_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Items_DataManager.load_items_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Load All Items Names From Items DataBase
    public ObservableList<String> load_items_names_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<String> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `items` " +
                "ORDER BY `item_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
              String name ="";
              name = rs.getString("item_name") ;


                op_list.add(name);
            }
            System.out.println("Items_DataManager.load_items_names_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Items_DataManager.load_items_names_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Items_DataManager.load_items_names_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Items_DataManager.load_items_names_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // TODO : For Other Controllers Purpose

    // For Load All Items Codes From Items DataBase
    public ObservableList<String> load_items_codes_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<String> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `items` " +
                "ORDER BY `item_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                String code ="";
                code = String.valueOf(rs.getInt("item_code")) ;


                op_list.add(code);
            }
            System.out.println("Items_DataManager.load_items_codes_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Items_DataManager.load_items_codes_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Items_DataManager.load_items_codes_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Items_DataManager.load_items_codes_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Return  Item BY Its Code From Items DataBase
    public Item load_item_by_code(int code) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        Item  item =new Item();
        String query = "SELECT * " +
                "FROM `items` " +
                "WHERE  `item_code` =" + code +"";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                item.setCode(rs.getInt("item_code"));
                item.setName(rs.getString("item_name"));
                item.setCurrent_quantity(rs.getDouble("item_current_quantity"));
                item.setKind(rs.getString("item_kind"));
                item.setFarmer_cash_price(Double.parseDouble(df.format(rs.getDouble("item_farmer_cash_price"))));
                item.setTrade_cash_price(Double.parseDouble(df.format(rs.getDouble("item_trade_cash_price"))));
                item.setFarmer_Installment_price(Double.parseDouble(df.format(rs.getDouble("item_farmer_Installment_price"))));
                item.setTrade_Installment_price(Double.parseDouble(df.format(rs.getDouble("item_trade_Installment_price"))));
                item.setBuy_price(Double.parseDouble(df.format(rs.getDouble("item_buy_price"))));
                item.setQuantity_limit(rs.getDouble("item_quantity_limit"));
                item.setNotes(rs.getString("item_notes"));
                item.getIsSelected_checkBox().setSelected(true);



            }
            System.out.println("Items_DataManager.load_item_by_code() | Size " + item.getName()) ;

        } catch (Exception e){
            System.out.println("Items_DataManager.load_item_by_code() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Items_DataManager.load_item_by_code() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Items_DataManager.load_item_by_code() | Connection | " + e.getMessage());
            }
            return item;
        }
    }

    // For Return  Item BY Its Code From Items DataBase
    public Item load_item_by_name(String name) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        Item  item =new Item();
        String query = "SELECT * " +
                "FROM `items` " +
                "WHERE  `item_name` LIKE '" + name +"'";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                item.setCode(rs.getInt("item_code"));
                item.setName(rs.getString("item_name"));
                item.setCurrent_quantity(rs.getDouble("item_current_quantity"));
                item.setKind(rs.getString("item_kind"));
                item.setFarmer_cash_price(Double.parseDouble(df.format(rs.getDouble("item_farmer_cash_price"))));
                item.setTrade_cash_price(Double.parseDouble(df.format(rs.getDouble("item_trade_cash_price"))));
                item.setFarmer_Installment_price(Double.parseDouble(df.format(rs.getDouble("item_farmer_Installment_price"))));
                item.setTrade_Installment_price(Double.parseDouble(df.format(rs.getDouble("item_trade_Installment_price"))));
                item.setBuy_price(Double.parseDouble(df.format(rs.getDouble("item_buy_price"))));
                item.setQuantity_limit(rs.getDouble("item_quantity_limit"));
                item.setNotes(rs.getString("item_notes"));
                item.getIsSelected_checkBox().setSelected(true);

            }
            System.out.println("Items_DataManager.load_item_by_name() | Size " + item.getName()) ;

        } catch (Exception e){
            System.out.println("Items_DataManager.load_item_by_name() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Items_DataManager.load_item_by_name() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Items_DataManager.load_item_by_name() | Connection | " + e.getMessage());
            }
            return item;
        }
    }















}
