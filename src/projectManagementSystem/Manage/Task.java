package projectManagementSystem.Manage;

import projectManagementSystem.Main;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class Task {

        public static void main(int user_id){
            try{
                Scanner sc = new Scanner(System.in);

                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for Tasks Menu\s
                        \t1.Add Task
                        \t2.Modify Task
                        \t3.Review Task
                        \t4.Close Task
                        \t5.View Task
                        \t6.Exit
                        """);
                while(true){
                    int choice = sc.nextInt();
                    if(choice>=1 && choice<=5){
                        switch (choice) {
                            case 1:
                                Task.add(user_id);
                                break;
                            case 2:
                                Task.modify();
                                break;
                            case 3:
                                Task.review();
                                break;
                            case 4:
                                Task.close();
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



    public static void add(int user_id) throws SQLException {
        Scanner sc = new Scanner(System.in);
        Connection con = Main.ConnectDB();
        System.out.println("Enter Task name :");
        String Task_name=sc.nextLine();
        System.out.println("Enter Task description :");
        String Task_description=sc.nextLine();
        System.out.println("Enter project id of the bug :");
        int project_id =sc.nextInt();
        System.out.printf("Enter Status of Task %s\n",Task_name);
        int status = sc.nextInt();
        String sql1 = "insert into bug(task_name,description,project_id,status) values (?,?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql1);
        stmt.setString(1, Task_name);
        stmt.setString(2, Task_description);
        stmt.setInt(3, project_id);
        stmt.setInt(4, status);
        System.out.println("1");
        int i = stmt.executeUpdate();
        System.out.println("Successfully Added The Project");
        PreparedStatement stmt1= con.prepareStatement("select * from bug",TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
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
    public static void modify(){}
    public static void review(){}
    public static void close(){}

    public static void view() throws SQLException { //,int project_id
        String sql = "select * from task";
        Connection con = Main.ConnectDB();
        PreparedStatement stmt = con.prepareStatement(sql,TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
        for(int i = 1; i <= row_count; i++) {
            rs.next();

            for(int j =1;j<=rsmd.getColumnCount();j++) {
                System.out.printf("| %-20s", rs.getString(j));
            }
            System.out.println("");
        }
        System.out.println("\n\n\n");
    }

}
