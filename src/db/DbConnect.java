/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author hocin
 */
public class DbConnect {
    
    private static String HOST = "localhost";
        private static int PORT = 3306;
        private static String DB_NAME = "notex_asma";
        private static String USERNAME = "root";
        private static String PASSWORD = "";
        private static Connection conn ;
        
        
        public static Connection getConnection (){
        try {
            conn = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            return  conn;
        }
        
        
        

    
}
