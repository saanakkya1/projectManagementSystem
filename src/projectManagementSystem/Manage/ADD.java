package projectManagementSystem.Manage;

import projectManagementSystem.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ADD {
    public static void main(){

    }

    /*public static void add(String tableName,String ) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = Main.ConnectDB();
        System.out.println("Enter :");
        while (true) {
            String email = sc.nextLine();
            String sql = "insert into "+tableName+" ";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            String sql1 = "insert into project(proj_name,created_by,status) values (?,?,?);";
            PreparedStatement stmt1 = con.prepareStatement(sql1);
            String proj_name = "";
            stmt.setString(1,proj_name);
            stmt.setString();
        }
    }*/



}
