package projectManagementSystem.Admin;

import projectManagementSystem.Manage.Bugs;
import projectManagementSystem.Manage.Project;
import projectManagementSystem.Manage.Task;
import projectManagementSystem.Manage.User_Edit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.InputMismatchException;

import static projectManagementSystem.GetInput.getChoice;

public class Admin {
    public static void main(int user_id) throws SQLException {
        try{
            System.out.println("""
                \t1.Project Menu
                \t2.User menu
                \t3.Tasks Menu
                \t4.Bugs Menu
                \t5.
                \t6.Review Work
                \t7.Exit
                Enter your choice""");
            while(true){
                int choice = getChoice(7,"Choice");

                if(choice>=1 && choice<=7){
                    switch (choice) {
                        case 1:
                            Project.main(user_id);
                            break;
                        case 2:
                            User_Edit.main(user_id);
                            break;
                        case 3:
                            Task.main(user_id);
                            break;
                        case 4:
                            Bugs.main(user_id);
                            break;
                        case 5: break;
                        case 6: break;
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
        catch (InputMismatchException e){
            System.out.println("Enter a valid input");
            main(user_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
