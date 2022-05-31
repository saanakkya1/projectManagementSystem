package projectManagementSystem.User;

import projectManagementSystem.Manage.Bugs;
import projectManagementSystem.Manage.Project;
import projectManagementSystem.Manage.Task;
import projectManagementSystem.Manage.User_Edit;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;

public class User {
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
        /*try{
            System.out.println("""
                    \t1.View Tasks
                    \t2.View Bugs
                    \t3.Update Tasks
                    \t4.Update Bugs
                    \t5.Change password
                    \t6.Exit
                    Enter your choice""");
            while(true){
                int choice = projectManagementSystem.GetInput.getChoice(6,"Choice");

                if(choice>=1 && choice<=6){
                    switch (choice) {
                        case 1:
                            User.review(user_id,"");
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


    public static void review(int user_id,String table_name,int project_id) throws IOException {
        try{
            String sqlqry = "select bug_id,bug_name,description,reported_time,project_id from bug join status on status=status_id join bug_sevr on sevr=sevr_id where "+table_name+"_id=? and status <> 5";
            PreparedStatement stmnt = con.prepareStatement(sqlqry);
            stmnt.setInt(1, project_id);
            ResultSet rs = stmnt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            rs.next();
            for (int j = 1; j <= col_count; j++) {
                if (j == rsmd.getColumnCount()) System.out.printf("| %-20s |", rs.getString(j));
                else System.out.printf("| %-20s ", rs.getString(j));
            }
            System.out.println();
        }
        catch (InputMismatchException | NumberFormatException | SQLException e){
            System.out.println("Enter a valid "+table_name+"_id which is not closed");
            review(user_id,table_name,project_id);
        }*/
    }
}

