package projectManagementSystem.Manage;

import projectManagementSystem.Manage.Bugs;
import projectManagementSystem.Connect_DB.Connect_DB;
import projectManagementSystem.Manage.Project;
import projectManagementSystem.Manage.Task;
import projectManagementSystem.Manage.User_Edit;
import static projectManagementSystem.GetInput.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;
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

    public static void main(int user_id){
        try{

/*        //System.out.println("From manager");
            System.out.print("\033[H\033[2J");
            System.out.flush();*/
        System.out.println("""
                \t1.Project Menu
                \t2.User menu
                \t3.Tasks Menu
                \t4.Bugs Menu
                \t5.Assign Work
                \t6.Exit
                Enter your choice""");
        while(true){
int choice = getChoice(6,"Choice");

            if(choice>=1 && choice<=6){
            switch (choice) {
                case 1:
                    Project.main(user_id);
                    break;
                case 2:
                    User_Edit.main();
                    break;
                case 3:
                    Task.main(user_id);
                    break;
                case 4:
                    Bugs.main(user_id);
                    break;
                case 5: assignWork();
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
catch (InputMismatchException | SQLException e){
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

}