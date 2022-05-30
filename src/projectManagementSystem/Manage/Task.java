package projectManagementSystem.Manage;

import projectManagementSystem.GetInput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.String;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;
import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;
import static projectManagementSystem.Connect_DB.Connect_DB.PrintDB;
import static projectManagementSystem.GetInput.*;
import static projectManagementSystem.GetInput.getConfirmation;

public class Task {
    private static final String table_name = "task";
    private static int task_id;
    private String task_name;
    private int task_created_by;
    private Timestamp reported_date;
    private int status;
    private static ArrayList<String> col_labels = new ArrayList<>();
    private static ArrayList<String> col_types = new ArrayList<>();
    private static final HashMap<String,String> labels_types = new HashMap<>();

    static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    static Connection con;
    static {
        try {
            con = ConnectDB();
            con.setAutoCommit(false);
            col_labels = getColNames(table_name);
            col_types = getColTypes(table_name);
            for(int i=0;i<col_labels.size();i++)labels_types.put(col_labels.get(i), col_types.get(i));

        } catch (SQLException | IOException e) {
            System.out.println("[-] Database Not Connected Please Make sure The DB server is running...");
        }
    }
        public static void main(int user_id) throws SQLException{
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
                    if(choice>=1 && choice<=6){
                        switch (choice) {
                            case 1:
                            {
                                Task.add(user_id);
                                break;
                            }
                            case 2:
                            {
                                System.out.println("Enter "+table_name+" id of the "+ table_name+" to review");
                                int project_id = Integer.parseInt(GetInput.getValidInput(read.readLine(),table_name.toUpperCase()+" ID"));
                                Task.Edit(user_id,table_name,project_id);
                                break;
                            }
                            case 3:
                            {System.out.println("Enter"+table_name+" id of the "+table_name+" to Edit");
                                int project_id = getInt("Project ID");
                                Task.review(table_name,project_id);
                                break;}
                            case 4:
                            {Task.close(table_name,5);
                                break;}
                            case 5: {PrintDB(con,table_name);}
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
            } catch (SQLException | IOException | ParseException e) {
                e.printStackTrace();
            }
        }


    public static void add(int user_id) throws SQLException, IOException {
        try {
            System.out.println("Enter task name :");
            String task_name = getValidInput(read.readLine(), table_name.toUpperCase() + " Name");
            System.out.println("Enter task description :");
            String task_description = getValidInput(read.readLine(), table_name.toUpperCase() + " Description");
            System.out.println("Enter project id of the task :");
            int project_id = checkId(con,"project","Project id of task");
            System.out.printf("Enter Status of task %s\n", task_name);
            int status = getChoice(5, "Status of task");
            String sql1 = "insert into task(task_name,description,project_id,status) values (?,?,?,?);";
            PreparedStatement stmt = con.prepareStatement(sql1);
            stmt.setString(1, task_name);
            stmt.setString(2, task_description);
            stmt.setInt(3, project_id);
            stmt.setInt(4, status);
            int i = stmt.executeUpdate();
            GetInput.getConfirmation(con);
            System.out.println("Successfully Added The " + table_name.toUpperCase());
            PreparedStatement stmt1 = con.prepareStatement("select task_id,task_name,description,reported_time,project_id,status_title from task join status on status=status_id order by reported_time", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt1.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int j = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.printf("| %-20s", rsmd.getColumnLabel(i).toUpperCase().replace("_", " "));
            }
            System.out.println("");
            rs.last();
            for (int j = 1; j <= rsmd.getColumnCount(); j++) {
                System.out.printf("| %-20s", rs.getString(j));
            }
            System.out.println("\n\n\n");
            if (i == 0) {
                System.out.println("Please Re-Enter the details again");
                add(user_id);
            }
        }catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Enter a valid project id");;}
    }
    public static void Edit(int user_id,String table_name,int project_id) throws SQLException, IOException, ParseException {
        col_labels.removeIf(e -> (e.equalsIgnoreCase("reported_time") || e.equalsIgnoreCase("created_by") || e.equalsIgnoreCase(table_name + "_id") || e.equalsIgnoreCase("created_date")));
        System.out.println("Which Field Do you Want to Edit");
        int i;
        for (i = 0; i < col_labels.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, col_labels.get(i).replace("_", " ").toUpperCase());
        }
        System.out.printf("%d. Exit\n", i);
        int choice = getChoice(col_labels.size(), "Choice") - 1;
        if(choice==i) {return;}
        String col = col_labels.get(choice);
        System.out.printf("\nEnter Value of %s\n", col.toUpperCase());
        String query = "update " + table_name + " set " + col + "=? where " + table_name + "_id=" + project_id;
        PreparedStatement stmt = con.prepareStatement(query);
        if (labels_types.get(col).equalsIgnoreCase("INT")) {
            System.out.println("INT");
            stmt.setInt(1, getInt(col));
        } else if (labels_types.get(col).equalsIgnoreCase("TINYINT")) {
            stmt.setInt(1, getChoice(5, col));
        } else if (labels_types.get(col).equalsIgnoreCase("VARCHAR")) {
            stmt.setString(1, getValidInput(read.readLine(), col));
        } else if (labels_types.get(col).equalsIgnoreCase("timestamp")) {
            stmt.setTimestamp(1, getTimestamp());
        }
        stmt.executeUpdate();
        getConfirmation(con);
        System.out.println("\nDo you want to Edit another " + table_name + "[y/N]?");
        String close_another = GetInput.getValidInput(read.readLine(), "[y/N] ONLY");
        if (close_another.equalsIgnoreCase("Y")) {
            Edit(user_id, table_name, project_id);
        }
    }
    public static void review(String table_name,int project_id) throws IOException {

        try{
            String sqlqry = "select task_id,task_name,description,reported_time,project_id,status_title from task join status on status=status_id where "+table_name+"_id=? and status <> 5";
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
            review(table_name,project_id);
        }
    }
    public static void close(String table_name,int status) throws SQLException {
        try {
            System.out.println("Enter "+table_name +" id");
            int task_id = checkIfClosed(con,"task",table_name.toUpperCase()+" ID","status");
            String sqlqry = "select task_id,task_name,description,reported_time,project_id, status_title from task join status on status=status_id where task_id=? and status <> 5";
            PreparedStatement stmnt = con.prepareStatement(sqlqry);
            stmnt.setInt(1, task_id);
            ResultSet rs = stmnt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            rs.next();
            String query = "update " + table_name + " set status = " + status + " where " + table_name + "_id=?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, task_id);
            int result = statement.executeUpdate();
            if (result > 0) {
                //System.out.println("\nAre You Sure to Close The " + table_name + " [y/N]");
                getConfirmation(con);
                for (int j = 1; j <= col_count; j++) {
                    if (j == rsmd.getColumnCount()) System.out.printf("| %-20s |", rs.getString(j));
                    else System.out.printf("| %-20s ", rs.getString(j));
                }
                System.out.println("\nDo you want to close another " + table_name + "[y/N]?");
                String close_another = GetInput.getValidInput(read.readLine(),"[y/N] ONLY");
                if (close_another.equalsIgnoreCase("yes")) {
                    close(table_name, 5);
                }
            } else {
                System.out.println("Enter " + table_name.toUpperCase() + " Id Again....");close(table_name,5);
            }
        }
        catch(InputMismatchException|IOException e ){
            System.out.println("Enter a valid "+table_name+"_id which is not closed");
            close(table_name,5);
        }
    }

