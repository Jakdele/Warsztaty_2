package pl.coderslab.admin;

import pl.coderslab.model.DBManager;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminTools {
    static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What module do you want to work on?\nuser | group | exercise | solution");
            String userAction = scanner.nextLine();
            switch (userAction){
                case "user":
                    UserAdmin.userOptions();
                case "group":
                    GroupAdmin.groupOptions();
                case "exercise":
                    ExerciseAdmin.exerciseOptions();
                case "solution":
                    SolutionAdmin.solutionOptions();
                default:
                    System.out.println("Incorrect input - try again.");
            }
        }
    }
    public static void closeApp(){
        DBManager.close();
        System.out.println("Application closed");
        System.exit(0);
    }

}
