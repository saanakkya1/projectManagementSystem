package projectManagementSystem.Manage;

import projectManagementSystem.Main;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class Bugs {
        public static void main(int user_id)throws SQLException{
            try{
                Scanner sc = new Scanner(System.in);

                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for Bugs Menu
                        \t1.Add Bugs
                        \t2.Modify Bugs
                        \t3.Review Bugs
                        \t4.Close Bugs
                        \t5.View Bugs
                        \t6.Exit
                        """);
                while(true){
                    int choice = sc.nextInt();
                    if(choice>=1 && choice<=5){
                        switch (choice) {
                            case 1:
                                Bugs.add(user_id);
                                break;
                            case 2:
                                Bugs.modify(user_id);
                                break;
                            case 3:
                                Bugs.review();
                                break;
                            case 4:
                                Bugs.close();
                                break;
                            case 5: Bugs.view();
                        }
                        if(choice==6)break;
                        main(user_id);
                        break;
                    }
                    else{
                        System.out.println("Enter a valid choice....");
                    }
                }
            }
            catch (InputMismatchException e){
                System.out.println("Enter a valid input");
                main(user_id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    public static void add(int user_id) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = Main.ConnectDB();
        System.out.println("Enter Bug name :");
        String bug_name=sc.nextLine();
        System.out.println("Enter Bug description :");
        String bug_description=sc.nextLine();
        System.out.println("Enter project id of the bug :");
        int project_id =sc.nextInt();
        System.out.printf("Enter Severity of Bug %s\n",bug_name);
        int sevr = sc.nextInt();
        String sql1 = "insert into bug(bug_name,description,project_id,sevr) values (?,?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql1);
        stmt.setString(1, bug_name);
        stmt.setString(2, bug_description);
        stmt.setInt(3, project_id);
        stmt.setInt(4, sevr);
        System.out.println("1");
        int i = stmt.executeUpdate();
        System.out.println("Successfully Added The Bug");
        PreparedStatement stmt1= con.prepareStatement("select * from bug",TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt1.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        for(int j = 1; i <= rsmd.getColumnCount(); i++)
        {
            System.out.printf(String.format("| %-20s",rsmd.getColumnLabel(i)));
        }
        System.out.println("");
        rs.last();
        for(int j =1;j<=rsmd.getColumnCount();j++) {
            System.out.printf("| %-20s", rs.getString(j));
        }
        System.out.println("\n\n\n");
        if(i==0){
            System.out.println("Please Re-Enter the details again");
            add(user_id);
        }
        ;
    }
    public static void modify(int user_id){}
    public static void review(){}
    public static void close(){}
    public static void view() throws SQLException {
        String sql = "select * from bug";
        Connection con = Main.ConnectDB();
        PreparedStatement stmt = con.prepareStatement(sql,TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        StringBuilder sb = new StringBuilder();
        rs.last();
        int row_count= rs.getRow();
        rs.beforeFirst();
        for(int i = 1; i <= rsmd.getColumnCount(); i++)
        {
            sb.append(String.format("| %-20s",rsmd.getColumnLabel(i)));
        }
        System.out.println(sb);
//        StringBuilder stringb = new StringBuilder();

        for(int i = 1; i <= row_count; i++) {
            rs.next();

            for(int j =1;j<=rsmd.getColumnCount();j++) {
                System.out.printf("| %-20s", rs.getString(j));
            }
            System.out.println("");
        }
        System.out.println("\n\n\n");
    }
}
