package projectManagementSystem.Manage;

import projectManagementSystem.GetInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;
import static projectManagementSystem.Connect_DB.Connect_DB.*;
import static projectManagementSystem.GetInput.*;

public class Project {
    private static final String table_name = "project";
    private static int project_id;
    private String project_name;
    private int project_created_by;
    private Timestamp created_date;
    private int project_status;
    private static ArrayList<String> col_labels = new ArrayList<>();
    private static ArrayList<String> col_types  = new ArrayList<>();
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

    public static void main(int user_id){
            try{
                System.out.println("""
                        \t1.Add Project
                        \t2.Edit Project Parameters
                        \t3.Review Project
                        \t4.Close Project
                        \t5.View Projects
                        \t6.Exit
                        Enter your choice for Project Menu""");
                while(true){
                    int choice = Integer.parseInt(GetInput.getValidInput(read.readLine(),"Choice"));
                    if(choice>=1 && choice<=6){
                        switch (choice) {
                            case 1 -> {
                                Project.add(user_id,table_name);
                                                            }
                            case 2 -> {
                                System.out.println("Enter " + table_name + " id of the " + table_name + " to Edit");
                                int project_id = checkId(con,table_name,"PROJECT ID");
                                Project.Edit(user_id, table_name,project_id);

                            }
                            case 3 -> {
                                System.out.println("Enter " + table_name + " id of the " + table_name + " to Review");
                                int project_id = checkIfClosed(con,table_name,table_name.toUpperCase()+" ID","status");
                                Project.review(table_name,project_id);
                            }
                            case 4 -> {
                                System.out.println("Enter " + table_name + " id of the " + table_name + " to Close");
                                int project_id = checkId(con,table_name,"PROJECT ID");
                                Project.close(table_name, 5,project_id);
                            }
                            case 5 -> PrintDB(con, table_name);
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
            catch (NumberFormatException|InputMismatchException e){
                System.out.println("Enter a valid input....");
                main(user_id);
            } catch (SQLException | ParseException | IOException e) {
                e.printStackTrace();
            }
    }
    public static void add(int user_id,String table_name) throws SQLException {
        try{
            System.out.printf("Enter %s name :\n",table_name);
            String project_name=GetInput.getValidInput(read.readLine(),"Project Name");
            System.out.printf("Enter Status of Project %s\n",project_name);
            int status = getChoice(5,"Status");
            String sql1 = "insert into project(project_name,created_by,status) values (?,?,?);";
            PreparedStatement stmt = con.prepareStatement(sql1, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, project_name);
            stmt.setInt(2, user_id);
            stmt.setInt(3, status);
            int result = stmt.executeUpdate();
            GetInput.getConfirmation(con);
            System.out.println("Successfully Added The Project");
            PreparedStatement stmt1= con.prepareStatement("select * from project",TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt1.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            for(int i = 1; i <= rsmd.getColumnCount(); i++)
            {
                System.out.printf("| %-20s",rsmd.getColumnLabel(i).toUpperCase().replace("_"," "));
            }
            System.out.println();
            rs.last();
            for(int j =1;j<=rsmd.getColumnCount();j++) {
                System.out.printf("| %-20s", rs.getString(j));
            }
            System.out.println("\n\n\n");
            if(result==0){
                System.out.println("Please Re-Enter the details again");
                add(user_id,table_name);
            }
            }
        catch(SQLException e){
            System.out.println("Please renter the Details without mistakes");
            add(user_id,table_name);
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Edit(int user_id, String table_name, int project_id) throws SQLException, IOException, ParseException {
        col_labels.removeIf(e -> (e.equalsIgnoreCase("reported_time") || e.equalsIgnoreCase("created_by") || e.equalsIgnoreCase(table_name + "_id") || e.equalsIgnoreCase("created_date")));;
        int i;
        for (i = 0; i < col_labels.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, col_labels.get(i).replace("_", " ").toUpperCase());
        }
        System.out.printf("%d. Exit\n", i+1);
        System.out.println("Which Field Do you Want to Edit");
        int choice = getChoice(col_labels.size()+1, "Choice") - 1;
        if (choice == i) {return;}
        String col = col_labels.get(choice);
        System.out.printf("\nEnter Value of %s\n", col.toUpperCase());
        String query = "update " + table_name + " set " + col + "=? where " + table_name + "_id=" + project_id;
        PreparedStatement stmt = con.prepareStatement(query, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        if (labels_types.get(col).equalsIgnoreCase("INT")) {
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
        review(table_name,project_id);
        System.out.println("\nDo you want to Edit " + table_name + " again [y/N]?");
        String close_another = GetInput.getValidInput(read.readLine(), "[y/N] ONLY");
        if (close_another.equalsIgnoreCase("Y")) {
            Edit(user_id, table_name, project_id);
        }
    }
    public static void review(String table_name,int project_id) throws IOException {
        try{

            String sqlqry = "select project_id,project_name,created_date,created_by,status_title from " +table_name+ " join status on status = status_id where "+table_name+"_id=? and status <> 5";
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
    public static void close(String table_name, int status, int project_id) throws SQLException {
        try {
            String sqlqry = "select * from " + table_name + " where " + table_name + "_id=?";
            PreparedStatement stmnt = con.prepareStatement(sqlqry, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmnt.setInt(1, project_id);
            ResultSet rs = stmnt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            rs.next();
            String query = "update " + table_name + " set status = " + status + " where " + table_name + "_id=?";
            PreparedStatement statement = con.prepareStatement(query, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, project_id);
            int result = statement.executeUpdate();
            rs.refreshRow();
            if (result > 0) {
                System.out.println("\nAre You Sure to Close The " + table_name + " [y/N]");
                String is_sure = GetInput.getValidInput(read.readLine(),"[y/N] ONLY");
                if (is_sure.equalsIgnoreCase("yes")) con.commit();
                else con.rollback();
                for (int j = 1; j <= col_count; j++) {
                    if (j == rsmd.getColumnCount()) System.out.printf("| %-20s |", rs.getString(j));
                    else System.out.printf("| %-20s ", rs.getString(j));
                }
                System.out.println("\nDo you want to close another " + table_name + "[y/N]?");
                String close_another = GetInput.getValidInput(read.readLine(),"[y/N] ONLY");
                if (close_another.equalsIgnoreCase("yes")) {
                    close("project", 5, project_id);
                }
            } else {
                System.out.println("Enter " + table_name.toUpperCase() + " Id Again....");
                close("project",5, project_id);
            }
        }
        catch(InputMismatchException|IOException e ){
            System.out.println("Enter a valid project_id");
            close("project",5, project_id);
    }
    }
    }



