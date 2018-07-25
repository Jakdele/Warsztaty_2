package pl.coderslab.admin;

import pl.coderslab.model.Exercise;

public class ExerciseAdmin {

    static void exerciseOptions() {
        while (true) {
            Exercise.loadAll();
            System.out.println("What would you like to do: edit, add, or delete an exercise?\nType quit to quit the app.");
            String userAction = AdminTools.scanner.nextLine();
            if (userAction.equals("edit")) {
                editExercise();
            } else if (userAction.equals("add")) {
                addExercise();
            } else if (userAction.equals("delete")) {
                deleteExercise();
            } else if(userAction.equals("quit")){
                AdminTools.closeApp();
            } else {
                System.out.println("Input incorrect, try again");
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
