package projectManagementSystem.Manage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Task {

        public static void main(){
            try{
                Scanner sc = new Scanner(System.in);

                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for Tasks Menu\s
                        \t1.Add Task
                        \t2.Modify Task
                        \t3.Review Task
                        \t4.Close Task
                        \t5.Exit
                        """);
                while(true){
                    int choice = sc.nextInt();
                    if(choice>=1 && choice<=5){
                        switch (choice) {
                            case 1:
                                Task.add();
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
