package projectManagementSystem.Manage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class User_Edit {
        public static void main(){
            try{
                Scanner sc = new Scanner(System.in);

                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for User_Edit Menu
                        \t1.Add User_Edit
                        \t2.Modify User_Edit
                        \t3.Review User_Edit
                        \t4.Close User_Edit
                        \t5.Exit
                        """);
                while(true){
                    int choice = sc.nextInt();
                    if(choice>=1 && choice<=5){
                        switch (choice) {
                            case 1:
                                User_Edit.add();
                                break;
                            case 2:
                                User_Edit.modify();
                                break;
                            case 3:
                                User_Edit.review();
                                break;
                            case 4:
                                User_Edit.close();
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
    public static void add(){}
    public static void modify(){}
    public static void review(){}
    public static void close(){}
    }

