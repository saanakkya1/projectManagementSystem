package projectManagementSystem.User;

import projectManagementSystem.GetInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.InputMismatchException;

import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;
import static projectManagementSystem.GetInput.*;

public class User {
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
    public static void main(int user_id){
        try{
            System.out.println("""
                    \t1.View Tasks
                    \t2.View Bugs
                    \t3.View Project
                    \t4.Update Tasks
                    \t5.Update Bugs
                    \t6.Change password
                    \t7.Exit
                    Enter your choice""");
            while(true){
                int choice = projectManagementSystem.GetInput.getChoice(7,"Choice");

                if(choice>=1 && choice<=7){
                    switch (choice) {
                        case 1:
                            User.viewUserWork(user_id,"task");
                            break;
                        case 2:
                            User.viewUserWork(user_id,"bug");
                            break;
                        case 3:
                            User.viewUserWork(user_id,"project");
                            break;
                        case 4:
                        {
                            int id= checkID_table(con,"task",user_id);
                            updateUserWork(user_id,"task",id);
                            break;}
                        case 5: {
                            int id = checkID_table(con,"bug",user_id);
                            updateUserWork(user_id,"bug",id);break;
                        }
                        case 6:{
                            changePass(con,user_id);
                            break;
                    }
                    }
                    if(choice==7)break;
                    main(user_id);
                    break;
                }
                else{
                    System.out.println("Enter a valid choice....");
                }
            }
        }
        catch (InputMismatchException  e){
            System.out.println("Enter a valid input");
            main(user_id);
        } catch (IOException |SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewUserWork(int user_id,String table_name) throws IOException, SQLException {

        try {
            String query = "";
//            System.out.println(table_name);
            if ("project".equals(table_name)) {
                query = "select distinct project.project_id,project_name,status_title from project join user_project on project.project_id=user_project.project_id join status on project.status=status_id join user on user_project.user_id=user.user_id join role on user.role_id=role.role_id where user.user_id=? and project.status<=2;";
            } else if ("task".equals(table_name)) {
                query = "select distinct task.task_id,task_name,status_title,project_name from task join status on task.status=status_id join user_task on user_task.task_id=task.task_id join project on task.project_id=project.project_id where user_task.user_id=? ;";
            } else if ("bug".equals(table_name)) {
                query = "select distinct bug.bug_id bug_name,status_title,project_name from bug join status on bug.status=status_id join user_bug on user_bug.bug_id=bug.bug_id join project on bug.project_id=project.project_id where user_bug.user_id=? ;";
            }
            PreparedStatement stmnt = con.prepareStatement(query);
            stmnt.setInt(1, user_id);
//            System.out.println(stmnt);
            ResultSet rs = stmnt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            System.out.println(col_count);
            while (rs.next()) {
                for (int j = 1; j <= col_count; j++) {
                    if (j == rsmd.getColumnCount()) System.out.printf("| %-20s |", rs.getString(j));
                    else System.out.printf("| %-20s ", rs.getString(j));
                }
                System.out.println();
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Enter a valid  which is not closed");
            viewUserWork(user_id, table_name);
        }

    }


    public static void updateUserWork(int user_id,String table_name,int id) throws IOException, SQLException{
        String query = "";
        System.out.println("Enter the status of work\n1. IN PROGRESS\n2. COMPLETED\n");
        int choice1=getChoice(2,"Choice");
        query = "";
        if ("project".equals(table_name)) {
            query = "update user_project values(?,?)";
        }else if("task".equals(table_name)){
            query = "update task set status=? where task_id=?";
        }else if("bug".equals(table_name)){
            query = "update bug set status=? where bug_id=?";
        }
        PreparedStatement stmt= con.prepareStatement(query);
        stmt.setInt(1,choice1+1);
        stmt.setInt(2,id);

        int result = stmt.executeUpdate();
        if (getConfirmation(con)) {
            System.out.println("\nDo you want to update another " + table_name + " [y/N]?");
            String close_another = GetInput.getValidInput(read.readLine(),"[y/N] ONLY");
            if (close_another.equalsIgnoreCase("y")) {
                System.out.printf("Enter %s_id\n",table_name);
                updateUserWork(user_id,table_name, checkID_table(con,table_name,user_id));
            }
            }

        }
    public static void changePass(Connection con,int user_id) throws IOException, SQLException {
        System.out.println("Enter Old Password...");
        String old_pass = getValidInput(read.readLine(),"Password");
        System.out.println(old_pass);
        String sql1 = "select password from user where user_id=?";
        PreparedStatement stmt = con.prepareStatement(sql1);
        stmt.setInt(1,user_id);
        System.out.println(stmt);
        System.out.println(user_id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        System.out.println(rs.getString("password"));
        if(old_pass.equals(rs.getString("password"))){
            String pass = getPassword();
            String sql = "update user set password=? where user_id=?";
            PreparedStatement stmt1 = con.prepareStatement(sql);
            stmt1.setString(1,pass);
            stmt1.setInt(2,user_id);
            stmt1.executeUpdate();
            if(getConfirmation(con)){
                System.out.println("Password successfully changed....");
            }
            else System.out.println("Password not changed........");
    }
    }
    }


