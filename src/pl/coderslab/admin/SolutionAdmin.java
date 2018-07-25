package pl.coderslab.admin;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

import java.sql.Date;
import java.sql.Timestamp;

public class SolutionAdmin {
   // private static final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static void solutionOptions() {
        while (true) {
            Solution.loadAll();
            System.out.println("What would you like to do: add (add new solution for a user), view (view user)?\nType quit to quit the app.");
            String userAction = AdminTools.scanner.nextLine();
            if (userAction.equals("add")) {
                addSolution();
            } else if (userAction.equals("view")) {
                viewUser();
            } else if(userAction.equals("quit")){
                AdminTools.closeApp();
            } else {
                System.out.println("Input incorrect, try again");
            }
        }
    }

    public static void addSolution() {
        User.loadAll();
        int userId =getIntFromUser(Type.USER_ID);
        Exercise.loadAll();
        int exerciseId = getIntFromUser(Type.EXERCISE_ID);
        Solution solution = new Solution(0, exerciseId,userId);
        solution.saveToDb();
    }


    public static void viewUser() {
        int id = getIntFromUser(Type.USER_ID);
        Solution.loadAllByUserID(id);
    }


    private static int getIntFromUser(Type type) {
        switch (type) {
            case USER_ID:
                System.out.println("Enter user id:");
                break;
            case EXERCISE_ID:
                System.out.println("Enter exercise id:");
                break;
        }
        while (!AdminTools.scanner.hasNextInt()){
            AdminTools.scanner.next();
            System.out.println("Wrong input - you have to enter a number");
        }
        return AdminTools.scanner.nextInt();
    }

    private enum Type{
        USER_ID,
        EXERCISE_ID
    }

}
