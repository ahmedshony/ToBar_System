package DB_Manager.Permissions_Manager;

import AppManager.UIManager;
import DB_Manager.Connect;
import Model.Enums.QueryState;
import Model.Notification.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Notification_Manager {


    public QueryState add_notification (Notification notification) {

        Connect c =  Connect.start_Connection();
        QueryState missionState = QueryState.failed;

        String query =
                "INSERT INTO `notification` " +
                        "(`subject`, `date`) "+
                        "VALUES ( "+
                        "'" + notification.getSubject() + "', ";
        if (notification.getDate() != null){
            query = query + "'" + notification.getDate() + "') ";
        }else{
            query = query + "NULL)";
        }

        try{
            c.stmt.executeUpdate(query);
            missionState = QueryState.success;
            System.out.println("Notification_Manager.add_notification() | " + missionState);
        }catch (SQLException e) {
            System.out.println("Notification_Manager.add_notification() | Error | " + e.getMessage());
        }
        finally {
            try{
                // close connection
                System.out.println("Notification_Manager.add_notification() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Notification_Manager.add_notification() | Connection | " + e.getMessage());
            }
            return missionState;
        }
    }


    public ObservableList<Notification> load_noifictions(){
        Connect c =  Connect.start_Connection();
        ResultSet rs ;

       ObservableList<Notification> op_list  = FXCollections.observableArrayList();
        String query =
                "SELECT * "+
                        "FROM `notification` " +
                "ORDER BY `date` desc, `id`";

        try{
            rs = c.stmt.executeQuery(query);
            while (rs.next()) {
                Notification notification = new Notification();
                notification.setId(rs.getInt("id"));
                notification.setSubject(rs.getString("subject"));
                if (rs.getDate("date") != null){
                    notification.setDate(rs.getDate("date"));
                }else{
                    notification.setDate(null);
                }
                op_list.add(notification);
            }
            System.out.println("Notification_Manager.load_noifictions() | Size | " + op_list.size());
        }catch (Exception e){
            System.out.println("Notification_Manager.load_noifictions() | Error | " + e.getMessage());
        }finally {
            try{
                // close connection
                System.out.println("Notification_Manager.load_noifictions() | Connection | Closed" );
            }catch (Exception e){
                System.out.println("Notification_Manager.load_noifictions() | Connection | " + e.getMessage());
            }
            return op_list;
        }
    }




}
