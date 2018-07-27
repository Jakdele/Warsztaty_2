package pl.coderslab.admin;

import pl.coderslab.model.Exercise;
import static pl.coderslab.admin.AdminTools.scanner;
import static pl.coderslab.admin.AdminTools.getInt;
import static pl.coderslab.admin.AdminTools.closeApp;

public class ExerciseAdmin {

    static void exerciseOptions() {
        while (true) {
            Exercise.loadAll();
            System.out.println("What would you like to do: \n(1) edit an existing exercise\n(2) add a new exercise\n(3) delete an exercise\n(0) quit the app");
            switch (getInt(scanner)) {
                case 1:
                    addExercise();
                    break;
                case 2:
                    editExercise();
                    break;
                case 3:
                    deleteExercise();
                    break;
                case 0:
                    closeApp();
                    break;
                default:
                    System.out.println("Incorrect input - try again.");
            }
        }
    }

    public static void editExercise(){
        int id= getID();
        String title = getStringFromUser(Type.TITLE);
        String description = getStringFromUser(Type.DESC);
        Exercise newExercise = new Exercise(id, title, description);
        newExercise.saveToDb();
    }

    public static void addExercise() {
        int id =0;
        String title = getStringFromUser(Type.TITLE);
        String description = getStringFromUser(Type.DESC);
        Exercise newExercise = new Exercise(id, title, description);
        newExercise.saveToDb();
    }

    public static void deleteExercise() {
        int id = getID();
        Exercise.delete(id);
    }


    private static String getStringFromUser (Type type){
        switch (type) {
            case TITLE:
                System.out.println("Enter new exercise title:");
                break;
            case DESC:
                System.out.println("Enter new exercise description:");
                break;

            default:
                break;
        }
        return AdminTools.scanner.nextLine();
    }

    public static int getID(){
        System.out.println("Enter exercise id: ");
        while(!AdminTools.scanner.hasNextInt()){
            AdminTools.scanner.next();
            System.out.println("Id has to be a number. \nEnter id: ");
        }
        int id = AdminTools.scanner.nextInt();
        AdminTools.scanner.nextLine();
        return id;
    }

    private enum Type{
        TITLE,
        DESC
    }

}
