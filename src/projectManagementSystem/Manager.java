package projectManagementSystem;

//import projectManagementSystem.Manage.Bugs;
//import projectManagementSystem.Manage.Project;
import projectManagementSystem.Manage.Task;
//import projectManagementSystem.Manage.User_Edit;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Manager {

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
                Enter your choice
                \t1.Project Menu
                \t2.User menu
                \t3.Tasks Menu
                \t4.Bugs Menu
                \t5.Exit
                """);
        while(true){
int choice = 3;//sc.nextInt();
        if(choice>=1 && choice<=5){
            switch (choice) {
                case 1:
//                    Project.main(user_id);
                    break;
                case 2:
                    System.out.println("USER EDIT");
                    //User_Edit.main();
                    break;
                case 3:
                    System.out.println("TASK");
                    Task.main(user_id);
                    break;
                case 4:
                    System.out.println("BUGS");
//                    Bugs.main(user_id);
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
catch (InputMismatchException e){
    System.out.println("Enter a valid input");
    main(user_id);
} catch (SQLException e) {
            e.printStackTrace();
        }
    }
}