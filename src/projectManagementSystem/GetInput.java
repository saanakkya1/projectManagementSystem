package projectManagementSystem;

import projectManagementSystem.Connect_DB.Connect_DB;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;

public class GetInput {
    static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    static Connection con;

    static {
        try {
            con = Connect_DB.ConnectDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
public static ArrayList<String> getColNames(String table_name) throws IOException {
        ArrayList<String> col_labels = new ArrayList<String>();
    try{
        String sqlqry = "select * from " +table_name+ " where "+table_name+"_id=1 limit 1";
        Statement stmnt = con.createStatement();
        ResultSet rs = stmnt.executeQuery(sqlqry);
        ResultSetMetaData rsmd = rs.getMetaData();
        int col_count = rsmd.getColumnCount();
        rs.next();
        for (int j = 1; j <= col_count; j++) {
                col_labels.add(rsmd.getColumnLabel(j));
//                col_types.add(rsmd.getColumnTypeName(j));
            }

    }
    catch (InputMismatchException | NumberFormatException | SQLException e){
        e.printStackTrace();
    }
    return col_labels;
}
    public static ArrayList<String> getColTypes(String table_name) throws IOException {
        ArrayList<String> col_types = new ArrayList<String>();
//    System.out.println("Enter project id of the project to review");
//    int project_id = Integer.parseInt(GetInput.getValidInput(read.readLine(),"Project ID"));
        try{
            String sqlqry = "select * from " +table_name+ " where "+table_name+"_id=1 limit 1";
            Statement stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(sqlqry);
            ResultSetMetaData rsmd = rs.getMetaData();
            int col_count = rsmd.getColumnCount();
            rs.next();
            for (int j = 1; j <= col_count; j++) {
//                col_labels.add(rsmd.getColumnLabel(j));
                col_types.add(rsmd.getColumnTypeName(j));
            }

        }

        catch (InputMismatchException | NumberFormatException | SQLException e){
            e.printStackTrace();
        }
        return col_types;
    }


    public static String getValidInput(String val, String field) throws IOException {
        while(val.equals("")){
            System.out.println(field+" cannot be empty!");
            System.out.print("Enter "+field+": ");
            val = read.readLine();
        }
        return val;
    }
    public static int getChoice(int end,String field) throws IOException {
        String namePattern = String.format("^[1-%d]\\s*$",end);// "^(?=.*[1-"+end+"])(?=\\S+$).+$";
        String choice = read.readLine().strip();
        while(!choice.matches(namePattern)){
            System.out.printf("Enter a valid %s with numbers [1-%d] only!\n",field,end);
            System.out.printf("Enter %s :\n",field);
            choice = read.readLine();
        }
//        System.out.println(choice);
        return Integer.parseInt(choice);

    }
    public static int getInt(String field) throws IOException {
        String namePattern = "^(?=.*\\d)(?=\\S+$).+$";
        String Num = read.readLine();
        while(!Num.matches(namePattern)){
//            System.out.println("Enter a numbers only!");
            System.out.printf("Enter %s:\n [NUMBERS ONLY]",field);
            Num = read.readLine();

        }
//        System.out.println(Num);
        return Integer.parseInt(Num);

    }
    public static boolean checkExists(Connection con,String table_name,int id) throws SQLException {
        String query = String.format("select * from %s where %s_id=%d limit 1",table_name,table_name,id);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs.next();
    }
    public static int checkId(Connection con,String table_name,String field) throws SQLException, IOException {
        int id = getInt(field);
        while(!checkExists(con,table_name,id)){
            System.out.printf("Enter a valid %s \n",field);
            id = getInt(field);
        }
        return id;
    }
    public static boolean checkClosed(Connection con,String table_name,String s,int id) throws SQLException {
        String query="";
        if(table_name.equalsIgnoreCase("user")){  query = String.format("select * from %s where %s_id=%d and %s <> 0",table_name,table_name,id,s);}
        else{  query = String.format("select * from %s where %s_id=%d and %s <> 5",table_name,table_name,id,s);}
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs.next();
    }

    public static int checkIfClosed(Connection con,String table_name,String field,String s) throws SQLException, IOException {
        int id = getInt(field);
        while(!checkClosed(con,table_name,s,id)){
            System.out.printf("Enter a valid %s which is not closed\n",field);
            id = getInt(field);
        }
        return id;
    }
    public static String getName() throws IOException{
        String namePattern = "^[\\p{L} .'-]+$";
        String Name = read.readLine();

        while(!Name.matches(namePattern)){
            System.out.println("Enter a valid name with alphabets only!");
            System.out.println("Enter Name: ");
            Name = read.readLine();
        }
        return Name;
    }
    public static boolean checkEmail(Connection con,String email) throws SQLException{
        String query = "select * from user where email=? ";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1,email);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
    public static String getEmail(Connection con) throws IOException, SQLException {
        String emailPat = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
        String email = read.readLine();

        while (email.matches(emailPat)&&checkEmail(con,email) ) {
            System.out.println("E-mail already taken.......\nPlease use another E-mail");
            email = read.readLine();
        }
        if(!email.matches(emailPat)) {
            System.out.println("Enter a valid emain in the format 'abc@def.ghi' ");
            getEmail(con);
        }
            return email;
        }
//    ^\d{4}-\d{2}-\d{2}$
    public static Timestamp getTimestamp() throws IOException, ParseException {
        String namePattern = "^\\d{4}-\\d{2}-\\d{2}$";
        String d = read.readLine();
        while(!d.matches(namePattern)){
            System.out.println("Enter a valid Date in [YYYY-MM-DD ] format only!");
            System.out.println("Enter Name: ");
            d = read.readLine();
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(d);
        return (Timestamp) date;
    }
    public static String getDate() throws IOException, ParseException {
        String namePattern = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";
        String d = read.readLine();
        while(!d.matches(namePattern)){
            System.out.println("Enter a valid Date in [YYYY-MM-DD ] format only!");
            System.out.println("Enter Date: ");
            d = read.readLine();
        }
        return  d;
    }

/*    void getConfirmation() throws IOException, SQLException {
        System.out.print("\n\tAre you sure that the details give above are correct? [y/N] ");
        String ch = read.readLine();
        while(!ch.equalsIgnoreCase("y") && !ch.equalsIgnoreCase("n")){
            System.out.println("Enter either Y or N: ");
            ch = read.readLine();
        }

        if(ch.equalsIgnoreCase("Y")){
            System.out.println("Saving the details entered.....");
            con.commit();
        }
        else if(ch.equalsIgnoreCase("N")){
            System.out.println("Rolling back the changes.....");
            con.rollback();
        }
    }*/
    public static boolean chechPass(String pass) throws IOException {
        System.out.println("Re-enter Password :");
        String pass1 = read.readLine();
        return pass1.equals(pass);
    }
    public static String getPassword() throws IOException{
        String namePattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String pass = read.readLine();

        while(pass.matches(namePattern) && !chechPass(pass) ){
            System.out.println("Enter a valid Password with atleast one symbol one UPPER CASE ONE lowercase and one NUMBER and more than 8 digits !");
            System.out.println("Enter Password: ");
            pass = read.readLine();
        }
        if(!pass.matches(namePattern)){
            System.out.println("Both Passwords DONOT MATCH RE-ENTER PASSWORDS AGAIN");
            getPassword();
        }
        return pass;
    }
    public static void getConfirmation(Connection con) throws IOException, SQLException{
        System.out.print("\n\tAre you sure that the details give above are correct? [y/N] ");
        String ch = read.readLine();
        while(!ch.equalsIgnoreCase("y") && !ch.equalsIgnoreCase("n")){
            System.out.println("Enter either Y or N: ");
            ch = read.readLine();
        }

        if(ch.equalsIgnoreCase("Y")){
            System.out.println("Saving the entered details .....");
            con.commit();
        }
        else if(ch.equalsIgnoreCase("N")){
            System.out.println("Rolling back the changes.....");
            con.rollback();
        }
    }
}
