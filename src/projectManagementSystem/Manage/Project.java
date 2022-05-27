package projectManagementSystem.Manage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Project {
        public static void main(){
            try{
                Scanner sc = new Scanner(System.in);

                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for Project Menu
                        \t1.Add Project
                        \t2.Modify Project
                        \t3.Review Project
                        \t4.Close Project
                        \t5.Exit
                        """);
                while(true){
                    int choice = sc.nextInt();
                    if(choice>=1 && choice<=5){
                        switch (choice) {
                            case 1:
                                Project.add();
                                break;
                            case 2:
                                Project.modify();
                                break;
                            case 3:
                                Project.review();
                                break;
                            case 4:
                                Project.close();
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

