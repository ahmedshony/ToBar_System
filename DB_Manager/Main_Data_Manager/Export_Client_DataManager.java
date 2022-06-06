package DB_Manager.Main_Data_Manager;

import DB_Manager.Connect;
import Model.Enums.QueryState;
import Model.Main_Data.Client;
import Model.Main_Data.Export_Bill;
import Model.Main_Data.Import_Bill;
import Model.Main_Data.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class Export_Client_DataManager {

    DecimalFormat df=new DecimalFormat("#.##");

    // TODO : For Client Controller Purpose

    // For Add New Client To clients DataBase
    public QueryState add_client(Client client) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;



        String query =
                "INSERT INTO `clients`(`client_code`, `client_name`, `client_kind`, " +
                        "`client_envoy_code`, `client_envoy_name`, `client_address`," +
                        " `client_phone`, `client_current_balance`, `client_balance_received`," +
                        " `client_balance_remaining`)" ;

        query = query + "VALUES ( "+
                "'" + client.getCode() + "', "+
                "'" + client.getName() + "', "+
                "'" + client.getKind() + "', "+
                "'" + client.getEnvoy_code() + "', "+
                "'" + client.getEnvoy_name() + "', "+
                "'" + client.getAddress() + "', "+
                "'" + client.getPhone() + "', "+
                "'" + client.getCurrent_balance() + "', "+
                "'" + client.getBalance_received() + "', "+
                "'" + client.getBalance_remaining() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("Export_Client_DataManager.add_client() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("Export_Client_DataManager.add_client() | Success");
            }
            try {
                // close connection
                System.out.println("Export_Client_DataManager.add_client()| Connection | Close");
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.add_client() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Update client's Data From clients DataBase
    public QueryState update_client(Client client) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "UPDATE `clients` " +
                        " SET "+
                        " `clients`.`client_name` = '" + client.getName() +"' , "+
                        " `clients`.`client_kind` = '" + client.getKind() +"' , "+
                        " `clients`.`client_envoy_code` = '" + client.getEnvoy_code() +"' , "+
                        " `clients`.`client_envoy_name` = '" + client.getEnvoy_name() +"' , "+
                        " `clients`.`client_address` = '" + client.getAddress() +"' , "+
                        " `clients`.`client_phone` = '" + client.getPhone() +"' , "+
                        " `clients`.`client_current_balance` = '" + client.getCurrent_balance() +"' , "+
                        " `clients`.`client_balance_received` = '" + client.getBalance_received() +"' , "+
                        " `clients`.`client_balance_remaining` = '" + client.getBalance_remaining() +"'";

                        query = query +
                "WHERE " +
                "`clients`.`client_code` = " + "'" + client.getCode() + "'";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Export_Client_DataManager.update_client() | Success | " );
        }catch (SQLException e) {
            System.out.println("Export_Client_DataManager.update_client() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.update_client() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.update_client() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Delete client's Data From clients DataBase
    public QueryState delete_client(Client client) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `clients` WHERE  `client_code` = '" + client.getCode() + "'";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Export_Client_DataManager.delete_client() | Success | " );
        }catch (SQLException e) {
            System.out.println("Export_Client_DataManager.delete_client() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.delete_client() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.delete_client() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Load All Clients From Clients DataBase
    public ObservableList<Client> load_clients_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Client> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `clients` " +
                "ORDER BY `client_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Client client = new Client();

                client.setCode(rs.getInt("client_code"));
                client.setName(rs.getString("client_name"));
                client.setKind(rs.getString("client_kind"));
                client.setEnvoy_code(rs.getInt("client_envoy_code"));
                client.setEnvoy_name(rs.getString("client_envoy_name"));
                client.setAddress(rs.getString("client_address"));
                client.setPhone(rs.getString("client_phone"));
                client.setCurrent_balance(Double.parseDouble(df.format(rs.getDouble("client_current_balance"))));
                client.setBalance_received(Double.parseDouble(df.format(rs.getDouble("client_balance_received"))));
                client.setBalance_remaining(Double.parseDouble(df.format(rs.getDouble("client_balance_remaining"))));
                client.getIsSelected_checkBox().setSelected(true);


                op_list.add(client);
            }
            System.out.println("Export_Client_DataManager.load_clients_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Export_Client_DataManager.load_clients_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.load_clients_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.load_clients_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Load All Clients Names From Clients DataBase
    public ObservableList<String> load_clients_names_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<String> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `clients` " +
                "ORDER BY `client_code` ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
              String name ="";
              name = rs.getString("client_name") ;


                op_list.add(name);
            }
            System.out.println("Export_Client_DataManager.load_clients_names_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Export_Client_DataManager.load_clients_names_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.load_clients_names_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.load_clients_names_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }


    // TODO : For Others Controller Purpose

    // For Load All Clients By Envoy's Data From Clients DataBase
    public ObservableList<Client> load_clients_by_envoy(int code) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Client> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `clients` " +
                "WHERE `client_envoy_code` = " + code +"";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Client client = new Client();

                client.setCode(rs.getInt("client_code"));
                client.setName(rs.getString("client_name"));
                client.setKind(rs.getString("client_kind"));
                client.setEnvoy_code(rs.getInt("client_envoy_code"));
                client.setEnvoy_name(rs.getString("client_envoy_name"));
                client.setAddress(rs.getString("client_address"));
                client.setPhone(rs.getString("client_phone"));
                client.setCurrent_balance(Double.parseDouble(df.format(rs.getDouble("client_current_balance"))));
                client.setBalance_received(Double.parseDouble(df.format(rs.getDouble("client_balance_received"))));
                client.setBalance_remaining(Double.parseDouble(df.format(rs.getDouble("client_balance_remaining"))));
                client.getIsSelected_checkBox().setSelected(true);


                op_list.add(client);
            }
            System.out.println("Export_Client_DataManager.load_clients_by_envoy() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Export_Client_DataManager.load_clients_by_envoy() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.load_clients_by_envoy() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.load_clients_by_envoy() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }

    // For Return  Client BY His Name From Clients DataBase
    public Client load_client_by_name(String name) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        Client  client =new Client();
        String query = "SELECT * " +
                "FROM `clients` " +
                "WHERE  `client_name` LIKE '" + name +"'";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                client.setCode(rs.getInt("client_code"));
                client.setName(rs.getString("client_name"));
                client.setKind(rs.getString("client_kind"));
                client.setEnvoy_code(rs.getInt("client_envoy_code"));
                client.setEnvoy_name(rs.getString("client_envoy_name"));
                client.setAddress(rs.getString("client_address"));
                client.setPhone(rs.getString("client_phone"));
                client.setCurrent_balance(Double.parseDouble(df.format(rs.getDouble("client_current_balance"))));
                client.setBalance_received(Double.parseDouble(df.format(rs.getDouble("client_balance_received"))));
                client.setBalance_remaining(Double.parseDouble(df.format(rs.getDouble("client_balance_remaining"))));
                client.getIsSelected_checkBox().setSelected(true);

            }
            System.out.println("Export_Client_DataManager.load_client_by_name() | Size " + client.getName()) ;

        } catch (Exception e){
            System.out.println("Export_Client_DataManager.load_client_by_name() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.load_client_by_name() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.load_client_by_name() | Connection | " + e.getMessage());
            }
            return client;
        }
    }



    /**********************************************************************/

    // TODO : For Export Bill Controller Purpose

    // For Detect Bill Is Exist Or Not On import company bills DataBase
    public boolean detect_export_bill_next_code(int code_num) {
        boolean code_status = false ;

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        String query = "SELECT EXISTS(SELECT * FROM export_client_bills WHERE export_code = "+ code_num + " LIMIT 1 ) AS code_status" ;

        try {

            rs = c.stmt.executeQuery(query);
            while (rs.next()){
                code_status = rs.getBoolean("code_status") ;
            }
            System.out.println("Export_Client_DataManager.detect_export_bill_next_code() | Size " + code_status) ;

        } catch (Exception e){
            System.out.println("Export_Client_DataManager.detect_export_bill_next_code() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.detect_export_bill_next_code() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.detect_export_bill_next_code() | Connection | " + e.getMessage());
            }
            return code_status;
        }
    }

    // For Add New Exported Client Bill To export client bills DataBase
    public QueryState add_exported_bill(Export_Bill export_bill) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "INSERT INTO `export_client_bills`(`export_bill_item_code`, `export_code`, `export_bill_kind`, `export_pay_kind`," +
                        " `export_date`, `export_client_code`, `export_client_name`," +
                        " `export_envoy_code`, `export_envoy_name`, `export_item_code`," +
                        " `export_item_name`, `export_item_buy_price`,`export_item_kind`, `export_item_count`," +
                        " `export_item_price`, `export_item_total_price` , `export_item_expire_date`, `export_bill_total_price`," +
                        " `export_bill_total_discount`, `export_bill_total_received`, `export_bill_final_total`, " +
                        "`export_bill_receipt_number`)" ;

        query = query + "VALUES ( "+
                "'" + export_bill.getBill_item_code() + "', "+
                "'" + export_bill.getCode() + "', "+
                "'" + export_bill.getBill_kind() + "', "+
                "'" + export_bill.getPay_kind() + "', "+
                "'" + export_bill.getDate() + "', "+
                "'" + export_bill.getClient_code() + "', "+
                "'" + export_bill.getClient_name() + "', "+
                "'" + export_bill.getEnvoy_code() + "', "+
                "'" + export_bill.getEnvoy_name() + "', "+
                "'" + export_bill.getItem_code() + "', "+
                "'" + export_bill.getItem_name() + "', "+
                "'" + export_bill.getItem_buy_price() + "', "+
                "'" + export_bill.getItem_kind() + "', "+
                "'" + export_bill.getItem_count() + "', "+
                "'" + export_bill.getItem_price() + "', "+
                "'" + export_bill.getItem_total_price() + "', "+
                "'" + export_bill.getExpire_date() + "', "+
                "'" + export_bill.getBill_total_price() + "', "+
                "'" + export_bill.getBill_total_discount() + "', "+
                "'" + export_bill.getBill_total_received() + "', "+
                "'" + export_bill.getBill_final_total() + "', "+
                "'" + export_bill.getBill_receipt_number() + "' )";

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
        }catch (SQLException e) {
            System.out.println("export_Client_DataManager.add_exported_bill() | Error | " + e.getMessage());
        }
        finally {
            if(missionState == QueryState.success){
                System.out.println("export_Client_DataManager.add_exported_bill() | Success");
            }
            try {
                // close connection
                System.out.println("export_Client_DataManager.add_exported_bill()| Connection | Close");
            }catch (Exception e){
                System.out.println("export_Client_DataManager.add_exported_bill() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }

    // For Load All Exported Bills From Export Client Bills DataBase
    public ObservableList<Export_Bill> load_exported_bills_data_list() {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Export_Bill> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `export_client_bills` " +
                "ORDER BY  `export_date`  ,`export_bill_item_code` DESC ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Export_Bill export_bill = new Export_Bill();

                export_bill.setBill_item_code(rs.getInt("export_bill_item_code"));
                export_bill.setCode(rs.getInt("export_code"));
                export_bill.setClient_code(rs.getInt("export_client_code"));
                export_bill.setEnvoy_code(rs.getInt("export_envoy_code"));
                export_bill.setItem_code(rs.getInt("export_item_code"));
                export_bill.setItem_count(rs.getDouble("export_item_count"));

                export_bill.setBill_kind(rs.getString("export_bill_kind"));
                export_bill.setPay_kind(rs.getString("export_pay_kind"));
                export_bill.setClient_name(rs.getString("export_client_name"));
                export_bill.setEnvoy_name(rs.getString("export_envoy_name"));
                export_bill.setItem_name(rs.getString("export_item_name"));
                export_bill.setItem_kind(rs.getString("export_item_kind"));
                export_bill.setBill_receipt_number(rs.getString("export_bill_receipt_number"));

                export_bill.setItem_buy_price(Double.parseDouble(df.format(rs.getDouble("export_item_buy_price"))));
                export_bill.setItem_price(Double.parseDouble(df.format(rs.getDouble("export_item_price"))));
                export_bill.setItem_total_price(Double.parseDouble(df.format(rs.getDouble("export_item_total_price"))));
                export_bill.setBill_total_price(Double.parseDouble(df.format(rs.getDouble("export_bill_total_price"))));
                export_bill.setBill_total_discount(Double.parseDouble(df.format(rs.getDouble("export_bill_total_discount"))));
                export_bill.setBill_total_received(Double.parseDouble(df.format(rs.getDouble("export_bill_total_received"))));
                export_bill.setBill_final_total(Double.parseDouble(df.format(rs.getDouble("export_bill_final_total"))));

                export_bill.setDate(rs.getDate("export_date"));
                export_bill.setExpire_date(rs.getDate("export_item_expire_date"));

                export_bill.getIsSelected_checkBox().setSelected(true);



                op_list.add(export_bill);
            }
            System.out.println("Export_Client_DataManager.load_exported_bills_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Export_Client_DataManager.load_exported_bills_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.load_exported_bills_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.load_exported_bills_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }


    public QueryState delete_bill(Export_Bill export_bill) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "DELETE FROM `export_client_bills` WHERE  `export_code` = '" + export_bill.getCode() + "'";


        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Export_Client_DataManager.delete_bill() | Success | " );
        }catch (SQLException e) {
            System.out.println("Export_Client_DataManager.delete_bill() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.delete_bill() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.delete_bill() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }



    // For Load All Exported Bills From Export Client Bills DataBase To Detected Client
    public ObservableList<Export_Bill> load_client_exported_bills_data_list(int client_code) {

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        ObservableList<Export_Bill> op_list = FXCollections.observableArrayList();

        String query = "SELECT * " +
                "FROM `export_client_bills` " +
                "WHERE `export_client_code` = "+ client_code+
                " ORDER BY  `export_date`  ,`export_bill_item_code` DESC ";

        try {
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Export_Bill export_bill = new Export_Bill();

                export_bill.setBill_item_code(rs.getInt("export_bill_item_code"));
                export_bill.setCode(rs.getInt("export_code"));
                export_bill.setClient_code(rs.getInt("export_client_code"));
                export_bill.setEnvoy_code(rs.getInt("export_envoy_code"));
                export_bill.setItem_code(rs.getInt("export_item_code"));
                export_bill.setItem_count(rs.getDouble("export_item_count"));

                export_bill.setBill_kind(rs.getString("export_bill_kind"));
                export_bill.setPay_kind(rs.getString("export_pay_kind"));
                export_bill.setClient_name(rs.getString("export_client_name"));
                export_bill.setEnvoy_name(rs.getString("export_envoy_name"));
                export_bill.setItem_name(rs.getString("export_item_name"));
                export_bill.setItem_kind(rs.getString("export_item_kind"));
                export_bill.setBill_receipt_number(rs.getString("export_bill_receipt_number"));

                export_bill.setItem_buy_price(Double.parseDouble(df.format(rs.getDouble("export_item_buy_price"))));
                export_bill.setItem_price(Double.parseDouble(df.format(rs.getDouble("export_item_price"))));
                export_bill.setItem_total_price(Double.parseDouble(df.format(rs.getDouble("export_item_total_price"))));
                export_bill.setBill_total_price(Double.parseDouble(df.format(rs.getDouble("export_bill_total_price"))));
                export_bill.setBill_total_discount(Double.parseDouble(df.format(rs.getDouble("export_bill_total_discount"))));
                export_bill.setBill_total_received(Double.parseDouble(df.format(rs.getDouble("export_bill_total_received"))));
                export_bill.setBill_final_total(Double.parseDouble(df.format(rs.getDouble("export_bill_final_total"))));

                export_bill.setDate(rs.getDate("export_date"));
                export_bill.setExpire_date(rs.getDate("export_item_expire_date"));

                export_bill.getIsSelected_checkBox().setSelected(true);



                op_list.add(export_bill);
            }
            System.out.println("Export_Client_DataManager.load_exported_bills_data_list() | Size " + op_list.size()) ;

        } catch (Exception e){
            System.out.println("Export_Client_DataManager.load_exported_bills_data_list() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Export_Client_DataManager.load_exported_bills_data_list() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Export_Client_DataManager.load_exported_bills_data_list() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }




}
