package pl.coderslab.admin;

import pl.coderslab.model.Solution;

import java.util.ArrayList;
import java.util.Scanner;


public class UserTools {
    static Scanner scann;
    static int userId;
    public static void main(String[] args) {
        scann = new Scanner(System.in);
        userId = getIntFromUser(Type.USER_ID);
        userToolsOptions();
    }


    public static void userToolsOptions(){
        while (true) {
            System.out.println("What would you like to do: add (adds a solution), show (view your solutions) or quit?");
            String userAction = scann.nextLine();
            if(userAction.equals("add")){
                addSolution(userId);
            } else if(userAction.equals("show")) {
                viewSolutions(userId);
            }else if(userAction.equals("quit")){
                AdminTools.closeApp();
            } else {
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
