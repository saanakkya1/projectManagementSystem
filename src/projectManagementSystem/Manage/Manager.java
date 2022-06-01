package projectManagementSystem.Manage;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;

import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;
import static projectManagementSystem.GetInput.checkId;
import static projectManagementSystem.GetInput.getChoice;

public class Manager {
    static Connection con;
    static {
        try {
            con = ConnectDB();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("[-] Database Not Connected Please Make sure The DB server is running...");
        }
    }

    public static void main(int user_id) throws SQLException{
        try{
        System.out.println("""
                \t1.Project Menu
                \t2.User menu
                \t3.Tasks Menu
                \t4.Bugs Menu
                \t5.Assign Work
                \t6.Review Work
                \t7.Exit
                Enter your choice""");
        while(true){
int choice = getChoice(7,"Choice");

            if(choice>=1 && choice<=7){
                switch (choice) {
                    case 1 -> Project.main(user_id);
                    case 2 -> User_Edit.main(user_id);
                    case 3 -> Task.main(user_id);
                    case 4 -> Bugs.main(user_id);
                    case 5 -> assignWork();
                    case 6 -> viewWork();
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
} catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void assignWork() throws IOException, SQLException {
        System.out.println("""
                \t1.Assign Project
                \t2.Assign Task
                \t3.Assign Bug
                \t4.Exit  
                Enter your choice """);
        while(true){
            int choice = getChoice(4,"Choice");

            if(choice>=1 && choice<=5){
                switch (choice) {
                    case 1:
                    {
                        System.out.println("Enter USER ID of User");
                        int user_id = checkId(con,"user","USER ID");
                        System.out.println("Enter PROJECT ID of Project");
                        int project_id = checkId(con,"project","PROJECT ID");
                        User_Task.assign("user_project",user_id,project_id);
                        break;
                    }
                    case 2:
                    {
                        System.out.println("Enter USER ID of User");
                        int user_id = checkId(con,"user","USER ID");
                        System.out.println("Enter TASK ID of Task");
                        int task_id = checkId(con,"task","TASK ID");
                        User_Task.assign("user_task",user_id,task_id);
                        break;
                    }
                    case 3:
                    {
                        System.out.println("Enter USER ID of User");
                        int user_id = checkId(con,"user","USER ID");
                        System.out.println("Enter BUG ID of Project");
                        int bug_id = checkId(con,"bug","BUG ID");
                        User_Task.assign("user_bug",user_id,bug_id);
                        break;
                    }
                    case 4:break;
                }
                if(choice==4)break;
                break;
            }
            else{
                System.out.println("Enter a valid choice....");
            }
        }

    }
    public static void viewWork() throws IOException,SQLException{
        while(true){
        System.out.println("""
                \t1. Project
                \t2. Task
                \t3. Bug
                \t4. Exit  
                Enter your choice """);
        int choice = getChoice(4,"Choice");
            viewWork(choice);
            if(choice==4)break;
            viewWork();

        }
    }
    public static void viewWork(int choice) throws IOException, SQLException {
            if(choice>=1 && choice<=4){
                switch (choice) {
                    case 1:
                    {
                        String sql = "select project_id,project_name,created_date,first_name,status_title from project join status on status=status_id join user on created_by=user_id  where status_id=3";
                        view(con,sql);
                        break;
                    }
                    case 2:
                    {
                        String sql = "select task_id,task_name,description,reported_time,project_name,status_title from task join status on status=status_id join project on task.project_id=project.project_id where status_id=3";
                        view(con,sql);
                        break;
                    }
                    case 3:
                    {
                        String sql="select bug_id,bug_name,description,reported_time,project_name,sevr_title, status_title from bug join status on bug.status=status_id join bug_sevr on sevr=sevr_id join project on bug.project_id=project.project_id where status_id = 3";
                        view(con,sql);
                        break;
                    }
                    case 4:break;
                }
                }
            else{
                System.out.println("Enter a valid choice....");
            }
        }



    public static void view(Connection con,String sql) throws SQLException {
        Statement stmt=  con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData rsmd = rs.getMetaData();
        int col_count = rsmd.getColumnCount();
        rs.next();
        while (rs.next()){
            for (int j = 1; j <= col_count; j++) {
                if (j == rsmd.getColumnCount()) System.out.printf("| %-25s |", rs.getString(j));
                else System.out.printf("| %-25s ", rs.getString(j));
            }
            System.out.println();
        }
        if(!rs.next()){
            System.out.println("Nothing to show  !!!!");
        }
    }
    public static void completeWork(){

    }
}