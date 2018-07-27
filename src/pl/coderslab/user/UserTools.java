package pl.coderslab.user;


import pl.coderslab.admin.AdminTools;
import pl.coderslab.model.Solution;

import java.util.ArrayList;
import java.util.Scanner;

import static pl.coderslab.admin.AdminTools.getInt;


public class UserTools {
    static Scanner scann;
    static int userId;
    public static void userPanel() {
        scann = new Scanner(System.in);
        userId = getIntFromUser(Type.USER_ID);
        userToolsOptions();
    }


    public static void userToolsOptions(){
        while (true) {
              System.out.println("What would you like to do: \n(1) add (add a solution)\n(2) show (view your solutions)\n(0) quit");
              switch (getInt(scann)){
                  case 1:
                      addSolution(userId);
                      break;
                  case 2:
                      viewSolutions(userId);
                      break;
                  case 0:
                      AdminTools.closeApp();
                      break;
                  default:
                      System.out.println("Incorrect input - try again.");

              }
        }
    }


    public static void addSolution(int id){
        ArrayList<Solution> solutions = Solution.loadAllNotDone(id);
        if (solutions.isEmpty()){
            userToolsOptions();
        }
        int taskId = getIntFromUser(Type.SOLUTION_ID);
        String description = getDescription();
        Solution solution = new Solution(taskId,id, description);
        solution.saveToDb();
    }
    public static void viewSolutions(int id){
        Solution.loadAllByUserID(id);
    }
    public static String getDescription() {
        System.out.println("Enter solution: ");
        return scann.nextLine();
    }

    private static int getIntFromUser(Type type) {
        switch (type) {
            case USER_ID:
                System.out.println("Enter your id: ");
                break;
            case SOLUTION_ID:
                System.out.println("Enter your solution id: ");
                break;
        }
        while (!scann.hasNextInt()){
            scann.next();
            System.out.println("Wrong input - you have to enter a number");
        }
        int id = scann.nextInt();
        scann.nextLine();
        return id;
    }

    private enum Type{
        USER_ID,
        SOLUTION_ID,
    }


}
