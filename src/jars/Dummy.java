package jars;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Dummy {
    static BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
    public static int getChoice(int end) throws IOException {
        String namePattern = "^(?=.*[1-"+end+"])(?=\\S+$).+$";
        String choice = read.readLine();
        while(!choice.matches(namePattern)){
            System.out.println("Enter a valid name with numbers only!");
            System.out.println("Enter Choice: ");
            choice = read.readLine();
        }
        return Integer.parseInt(choice);

    }
    public static void main(String[] args) throws IOException {
        System.out.println(getChoice(5));
    }


}
