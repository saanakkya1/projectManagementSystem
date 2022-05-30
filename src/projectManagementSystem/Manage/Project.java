package projectManagementSystem.Manage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.sql.*;
import java.util.*;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;
import static projectManagementSystem.Connect_DB.Connect_DB.*;

public class Project {
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


    static int project_id;
    static String project_name;
    static int project_created_by;
    static Timestamp created_date;
    static int project_status;
    static Scanner sc = new Scanner(System.in);
    public static void main(int user_id){
            try{
                System.out.println("""
                        Enter your choice for Project Menu
                        \t1.Add Project
                        \t2.Modify Project
                        \t3.Review Project
                        \t4.Close Project
                        \t5.View Projects
                        \t6.Exit
                        """);
                while(true){
                    int choice = sc.nextInt();
                    if(choice>=1 && choice<=6){
                        switch (choice) {
                            case 1:
                                Project.add(user_id);
//                                Project.add(user_id);
                                break;
                            case 2:
                                Project.modify(user_id);
                                break;
                            case 3:
                                Project.review("project");
                                break;
                            case 4:
                                Project.close("project",5);
                                break;
                            case 5: PrintDB(con,"project");
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
        try{
            System.out.println("Enter Project name :");
            String project_name=sc.nextLine();
            System.out.printf("Enter Status of Project %s\n",project_name);
            int status = sc.nextInt();
            String sql1 = "insert into project(project_name,created_by,status) values (?,?,?);";
            PreparedStatement stmt = con.prepareStatement(sql1);
            stmt.setString(1, project_name);
            stmt.setInt(2, user_id);
            stmt.setInt(3, status);
            int result = stmt.executeUpdate();
            System.out.println("Successfully Added The Project");
            PreparedStatement stmt1= con.prepareStatement("select * from project",TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt1.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                System.out.printf(String.format("| %-20s",rsmd.getColumnLabel(i)));
            }
            System.out.println("");
            rs.last();
            for(int j =1;j<=rsmd.getColumnCount();j++) {
                System.out.printf("| %-20s", rs.getString(j));
            }
            System.out.println("\n\n\n");
            if(result==0){
                System.out.println("Please Re-Enter the details again");
                add(user_id);
            }
            }
        catch(SQLException e){
            System.out.println("Please renter the Details without mistakes");
            add(user_id);
    }
;
    }

    public static void modify(int user_id)throws SQLException{
        /*try{
            Scanner sc = new Scanner(System.in);
            view();
        System.out.println("Enter Project Id to modify :");
        int project_id=sc.nextInt();
                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for Project Menu
                        \t1.Add Project
                        \t2.Modify Project
                        \t3.Review Project
                        \t4.Close Project
                        \t5.View Projects
                        \t6.Exit
                        """);
                while(true){
                    int choice = sc.nextInt();
                    if(choice>=1 && choice<=6){
                        switch (choice) {
                            case 1:
                                Project.add(user_id);
                                break;
                            case 2:
                                Project.modify(user_id);
                                break;
                            case 3:
                                Project.review();
                                break;
                            case 4:
                                Project.close();
                                break;
                            case 5: Project.view();
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
        System.out.printf("Enter Status of Project %s\n",project_name);
        int status = sc.nextInt();
        String sql1 = "insert into project(project_name,created_by,status) values (?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql1);
        stmt.setString(1, project_name);
        stmt.setInt(2, user_id);
        stmt.setInt(3, status);
        int i = stmt.executeUpdate();
        System.out.println("Successfully Added The Project");
        if(i==0){
            System.out.println("Please Re-Enter the details again");
            add(user_id);
        }*/


    }







    /*public static void view() throws SQLException { //,int project_id
        String sql = "select * from project";
        PreparedStatement stmt = con.prepareStatement(sql,TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
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
    }*/
    public static void review(String table_name){
        try{
            System.out.println("Enter project id of the project to review");
            int project_id = sc.nextInt();
            String sqlqry = "select * from " +table_name+ " where "+table_name+"_id=?";
            PreparedStatement stmnt = con.prepareStatement(sqlqry);
            stmnt.setInt(1, project_id);
            ResultSet rs = stmnt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            rs.next();
        }
        catch (InputMismatchException | SQLException e){
            System.out.println("Enter a valid Project_id");
            review("project");
        }
    }
    public static void close(String table_name,int status) throws SQLException {
        try{
            System.out.println("Enter project id");
            while(true) {
                int project_id = Integer.parseInt(read.readLine());
                System.out.println(project_id);
                String sqlqry = "select * from "+table_name+" where "+table_name+"_id=?";
                PreparedStatement stmnt = con.prepareStatement(sqlqry);
                stmnt.setInt(1, project_id);
                System.out.println(stmnt);
                ResultSet rs = stmnt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int col_count = rsmd.getColumnCount();
                rs.next();
                String query = "update " + table_name + " set status = " + status +" where "+table_name+"_id=?";
                PreparedStatement statement = con.prepareStatement(query);
                statement.setInt(1,project_id);
                int result = statement.executeUpdate();
                System.out.println(result);
                if (result >0) {
                    for (int j = 1; j <= col_count; j++) {
                        if (j == rsmd.getColumnCount()) System.out.printf("| %-20s |", rs.getString(j));
                        else System.out.printf("| %-20s ", rs.getString(j));
                    }
                    System.out.println("\nAre You Sure to Close The " + table_name + " [yes/NO]");
                    String is_sure = sc.next();
                    if (is_sure.equalsIgnoreCase("yes")) con.commit();
                    else con.rollback();
                    System.out.println("Do you want to close another "+table_name+ "[yes/NO]?");
                    String close_another = sc.next();
                    if (close_another.equalsIgnoreCase("yes")) {
                        close("project",5);break;
                    }

                    else break;

                }
                else {
                    System.out.println("Enter " + table_name.toUpperCase() + " Id Again....");

                }
                break;
            }
            }
        catch(InputMismatchException|IOException e ){
            System.out.println("Enter a valid project_id");
            close("project",5);
    }
    }
    }



