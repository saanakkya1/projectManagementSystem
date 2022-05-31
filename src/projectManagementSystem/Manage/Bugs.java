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
import static projectManagementSystem.Connect_DB.Connect_DB.ConnectDB;
import static projectManagementSystem.Connect_DB.Connect_DB.PrintDB;
import static projectManagementSystem.GetInput.*;
import static projectManagementSystem.GetInput.getConfirmation;

public class Bugs {
    private static final String table_name = "bug";
    private static int bug_id;
    private String bug_name;
    private int bug_created_by;
    private Timestamp reported_date;
    private int sevr;
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
    public static void main(int user_id)throws SQLException{
            try{


                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for Bugs Menu
                        \t1.Add Bugs
                        \t2.Modify Bugs
                        \t3.Review Bugs
                        \t4.Close Bugs
                        \t5.View Bugs
                        \t6.Exit
                        """);
                while(true){
                    int choice = getChoice(6,"Choice");
                    if(choice>=1 && choice<=6){
                        switch (choice) {
                            case 1 -> {
                                Bugs.add(user_id);

                            }
                            case 2 -> {
                                System.out.println("Enter " + table_name + " id of the " + table_name + " to review");
                                int bug_id = Integer.parseInt(GetInput.getValidInput(read.readLine(), table_name.toUpperCase() + " ID"));
                                Bugs.Edit(user_id, table_name, bug_id);

                            }
                            case 3 -> {
                                System.out.println("Enter " + table_name + " id of the " + table_name + " to Edit");
                                int bug_id = getInt("Bug ID");
                                Bugs.review(table_name, bug_id);

                            }
                            case 4 -> {
                                System.out.println("Enter "+table_name +" id");
                                int bug_id = checkIfClosed(con,"bug",table_name.toUpperCase()+" ID","status");
                                Bugs.close(table_name, 5, bug_id);
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
            catch (InputMismatchException e){
                System.out.println("Enter a valid input");
                main(user_id);
            } catch (SQLException | IOException | ParseException e) {
                e.printStackTrace();
            }
    }
    public static void add(int user_id) throws SQLException, IOException {
        try {
            System.out.println("Enter Bug name :");
            String bug_name = getValidInput(read.readLine(), table_name.toUpperCase() + " Name");
            System.out.println("Enter Bug description :");
            String bug_description = getValidInput(read.readLine(), table_name.toUpperCase() + " Description");
            System.out.println("Enter project id of the bug :");
            int project_id = checkId(con,"project","Project id of bug");
            System.out.printf("Enter Severity of Bug %s\n", bug_name);
            int sevr = getChoice(5, "Severity of Bug");
            System.out.printf("Enter Status of Bug %s\n", bug_name);
            int status = getChoice(5, "Status of Bug");
            String sql1 = "insert into bug(bug_name,description,project_id,sevr,status) values (?,?,?,?,?);";
            PreparedStatement stmt = con.prepareStatement(sql1, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, bug_name);
            stmt.setString(2, bug_description);
            stmt.setInt(3, project_id);
            stmt.setInt(4, sevr);
            stmt.setInt(5, status);

            int i = stmt.executeUpdate();
            GetInput.getConfirmation(con);
            System.out.println("Successfully Added The " + table_name.toUpperCase());
            PreparedStatement stmt1 = con.prepareStatement("select bug_id,bug_name,description,reported_time,project_id, sevr_title,status_title from bug join status on status=status_id join bug_sevr on sevr=sevr_id order by reported_time", TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
    public static void Edit(int user_id,String table_name,int bug_id) throws SQLException, IOException, ParseException {
        col_labels.removeIf(e -> (e.equalsIgnoreCase("reported_time") || e.equalsIgnoreCase("created_by") || e.equalsIgnoreCase(table_name + "_id") || e.equalsIgnoreCase("created_date")));
        int i;
        for (i = 0; i < col_labels.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, col_labels.get(i).replace("_", " ").toUpperCase());
        }
        System.out.printf("%d. Exit\n", i+1);
        System.out.println("Which Field Do you Want to Edit");
        int choice = getChoice(col_labels.size()+1, "Choice") - 1;
        if (choice == i) {
            return;
        }
        String col = col_labels.get(choice);
        System.out.printf("\nEnter Value of %s\n", col.toUpperCase());
        String query = "update " + table_name + " set " + col + "=? where " + table_name + "_id=" + bug_id;
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
        review(table_name,bug_id);
        System.out.println("\nDo you want to Edit another " + table_name + "[y/N]?");
        String close_another = GetInput.getValidInput(read.readLine(), "[y/N] ONLY");
        if (close_another.equalsIgnoreCase("Y")) {
            Edit(user_id, table_name, bug_id);
        }
    }
    public static void review(String table_name,int project_id) throws IOException {

        try{

            String sqlqry = "select bug_id,bug_name,description,reported_time,project_id, sevr_title,status_title from bug join status on status=status_id join bug_sevr on sevr=sevr_id where "+table_name+"_id=? and status <> 5";
            PreparedStatement stmnt = con.prepareStatement(sqlqry, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
    public static void close(String table_name,int status,int bug_id) throws SQLException {
        try {
            String sqlqry = "select bug_id,bug_name,description,reported_time,project_id, sevr_title,status_title from bug join status on status=status_id join bug_sevr on sevr=sevr_id  where bug_id=? and status <> 5";
            PreparedStatement stmnt = con.prepareStatement(sqlqry, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmnt.setInt(1, bug_id);
            ResultSet rs = stmnt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            rs.next();
            String query = "update " + table_name + " set status = " + status + " where " + table_name + "_id=?";
            PreparedStatement statement = con.prepareStatement(query, TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statement.setInt(1, bug_id);
            int result = statement.executeUpdate();
            rs.refreshRow();
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
                    close(table_name, 5,bug_id);
                }
            } else {
                System.out.println("Enter " + table_name.toUpperCase() + " Id Again....");close(table_name,5,bug_id);
            }
        }
        catch(InputMismatchException|IOException e ){
            System.out.println("Enter a valid "+table_name+"_id which is not closed");
            close(table_name,5,bug_id);
        }
    }
    /*public static void close(String table_name) throws SQLException, IOException {
        System.out.println("Enter "+table_name+" id");
        int project_id = getInt(table_name+" Id");
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

    }*/
/*    public static void view() throws SQLException {
        String sql = "select * from bug";
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
//        StringBuilder stringb = new StringBuilder();

        for(int i = 1; i <= row_count; i++) {
            rs.next();

            for(int j =1;j<=rsmd.getColumnCount();j++) {
                System.out.printf("| %-20s", rs.getString(j));
            }
            System.out.println("");
        }
        System.out.println("\n\n\n");
    }*/
}
