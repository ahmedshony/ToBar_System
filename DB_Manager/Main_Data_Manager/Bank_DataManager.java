package DB_Manager.Main_Data_Manager;

import DB_Manager.Connect;

import java.sql.ResultSet;

public class Bank_DataManager {

    // TODO : For Bank Controller Purpose

       // 1- from export clients database

        // For Calculate All Export Bills Total
        public double calculate_export_all_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(`client_current_balance`) AS total FROM `clients`" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_export_all_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_export_all_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_export_all_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_export_all_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Received Export Bills Total
        public double calculate_export_received_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(`client_balance_received`) AS total FROM `clients`" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_export_all_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_export_all_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_export_all_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_export_all_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Remaining Export Bills Total
        public double calculate_export_remaining_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(`client_balance_remaining`) AS total FROM `clients`" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_export_all_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_export_all_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_export_all_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_export_all_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

      // 2- from clients database

        // For Calculate All Import Bills Total
        public double calculate_import_all_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(`company_current_balance`) AS total FROM `companies`" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_import_all_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_import_all_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_import_all_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_import_all_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Paid Import Bills Total
        public double calculate_import_paid_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(`company_balance_paid`) AS total FROM `companies`" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_import_paid_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_import_paid_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_import_paid_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_import_paid_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Remaining Import Bills Total
        public double calculate_import_remaining_total() {
        double total =0.00 ;

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        String query = "SELECT SUM(`company_balance_remaining`) AS total FROM `companies`" ;

        try {

            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                total = rs.getDouble("total") ;
            }
            System.out.println("Bank_DataManager.calculate_import_remaining_total() | Size " + total) ;

        } catch (Exception e){
            System.out.println("Bank_DataManager.calculate_import_remaining_total() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Bank_DataManager.calculate_import_remaining_total() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Bank_DataManager.calculate_import_remaining_total() | Connection | " + e.getMessage());
            }
            return total;
        }
    }


     // 3- from earn database

        // For Calculate All Earn Bills Total
        public double calculate_earn_all_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(bill_value) AS total FROM `earn_and_spend_money` WHERE `bill_kind` LIKE 'تحصيل من عميل' OR `bill_kind` LIKE 'تحصيلات أخري'" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_earn_all_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_earn_all_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_earn_all_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_earn_all_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Other Earn Bills Total
        public double calculate_earn_other_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(bill_value) AS total FROM `earn_and_spend_money` WHERE  `bill_kind` LIKE 'تحصيلات أخري'" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_earn_other_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_earn_other_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_earn_other_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_earn_other_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Clients Earn Bills Total
        public double calculate_earn_clients_total() {
        double total =0.00 ;

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
            String query = "SELECT SUM(bill_value) AS total FROM `earn_and_spend_money` WHERE `bill_kind` LIKE 'تحصيل من عميل' " ;

        try {

            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                total = rs.getDouble("total") ;
            }
            System.out.println("Bank_DataManager.calculate_earn_clients_total() | Size " + total) ;

        } catch (Exception e){
            System.out.println("Bank_DataManager.calculate_earn_clients_total() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Bank_DataManager.calculate_earn_clients_total() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Bank_DataManager.calculate_earn_clients_total() | Connection | " + e.getMessage());
            }
            return total;
        }
    }

    // 4- from spend database

        // For Calculate All Spend Bills Total
        public double calculate_spend_all_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(bill_value) AS total FROM `earn_and_spend_money` WHERE `bill_kind` LIKE 'صرف لشركة' OR `bill_kind` LIKE 'صرف لمندوب' OR `bill_kind` LIKE 'مصاريف أخري'" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_spend_all_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_spend_all_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_spend_all_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_spend_all_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Other Spend Bills Total
        public double calculate_spend_other_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(bill_value) AS total FROM `earn_and_spend_money` WHERE  `bill_kind` LIKE 'صرف لمندوب' OR `bill_kind` LIKE 'مصاريف أخري'" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_spend_other_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_spend_other_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_spend_other_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_spend_other_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Companies Spend Bills Total
        public double calculate_spend_companies_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(bill_value) AS total FROM `earn_and_spend_money` WHERE `bill_kind` LIKE 'صرف لشركة' " ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_spend_companies_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_spend_companies_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_spend_companies_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_spend_companies_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }


        // For Calculate Exported Sells  Bills Total
        public double calculate_exported_sells_total() {
        double total =0.00 ;

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        String query = "SELECT SUM((move_quantity * move_price) - (move_quantity * move_buy_price)) AS total FROM `item_move` WHERE move_kind LIKE 'صادر'" ;

        try {

            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                total = rs.getDouble("total") ;
            }
            System.out.println("Bank_DataManager.calculate_exported_sells_total() | Size " + total) ;

        } catch (Exception e){
            System.out.println("Bank_DataManager.calculate_exported_sells_total() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Bank_DataManager.calculate_exported_sells_total() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Bank_DataManager.calculate_exported_sells_total() | Connection | " + e.getMessage());
            }
            return total;
        }
    }

        // For Calculate Returned Sells  Bills Total
        public double calculate_export_returned_sells_total() {
        double total =0.00 ;

        Connect c =  Connect.start_Connection();
        ResultSet rs ;
        String query = "SELECT SUM((move_quantity * move_price) - (move_quantity * move_buy_price)) AS total FROM `item_move` WHERE move_kind LIKE 'مرتجع عميل'" ;

        try {

            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                total = rs.getDouble("total") ;
            }
            System.out.println("Bank_DataManager.calculate_returned_sells_total() | Size " + total) ;

        } catch (Exception e){
            System.out.println("Bank_DataManager.calculate_returned_sells_total() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Bank_DataManager.calculate_returned_sells_total() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Bank_DataManager.calculate_returned_sells_total() | Connection | " + e.getMessage());
            }
            return total;
        }
    }


        // For Calculate Returned Sells  Bills Total
        public double calculate_import_returned_sells_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM((move_quantity * move_price)) AS total FROM `item_move` WHERE move_kind LIKE 'مرتجع شركة'" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_returned_sells_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_returned_sells_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_returned_sells_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_returned_sells_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }

        // For Calculate Current Store  Values Total
        public double calculate_store_values_total() {
            double total =0.00 ;

            Connect c =  Connect.start_Connection();
            ResultSet rs ;
            String query = "SELECT SUM(item_current_quantity * item_buy_price) AS total FROM `items`" ;

            try {

                rs = c.stmt.executeQuery(query);
                while (rs.next()) {
                    total = rs.getDouble("total") ;
                }
                System.out.println("Bank_DataManager.calculate_store_values_total() | Size " + total) ;

            } catch (Exception e){
                System.out.println("Bank_DataManager.calculate_store_values_total() | Error | " + e.getMessage());
            }finally {
                try{
                    // close connection
                    System.out.println("Bank_DataManager.calculate_store_values_total() | Connection | Closed" );
                }catch (Exception e){
                    System.out.println("Bank_DataManager.calculate_store_values_total() | Connection | " + e.getMessage());
                }
                return total;
            }
        }


}
