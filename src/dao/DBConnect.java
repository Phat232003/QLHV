/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author phath
 */
public class DBConnect {
    private static String DB_URL = "jdbc:mysql://localhost:3306/qlhv";
    private static String USER_NAME = "root";
    private static String PASSWORD = "";
    
    public static void main(String[] args){
        try{
            Class.forName("com.mysql.jdbc.Drive");
            Connection cons = DriverManager.getConnection("DB_URl, USER_NAME,PASSWORD);
            if(con != null){
                System.out.println("ket noi thanh cong");
            }
        }catch (Exception ex){
            System.err.println("loi ket noi"+ ex.getMessage);
            ex.printStackTrace();
        }
    }
}
