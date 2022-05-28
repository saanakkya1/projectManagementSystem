package projectManagementSystem.Manage;

import projectManagementSystem.Main;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class Project {
        public static void main(int user_id){
            try{
                Scanner sc = new Scanner(System.in);

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
        }
    public static void add(int user_id) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = Main.ConnectDB();
        System.out.println("Enter Project name :");
        String project_name=sc.nextLine();
        System.out.printf("Enter Status of Project %s\n",project_name);
        int status = sc.nextInt();
        String sql1 = "insert into project(project_name,created_by,status) values (?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql1);
        stmt.setString(1, project_name);
        stmt.setInt(2, user_id);
        stmt.setInt(3, status);
        int i = stmt.executeUpdate();
        System.out.println("Successfully Added The Project");
        PreparedStatement stmt1= con.prepareStatement("select * from project",TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
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

    public static void modify(int user_id)throws SQLException{
        Scanner sc = new Scanner(System.in);
        Connection con = Main.ConnectDB();
        System.out.println("Enter Project name :");
        String project_name=sc.nextLine();
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
        }

    }
    public static void view() throws SQLException { //,int project_id
        String sql = "select * from project";
        Connection con = Main.ConnectDB();
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
    }
    public static void review(){}
    public static void close(){}

}

