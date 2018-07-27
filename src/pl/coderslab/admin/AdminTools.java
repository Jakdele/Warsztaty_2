package pl.coderslab.admin;

import pl.coderslab.model.DBManager;

import java.util.Scanner;

public class AdminTools {
    static Scanner scanner;

    public static void adminPanel() {
        scanner = new Scanner(System.in);
        while (true) {
            System.out.println("What module do you want to work on?\n(1) user\n(2) group\n(3) exercise\n(4) solution\n(0) quit");
                switch (getInt(scanner)){
                case 1:
                    UserAdmin.userOptions();
                    break;
                case 2:
                    GroupAdmin.groupOptions();
                    break;
                case 3:
                    ExerciseAdmin.exerciseOptions();
                    break;
                case 4:
                    SolutionAdmin.solutionOptions();
                    break;
                case 0:
                    closeApp();
                    break;
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

    public static int getInt(Scanner scann){
        while (!scann.hasNextInt()){
            scann.next();
            System.out.println("Wrong input - you have to enter a number");
        }
        int id = scann.nextInt();
        scann.nextLine();
        return id;
    }

}
