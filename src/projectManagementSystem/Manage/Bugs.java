package projectManagementSystem.Manage;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Bugs {
        public static void main(){
            try{
                Scanner sc = new Scanner(System.in);

                //System.out.println("From manager");
                System.out.println("""
                        Enter your choice for Bugs Menu
                        \t1.Add Bugs
                        \t2.Modify Bugs
                        \t3.Review Bugs
                        \t4.Close Bugs
                        \t5.Exit
                        """);
                while(true){
                    int choice = sc.nextInt();
                    if(choice>=1 && choice<=5){
                        switch (choice) {
                            case 1:
                                Project.main();
                                break;
                            case 2:
                                UserEdit.main();
                                break;
                            case 3: Bugs.main();
                                break;
                            case 4:
                                Bugs.main();
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
    }
