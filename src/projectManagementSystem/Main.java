package projectManagementSystem;
//import projectManagementSystem.*;
import java.sql.*;
import java.sql.Connection;
import java.util.Scanner;
import java.io.Console;
import projectManagementSystem.*;
public class Main {
    static final String     DB_NAME     = System.getenv("DB_NAME");
    static final String     DB_HOST     = System.getenv("DB_HOST");
    static final String     UNAME       = System.getenv("UNAME");
    static final String     PASS        = System.getenv("PASS");

    public static Connection ConnectDB() throws SQLException {
        String DB_URL = "jdbc:mysql://"+DB_HOST+"/"+DB_NAME;
        //System.out.println("Established connection to MySql DB Successfully....");
        return DriverManager.getConnection(DB_URL,UNAME,PASS);
    }
/*
* nbeves0@uol.com.br --> manager
* pdowbakinj@scribd.com --> Admin
* kfaullh@yolasite.com --> user
*
* */

    public static String read_password() {
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        console.printf("Testing password%n");
        char[] passwordArray = console.readPassword("Enter your secret password: ");
        console.printf("Password entered was: %s%n", new String(passwordArray));
        return new String(passwordArray);
    }
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = ConnectDB();
        boolean passw = true;
        while (true) {
            if(passw)System.out.println("Enter Email-Id:");
            passw = true;
String email ="nbeves0@uol.com.br";// sc.nextLine();
            String sql = "Select * from user where email=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            //System.out.println(rs.next());
            if (rs.next()) {
                while (true) {
                    System.out.println("Enter password");
String pass = "123456789";//1sc.nextLine();//read_password();
                    String sql1 = "Select * from user where email=? and password=?";
                    PreparedStatement stmt1 = con.prepareStatement(sql1);
                    stmt1.setString(1, email);
                    stmt1.setString(2, pass);
                    ResultSet rs1 = stmt1.executeQuery();
                    if (rs1.next()) {
                        System.out.println("Logged In as:\t" + rs1.getString("first_name"));
                        switch (rs1.getInt("role_id")) {
                            case 1 : {
                                User.main();
                                break;
                            }
                            case 2 : {
                                System.out.print("\033[H\033[2J");
                                System.out.flush();
                                Manager.main(rs1.getInt("user_id"));
                                break;
                            }
                            case 3 : {
                                Admin.main();
                                break;
                            }
                        }

                        break;
                    } else {
                        System.out.println("Incorrect password Re-Enter Password Again...");
                    }
                    //System.out.println("Logged In as:\t"+rs1.getString("first_name"));
                }
                //break;
            }
                else{
                    System.out.println("Enter a valid Email-Id:");
                   passw =false;
                }


        }
    }


    }