    /*public static void add(int user_id) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Task name :");
        String Task_name=sc.nextLine();
        System.out.println("Enter Task description :");
        String Task_description=sc.nextLine();
        System.out.println("Enter project id of the Task :");
        int project_id =sc.nextInt();
        System.out.printf("Enter Status of Task %s\n",Task_name);
        int status = sc.nextInt();
        String sql1 = "insert into task(task_name,description,project_id,status) values (?,?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql1);
        stmt.setString(1, Task_name);
        stmt.setString(2, Task_description);
        stmt.setInt(3, project_id);
        stmt.setInt(4, status);
        System.out.println("1");
        int i = stmt.executeUpdate();
        System.out.println("Successfully Added The Task");
        PreparedStatement stmt1= con.prepareStatement("select * from task",TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
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
    public static void close(String table_name) throws SQLException {
        System.out.println("Enter project id");
        int project_id = sc.nextInt();
        String sqlqry = "select * from " +table_name+ " where "+table_name+"_id=?";
        PreparedStatement stmnt = con.prepareStatement(sqlqry);
        stmnt.setInt(1, project_id);
        ResultSet rs = stmnt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        int col_count = rsmd.getColumnCount();
        rs.next();
        String query = String.format("insert into project_closed values ("+"?,".repeat(col_count)+" );").replaceFirst(", ","");
        PreparedStatement stmt = con.prepareStatement(query);
        for (int i = 1; i <=col_count; i++) {
            String col_type = rsmd.getColumnTypeName(i);
            //stmt.setString(i,rsmd.getColumnName(i));
            if (col_type.equalsIgnoreCase("INT")){
                stmt.setInt(i,rs.getInt(i));
            }
            if (col_type.equalsIgnoreCase("TINYINT")){
                stmt.setInt(i,rs.getInt(i));
            }
            else if (col_type.equalsIgnoreCase("VARCHAR")) {
                stmt.setString(i,rs.getString(i));
            }
            else if (col_type.equalsIgnoreCase("timestamp")) {
                stmt.setTimestamp(i,rs.getTimestamp(i));
            }
        }
        String[] state = stmt.toString().split(": ",2)[1].split("values ");
        String s = state[0].replace("'","`")+"values "+state[1];
        System.out.println(s);
        Statement statement = con.createStatement();
        int result = statement.executeUpdate(s);
        if(result==1){
            for(int j =1;j<=col_count;j++) {
                if(j == rsmd.getColumnCount()) System.out.printf("| %-20s |", rs.getString(j));
                else System.out.printf("| %-20s ", rs.getString(j));
            }
            System.out.println("\nAre You Sure to Close The "+ table_name +" [yes/NO]");
            String is_sure = sc.next();
            if(is_sure.equalsIgnoreCase("yes")) con.commit();
            con.rollback();

        }
        else{
            System.out.println("Enter "+table_name.toUpperCase()+" Id Again....");

        }

    }
*/


}
