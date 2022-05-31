package projectManagementSystem.Manage;

import projectManagementSystem.GetInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;

public class User_Task {
    static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    static Connection con;
    static {
        try {
            con = ConnectDB();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("[-] Database Not Connected Please Make sure The DB server is running...");
        }
    }
    public static void assign(String table_name,int id_1,int id_2) throws  IOException {
        try {
            
            String sql = String.format("insert into %s values(?,?)", table_name);
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id_1);
            statement.setInt(2, id_2);
            int rs = statement.executeUpdate();
            if (rs == 1) GetInput.getConfirmation(con);
        }
        catch (SQLException e){
            System.out.println("[-] ID Doesnot EXIST CHECK ENTERED ID");
        }
    }
}
