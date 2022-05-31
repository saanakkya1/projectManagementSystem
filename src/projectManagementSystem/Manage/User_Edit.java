package projectManagementSystem.Manage;

import projectManagementSystem.Connect_DB.Connect_DB;
import projectManagementSystem.GetInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;
import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;
import static projectManagementSystem.GetInput.*;

public class User_Edit {
    private static final String table_name = "user";
    private static int user_id;
    private String user_name;
    private int user_created_by;
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
        public static void main(){
            try{
                Scanner sc = new Scanner(System.in);

                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for User_Edit Menu
                        \t1.Add User
                        \t2.Modify User
                        \t3.Review User_Edit
                        \t4.Remove User
                        \t5.View users
                        \t6.Exit
                        """);
                while(true){
                    int choice = getChoice(6,"Choice");
                    if(choice>=1 && choice<=6){
                        switch (choice) {
                            case 1 -> User_Edit.add(user_id);
                            case 2 -> {
                                System.out.println("Enter " + table_name + " id of the " + table_name + " to review");
                                int user_id = checkId(con, table_name, "USER ID");
                                User_Edit.Edit(table_name, user_id);
                            }
                            case 3 -> {
                                System.out.println("Enter " + table_name + " id of the " + table_name + " to review");
                                int user_id = checkIfClosed(con, "user", table_name.toUpperCase() + " ID", "user_id");
                                User_Edit.review(table_name, user_id);
                            }
                            case 4 -> {
                                System.out.println("Enter " + table_name + " id of the " + table_name + " to review");
                                int user_id = checkIfClosed(con, "user", table_name.toUpperCase() + " ID", "role_id");
                                User_Edit.close(table_name, 0, user_id);
                            }
                            case 5 -> Connect_DB.PrintDB(con, table_name);
                        }
                        if(choice==6)break;
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
            } catch (SQLException | IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    public static void add(int user_id) throws SQLException, IOException {
        try {
            System.out.println("Enter First name :");
            String first_name = getValidInput(read.readLine(), table_name.toUpperCase() + " Name");
            System.out.println("Enter last name :");
            String last_name = getValidInput(read.readLine(), table_name.toUpperCase() + " Name");
            System.out.println("Enter user email :");
            String email = getEmail(con);
            System.out.println("Enter role id of the user :");
            int role_id = getChoice(3,"Choice");
            System.out.println("Enter DOB of the user :");
            Date DOB = Date.valueOf(getDate());
            System.out.println("Enter Join Date of the user :");
            Date join_date = Date.valueOf(getDate());
            String sql1 = "insert into user (first_name, last_name,  email,role_id, DOB, join_date, password) values (?,?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql1);
            stmt.setString(1, first_name);
            stmt.setString(2, last_name);
            stmt.setString(3, email);
            stmt.setInt(4, role_id);
            stmt.setDate(5, DOB);
            stmt.setDate(6, join_date);
            stmt.setString(7,last_name+"@"+first_name);
            int i = stmt.executeUpdate();
            GetInput.getConfirmation(con);
            PreparedStatement stmt1 = con.prepareStatement("select user_id,first_name,last_name,email,DOB,join_date,last_login,password ,role_title from user join role on user.role_id=role.role_id order by user_id", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt1.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int j = 1; i <= rsmd.getColumnCount(); i++) {
                System.out.printf("| %-25s", rsmd.getColumnLabel(i).toUpperCase().replace("_", " "));
            }
            System.out.println("");
            rs.last();
            for (int j = 1; j <= rsmd.getColumnCount(); j++) {
                System.out.printf("| %-25s", rs.getString(j));
            }
            System.out.println("\n\n\n");
            if (i == 0) {
                System.out.println("Please Re-Enter the details again");
                add(user_id);
            }
        }catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Please user another email ");}
        catch(IllegalArgumentException e){
            System.out.println("Please enter a valid date and renter the details again....");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public static void Edit(String table_name,int user_id) throws SQLException, IOException, ParseException {
        col_labels.removeIf(e -> (e.equalsIgnoreCase("reported_time") || e.equalsIgnoreCase("created_by") || e.equalsIgnoreCase(table_name + "_id") || e.equalsIgnoreCase("created_date")||e.equalsIgnoreCase("last_login")));
        System.out.println("Which Field Do you Want to Edit");
        int i;
        for (i = 0; i < col_labels.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, col_labels.get(i).replace("_", " ").toUpperCase());
        }
        System.out.printf("%d. Exit\n", i+1);
        int choice = getChoice(col_labels.size()+1, "Choice") - 1;
        if(choice==i) {return;}
        String col = col_labels.get(choice);
        System.out.printf("\nEnter Value of %s\n", col.toUpperCase());
        String query = "update " + table_name + " set " + col + "=? where " + table_name + "_id=" + user_id;
        PreparedStatement stmt = con.prepareStatement(query);
        if (labels_types.get(col).equalsIgnoreCase("INT")) {
            System.out.println("INT");
            stmt.setInt(1, getInt(col));
        } else if (labels_types.get(col).equalsIgnoreCase("TINYINT")) {
            stmt.setInt(1, getChoice(3, col));
        } else if (col.equalsIgnoreCase("email")) {
            stmt.setString(1, getEmail(con));
        } else if (col.equalsIgnoreCase("password")) {
            stmt.setString(1, getPassword());
        }else if (labels_types.get(col).equalsIgnoreCase("timestamp")) {
            stmt.setTimestamp(1, getTimestamp());
        } else if (labels_types.get(col).equalsIgnoreCase("date")) {
                stmt.setDate(1, Date.valueOf(getDate()));
        }else if (labels_types.get(col).equalsIgnoreCase("VARCHAR")) {
            stmt.setString(1, getValidInput(read.readLine(), col));
        }
        stmt.executeUpdate();
        getConfirmation(con);
        review(table_name,user_id);
        System.out.println("\nDo you want to Edit another " + table_name + "[y/N]?");
        String close_another = GetInput.getValidInput(read.readLine(), "[y/N] ONLY");
        if (close_another.equalsIgnoreCase("Y")) {
            Edit(table_name,user_id);
        }
    }
    public static void review(String table_name,int user_id) throws IOException {

        try{
            String sqlqry = "select user_id,first_name,last_name,email,DOB,join_date,last_login,password ,role_title from user join role on user.role_id=role.role_id  where user_id=?";
            PreparedStatement stmnt = con.prepareStatement(sqlqry, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmnt.setInt(1, user_id);
            ResultSet rs = stmnt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            rs.next();
            for (int j = 1; j <= col_count; j++) {
                if (j == rsmd.getColumnCount()) System.out.printf("| %-25s |", rs.getString(j));
                else System.out.printf("| %-25s ", rs.getString(j));
            }
            System.out.println();
        }
        catch (InputMismatchException | NumberFormatException | SQLException e){
            System.out.println("Enter a valid "+table_name+"_id ");
            review(table_name,user_id);
        }
    }
    public static void close(String table_name,int role_id,int user_id) throws SQLException {
        try {
            //System.out.println("Enter "+table_name +" id");
            //int user_id = checkIfClosed(con,"user",table_name.toUpperCase()+" ID","role_id");
            String sqlqry = "select * from user  where user_id=? and user.role_id <> 0";
            PreparedStatement stmnt = con.prepareStatement(sqlqry, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmnt.setInt(1, user_id);
            ResultSet rs = stmnt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            rs.next();
            String query = String.format("update %s set role_id =%d  where %s_id=?",table_name,role_id,table_name);
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, user_id);
            int result = statement.executeUpdate();
            rs.refreshRow();
            if (result > 0) {
                //System.out.println("\nAre You Sure to Close The " + table_name + " [y/N]");
                getConfirmation(con);
                for (int j = 1; j <= col_count; j++) {
                    if (j == rsmd.getColumnCount()) System.out.printf("| %-25s |", rs.getString(j));
                    else System.out.printf("| %-25s ", rs.getString(j));
                }
                System.out.println("\nDo you want to close another " + table_name + "[y/N]?");
                String close_another = GetInput.getValidInput(read.readLine(),"[y/N] ONLY");
                if (close_another.equalsIgnoreCase("yes")) {
                    close(table_name, 0,user_id);
                }
            } else {
                System.out.println("Enter " + table_name.toUpperCase() + " Id Again....");close(table_name,0,user_id);
            }
        }
        catch(InputMismatchException|IOException e ){
            System.out.println("Enter a valid "+table_name+"_id which is not closed");
            close(table_name,0,user_id);
        }
    }

    }

