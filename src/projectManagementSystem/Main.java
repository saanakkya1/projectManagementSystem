package projectManagementSystem;
//import projectManagementSystem.*;
import java.sql.*;
import java.sql.Connection;
import java.util.Scanner;


public class Main {
    static final String     DB_NAME     = System.getenv("DB_NAME");
    static final String     DB_HOST     = System.getenv("DB_HOST");
    static final String     UNAME       = System.getenv("UNAME");
    static final String     PASS        = System.getenv("PASS");

    public static Connection ConnectDB() throws SQLException {
        String DB_URL = "jdbc:mysql://"+DB_HOST+"/"+DB_NAME;
        Connection con = DriverManager.getConnection(DB_URL,UNAME,PASS);
        //System.out.println("Established connection to MySql DB Successfully....");
        return con;
    }
/*
* nbeves0@uol.com.br --> manager
* pdowbakinj@scribd.com --> Admin
* kfaullh@yolasite.com --> user
*
* */
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = ConnectDB();
        System.out.println("Enter Email-Id:");
        while (true) {
            String email = sc.nextLine();
            String sql = "Select * from user where email=?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            //System.out.println(rs.next());
            if (rs.next()) {
                //sample mail id csmead0@comcast.net
//                boolean passw = true;
                while (true) {
                    System.out.println("Enter password");
                    String pass = sc.nextLine();
                    //System.out.println(pass);
                    String sql1 = "Select * from user where email=? and password=?";
                    PreparedStatement stmt1 = con.prepareStatement(sql1);
                    stmt1.setString(1, email);
                    stmt1.setString(2, pass);
                    ResultSet rs1 = stmt1.executeQuery();
                    if (rs1.next()) {
                        //                    System.out.println(rs1.getString("first_name"));
                        System.out.println("Logged In as:\t" + rs1.getString("first_name"));
                        switch (rs1.getInt("role_id")) {
                            case 1 -> {
                                User.main();
                                break;
                            }
                            case 2 -> {
                                Manager.main();
                                break;
                            }
                            case 3 -> {
                                Admin.main();
                                break;
                            }
                        }

                        //passw = false;
                        break;
                    } else {
                        System.out.println("Incorrect password LOGIN Again...");
//                        passw = true;
                    }
                    //System.out.println("Logged In as:\t"+rs1.getString("first_name"));
                }
                break;
            }
                else{
                    System.out.println("Enter a vaild Email-Id:");
                    continue;
                }


        }
    }


    }

