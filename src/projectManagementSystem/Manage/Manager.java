package projectManagementSystem.Manage;

import projectManagementSystem.Manage.Bugs;
import projectManagementSystem.Connect_DB.Connect_DB;
import projectManagementSystem.Manage.Project;
import projectManagementSystem.Manage.Task;
import projectManagementSystem.Manage.User_Edit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;

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
    public void CreateProject(){
        System.out.println("   ");
    }
    public static void main(int user_id){
        try{
            Scanner sc = new Scanner(System.in);

/*        //System.out.println("From manager");
            System.out.print("\033[H\033[2J");
            System.out.flush();*/
        System.out.println("""
                \t1.Project Menu
                \t2.User menu
                \t3.Tasks Menu
                \t4.Bugs Menu
                \t5.Exit
                Enter your choice""");
        while(true){
int choice = projectManagementSystem.GetInput.getChoice(5,"Choice");

            if(choice>=1 && choice<=5){
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
                case 5: break;
            }
            if(choice==5)break;
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
}