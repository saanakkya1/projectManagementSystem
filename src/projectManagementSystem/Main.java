package projectManagementSystem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.sql.Connection;
import java.io.Console;

import projectManagementSystem.Admin.Admin;
import projectManagementSystem.Manage.Manager;
import projectManagementSystem.Manage.User_Edit;
import projectManagementSystem.User.User;

import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;

public class Main {
    static Connection con;
    static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    static {
        try {
            con = ConnectDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

/*
* nbeves0@uol.com.br --> manager
* pdowbakinj@scribd.com --> Admin
* kfaullh@yolasite.com --> user
*
*/
    public static String read_password() {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        char[] passwordArray = console.readPassword("Enter password ");
        return new String(passwordArray);
    }
    public static void main(String[] args) throws SQLException, IOException {
        while(true){
            System.out.println("""
                \t1. Login
                \t2. Reg User
                Enter your choice""");

        int choice = GetInput.getChoice(2,"Choice");//1;//
        switch (choice) {
            case 1->
                login();
            case 2 -> {
                register();
                login();
            }
        }
        }

    }
    public static void register () throws SQLException, IOException {
        User_Edit.add(1);
    }
    public static void login() throws IOException, SQLException {
        boolean passw = true;
        while (true) {
            if(passw)System.out.println("Enter Email-Id:");
            passw = true;
            String email =read.readLine();//"nbeves0@uol.com.br";//
            String sql = "Select * from user where email=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            //System.out.println(rs.next());
            if (rs.next()) {
                System.out.println("Enter password");
                while (true) {
                    String pass =  read.readLine();//"Beves@Nevile";//read_password();
                    String sql1 = "Select * from user where email=?";
                    PreparedStatement stmt1 = con.prepareStatement(sql1);
                    stmt1.setString(1, email);
                    ResultSet rs1 = stmt1.executeQuery();
                    rs1.next();
                    String Pass = rs1.getString("password");
                    if (Pass.equals(pass)) {
                        System.out.println("Logged In as:\t" + rs1.getString("first_name"));
                        int role_id = rs1.getInt("role_id");
                        switch (role_id) {
                            case 1 -> {
                                User.main(rs1.getInt("user_id"));
                            }
                            case 2 -> {
                                Manager.main(rs1.getInt("user_id"));
                            }
                            case 3 -> {
                                Admin.main(rs1.getInt("user_id"));
                            }
                        }

                        break;
                    } else {
                        System.out.println("Incorrect password Re-Enter Password Again...");
                    }
                }
            break;
            }
            else{
                System.out.println("Enter a valid Email-Id:");
                passw =false;
            }


        }
    }
    }

