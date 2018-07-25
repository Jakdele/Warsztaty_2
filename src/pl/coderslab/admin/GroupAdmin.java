package pl.coderslab.admin;

import pl.coderslab.model.Group;

public class GroupAdmin {

    static void groupOptions() {
        while (true) {
            Group.loadAll();
            System.out.println("What would you like to do: edit, add, or delete a group?\nType quit to quit the app.");
            String userAction = AdminTools.scanner.nextLine();
            if (userAction.equals("edit")) {
                editGroup();
            } else if (userAction.equals("add")) {
                addGroup();
            } else if (userAction.equals("delete")) {
                deleteGroup();
            } else if(userAction.equals("quit")){
                AdminTools.closeApp();
            } else {
                System.out.println("Input incorrect, try again");
            }
        }
    }

    public static void editGroup(){
        int id= getID();
        String name = getStringFromUser();
        Group newGroup = new Group(id, name);
        newGroup.saveToDb();
    }

    public static void addGroup() {
        int id =0;
        String name = getStringFromUser();
        Group newGroup = new Group(id, name);
        newGroup.saveToDb();
    }

    public static void deleteGroup() {
        int id = getID();
        Group.delete(id);
    }


    private static String getStringFromUser (){
        System.out.println("Enter group id: ");
        return AdminTools.scanner.nextLine();
    }
    public static int getID(){
        System.out.println("Enter group id: ");
        while(!AdminTools.scanner.hasNextInt()){
           AdminTools.scanner.next();
            System.out.println("Id has to be a number. \nEnter id: ");
        }
        int id = AdminTools.scanner.nextInt();
        AdminTools.scanner.nextLine();
        return id;
    }

}
