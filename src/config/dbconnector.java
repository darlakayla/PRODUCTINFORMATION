package config;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import static javax.management.remote.JMXConnectorFactory.connect;

public class dbconnector {
    
    public Connection connection;
    
    public dbconnector(){
        
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/delivery","root","");
        }catch (SQLException e){
            System.err.println("Cannot connect to database: "+ e.getMessage());
        }
    }
    public Connection connect_db(){
              try{
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/productinformation", "root", "");
            }catch(SQLException ex){
                    System.out.println("Can't connect to database: "+ex.getMessage());
            }
              return connection;
        }
        
    
     public ResultSet getdata(String sql) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        return rs;
    }
         public void insertData(String sql){
             int result;
            try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
           
            pstmt.executeUpdate();
            System.out.println("Inserted Successfully!");
            pstmt.close();
            
            }catch(SQLException e){
                System.out.println("Connection Error: "+e);
               
            }
    }
   
      //for DELETE or REMOVE data, must be inside the connector class
    public void deleteData(int id, String table, String table_id) {
    try {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM "+table+" WHERE "+table_id+" = ?");
        stmt.setInt(1, id);
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println(rowsDeleted + " rows were deleted.");
        } else {
            System.out.println("No rows were deleted.");
        }
        stmt.close();
        connection.close();
    } catch (SQLException e) {
        System.out.println("Error deleting data: " + e.getMessage());
    }
}
   
      //for UPDATE or CHANGE data, must be inside the connector class
    public int updateData(String sql){
        int num = 0;
        try {
       
            String query = sql;
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowsUpdated = pstmt.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Data updated successfully!");
                num = 1;
            } else {
                System.out.println("Data update failed!");
                num = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
       
        return num;
    }
    
    
    
    
      
   
     
}
