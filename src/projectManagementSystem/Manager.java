package projectManagementSystem;

import projectManagementSystem.Manage.Bugs;
import projectManagementSystem.Manage.Project;
import projectManagementSystem.Manage.Task;
import projectManagementSystem.Manage.User_Edit;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Manager {

    public void CreateProject(){
        System.out.println("   ");
    }
    public static void main(){
        try{
            Scanner sc = new Scanner(System.in);

        //System.out.println("From manager");
        System.out.println("""
                Enter your choice
                \t1.Project Menu
                \t2.User menu
                \t3.Tasks Menu
                \t4.Bugs Menu
                \t5.Exit
                """);
        while(true){
        int choice = sc.nextInt();
        if(choice>=1 && choice<=5){
            switch (choice) {
                case 1:
                    Project.main();
                    break;
                case 2:
                    User_Edit.main();
                    break;
                case 3:
                    Task.main();
                    break;
                case 4:
                    Bugs.main();
                    break;
                case 5: break;
            }
            if(choice==5)break;
            main();
            break;
            }
        else{
            System.out.println("Enter a valid choice....");
        }
        }
        }
catch (InputMismatchException e){
    System.out.println("Enter a valid input");
    main();
}
    }
}