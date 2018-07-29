package pl.coderslab.programmingSchool.admin;

import pl.coderslab.programmingSchool.model.Exercise;
import pl.coderslab.programmingSchool.model.Solution;
import pl.coderslab.programmingSchool.model.User;
import static pl.coderslab.programmingSchool.admin.AdminTools.scanner;
import static pl.coderslab.programmingSchool.admin.AdminTools.getInt;

public class SolutionAdmin {
    static void solutionOptions() {
        while (true) {
            Solution.printAllSolutions();
            System.out.println("What would you like to do: \n(1) add (add new solution for a user)\n(2) view user with specified id\n(0) quit the app");
            switch (getInt(scanner)){
                case 1:
                    addSolution();
                    break;
                case 2:
                    viewUser();
                    break;
                case 0:
                    AdminTools.closeApp();
                    break;
                default:
                    System.out.println("Incorrect input - try again.");

            }
        }
    }

    public static void addSolution() {
        User.printAllUsers();
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
