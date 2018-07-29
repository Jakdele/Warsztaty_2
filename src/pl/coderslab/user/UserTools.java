package pl.coderslab.user;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

import java.util.ArrayList;
import java.util.Scanner;

import static pl.coderslab.admin.AdminTools.closeApp;
import static pl.coderslab.admin.AdminTools.getInt;


public class UserTools {
    static Scanner scann;
    public static void userPanel(String[] args) throws Exception {
        int userID;
        try {
            userID = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new Exception("Wrong user id.");
        }
        if(User.loadById(userID) == null) throw new Exception("Enter valid id.");

        scann = new Scanner(System.in);
        userToolsOptions(userID);
    }


    public static void userToolsOptions(int userId){
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
                      closeApp();
                      break;
                  default:
                      System.out.println("Incorrect input - try again.");

              }
        }
    }


    public static void addSolution(int id){
        ArrayList<Solution> solutions = Solution.loadAllNotDone(id);
        for (Solution solution : solutions) {
            System.out.println(solution);
        }
        if (solutions.isEmpty()){
            userToolsOptions(id);
        }
        int taskId = getIntFromUser(Type.SOLUTION_ID);
        String description = getDescription();
        Solution solution = new Solution(taskId,id, description);
        solution.saveToDb();
    }
    public static void viewSolutions(int id){
        for(Solution solution:Solution.loadAllByUserID(id)){
            System.out.println(solution);
        }
    }

    public static String getDescription() {
        System.out.println("Enter solution: ");
        return scann.nextLine();
    }

    public static int getIntFromUser(Type type) {
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

    public enum Type{
        USER_ID,
        SOLUTION_ID,
    }

}
