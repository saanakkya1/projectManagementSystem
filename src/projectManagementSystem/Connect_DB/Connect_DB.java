package projectManagementSystem.Connect_DB;


import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static java.sql.ResultSet.TYPE_SCROLL_SENSITIVE;

public class Connect_DB {
    static final String     DB_NAME     = System.getenv("DB_NAME");
    static final String     DB_HOST     = System.getenv("DB_HOST");
    static final String     UNAME       = System.getenv("UNAME");
    static final String     PASS        = System.getenv("PASS");

    public static Connection ConnectDB() throws SQLException {
        String DB_URL = "jdbc:mysql://"+DB_HOST+"/"+DB_NAME;
        //System.out.println("Established connection to MySql DB Successfully....");
        return DriverManager.getConnection(DB_URL,UNAME,PASS);
    }
    public static void PrintDB(Connection con,String table_name) throws SQLException {
        String sql = "select * from "+table_name;
        PreparedStatement stmt = con.prepareStatement(sql,TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        StringBuilder sb = new StringBuilder();
        rs.last();
        int row_count= rs.getRow();
        rs.beforeFirst();
//        for(int i =1;i<=rsmd.getColumnCount();i++)
//        {
//            String count = String.valueOf(rsmd.getColumnDisplaySize(i)* rsmd.getColumnCount());
//            String Print_format =     String.format("+ %"+count+"s","+").replace(" ","-");
//            System.out.println(Print_format);}
        for(int i = 1; i <= rsmd.getColumnCount(); i++)
        {
            sb.append(String.format("| %-20s ",rsmd.getColumnLabel(i)));
        }
        sb.append('|');
        System.out.println(sb);

        for(int i = 1; i <= row_count; i++) {
            rs.next();

            for(int j =1;j<=rsmd.getColumnCount();j++) {
                if(j == rsmd.getColumnCount()) System.out.printf("| %-20s |", rs.getString(j));
                else System.out.printf("| %-20s ", rs.getString(j));
            }
            System.out.println("");
        }
//        for(int i =1;i<=rsmd.getColumnCount();i++)
//        {
//            String count = String.valueOf(rsmd.getColumnDisplaySize(i)* rsmd.getColumnCount());
//            String Print_format =     String.format("+ %"+count+"s","+").replace(" ","-");
//            System.out.print(Print_format);}
        System.out.println("\n");

    }
public static void AddToDB(Connection con,String table_name,int user_id) throws SQLException{
/*
    Scanner sc = new Scanner(System.in);
    PreparedStatement stmt1= con.prepareStatement("select * from "+table_name,TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
    ResultSet rs = stmt1.executeQuery();
    ResultSetMetaData rsmd = rs.getMetaData();
    ArrayList<Object> details = new ArrayList<>();
    ArrayList<String> col_names = new ArrayList<String>();
    for(int i =1; i <=rsmd.getColumnCount();i++){
        String col_name=rsmd.getColumnLabel(i);
        col_names.add(col_name);
        // Objects.equals(rsmd.getColumnClassName(i), "INT")
        if(Objects.equals(rsmd.getColumnLabel(i),"project_id")||Objects.equals(rsmd.getColumnLabel(i),"created_by")){
            details.add(user_id);
        }
        System.out.println("Enter The "+col_name.replace("_"," "));
        System.out.println(rsmd.getColumnTypeName (i));
        if(Objects.equals(rsmd.getColumnClassName(i), "VARCHAR"))
        {
            String s = sc.nextLine();
            details.add(s);
        }
        else if(Objects.equals(rsmd.getColumnClassName(i), "INT")||Objects.equals(rsmd.getColumnClassName(i), "TINYINT"))
        {
            int s = sc.nextInt();
            details.add(s);
        }
    }

*/

}





}







