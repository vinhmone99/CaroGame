package caro.database;


import caro.common.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class DataFunc {
    Connection con = Connections.getConnection();

    public List<Users> getUserList() {
       
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        try {
           
            stm = con.prepareStatement("SELECT * FROM Users");
            rs = stm.executeQuery();
            
            List<Users> uslist = new ArrayList<Users>();
            
            while (rs.next()) {
                Users us = new Users();
                us.setId(rs.getInt("Id"));
                us.setUsername(rs.getString("username"));
                us.setPassword(rs.getString("password"));
                us.setWin(rs.getInt("win"));
                us.setLose(rs.getInt("lose"));
                us.setScore(rs.getInt("score"));
                uslist.add(us);
            }
            return uslist;
        } catch (SQLException ex) {
                  ex.printStackTrace();
        }    
        return null;
    }
    
    public Users checkLogin(String username, String password) {
        try {      
            String sql = "SELECT * FROM Users WHERE username = ? and password = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            boolean result = rs.next();
            rs.close();
            stm.close();
            if (result) {
                return getUser(username);
            }
        } catch (SQLException e) {
        }
        return null;
    }
    
    public boolean register(String username, String password) {
            Statement statement;           
            String str = "INSERT INTO Users(username,password)values('"+ username+"','"+password+"')" ;
                        
            if (con != null) {
                try {
                    statement = con.createStatement();
                    statement.executeUpdate(str);
                    return true;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //String tempuser = rs.getString("account");

            }
                        
       return false; 
}
    
    public boolean checkAva(int id) {
        try {
            String sql = "SELECT * FROM Users WHERE id = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            boolean result = rs.next();
            rs.close();
            stm.close();
            if (result) {
                return true;
            }
            
        } catch (SQLException e) {
        }
        return false;
    }
    
    public boolean updateUser(Users user) throws SQLException {
            String sqlStatement =
            "UPDATE Users " +
            "SET username = ?,"  +
            "password = ?,"  +
            "win = ?,"  +
            "lose = ?,"  +
            "score = ?"  +
            "WHERE Id = ?;";
            PreparedStatement updateQuery  = con.prepareStatement(sqlStatement);
            updateQuery.setString(1, user.getUsername());
            updateQuery.setString(2, user.getPassword());
            updateQuery.setInt(3, user.getWin());
            updateQuery.setInt(4, user.getLose());
            updateQuery.setInt(5, user.getScore());
            updateQuery.setInt(6, user.getId());
            updateQuery.execute();
                              
            return true;
    }
    
    public boolean updateWin(int  id, int win) throws SQLException {
            if(checkAva(id) == false)
                return false;
            
            String sqlStatement =
            "UPDATE Users " +
            "SET win = ?"  +
            "WHERE Id = ?";
            PreparedStatement updateQuery  = con.prepareStatement(sqlStatement);

            updateQuery.setInt(1, win);
            updateQuery.setInt(2, id);

            updateQuery.execute();
                              
            return true;
    }
    public boolean updateLose(int id, int lose) throws SQLException {
            String sqlStatement =
            "UPDATE Users " +
            "SET lose = ?"  +
            "WHERE Id = ?"; 
            PreparedStatement updateQuery  = con.prepareStatement(sqlStatement);

            updateQuery.setInt(1, lose);
            updateQuery.setInt(2, id);

            updateQuery.execute();
                              
            return true;
    }
    public int getId(String username)    {
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
           
            stm = con.prepareStatement("SELECT * FROM Users WHERE username = ?");
            stm.setString(1, username);
            rs = stm.executeQuery();
            
            while (rs.next()) {
               result = rs.getInt("Id");
               break;
            }
            rs.close();
            stm.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }    
        return result;
    
    }
    public Users getUser(String username)    {
        PreparedStatement stm = null;
        ResultSet rs = null;
                try {
           
            stm = con.prepareStatement("SELECT * FROM Users WHERE username = ?");
            stm.setString(1, username);
            
            
            
            rs = stm.executeQuery();
            
         
            
            while (rs.next()) {
       
                    Users us = new Users();
                    us.setId(rs.getInt("id"));
                    us.setUsername(rs.getString("username"));
                    us.setPassword(rs.getString("password"));
                    us.setWin(rs.getInt("win"));
                    us.setLose(rs.getInt("lose"));
                    us.setScore(rs.getInt("score"));
                   
                    return us;
                
                
        }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }    
        return null;
    }
    
    public boolean DeleteUser(int id) throws SQLException
    {
            PreparedStatement stm = null;
            
            stm = con.prepareStatement("DELETE FROM Users WHERE id = ?");
            stm.setInt(1, id);
                                 
            stm.execute();
        return false;
    
    }
    
}
